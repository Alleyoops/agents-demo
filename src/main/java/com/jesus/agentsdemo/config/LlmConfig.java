package com.jesus.agentsdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 配置 没有通过起步依赖自动配置模型实例到容器的 模型实例
 * 比如说 deepseek、openai等
 * openai起步依赖（spring-ai-starter-model-openai）只能自动配置一个模型，所以需要多模型时需要手动配置各个模型实例，更加方便统一
 * 而诸如dashscope这种不依赖于openai起步依赖的直接用starter自动配置好的模型实例
 */

@Configuration
@RequiredArgsConstructor
public class LlmConfig {
    private final LlmProperties props;

    /*
     * 根据props 创建 需要的模型实例（没有通过starter自动配置的才需要），手动new出模型实例注入到 Spring 容器中
     */
}
