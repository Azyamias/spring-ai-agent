package org.cjj.saaassistant.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.memory.redis.RedisChatMemoryRepository;
import org.cjj.saaassistant.tool.CustomTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Value("${SAA_KEY}")
    private String apiKey;

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    private final String MODEL = "qwen3.7-max";

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(apiKey).build();
    }

    @Bean
    public RedisChatMemoryRepository redisChatMemoryRepository() {
        return RedisChatMemoryRepository.builder()
                .host(host)
                .port(port)
                .build();
    }

    @Bean
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(RedisChatMemoryRepository redisRepo) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(redisRepo)
                .maxMessages(20)
                .build();
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    public ChatClient chatClient(DashScopeApi dashScopeApi, CustomTools customTools, MessageChatMemoryAdvisor memoryAdvisor) {
        ChatModel model = DashScopeChatModel.builder().dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder().withModel(MODEL).build()).build();
        return ChatClient.builder(model)
                .defaultTools(customTools)
                .defaultAdvisors(memoryAdvisor)
                .build();
    }
}
