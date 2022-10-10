package com.amp.utils.redis;

/**
 * redis key
 *
 * @author guoyu
 * @version 1.0
 * @date 2021/9/1 4:10 下午
 */
public interface RedisConstant {
    //分布式锁用lock后缀，存储值用key区分

    Long MONTH_TIME = 30 * 24 * 60 * 60L;

    Long DAY_TIME = 24 * 60 * 60L;

    Long HOUR_TIME = 60 * 60L;

    Long HALF_TIME = 12 * 60 * 60L;

    Long FIVE_MIN = 5 * 60L;

    Long THREE_MIN = 3 * 60L;

    Long MINUTE_TIME = 60L;

    Long TEN_MINUTE_TIME = 10 * MINUTE_TIME;

    Long THIRTY_MINUTE_TIME = 30 * MINUTE_TIME;

    Long TEN_SEC = 10L;

    Long FIVE_SEC = 5L;


    /**
     * jwt token存储key
     */
    String  JWT_TOKEN_KEY = "jwt:token:channelType:%s:user:%s:key";

    /**
     * 微信服务端token凭据
     */
    String WX_SERVER_API_TOKEN_KEY = "wx:server:api:token";

}
