package com.jesus.agentsdemo.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;

import java.time.LocalDate;

public class DayTimeTool implements Tool<DayTimeTool.DayTimeRequest, String> {
    // record 是 Java 16 引入的关键字，用来声明“只承载数据”的类，自动生成构造器、getter、equals/hashCode、toString。它非常适合当输入/输出的数据载体。
    public record DayTimeRequest() {}

    @Override
    public ToolCallback toolCallback() {
        return FunctionToolCallback
                .builder("get_local_date", new DayTimeTool())
                .description("Get local date for the server")
                .inputType(DayTimeRequest.class)
                .build();
    }

    @Override
    public String apply(
            DayTimeRequest request,
            ToolContext toolContext) {
        return LocalDate.now().toString();
    }
}
