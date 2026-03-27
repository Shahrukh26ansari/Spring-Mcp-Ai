package com.queriai.config;

//import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates a named ChatClient bean for each supported AI provider.
 * Beans are only instantiated if their respective auto-configuration
 * activates (i.e. the API key / URL is configured).
 */
@Configuration
public class ProviderConfig {

//
//    @Bean("claudeChatClient")
//    public ChatClient claudeChatClient(AnthropicChatModel model) {
//        return ChatClient.create(model);
//    }
//
//    @Bean("openAiChatClient")
//    public ChatClient openAiChatClient(OpenAiChatModel model) {
//        return ChatClient.create(model);
//    }
//
//    @Bean("geminiChatClient")
//    public ChatClient geminiChatClient(VertexAiGeminiChatModel model) {
//        return ChatClient.create(model);
//    }

    @Bean("ollamaChatClient")
    public ChatClient ollamaChatClient(OllamaChatModel model) {
        return ChatClient.create(model);
    }
}
