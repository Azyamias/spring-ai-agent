package org.cjj.saaassistant.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
public class AssistantController {

    @Value("classpath:/templates/prompt.txt")
    private Resource template;

    @Autowired
    private ChatClient chatClient;

    @GetMapping(
            value = "/chat",
            produces = "text/html;charset=utf-8"
    )
    public Flux<String> chat(@RequestParam(value = "msg", defaultValue = "你好") String msg) {
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of());
        return chatClient
                .prompt(prompt)
                .user(msg)
                .stream()
                .content();
    }
}
