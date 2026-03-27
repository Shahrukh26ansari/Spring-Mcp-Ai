package com.queriai.dto;

import com.queriai.model.AiProvider;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class QueryResponse {

    private String     question;
    private AiProvider provider;
    private String     providerDisplayName;
    private List<String>              columns;
    private List<Map<String, Object>> rows;
    private int        rowCount;
    private String     error;

    public static QueryResponse success(String question,
                                        AiProvider provider,
                                        List<String> columns,
                                        List<Map<String, Object>> rows) {
        QueryResponse r = new QueryResponse();
        r.question            = question;
        r.provider            = provider;
        r.providerDisplayName = provider.getDisplayName();
        r.columns             = columns;
        r.rows                = rows;
        r.rowCount            = rows.size();
        return r;
    }

    public static QueryResponse failure(String question, AiProvider provider, String error) {
        QueryResponse r = new QueryResponse();
        r.question            = question;
        r.provider            = provider;
        r.providerDisplayName = provider != null ? provider.getDisplayName() : "Unknown";
        r.error               = error;
        return r;
    }
}
