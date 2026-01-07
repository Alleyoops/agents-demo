package com.jesus.agentsdemo.constant;

public class OutputSchema {
    public static final String OUTPUT_SCHEMA_WEATHER = """
            请按以下JSON格式输出：
            {
              "title":"会话主题",
              "dayTime":"日期",
              "punnyResponse":"双关幽默回复",
              "weatherConditions":"天气状况"
            }
            """;
}
