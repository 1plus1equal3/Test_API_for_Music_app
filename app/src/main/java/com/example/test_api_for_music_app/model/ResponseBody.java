package com.example.test_api_for_music_app.model;

import com.google.gson.annotations.SerializedName;

public class ResponseBody {
    @SerializedName(("tracks"))
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }
}
