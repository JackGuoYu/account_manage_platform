package com.amp.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(
            RedissonClient cacheClient,
                      @Qualifier("redisSerializerForValue") RedisSerializer redisSerializerForValue) {

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(new RedissonConnectionFactory(cacheClient));

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        //根据配置选择value的序列化工具
        template.setValueSerializer(redisSerializerForValue);
        template.setHashValueSerializer(redisSerializerForValue);
        template.afterPropertiesSet();
        return template;
    }

    @Bean("redisSerializerForValue")
    @ConditionalOnMissingBean(name = "redisSerializerForValue")
    public RedisSerializer jackson2JsonRedisSerializer(){
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        return jackson2JsonRedisSerializer;
    }

    @Bean(name = "redisStringTemplate")
    public RedisTemplate<String, String> redisStringTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setValueSerializer(stringSerializer);// value序列化
        redisTemplate.setHashValueSerializer(stringSerializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Primary
    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://"+redisConfigProperties.getAddress();
        SingleServerConfig serverConfig = config.useSingleServer().setAddress(address)
                .setTimeout(redisConfigProperties.getTimeout())
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(1)
                .setDatabase(redisConfigProperties.getDatabase());

        if (StringUtils.isNotEmpty(redisConfigProperties.getPassword())) {
            serverConfig.setPassword(redisConfigProperties.getPassword());
        }

        return Redisson.create(config);
    }

    //    redis cluster模式
//    @Primary
//    @Bean
//    public RedissonClient redissonClient() {
//        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        List<String> clusterNodes = new ArrayList<>();
//        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
//            clusterNodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
//        }
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//        clusterServersConfig.setTimeout(redisConfigProperties.getTimeout());
//
//        if (StringUtils.isNotEmpty(redisConfigProperties.getPassword())) {
//            clusterServersConfig.setPassword(redisConfigProperties.getPassword());
//        }
//        //设置密码
//        return Redisson.create(config);
//    }

}
