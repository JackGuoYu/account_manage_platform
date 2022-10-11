package com.amp.config;


import com.amp.annotation.ApiExt;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModelProperty;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.IntegerMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Component
@Order
@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class SwaggerApiReaderConfig implements ParameterBuilderPlugin {
    private static final Logger log = LoggerFactory.getLogger(SwaggerApiReaderConfig.class);
    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        Class originClass = parameterContext.resolvedMethodParameter().getParameterType().getErasedType();


        Optional<ApiExt> optional = methodParameter.findAnnotation(ApiExt.class);
        if (optional.isPresent()) {
            Random random = new Random();
            String name = originClass.getSimpleName() + "Model" + random.nextInt(100);  //model 名称

            try {
                parameterContext.getDocumentationContext()
                        .getAdditionalModels()
                        .add(typeResolver.resolve(createRefModelIgp(optional.get(), originClass.getPackage()+"."+name, originClass)));  //像documentContext的Models中添加我们新生成的Class
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            parameterContext.parameterBuilder()  //修改Map参数的ModelRef为我们动态生成的class
                    .parameterType("body")
                    .modelRef(new ModelRef(name))
                    .name(name);
        }

    }

    private Class createRefModelIgp(ApiExt propertys, String name, Class origin) throws NotFoundException {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass( name);
            List<String> ignores =  Arrays.asList(propertys.ignore());
            List<String> requireds =  Arrays.asList(propertys.required());
            List<String> optionals =  Arrays.asList(propertys.optional());
            List<Field> fieldList = getAllField(origin);
            for (Field field : fieldList) {
                CtField ctField = new CtField(ClassPool.getDefault().get(field.getType().getName()), field.getName(), ctClass);
                ctField.setModifiers(Modifier.PUBLIC);
                ApiModelProperty ampAnno = getApiModelProperty(origin, field);
                String attributes = java.util.Optional.ofNullable(ampAnno).map(s->s.value()).orElse("");
                if (StringUtils.isNotBlank(attributes) ){ //添加model属性说明
                    ConstPool constPool = ctClass.getClassFile().getConstPool();
                    AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                    Annotation ann = new Annotation(ApiModelProperty.class.getName(), constPool);
                    if(StringUtils.isNotBlank(ampAnno.value()))
                        ann.addMemberValue("value", new StringMemberValue(ampAnno.value(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.name()))
                        ann.addMemberValue("name", new StringMemberValue(ampAnno.name(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.allowableValues()))
                        ann.addMemberValue("allowableValues", new StringMemberValue(ampAnno.allowableValues(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.access()))
                        ann.addMemberValue("access", new StringMemberValue(ampAnno.access(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.notes()))
                        ann.addMemberValue("notes", new StringMemberValue(ampAnno.notes(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.dataType()))
                        ann.addMemberValue("dataType", new StringMemberValue(ampAnno.dataType(), constPool));
                    if(ampAnno.position() != 0)
                        ann.addMemberValue("position", new IntegerMemberValue(ampAnno.position(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.example()))
                        ann.addMemberValue("example", new StringMemberValue(ampAnno.example(), constPool));
                    if(StringUtils.isNotBlank(ampAnno.reference()))
                        ann.addMemberValue("reference", new StringMemberValue(ampAnno.reference(), constPool));
                    if(ampAnno.allowEmptyValue())
                        ann.addMemberValue("allowEmptyValue", new BooleanMemberValue(ampAnno.allowEmptyValue(), constPool));
                    if(ignores.contains(field.getName())){
                        ann.addMemberValue("hidden", new BooleanMemberValue(true, constPool));
                    }else {
                        ann.addMemberValue("hidden", new BooleanMemberValue(ampAnno.hidden(), constPool));
                    }
                    if(requireds.contains(field.getName())){
                        ann.addMemberValue("required", new BooleanMemberValue(true,constPool));
                    } else if(optionals.contains(field.getName())){
                        ann.addMemberValue("required", new BooleanMemberValue(false,constPool));
                    }else{
                        ann.addMemberValue("required", new BooleanMemberValue(ampAnno.required(),constPool));
                    }
                    attr.addAnnotation(ann);
                    ctField.getFieldInfo().addAttribute(attr);
                }
                ctClass.addField(ctField);
            }
            return ctClass.toClass();
        } catch (Exception e) {
            log.error(String.format("name: %s, class: %s", name, origin.getName()) ,e);
            return null;
        }
    }

    private ApiModelProperty getApiModelProperty(Class origin, Field field){
        ApiModelProperty ampAnno = null;
        try {
            ampAnno = origin.getDeclaredField(field.getName()).getAnnotation(ApiModelProperty.class);
        } catch (NoSuchFieldException e) {
            origin = origin.getSuperclass();
            ampAnno = getApiModelProperty(origin, field);
        }
        return ampAnno;
    }

    private List<Field> getAllField(Class origin){
        List<Field> fieldList = new ArrayList<>();
        while (origin != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(origin.getDeclaredFields())));
            origin = origin.getSuperclass();
        }
        return fieldList;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

}
