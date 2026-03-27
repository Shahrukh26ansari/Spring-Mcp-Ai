package com.queriai.dto;

import com.queriai.model.AiProvider;
import lombok.Data;

@Data
public class QueryRequest {

    /** The natural language question to answer from the database. */
    private String question;

    /**
     * Which AI provider to use for SQL generation.
     * Defaults to the value in application.yml (queriai.default-provider).
     * Accepted values: CLAUDE, OPENAI, GEMINI, OLLAMA
     */
    private AiProvider provider;
}
