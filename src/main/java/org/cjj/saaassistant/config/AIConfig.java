package org.cjj.saaassistant.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.cjj.saaassistant.tool.CustomTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Value("${SAA_KEY}")
    private String apiKey;

    private final String MODEL = "qwen3.7-max";

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder().apiKey(apiKey).build();
    }

    @Bean
    public ChatClient chatClient(DashScopeApi dashScopeApi, CustomTools customTools) {
        ChatModel model = DashScopeChatModel.builder().dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder().withModel(MODEL).build()).build();
        return ChatClient.builder(model)
                .defaultTools(customTools)
                .build();
    }
}
