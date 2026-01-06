package com.jesus.agentsdemo.config.agent;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.jesus.agentsdemo.constant.LlmProviders;
import com.jesus.agentsdemo.constant.SystemPrompts;
import com.jesus.agentsdemo.format.ResponseFormat;
import com.jesus.agentsdemo.interceptor.LogToolInterceptor;
import com.jesus.agentsdemo.llm.LlmClientRegistry;
import com.jesus.agentsdemo.tools.DayTimeTool;
import com.jesus.agentsdemo.tools.WeatherForLocationTool;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//本质上是配置类，注入到ioc容器中
@Configuration
@RequiredArgsConstructor
public class WeatherAgentConfig {
     private final LlmClientRegistry llmClientRegistry;

     @Bean
     public ReactAgent weatherAgent() {
         return ReactAgent.builder()
                 .name("weather_agent")
                 .description("This is a weather agent")
                 .systemPrompt(SystemPrompts.WEATHER_AGENT_PROMPT)
                 .model(llmClientRegistry.getChatModel(LlmProviders.DASH_SCOPE))
                 .saver(new MemorySaver())
                 .tools(
                         new DayTimeTool().toolCallback(),
                         new WeatherForLocationTool().toolCallback()
                 )
                 .outputType(ResponseFormat.class)
                 .interceptors(new LogToolInterceptor())
                 .build();
     }
}

