package com.tt.ai.mcp_client.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author tantuo
 */
@Slf4j
@RequestMapping("/ai/mcp")
@RestController
public class McpClientController {

    private final ChatClient chatClient;

    private final List<McpSyncClient> mcpSyncClients;

    private final ToolCallingManager toolCallingManager;

    private final ToolCallback[] registeredTools;

    @Autowired
    public McpClientController(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients,
        ToolCallingManager toolCallingManager, SyncMcpToolCallbackProvider toolProvider) {

        this.chatClient = chatClientBuilder.defaultToolCallbacks(toolProvider)
            .defaultSystem("你是一个MCP小助手，可根据需求调用不同的MCP工具，优化回答效果").build();
        this.mcpSyncClients = mcpSyncClients;
        this.toolCallingManager = toolCallingManager;
        this.registeredTools = toolProvider.getToolCallbacks();
        Arrays.stream(registeredTools).toList().forEach(tool -> log
            .info("工具名称: " + tool.getToolDefinition().name() + ", 功能描述: " + tool.getToolDefinition().description()));
    }

    @GetMapping(value = "/tools")
    public ToolCallback[] getAllTools() {
        return this.registeredTools;
    }

    @GetMapping("/chat_stream")
    public Flux<Message> chatStream(@RequestParam String message) {
        Flux<ChatResponse> chatResponseFlux = chatClient.prompt().user(message).stream().chatResponse();

        log.info(">>> 流式输出...");
        log.info(">>> 问题：{}", message);

        Flux<Message> returnMessages = chatResponseFlux.map(chat -> chat.getResult().getOutput());

        return returnMessages;
    }
}
