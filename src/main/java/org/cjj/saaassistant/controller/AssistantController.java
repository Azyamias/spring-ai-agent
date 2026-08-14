package org.cjj.saaassistant.controller;

import org.cjj.saaassistant.tool.CustomTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class AssistantController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam(value = "msg", defaultValue = "你是谁") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .tools(new CustomTools())
                .stream().content();
    }
}
