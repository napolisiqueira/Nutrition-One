package br.com.nutritionone.Nutrition_One.security;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Getter
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityConfig {
    public static String PREFIX;
    public static Key KEY;
    public static Long EXPIRATION;

    private String prefix;
    private String key;
    private Long expiration;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        SecurityConfig.PREFIX = prefix;
    }

    public void setKey(String key) {
        this.key = key;
        SecurityConfig.KEY = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
        SecurityConfig.EXPIRATION = expiration;
    }

}