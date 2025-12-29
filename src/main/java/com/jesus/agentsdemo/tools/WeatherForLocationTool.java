package com.jesus.agentsdemo.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.function.FunctionToolCallback;

import java.util.function.BiFunction;

// 天气查询工具
public class WeatherForLocationTool implements Tool<String, String> {
    //Spring AI 的 FunctionToolCallback 支持通过 @ToolParam 注解添加元数据
    @Override
    public String apply(@ToolParam(description = "The city name") String city, ToolContext toolContext) {
        return "It's always sunny in " + city + "!";
    }

    @Override
    public ToolCallback toolCallback() {
        return FunctionToolCallback.builder("get_weather_for_location", this)
                .description("Get weather for a given city")
                .inputType(String.class)
                .build();
    }
}