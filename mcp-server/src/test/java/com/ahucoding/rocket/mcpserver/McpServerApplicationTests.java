package com.ahucoding.rocket.mcpserver;


import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;

class McpServerApplicationTests {

    public static void main(String[] args) {
        var transport = new HttpClientSseClientTransport("http://localhost:8080");
        new SampleClient(transport).run();
    }

}
