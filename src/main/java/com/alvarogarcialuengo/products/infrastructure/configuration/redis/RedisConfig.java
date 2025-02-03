package com.alvarogarcialuengo.products.infrastructure.configuration.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import com.alvarogarcialuengo.products.domain.model.Currency;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean("reactiveRedisConnectionFactory")
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public ReactiveRedisOperations<String, Currency> redisOperations(
            @Qualifier("reactiveRedisConnectionFactory") ReactiveRedisConnectionFactory factory
    ) {
        Jackson2JsonRedisSerializer<Currency> serializer = new Jackson2JsonRedisSerializer<>(Currency.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Currency> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Currency> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
