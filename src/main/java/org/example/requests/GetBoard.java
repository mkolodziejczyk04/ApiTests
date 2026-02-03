package org.example.requests;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;


public class GetBoard {
    public CloseableHttpResponse sendGet(String id) throws IOException {
        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");
        HttpGet request = new HttpGet(String.format("https://api.trello.com/1/boards/%s?key=%s&token=%s", id,
                key, token));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(request);
    }
}
