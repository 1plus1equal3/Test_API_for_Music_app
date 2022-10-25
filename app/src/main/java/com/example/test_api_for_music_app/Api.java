package com.example.test_api_for_music_app;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    /*String BASE_URL = "https://jsonplaceholder.typicode.com/";*/
    String BASE_URL = "https://shazam.p.rapidapi.com/";
    /*@GET("posts")*/


    @GET("songs/get-details?key=40333609")
    @Headers(
            {
                    "X-RapidAPI-Key: 9a3600aff0mshb09b2849f51a904p170ca6jsn9741a4d51e4b",
                    "X-RapidAPI-Host: shazam.p.rapidapi.com"
            })
    /*?key=40333609&locale=en-US*/
    Call<SongDetails> getSongTitle();
}
