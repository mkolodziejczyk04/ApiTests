package org.example.requests;

import kong.unirest.json.JSONObject;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class BoardClient {
    private CreateBoard createBoard = new CreateBoard();

    public JSONObject getJsonObject(CloseableHttpResponse response) throws IOException, ParseException {
        String result = EntityUtils.toString(response.getEntity());
        JSONObject json = new JSONObject(result);
        return json;
    }

    public ApiResponse createBoardAndGetJson(String boardName) throws IOException, ParseException {
        try(CloseableHttpResponse response = createBoard.sendPost(boardName)){
            JSONObject json = getJsonObject(response);
            return new ApiResponse(response.getCode(), json);
        }
    }

    public String getIdOfCreatedBoard(String boardName) throws IOException, ParseException {
        ApiResponse jsonAndCodeOfResponseOfCreatedBoard = createBoardAndGetJson(boardName);
        JSONObject jsonOfNewCreatedBoard = jsonAndCodeOfResponseOfCreatedBoard.getBody();
        return jsonOfNewCreatedBoard.getString("id");
    }
}
