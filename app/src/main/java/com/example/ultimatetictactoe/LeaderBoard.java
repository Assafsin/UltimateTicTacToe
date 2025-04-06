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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        lv = (ListView) findViewById(R.id.lvStati);
        btnBack = (ImageView)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        Resources res = getResources();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnBack.getId()) {
            Intent intent = new Intent();
            intent = new Intent(LeaderBoard.this, StartActivity.class);
            startActivity(intent);
        }
    }
}