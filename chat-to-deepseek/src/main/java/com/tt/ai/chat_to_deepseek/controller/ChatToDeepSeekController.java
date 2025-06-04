package com.tt.ai.chat_to_deepseek.controller;

import com.tt.ai.chat_to_deepseek.domain.ChatResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 与deepSeek聊天
 *
 * @author tantuo
 */
@Slf4j
@RequestMapping("/ai/deepSeek")
@RestController
public class ChatToDeepSeekController {

    private ChatClient chatClient;

    @Autowired
    public ChatToDeepSeekController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
//                .defaultSystem("你是用户平台、消息平台等基础平台业务的业务专家，可以根据问题做出专业和详细的回答")
                .build();
    }

    @GetMapping("/chat")
    public ChatResDTO chat(@RequestParam String message) {
        String content = chatClient.prompt().user(message).call().content();

        System.out.println(">>> 问题：" + message);
        System.out.println(">>> 回答：" + content);
        return new ChatResDTO(content);
    }
}
