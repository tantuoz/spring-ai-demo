package com.tt.ai.mcp_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String chatPage() {
        return "mcp-client-chat";
    }
}