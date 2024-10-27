package com.example.ultimatetictactoe;

public class InnerBoard {
    // for every given little square in the game board (in the back-end code) - 'O' is resembled as 'O', 'X' is resembled as 'X' and '{not yet "captured"}' is resembled as '_'

    private char[][] miniBoard;

    public InnerBoard() {
        miniBoard = new char[3][3];
        initialize();
    }

    private void initialize () {
        for (char[] charArray:miniBoard) {
            for (char symbol:charArray) {
                symbol = '_';
            }
        }
    }
}
