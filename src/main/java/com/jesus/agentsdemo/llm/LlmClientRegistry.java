package com.jesus.agentsdemo.llm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;

/**
 * 模型实例注册中心，作为模型实例的工厂类（路由中转），根据provider名称获取对应的模型实例
 */
@Component
@RequiredArgsConstructor
public class LlmClientRegistry {

    private final DashScopeChatModel dashScopeChatModel;
    private final OpenAiChatModel deepseekChatModel;

    public ChatModel getChatModel(String llmProvider) {
        if (llmProvider == null ) {
            throw new IllegalArgumentException("provider is null. ");
        }
        return switch (llmProvider) {
            case "dashscope" -> dashScopeChatModel;
            case "deepseek" -> deepseekChatModel;
            default -> throw new IllegalArgumentException("暂不支持的 provider: " + llmProvider);
        };
    }

}
