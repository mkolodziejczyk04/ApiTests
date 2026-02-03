package org.example.requests;

import kong.unirest.json.JSONObject;

public class ApiResponse {
    private int statusCode;
    private JSONObject body;

    public ApiResponse(int statusCode, JSONObject body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public JSONObject getBody() {
        return body;
    }

    public void setBody(JSONObject body) {
        this.body = body;
    }
}
