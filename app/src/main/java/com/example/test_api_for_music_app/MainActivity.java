package com.example.test_api_for_music_app;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView songTitle;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songTitle = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> getSongTitle());
    }

    private void getSongTitle() {
        Call<SongDetails> call = RetrofitClient.getInstance().getApi().getSongTitle();
        call.enqueue(new Callback<SongDetails>() {
            @Override
            public void onResponse(Call<SongDetails> call, Response<SongDetails> response) {
                SongDetails titleList = response.body();
                songTitle.setText(titleList.getTitle());
            }

            @Override
            public void onFailure(Call<SongDetails> call, Throwable t) {
                Log.e("Fail call: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error has occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}