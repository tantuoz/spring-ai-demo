package com.tt.ai.mcp_server_db.config;

import com.tt.ai.mcp_server_db.service.DBQueryService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider jdbcQueryTools(DBQueryService dbQueryService) {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(dbQueryService)
                .build();
    }
}
