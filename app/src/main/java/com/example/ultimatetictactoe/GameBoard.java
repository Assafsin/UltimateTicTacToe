package com.example.ultimatetictactoe;

public class GameBoard {
    private InnerBoard[][] mainBoard;

    public GameBoard() {
        mainBoard = new InnerBoard[3][3];
        initialize();
    }

    private void initialize() {
        for (InnerBoard[] board: mainBoard) {
            for (InnerBoard miniBoard: board) {
                miniBoard = new InnerBoard();
            }
        }
    }
}
