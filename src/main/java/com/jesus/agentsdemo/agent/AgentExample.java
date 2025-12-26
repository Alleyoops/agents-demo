package com.jesus.agentsdemo.agent;


import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.jesus.agentsdemo.llm.LlmClientRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.stream.DoubleStream;

@Component
@RequiredArgsConstructor
public class AgentExample {

    private final LlmClientRegistry llmClientRegistry;
    public void run() throws Exception {
// 创建模型实例
//        DashScopeApi dashScopeApi = DashScopeApi.builder()
//                .apiKey()
//                .build();
//
//        ChatModel chatModel = DashScopeChatModel.builder()
//                .dashScopeApi(dashScopeApi)
//                .build();

// 创建 Agent
//        ReactAgent agent = ReactAgent.builder()
//                .name("java_agent")
//                .model(llmClientRegistry.getChatModel("deepseek"))
//                .systemPrompt("You are a helpful java-development assistant.")
//                .build();

// 运行 Agent
//        AssistantMessage message = agent.call("who are you?");
        ChatModel model = llmClientRegistry.getChatModel("deepseek");
        // 提示词
        Prompt prompt = new Prompt("you are a java-development assistant");
        ChatResponse message = model.call(prompt);

        System.out.println(message);

    }
}