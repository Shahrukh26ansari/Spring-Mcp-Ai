package com.queriai.model;

/**
 * Supported AI providers for SQL generation.
 */
public enum AiProvider {

    /** Anthropic Claude (claude-opus-4-5) */
    //CLAUDE("Anthropic Claude", "claude-opus-4-5"),

    /** OpenAI GPT-4o */
   // OPENAI("OpenAI GPT-4o", "gpt-4o"),

    /** Google Gemini 1.5 Pro */
   // GEMINI("Google Gemini 1.5 Pro", "gemini-1.5-pro"),

    /** Ollama — local open-source models (e.g. Llama 3.2) */
    OLLAMA("Ollama (Local)", "llama3.2");

    private final String displayName;
    private final String modelId;

    AiProvider(String displayName, String modelId) {
        this.displayName = displayName;
        this.modelId     = modelId;
    }

    public String getDisplayName() { return displayName; }
    public String getModelId()     { return modelId; }
}
