package com.jesus.agentsdemo.controller;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agent") // 统一的父路径
public class AgentController {
    private final ReactAgent weatherAgent;

    public AgentController(ReactAgent weatherAgent) {
        this.weatherAgent = weatherAgent;
    }

    @GetMapping("/weather_agent/invoke/{session_id}/{query}")
    @ResponseBody
    public String invokeWeatherAgent(@PathVariable String session_id, @PathVariable String query) {
        try {
            RunnableConfig runnableConfig = RunnableConfig.builder()
                    .threadId(session_id)
                    .addMetadata("user_role", "superVip")// 此处添加了user_role元数据, 用于动态prompt
                    .build();
//            AssistantMessage response = weatherAgent.call(query, runnableConfig);
//            return response.getText();
            // 使用invoke方法获取完整动态
            Optional<OverAllState> result = weatherAgent.invoke(query, runnableConfig);
            if (result.isPresent()) {
                OverAllState overAllState = result.get();
                Optional<Message> messages = overAllState.value("messages");
                List<Message> messageList = (List<Message>)messages.get();
                return messageList.toString();
            }
            return "No response";
        } catch (Exception e) {
            // 添加异常处理逻辑
            return "Error: " + e.getMessage();
        }
    }
}
