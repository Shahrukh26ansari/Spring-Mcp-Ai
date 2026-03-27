export interface QueryRequest {
  question: string;
  provider?: string;
}

export interface QueryResponse {
  question:             string;
  provider:             string;
  providerDisplayName:  string;
  columns:              string[];
  rows:                 Record<string, unknown>[];
  rowCount:             number;
  error?:               string;
}

export interface AiProvider {
  key:         string;   // CLAUDE | OPENAI | GEMINI | OLLAMA
  displayName: string;
  model:       string;
}
