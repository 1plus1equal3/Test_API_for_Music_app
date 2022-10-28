package com.example.test_api_for_music_app.model;

import java.io.Serializable;

public class SongItem implements Serializable {
    private long id;
    private String title;
    private String permalink;

    public SongItem() {
    }

    public SongItem(long id, String title, String SC_link) {
        this.id = id;
        this.title = title;
        this.permalink = SC_link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}

