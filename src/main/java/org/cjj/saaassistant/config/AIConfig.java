package org.cjj.saaassistant.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AIConfig {

    @Value("")
    private String apiKey;

    private final String MODEL = "";

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(apiKey).build();
    }

    @Bean
    public ChatClient chatClient(DashScopeApi dashScopeApi, ChatMemory chatMemory) {
        ChatModel deepseekModel = DashScopeChatModel.builder().dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder().withModel(MODEL).build()).build();
        return ChatClient.builder(deepseekModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

}
