package com.amp;

import com.amp.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author guoyu
 * @version 1.0
 * @date 2022/9/5 5:16 下午
 */
@Slf4j
@MapperScan("com.amp.mapper")
@ServletComponentScan
@SpringBootApplication
public class AmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmpApplication.class, args);
        log.info("\n\t" + "[amp-service] Swagger文档: http://{}:9111/amp/doc.html\n", IpUtils.getHostIp());
    }
}
