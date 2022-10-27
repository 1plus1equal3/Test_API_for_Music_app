package com.example.test_api_for_music_app;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView songTitle;
    Button button, testSongBtn;
    ExoPlayer player;
    Uri uri;
    PlayerControlView playerControlView;
    boolean check = false;
    String[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declare id for view items
        songTitle = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        testSongBtn = findViewById(R.id.test_url);
        playerControlView = findViewById(R.id.player_control_view);
        //Generate player and player control view
        player = new ExoPlayer.Builder(this).build();
        playerControlView.setPlayer(player);
        //Add song id to String[] ids
        button.setOnClickListener(view -> {
            getSongId();
        });

        if (player.getPlaybackState() == ExoPlayer.STATE_ENDED) {
            player.release();
        }
        //Play music button
        testSongBtn.setOnClickListener(view -> {
            if (check) {
                player.prepare();
                if (player.isPlaying()) {
                    Log.e("State: ", "Stopped");
                    player.setPlayWhenReady(false);
                    player.getPlaybackState();
                } else {
                    Log.e("State: ", "Played");
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                }
            } else {
                Toast.makeText(this, "No source", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Call api to retrieve url of songs
    private void CookingPlayer(String track) {
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
                check = true;
            }

            @Override
            public void onFailure(Call<SongDetails> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSongId() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getSongId("https://soundcloud.com/kitelli/sets/the-masked-singer-vietnam");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                //Add all song id from api to String[] ids array!
                for (int i = 0; i < body.getTracks().getItems().length; i++) {
                    ids[i] = body.getTracks().getItems()[i].getId();
                    Log.e("Id of " + i + " song is: ", ids[i]);
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