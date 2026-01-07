package com.jesus.agentsdemo.constant;

public class OutputSchema {
    public static final String OUTPUT_SCHEMA_WEATHER = """
            请按以下JSON格式输出：
            {
              "title":"会话主题",
              "dayTime":"日期",
              "user_role":"用户会员等级英文标识，如vip、superVip",
              "punnyResponse":"双关幽默回复",
              "weatherConditions":"get_weather_for_location提供的天气状况（不得修改）"
            }
            """;
}
