package src.client.service;

import java.util.HashMap;

public class Query {
    HashMap<QueryParameter, String> parameters;
    QueryType type;

    public Query() {
        parameters = new HashMap<>();
    }

    public void setParameter(QueryParameter parameter, String value) {// ハッシュマップにパラメータを設定
        if (value == null) {
            parameters.remove(parameter);
        } else {
            parameters.put(parameter, value);
        }
    }

    public void setType(QueryType type) {// クエリのタイプを設定
        this.type = type;
    }

    private String generateParamString() {
        return parameters.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).reduce((a, b) -> a + "&" + b)
                .orElse("");
    }

    public void sendQueryToServer() throws Exception {// クエリをサーバーに送信
        System.out.println("Query type: " + type);
        String query = type + "?" + generateParamString();
        System.out.println("Query sent to server: " + query);
    }
}
