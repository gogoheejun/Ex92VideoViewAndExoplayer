package com.example.ex92videoviewandexoplayer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("/videos/videoData.json")
    Call<String> getVideoDatas();

}
