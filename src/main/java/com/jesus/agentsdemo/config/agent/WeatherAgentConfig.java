package com.jesus.agentsdemo.config.agent;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.HookPosition;
import com.alibaba.cloud.ai.graph.agent.hook.messages.AgentCommand;
import com.alibaba.cloud.ai.graph.agent.hook.messages.MessagesModelHook;
import com.alibaba.cloud.ai.graph.agent.hook.modelcalllimit.ModelCallLimitHook;
import com.alibaba.cloud.ai.graph.checkpoint.savers.redis.RedisSaver;
import com.jesus.agentsdemo.constant.LlmProviders;
import com.jesus.agentsdemo.constant.OutputSchema;
import com.jesus.agentsdemo.constant.SystemPrompts;
import com.jesus.agentsdemo.interceptor.DynamicPromptWeatherInterceptor;
import com.jesus.agentsdemo.interceptor.LogToolInterceptor;
import com.jesus.agentsdemo.llm.LlmClientRegistry;
import com.jesus.agentsdemo.tool.DayTimeTool;
import com.jesus.agentsdemo.tool.WeatherForLocationTool;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//本质上是配置类，注入到ioc容器中
@Configuration
@RequiredArgsConstructor
public class WeatherAgentConfig {
     private final LlmClientRegistry llmClientRegistry;
     private final RedissonClient redissonClient;

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
         // 创建消息钩子（MessagesModelHook 只挂在“模型节点（AGENT_MODEL)”前后触发，不包含工具节点）
         MessagesModelHook messagesModelHook = new MessagesModelHook() {
             @Override
             public String getName() {
                 return "weather-agent-messages-hook";
             }

             @Override
             public HookPosition[] getHookPositions() {
                 return new HookPosition[]{HookPosition.BEFORE_MODEL, HookPosition.AFTER_MODEL};
             }

             @Override
             public AgentCommand afterModel(List<Message> previousMessages, RunnableConfig config) {
                 // 记录模型调用后的消息状态
                 System.out.println("Weather agent 模型调用完成");
                 return new AgentCommand(previousMessages);
             }

             @Override
             public AgentCommand beforeModel(List<Message> messages, RunnableConfig config) {
                 // 记录模型调用前的消息状态
                 System.out.println("Weather agent 准备调用模型");
                 return new AgentCommand(messages);
             }
         };

         return ReactAgent.builder()
                 .name("weather_agent")
                 .description("This is a weather agent")
                 .systemPrompt(SystemPrompts.WEATHER_AGENT_PROMPT)
                 .model(llmClientRegistry.getChatModel(LlmProviders.DASH_SCOPE))
                 .saver(RedisSaver.builder().redisson(redissonClient).build())
                 .tools(
                         new DayTimeTool().toolCallback(),
                         new WeatherForLocationTool().toolCallback()
                 )
//                 .outputType(WeatherResponseFormat.class) //推荐使用 outputType
                 .outputSchema(OutputSchema.OUTPUT_SCHEMA_WEATHER)
                 .hooks(hook,messagesModelHook)
                 .interceptors(new LogToolInterceptor(),new DynamicPromptWeatherInterceptor())
                 .build();
     }
}

