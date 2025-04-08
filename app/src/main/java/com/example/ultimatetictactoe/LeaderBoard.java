package com.example.ultimatetictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity implements View.OnClickListener {

    // list view of scores
    private ListView lv;
    //go back to mainMenu
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leader_board);

        lv = findViewById(R.id.lvStati);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        // Load leaderboard
        DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<Player> players = db.getAllPlayers();

        // Custom adapter
        PlayerAdapter adapter = new PlayerAdapter(this, players);
        lv.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == btnBack.getId()) {
            finish();
        }
    }
}