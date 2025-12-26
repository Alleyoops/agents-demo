package com.jesus.agentsdemo;

import com.jesus.agentsdemo.agent.AgentExample;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AgentExampleTest {
    @Resource
    private AgentExample agentExample;
    @Test
    public void run() throws Exception {
        agentExample.run();
    }
}
