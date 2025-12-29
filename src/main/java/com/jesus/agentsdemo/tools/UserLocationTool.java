package com.jesus.agentsdemo.tools;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.function.FunctionToolCallback;

import java.util.Optional;

import static com.alibaba.cloud.ai.graph.agent.tools.ToolContextConstants.AGENT_CONFIG_CONTEXT_KEY;

public class UserLocationTool implements Tool<String,String>{

    @Override
    public ToolCallback toolCallback() {
        return FunctionToolCallback
                .builder("getUserLocation", new UserLocationTool())
                .description("Retrieve user location based on user ID")
                .inputType(String.class)
                .build();
    }

    @Override
    public String apply(
            @ToolParam(description = "User query") String query,
            ToolContext toolContext) {
        // 从上下文中获取用户信息
        String userId = "";// 假设用户ID为空
        if (toolContext != null && toolContext.getContext() != null) {
            RunnableConfig runnableConfig = (RunnableConfig) toolContext.getContext().get(AGENT_CONFIG_CONTEXT_KEY);
            Optional<Object> userIdObjOptional = runnableConfig.metadata("user_id");
            if (userIdObjOptional.isPresent()) {
                userId = (String) userIdObjOptional.get();
            }
        }
        if (userId == null) {
            userId = "1";// 假设用户ID为空时，默认用户1
        }
        return "1".equals(userId) ? "Florida" : "San Francisco";// 假设用户1在佛罗里达，其它在圣弗朗西斯科
    }
}
