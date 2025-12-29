package com.jesus.agentsdemo;

import com.jesus.agentsdemo.agent.ExampleAgent;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExampleAgentTest {
    @Resource
    private ExampleAgent exampleAgent;
    @Test
    public void run() throws Exception {
        exampleAgent.run();
    }
}
