package org.example.requests;

import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PutBoard {
    public CloseableHttpResponse sendPut(String id, String newBoardName) throws IOException {
        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");
        HttpPut put = new HttpPut(String.format("https://api.trello.com/1/boards/%s?key=%s&token=%s", id, key, token));

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("name", newBoardName));

        put.setEntity(new UrlEncodedFormEntity(urlParameters));


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            return httpClient.execute(put);
        }
    }
}
