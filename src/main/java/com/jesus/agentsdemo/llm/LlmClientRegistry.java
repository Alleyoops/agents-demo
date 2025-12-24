package com.jesus.agentsdemo.llm;

import com.jesus.agentsdemo.config.LlmProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;

@Component
@RequiredArgsConstructor
public class LlmClientRegistry {

    private final DashScopeChatModel dashScopeChatModel;
    private final OpenAiChatModel deepseekChatModel;

//    public LlmClientRegistry(DashScopeChatModel dashScopeChatModel,@Qualifier("deepseekChatModel") OpenAiChatModel deepseekChatModel) {
//        this.dashScopeChatModel = dashScopeChatModel;
//        this.deepseekChatModel = deepseekChatModel;
//    }

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
