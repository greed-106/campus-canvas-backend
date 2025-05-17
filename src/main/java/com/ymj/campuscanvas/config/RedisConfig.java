package com.ymj.campuscanvas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 使用String序列化器（避免二进制存储）
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));  // 使用 JSON 序列化

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}

/*
两者的本质区别
|                      | `RedisTemplate<String, Object>`        | `StringRedisTemplate`                     |
|----------------------|----------------------------------------|-------------------------------------------|
| 继承关系         | 父类：`RedisTemplate<K, V>`           | 子类：`RedisTemplate<String, String>`     |
| 默认序列化器     | `JdkSerializationRedisSerializer`      | `StringRedisSerializer`（键值均为字符串） |
| 数据兼容性       | 二进制存储（默认不可读）               | 纯字符串存储（可读）                      |
| 典型场景         | 存储复杂对象（如 JSON、Java 对象）     | 存储简单字符串（如计数器、验证码）        |

• `RedisTemplate` 的 JSON 序列化：

  不是另一种 `StringRedisTemplate`，而是通过 JSON 格式在对象与字符串之间架设桥梁，解决对象存储问题。

• `StringRedisTemplate` 的必要性：

  显式声明是 Spring 配置的最佳实践，确保组件明确性，避免隐式行为导致的意外错误。

• 实际开发建议：

  同时保留两者配置，根据场景选择使用：
  • 需要操作对象 → `RedisTemplate`

  • 需要操作纯字符串 → `StringRedisTemplate`

 */