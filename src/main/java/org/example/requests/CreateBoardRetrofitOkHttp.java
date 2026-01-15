package org.example.requests;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.example.api.ApiService;
import org.example.pojos.Root;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class CreateBoardRetrofitOkHttp {

    public Response sendPost(String name) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.trello.com/1/boards/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");

        ApiService api = retrofit.create(ApiService.class);


        LoginRequest requestBody = new LoginRequest(name);


        Call<Root> call = api.sendPost(token, key, requestBody);

        Response<Root> response = call.execute();

        return response;
    }

    static void main() throws IOException {
        CreateBoardRetrofitOkHttp createBoardRetrofitOkHttp = new CreateBoardRetrofitOkHttp();
        var response = createBoardRetrofitOkHttp.sendPost("New board name");
        System.out.println(response.code());
    }
}
