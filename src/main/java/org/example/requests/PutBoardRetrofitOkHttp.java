package org.example.requests;

import org.example.api.ApiService;
import org.example.pojos.Root;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class PutBoardRetrofitOkHttp {
    public Response sendPut(String boardId, String newBoardName) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.trello.com/1/boards/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String key = System.getenv("TRELLO_KEY");
        String token = System.getenv("TRELLO_TOKEN");

        ApiService api = retrofit.create(ApiService.class);

        LoginRequest requestBody = new LoginRequest(newBoardName);

        Call<Root> call = api.sendPut(boardId,token, key, requestBody);

        Response<Root> response = call.execute();

        return response;
    }

    static void main() throws IOException {
        PutBoardRetrofitOkHttp putBoardRetrofitOkHttp = new PutBoardRetrofitOkHttp();
        Response response = putBoardRetrofitOkHttp.sendPut("6968c8f6b03b5890b1e6760e", "newBoardName");
        System.out.println(response.code());
    }
}
