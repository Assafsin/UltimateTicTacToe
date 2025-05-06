package com.example.ultimatetictactoe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GameManager gManager;

    String userX;
    String userO;

    private ImageView[][] gameBoard;
    private ImageButton[][] gameButtons;
    private Button musicBtn, backBtn;

    private Dialog dialog;
    private Button reStart;
    private TextView winnerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gameButtons = new ImageButton[3][3];
        gameBoard = new ImageView[9][9];
        gManager = new GameManager();

        userO = getIntent().getStringExtra("DATA1");
        userX = getIntent().getStringExtra("DATA2");

        musicBtn = (Button) findViewById(R.id.btnMusic);
        musicBtn.setOnClickListener(this);

        backBtn = (Button) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(this);

        String str;
        int resID;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                str = "imageView" + i + j;
                resID = getResources().getIdentifier(str, "id", getPackageName());
                gameBoard[i][j] = findViewById(resID);
            }
        }

        for (int i = 0; i < gameButtons.length; i++) {
            for (int j = 0; j < gameButtons[i].length; j++) {
                str = "imageButton" + i + j;
                resID = getResources().getIdentifier(str, "id", getPackageName());
                gameButtons[i][j] = findViewById(resID);
                gameButtons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == musicBtn.getId())
            startActivity(new Intent(MainActivity.this, MusicListActivity.class));
        if (view.getId() == backBtn.getId()) {
            finish();
        }
        else{
            if (!gManager.getGameEnded()) {
                int column = 0;
                int row = 0;
                for (ImageButton[] buttonsArray : gameButtons) {
                    for (ImageButton iButton : buttonsArray) {
                        if (view.getId() == iButton.getId()) {
                            gManager.turn(column, row);
                            if (gManager.getGameEnded()) createWinnerDialog();
                        }
                        row++;
                    }
                    row = 0;
                    column++;
                }

                Drawable icon;
                for (int i = 0; i < gameBoard.length; i++) {
                    for (int j = 0; j < gameBoard[0].length; j++) {
                        if (gManager.getPiece(i, j) == Piece.X) {
                            icon = getResources().getDrawable(R.drawable.x, getTheme());
                            gameBoard[i][j].setImageDrawable(icon);
                        } else if (gManager.getPiece(i, j) == Piece.O) {
                            icon = getResources().getDrawable(R.drawable.o, getTheme());
                            gameBoard[i][j].setImageDrawable(icon);
                        } else if (gManager.getPiece(i, j) == Piece.EMPTY) {
                            icon = getResources().getDrawable(R.drawable.frm, getTheme());
                            gameBoard[i][j].setImageDrawable(icon);
                        }
                    }
                }
            }
        }
    }

    public void createWinnerDialog() {
        // Confirm dbHelper is initialized before use
        if (StartActivity.dbHelper == null) {
            Toast.makeText(this, "Database not initialized!", Toast.LENGTH_LONG).show();
            return; // Prevent crash
        }

        // Inflate your custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.winnerpopup, null);

        // Find views inside the custom layout
        TextView winnerView = dialogView.findViewById(R.id.winner);
        Button reStart = dialogView.findViewById(R.id.btnRestart);

        // Determine and display the winner
        Piece winner = gManager.endGame();
        if (winner == Piece.X) {
            winnerView.setText("Player X won!");
            StartActivity.dbHelper.addToPlayerList(userX);
        } else if (winner == Piece.O) {
            winnerView.setText("Player O won!");
            StartActivity.dbHelper.addToPlayerList(userO);
        } else {
            winnerView.setText("It's a TIE!");
        }

        // Build the AlertDialog with the custom layout
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(false); // Optional: prevent back press or outside tap from dismissing
        AlertDialog alertDialog = builder.create();

        // Restart button logic
        reStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("DATA1", userO);
                intent.putExtra("DATA2", userX);
                startActivity(intent);
                finish(); // Close current activity to avoid stack buildup
            }
        });

        alertDialog.show(); // Show the dialog
    }
}