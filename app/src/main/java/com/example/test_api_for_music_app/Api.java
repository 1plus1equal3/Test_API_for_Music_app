package com.example.test_api_for_music_app;

import com.example.test_api_for_music_app.model.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {
    /*String BASE_URL = "https://jsonplaceholder.typicode.com/";*/
    String BASE_URL = "https://soundcloud-scraper.p.rapidapi.com/";
    /*@GET("posts")*/

    //Headers
    @Headers(
            {
                    "X-RapidAPI-Key: 4eaf7ff150msh5e6765b4d66afedp108270jsn0af9710bc796",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })
    @GET("v1/track/metadata?download=sq")
    Call<SongDetails> getSongUrl(@Query("track") Long track);

    @Headers(
            {
                    "X-RapidAPI-Key: 4eaf7ff150msh5e6765b4d66afedp108270jsn0af9710bc796",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })
    @GET("v1/playlist/tracks")
    Call<ResponseBody> getSongId(@Query("playlist") String playlist);
}
