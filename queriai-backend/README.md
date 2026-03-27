# QueryAI — Multi-Provider NL-to-SQL Backend

Spring Boot backend that converts natural-language questions into MySQL SELECT queries
using your choice of AI provider: **Claude**, **GPT-4o**, **Gemini**, or **Ollama (local)**.

---

## Supported AI Providers

| Key      | Provider              | Model             | Needs API Key? |
|----------|-----------------------|-------------------|----------------|
| `CLAUDE` | Anthropic Claude      | claude-opus-4-5   | Yes            |
| `OPENAI` | OpenAI                | gpt-4o            | Yes            |
| `GEMINI` | Google Gemini         | gemini-1.5-pro    | GCP project    |
| `OLLAMA` | Ollama (local)        | llama3.2          | No — free      |

---

## Stack

| Layer     | Tech                        |
|-----------|-----------------------------|
| Framework | Spring Boot 3.3             |
| AI        | Spring AI 1.0 (multi-model) |
| Database  | MySQL 8+                    |
| Java      | 21                          |

---

## Project Structure

```
src/main/java/com/queriai/
├── QueryAiApplication.java
├── config/
│   ├── CorsConfig.java          # CORS for Angular dev
│   └── ProviderConfig.java      # Registers one ChatClient per provider
├── controller/
│   └── QueryController.java     # POST /api/query  |  GET /api/providers
├── dto/
│   ├── QueryRequest.java        # { question, provider }
│   └── QueryResponse.java       # { columns, rows, rowCount, provider, error }
├── model/
│   └── AiProvider.java          # Enum: CLAUDE, OPENAI, GEMINI, OLLAMA
└── service/
    ├── QueryService.java         # Routes to provider → generates SQL → executes
    └── SchemaService.java        # DB schema fed to AI as system context
```

---

## Setup

### 1. MySQL
Spring Boot auto-runs `schema.sql` + `data.sql` on startup.
Update credentials in `application.yml`:
```yaml
spring.datasource.username: root
spring.datasource.password: your_password
```

### 2. API Keys (set only what you want to use)
```bash
export ANTHROPIC_API_KEY=sk-ant-...
export OPENAI_API_KEY=sk-...
export GOOGLE_CLOUD_PROJECT=your-gcp-project-id   # for Gemini
```
Ollama needs no key — just install it: https://ollama.com

### 3. Run
```bash
./mvnw spring-boot:run
```

---

## API

### POST /api/query
```json
// Request
{
  "question": "Who are the top 5 customers by total spend?",
  "provider": "CLAUDE"
}

// Response
{
  "question": "Who are the top 5 customers by total spend?",
  "provider": "CLAUDE",
  "providerDisplayName": "Anthropic Claude",
  "columns": ["name", "total_spend"],
  "rows": [
    { "name": "Alice Johnson", "total_spend": 3089.97 }
  ],
  "rowCount": 5
}
```

`provider` is optional — defaults to `queriai.default-provider` in `application.yml` (CLAUDE).

### GET /api/providers
Returns all available providers for the Angular dropdown:
```json
[
  { "key": "CLAUDE", "displayName": "Anthropic Claude",    "model": "claude-opus-4-5" },
  { "key": "OPENAI", "displayName": "OpenAI GPT-4o",       "model": "gpt-4o" },
  { "key": "GEMINI", "displayName": "Google Gemini 1.5 Pro","model": "gemini-1.5-pro" },
  { "key": "OLLAMA", "displayName": "Ollama (Local)",       "model": "llama3.2" }
]
```

### GET /api/health
```
QueryAI backend is running
```

---

## Switching Default Provider
In `application.yml`:
```yaml
queriai:
  default-provider: OPENAI   # CLAUDE | OPENAI | GEMINI | OLLAMA
```
