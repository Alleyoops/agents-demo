package com.jesus.agentsdemo.agent;


import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.jesus.agentsdemo.llm.LlmClientRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;

import org.springframework.ai.chat.model.ToolContext;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ExampleAgent {

    private final LlmClientRegistry llmClientRegistry;
    public void run() throws Exception {
        ToolCallback weatherTool = FunctionToolCallback.builder("get_weather", new WeatherTool())
                .description("Get weather for a given city")
                .inputType(String.class)
                .build();
        // 创建 agent
        ReactAgent agent = ReactAgent.builder()
                .name("weather_agent")
                .model(llmClientRegistry.getChatModel("dashscope"))
                .tools(weatherTool)
                .systemPrompt("You are a helpful assistant")
                .saver(new MemorySaver())
                .build();
        // 运行 agent
        AssistantMessage response = agent.call("what is the weather in San Francisco");
        System.out.println(response.getText());
    }
    // 定义天气查询工具
    public class WeatherTool implements BiFunction<String, ToolContext, String> {
        @Override
        public String apply(String city, ToolContext toolContext) {
            return "It's always sunny in " + city + "!";
        }
    }
}