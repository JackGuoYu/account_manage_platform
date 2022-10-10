package com.amp.config;

import com.amp.utils.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * redis配置
 *
 * @author guoyu
 * @version 1.0
 * @date 2021/12/21 10:11 上午
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {

    private cluster cluster;
    private String host;
    private Integer port;
    private String address;
    private String password;
    private Integer timeout;
    private Integer database;


    public String getAddress(){
        if(StringUtils.isNotEmpty(this.host) && this.port != null){
            return String.format("%s:%s",this.host, this.port);
        }
        return this.address;
    }


    @Data
    public static class cluster {
        private List<String> nodes;

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }


}
