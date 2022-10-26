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
        //Set up song url and add MediaItem to player
        button.setOnClickListener(view -> {
            getSongTitle();
        });

        if(player.getPlaybackState() == ExoPlayer.STATE_ENDED){
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
    private void getSongTitle() {
        Call<SongDetails> call = RetrofitClient.getInstance().getApi().getSongUrl("https://soundcloud.com/huynguyen0503/anh-chua-thuong-em-den-vay-dau");
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
}