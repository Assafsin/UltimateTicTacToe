package com.example.ultimatetictactoe;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GameManager gManager;

    private ImageView[][] gameBoard;
    private ImageButton[][] gameButtons;
    private DatabaseHelper dbHelper;


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
        dbHelper = new DatabaseHelper();

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
        if (!gManager.getGameEnded()) {
            int column = 0;
            int row = 0;
            for (ImageButton[] buttonsArray : gameButtons) {
                for (ImageButton iButton : buttonsArray) {
                    if (view.getId() == iButton.getId()) {
                        gManager.turn(column, row);
                        // TODO: add won innerGrid animation
                        //                   if (gManager.getIsWon())
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