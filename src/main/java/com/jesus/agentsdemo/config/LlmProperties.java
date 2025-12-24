package com.jesus.agentsdemo.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
 * 获取llm配置文件内容
 */
@Component
@ConfigurationProperties(prefix = "llm")
@Data
public class LlmProperties {

    /**
     * providers: dashscope / deepseek / openai ...
     */
    private Map<String, Provider> providers = new HashMap<>();

    @Data
    public static class Provider {
        private String apiKey;
        private String baseUrl;
    }

}
