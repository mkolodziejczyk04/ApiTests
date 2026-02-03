package org.example.api;

import org.example.requests.LoginRequest;
import org.example.pojos.Root;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST(".")
    Call<Root> sendPost(@Query("token") String token, @Query("key") String key, @Body LoginRequest body);

    @GET("{boardId}")
    Call<Root> sendGet(@Path("boardId") String boardId, @Query("token") String token, @Query("key") String key);

    @PUT("{boardId}")
    Call<Root> sendPut(@Path("boardId") String boardId, @Query("token") String token, @Query("key") String key, @Body LoginRequest body);

    @DELETE("{boardId}")
    Call<Root> sendDelete(@Path("boardId") String boardId, @Query("token") String token, @Query("key") String key);
}
