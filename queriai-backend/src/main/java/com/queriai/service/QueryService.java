package com.queriai.service;

import com.queriai.dto.QueryResponse;
import com.queriai.model.AiProvider;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Routes natural-language questions to the selected AI provider,
 * generates SQL, executes it against MySQL, and returns structured results.
 */
@Service
public class QueryService {

//    private final ChatClient claudeClient;
//    private final ChatClient openAiClient;
//    private final ChatClient geminiClient;
    private final ChatClient ollamaClient;

    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
    private final SchemaService schemaService;

    @Value("${queriai.default-provider:OLLAMA}")
    private String defaultProvider;

    private static final Pattern SQL_PATTERN =
            Pattern.compile("```sql\\s*(.*?)\\s*```|(?i)(SELECT\\s.+)", Pattern.DOTALL);

    public QueryService(
//            @Qualifier("claudeChatClient") ChatClient claudeClient,
//            @Qualifier("openAiChatClient") ChatClient openAiClient,
//            @Qualifier("geminiChatClient") ChatClient geminiClient,
            @Qualifier("ollamaChatClient") ChatClient ollamaClient,
            org.springframework.jdbc.core.JdbcTemplate jdbcTemplate,
            SchemaService schemaService) {
//        this.claudeClient  = claudeClient;
//        this.openAiClient  = openAiClient;
//        this.geminiClient  = geminiClient;
        this.ollamaClient  = ollamaClient;
        this.jdbcTemplate  = jdbcTemplate;
        this.schemaService = schemaService;
    }

    // ── Public API ────────────────────────────────────────────────

    public QueryResponse ask(String question, AiProvider provider) {
        AiProvider resolved = (provider != null) ? provider : resolveDefault();

        try {
            String sql = generateSql(question, resolved);

            if (!sql.trim().toUpperCase().startsWith("SELECT")) {
                return QueryResponse.failure(question, resolved,
                        "Only SELECT queries are permitted.");
            }

            List<Map<String, Object>> rows    = jdbcTemplate.queryForList(sql);
            List<String>              columns = rows.isEmpty()
                    ? List.of()
                    : new ArrayList<>(rows.get(0).keySet());

            return QueryResponse.success(question, resolved, columns, rows);

        } catch (Exception e) {
            return QueryResponse.failure(question, resolved,
                    "Could not process your question: " + e.getMessage());
        }
    }

    // ── Private helpers ───────────────────────────────────────────

    private String generateSql(String question, AiProvider provider) {
        ChatClient client = switch (provider) {
//            case CLAUDE -> claudeClient;
//            case OPENAI -> openAiClient;
//            case GEMINI -> geminiClient;
            case OLLAMA -> ollamaClient;
        };

        String raw = client.prompt()
                .system(buildSystemPrompt())
                .user(question)
                .call()
                .content();

        return extractSql(raw);
    }

    private String buildSystemPrompt() {
        return """
                You are a MySQL expert assistant. Your ONLY job is to convert natural language questions
                into valid MySQL SELECT queries using the schema below.
                
                Rules:
                - Return ONLY the SQL query — no explanation, no markdown, no extra text.
                - Always use fully qualified column references when joining tables.
                - Never use DROP, DELETE, INSERT, UPDATE, or any DDL/DML — SELECT only.
                - If the question cannot be answered with the given schema, respond with:
                  SELECT 'Unable to answer with available data' AS message;
                
                Schema:
                """ + schemaService.getSchemaDescription();
    }

    private String extractSql(String response) {
        if (response == null || response.isBlank()) {
            throw new IllegalStateException("AI provider returned an empty response.");
        }
        Matcher matcher = SQL_PATTERN.matcher(response);
        if (matcher.find()) {
            String sql = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            if (sql != null && !sql.isBlank()) return sql.trim();
        }
        return response.trim();
    }

    private AiProvider resolveDefault() {
        try {
            return AiProvider.valueOf(defaultProvider.toUpperCase());
        } catch (IllegalArgumentException e) {
            return AiProvider.OLLAMA;
        }
    }
}
