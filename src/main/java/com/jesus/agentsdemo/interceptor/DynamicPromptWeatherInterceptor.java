package com.jesus.agentsdemo.interceptor;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelInterceptor;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelRequest;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelResponse;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelCallHandler;
import org.springframework.ai.chat.messages.SystemMessage;

public class DynamicPromptWeatherInterceptor extends ModelInterceptor {
    @Override
    public ModelResponse interceptModel(ModelRequest request, ModelCallHandler handler) {
        // 基于上下文构建动态 system prompt
        String userRole = (String) request.getContext().getOrDefault("user_role", "default");
        String dynamicPrompt = switch (userRole) {
            case "vip" -> """
                    你正在与普通会员对话。
                    - 使用正常用语
                    - 平等交流
                    """;
            case "superVip" -> """
                    你正在与超级会员对话。
                    - 使用恭敬的语言
                    - 幽默又不失礼仪
                    """;
            default -> "你是一个专业的天气助手，保持友好和专业。";
        };

        SystemMessage enhancedSystemMessage;
        if (request.getSystemMessage() == null) {
            enhancedSystemMessage = new SystemMessage(dynamicPrompt);
        } else {
            enhancedSystemMessage = new SystemMessage(request.getSystemMessage().getText() + " " + dynamicPrompt);
        }

        ModelRequest modified = ModelRequest.builder(request)
                .systemMessage(enhancedSystemMessage)
                .build();
        return handler.call(modified);
    }

    @Override
    public String getName() {
        return "DynamicPromptWeatherInterceptor";
    }
}