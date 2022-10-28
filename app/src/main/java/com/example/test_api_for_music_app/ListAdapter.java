package com.example.test_api_for_music_app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test_api_for_music_app.model.SongItem;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    List<SongItem> list;
    TextView songName, songId, songLink;


    public ListAdapter(List<SongItem> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = View.inflate(viewGroup.getContext(), R.layout.song_item, null);
        songName = itemView.findViewById(R.id.song_name);
        songId = itemView.findViewById(R.id.song_id);
        songLink = itemView.findViewById(R.id.song_online_link);
        songName.setText(list.get(i).getTitle());
        songId.setText(String.valueOf(list.get(i).getId()));
        songLink.setText(list.get(i).getPermalink());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity2.prepareSong(i, context);
            }
        });
        return itemView;
    }
}
