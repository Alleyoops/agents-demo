package com.jesus.agentsdemo.config.agent;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.Hook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.HumanInTheLoopHook;
import com.alibaba.cloud.ai.graph.agent.hook.hip.ToolConfig;
import com.alibaba.cloud.ai.graph.agent.hook.modelcalllimit.ModelCallLimitHook;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.jesus.agentsdemo.constant.LlmProviders;
import com.jesus.agentsdemo.constant.OutputSchema;
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
//         // 创建 hook
//         Hook humanInTheLoopHook = HumanInTheLoopHook.builder()
//                 .approvalOn("get_weather_for_location", ToolConfig.builder().description("Please confirm tool execution.")
//                         .build())
//                 .build();
        // 为防止无限循环，可以使用 ModelCallLimitHook 来限制模型调用次数
         ModelCallLimitHook hook = ModelCallLimitHook.builder()
                 .runLimit(5)  // 限制最多调用 5 次
                 .exitBehavior(ModelCallLimitHook.ExitBehavior.ERROR)  // 超出限制时抛出异常
                 .build();
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
//                 .outputType(ResponseFormat.class)
                 .outputSchema(OutputSchema.OUTPUT_SCHEMA_WEATHER)
                 .hooks(hook)
                 .interceptors(new LogToolInterceptor())
                 .build();
     }
}

