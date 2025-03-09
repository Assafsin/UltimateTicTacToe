package com.example.ultimatetictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoadActivity extends AppCompatActivity {


    final int TIME1=500;//להפןך את ה500 ל1 בשביל לקצר
    final int TIME2=6000;//להפוך את ה6000 ל1 בשביל לקצר
    private ImageView t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        t1 = (ImageView) findViewById(R.id.img1);
        t2 = (ImageView) findViewById(R.id.img2);
        t3 = (ImageView) findViewById(R.id.img3);



        //animation of appearing and disappearing of the buttons, like in the game
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
            }
        }, 3*TIME1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t3.setVisibility(View.VISIBLE);
            }
        }, 4*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t2.setVisibility(View.VISIBLE);
            }
        }, 5*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.VISIBLE);
            }
        }, 6*TIME1);

        //another round of the animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                t3.setVisibility(View.INVISIBLE);
            }
        }, 7*TIME1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t3.setVisibility(View.VISIBLE);
            }
        }, 8*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t2.setVisibility(View.VISIBLE);
            }
        }, 9*TIME1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setVisibility(View.VISIBLE);
            }
        }, 10*TIME1);


        // moving to main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),StartActivity.class);
                finish();//close this activity
                startActivity(i);
            }
        }, TIME2);
    }
}