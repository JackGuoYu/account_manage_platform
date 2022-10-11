package com.amp.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.amp.annotation.Lock;
import com.amp.domain.Result;
import com.amp.enums.ResultCodeEnum;
import com.amp.exception.RedisLockException;
import com.amp.utils.ServletUtils;
import com.amp.utils.redis.RedisLockUtil;
import io.lettuce.core.RedisException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 分布式锁切面处理
 *
 * @author guoyu
 */
@Aspect
@Component
public class LockAspect
{
    private static final Logger log = LoggerFactory.getLogger(LockAspect.class);

    @Autowired
    private RedisLockUtil redisLockUtil;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @Around(value = "@annotation(com.amp.annotation.Lock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return handleLock(joinPoint);
    }

    public Object handleLock(final ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            Lock lock = getAnnotationLock(joinPoint);
            String lockKey = "";
            if(isAccess(joinPoint)){ //精细化控制，通过则执行分布式锁，不通过则放行
                String[] fields = lock.fields();
                String key = lock.keyName();
                String val = getVal(joinPoint,fields);
                lockKey = key + val;
                log.info("请求接口锁住对象{}",lockKey);
                return doLock(joinPoint,lock,lockKey);
            }else{ //执行防重复提交
                lockKey = getDefaultKey(joinPoint);
                return doLock(joinPoint,lock,lockKey);
            }

        }catch (RedisLockException e) {
            log.warn("获取不到锁 msg:{}", e.getMessage());
            return Result.fail(ResultCodeEnum.RETRY_ERROR);
        }catch (RedisException exp) {
            // 记录本地异常日志
            log.error("==分布式锁环绕通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

    /**
     * 默认生成防重复提交key
     * @param joinPoint
     * @return
     */
    public String getDefaultKey(ProceedingJoinPoint joinPoint){
         //生成key，请求路径+请求参数
        JSONObject keyJson = new JSONObject();
        keyJson.put("url", ServletUtils.getRequest().getRequestURL());
        keyJson.put("request_method", ServletUtils.getRequest().getMethod());
        JSONObject jsonObject = getRequestParam(joinPoint);
        if(joinPoint != null){
            keyJson.put("post_param", jsonObject);
        }
        Map<String, String[]> urlParam = ServletUtils.getRequest().getParameterMap();
        if(urlParam != null){
            keyJson.put("url_param", urlParam);
        }
        return keyJson.toJSONString();
    }

    /**
     * 执行锁操作
     * @param joinPoint
     * @param lock
     * @param lockKey
     * @return
     * @throws Throwable
     */
    public Object doLock(ProceedingJoinPoint joinPoint, Lock lock, String lockKey) throws Throwable{
        try (RedisLockUtil.RLockContainer lockContainer = redisLockUtil.getLockContainer(lockKey)) {
            lockContainer.tryLock(lock.time(), lock.timeType());
            return joinPoint.proceed(joinPoint.getArgs());
        }
    }


    /**
     * 切入点参数校验
     * @param joinPoint
     * @return
     */
    private boolean isAccess(ProceedingJoinPoint joinPoint){
        try {
            // 获得注解
            Lock lock = getAnnotationLock(joinPoint);
            if (lock == null) {
                return false;
            }

            String[] fields = lock.fields();
            if(fields == null || fields.length == 0){
                return false;
            }

            String key = lock.keyName();
            if(StringUtils.isEmpty(key)){
                return false;
            }

            String val = getVal(joinPoint,fields);
            if(StringUtils.isEmpty(val)){
                return false;
            }

        }catch (Exception e){
            log.error("分布式锁参数校验异常 e:{}",e);
        }
        return true;
    }

    /**
     * 获取请求参数
     * @param joinPoint
     * @return
     */
    private JSONObject getRequestParam(JoinPoint joinPoint){
        JSONObject json = null;
        if(joinPoint != null){
           Object[] args = joinPoint.getArgs();
           if(args != null && args.length>0){
               Object obj =  JSONObject.toJSON(args[0]);
               if(obj instanceof JSONObject){
                   json = (JSONObject)obj;
               }
           }
        }
        return json;
    }

    /**
     * 获取指定的动态参数
     * @param joinPoint
     * @param fields
     * @return
     */
    private String getVal(JoinPoint joinPoint, String[] fields){
        JSONObject jsonObject = getRequestParam(joinPoint);
        StringBuffer val = new StringBuffer();
        if(jsonObject != null){
            for(String field : fields){
                Object obj = jsonObject.get(field);
                if(obj != null){
                    val.append(":"+obj.toString());
                }else{
                    String param = ServletUtils.getRequest().getHeader(field);
                    if(StringUtils.isNotEmpty(param)){
                        val.append(":"+param);
                    }
                }
            }
        }else{
            for(String field : fields){
                String param = ServletUtils.getRequest().getParameter(field);
                if(StringUtils.isNotEmpty(param)){
                    val.append(":"+param);
                }else{
                    param = ServletUtils.getRequest().getHeader(field);
                    if(StringUtils.isNotEmpty(param)){
                        val.append(":"+param);
                    }
                }
            }
        }

        return val.toString();
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private Lock getAnnotationLock(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(Lock.class);
        }
        return null;
    }
}
