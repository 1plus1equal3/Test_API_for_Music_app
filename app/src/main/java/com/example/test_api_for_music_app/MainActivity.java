package com.example.test_api_for_music_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_api_for_music_app.model.ResponseBody;
import com.example.test_api_for_music_app.model.SongItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<SongItem> songItemList = new ArrayList<>();
    TextView songTitle;
    Button button, testSongBtn;
    List<Long> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declare id for view items
        songTitle = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        testSongBtn = findViewById(R.id.test_url);
        //Add song id to String[] ids
        button.setOnClickListener(view -> getSongId());
        //Play music button
        testSongBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        });
    }


   /* Call api to retrieve url of songs

   private void CookingPlayer(Long track) {
        Call<SongDetails> call = RetrofitClient.getInstance().getApi().getSongUrl(track);
        call.enqueue(new Callback<SongDetails>() {
            @Override
            public void onResponse(Call<SongDetails> call, Response<SongDetails> response) {
                SongDetails songDetails = response.body();
                Log.e("Url: ", songDetails.getAudio()[0].getUrl());
                String url = songDetails.getAudio()[0].getUrl();
                uri = Uri.parse(url);
                MediaItem item = MediaItem.fromUri(uri);
                player.addMediaItem(item);
                player.prepare();
                player.setPlayWhenReady(true);
                player.getPlaybackState();
            }

            @Override
            public void onFailure(Call<SongDetails> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void getSongId() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getSongId("https://soundcloud.com/kitelli/sets/the-masked-singer-vietnam");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                Log.d("", "onResponse: " + body);
                //Add all song id from api to String[] ids array!
                for (int i = 0; i < body.getTracks().getItems().length; i++) {
                    ids.add(i, body.getTracks().getItems()[i].getId());
                    //add info to songItemList
                    songItemList.add(i, new SongItem(ids.get(i), body.getTracks().getItems()[i].getTitle(), body.getTracks().getItems()[i].getPermalink()));
                    Log.e("Id of " + i + " song is: ", String.valueOf(ids.get(i)));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
