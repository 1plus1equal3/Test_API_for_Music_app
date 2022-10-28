package com.example.test_api_for_music_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test_api_for_music_app.model.SongItem;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    ListView list;
    PlayerControlView controller;
    private static List<SongItem> songItems = new ArrayList<>();
    private static ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Get view's ids
        list = findViewById(R.id.music_list);
        songItems = MainActivity.songItemList;
        controller = findViewById(R.id.control_view);
        //Set up Listview
        ListAdapter adapter = new ListAdapter(songItems, this);
        list.setAdapter(adapter);
        //Set up player
        player = new ExoPlayer.Builder(this).build();
        controller.setPlayer(player);
    }

    public static void prepareSong(int position, Context context){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CookingPlayer(songItems.get(position).getId(), context);
                player.setPlayWhenReady(true);
                player.getPlaybackState();
            }
        }, 1000);
    }

    private static void CookingPlayer(Long track, Context context) {
        Call<SongDetails> call = RetrofitClient.getInstance().getApi().getSongUrl(track);
        call.enqueue(new Callback<SongDetails>() {
            @Override
            public void onResponse(Call<SongDetails> call, Response<SongDetails> response) {
                SongDetails songDetails = response.body();
                Log.e("Url: ", songDetails.getAudio()[0].getUrl());
                String url = songDetails.getAudio()[0].getUrl();
                Uri uri = Uri.parse(url);
                MediaItem item = MediaItem.fromUri(uri);
                player.setMediaItem(item);
                player.prepare();
                Log.e("Player: ", "Started");
            }

            @Override
            public void onFailure(Call<SongDetails> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                Toast.makeText(context, "Error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

