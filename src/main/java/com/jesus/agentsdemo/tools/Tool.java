package com.jesus.agentsdemo.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;

import java.util.function.BiFunction;
// 工具接口
public interface Tool<I, O> extends BiFunction<I, ToolContext, O> {

    ToolCallback toolCallback();

}
