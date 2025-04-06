package com.example.ultimatetictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private Context context;
    private List<Player> players;

    public PlayerAdapter(Context context, List<Player> players) {
        super(context, 0, players);
        this.context = context;
        this.players = players;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Player player = players.get(position);

        TextView tvName = convertView.findViewById(android.R.id.text1);
        TextView tvScore = convertView.findViewById(android.R.id.text2);

        tvName.setText(player.getName());
        tvScore.setText("Score: " + player.getScore());

        return convertView;
    }
}
