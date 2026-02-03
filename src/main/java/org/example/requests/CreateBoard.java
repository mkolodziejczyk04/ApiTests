package org.example.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.example.pojos.Root;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateBoard {

    public CloseableHttpResponse sendPost(String boardName) throws IOException, ParseException {
        HttpPost post = new HttpPost("https://api.trello.com/1/boards/");

        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("name", boardName));
        urlParameters.add(new BasicNameValuePair("key", key));
        urlParameters.add(new BasicNameValuePair("token", token));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(post);
    }

    public void createAnObject(String input) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Root root = om.readValue(input, Root.class);
    }
}
