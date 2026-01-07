package com.jesus.agentsdemo.tool;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.ai.tool.function.FunctionToolCallback;


// 天气查询工具
public class WeatherForLocationTool implements Tool<WeatherForLocationTool.WeatherForLocationRequest, String> {
    //Spring AI 的 FunctionToolCallback 支持通过 @ToolParam 注解添加元数据

    public record WeatherForLocationRequest(String city) {
    }
    @Override
    public String apply(@ToolParam(description = "The city name") WeatherForLocationRequest request, ToolContext toolContext) {
        return "It's always sunny in " + request.city + "!";
    }

    @Override
    public ToolCallback toolCallback() {
        return FunctionToolCallback.builder("get_weather_for_location", this)
                .description("Get weather for a given city")
                .inputType(WeatherForLocationRequest.class)
                .build();
    }
}