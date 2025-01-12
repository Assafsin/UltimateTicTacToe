package com.example.ultimatetictactoe;

public class GameManager {
    private GameBoard gameBoard;
    private Piece currTurn;
    private int currInnerGridI;
    private int currInnerGridJ;
    private boolean canChoose;

    public GameManager() {
        gameBoard = new GameBoard();
        currTurn = Piece.X;
        canChoose = true;

    }

    public void turn (int i, int j) {
        if (!canChoose) {
            if(currTurn == Piece.X) currTurn = Piece.O;
            else if (currTurn == Piece.O) currTurn = Piece.X;
            gameBoard.placePiece(currInnerGridI, currInnerGridJ, i, j, currTurn);
        }
        canChoose = false;
        currInnerGridI = i;
        currInnerGridJ = j;
    }

    public Piece getPiece(int i, int j) {
       return gameBoard.getPiece(i / 3,j / 3,i % 3,j % 3);
    }

    private char endGame() {
        if (gameBoard.playerWon('X')) return 'x';
        if (gameBoard.playerWon('O')) return 'o';
        return '_';
    }
}
