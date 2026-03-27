package com.queriai.controller;

import com.queriai.dto.QueryRequest;
import com.queriai.dto.QueryResponse;
import com.queriai.model.AiProvider;
import com.queriai.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * POST /api/query
     * {
     *   "question": "Top 5 customers by spend",
     *   "provider": "CLAUDE"          // optional — CLAUDE | OPENAI | GEMINI | OLLAMA
     * }
     */
    @PostMapping("/query")
    public ResponseEntity<QueryResponse> query(@RequestBody QueryRequest request) {
        if (request.getQuestion() == null || request.getQuestion().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(QueryResponse.failure("", null, "Question must not be empty."));
        }
        QueryResponse response = queryService.ask(
                request.getQuestion().trim(),
                request.getProvider()
        );
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/providers
     * Returns all supported AI providers with display names and model IDs.
     * Angular uses this to populate the provider selector dropdown.
     */
    @GetMapping("/providers")
    public ResponseEntity<List<Map<String, String>>> providers() {
        List<Map<String, String>> list = Arrays.stream(AiProvider.values())
                .map(p -> Map.of(
                        "key",         p.name(),
                        "displayName", p.getDisplayName(),
                        "model",       p.getModelId()
                ))
                .toList();
        return ResponseEntity.ok(list);
    }

    /**
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("QueryAI backend is running");
    }
}
