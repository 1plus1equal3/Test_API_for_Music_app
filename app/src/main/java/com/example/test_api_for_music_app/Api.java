package com.example.test_api_for_music_app;

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
                    "X-RapidAPI-Key: 9a3600aff0mshb09b2849f51a904p170ca6jsn9741a4d51e4b",
                    "X-RapidAPI-Host: soundcloud-scraper.p.rapidapi.com"
            })

    @GET("v1/track/metadata?download=sq")
    Call<SongDetails> getSongUrl(@Query("track") String track);

    @GET("v1/playlist/tracks")
    Call<ResponseBody> getSongId(@Query("playlist") String playlist);
}
