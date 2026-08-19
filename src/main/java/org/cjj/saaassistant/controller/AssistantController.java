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
import org.springframework.http.MediaType;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
public class AssistantController {

    @Value("classpath:/templates/prompt.txt")
    private Resource template;

    @Autowired
    private ChatClient chatClient;

    @GetMapping(
            value = "/chat",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> chat(
            @RequestParam String msg,
            @RequestParam String convId
    ) {

        return chatClient
                .prompt()
                .user(msg)
                .stream()
                .content();
    }
}
