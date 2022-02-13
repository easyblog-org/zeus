package top.easyblog.titan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * redis lua 脚本加载配置 ，脚本位置：classpath:resources/scripts/
 *
 * @author: frank.huang
 * @date: 2022-02-12 23:45
 */
@Configuration
public class RedisLuaConfigurer {


    @Bean
    public DefaultRedisScript<String> loginRedisScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/login.lua")));
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<String> logoutRedisScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/logout.lua")));
        return redisScript;
    }
}
