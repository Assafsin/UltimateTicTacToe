package com.example.ultimatetictactoe;

public class GameManager {
    private GameBoard gameBoard;
    private Piece currTurn;
    private int currInnerGrid;
    private boolean canChoose;

    public GameManager() {
        gameBoard = new GameBoard();
        currTurn = Piece.X;
        canChoose = true;
    }

    public void turn (int i, int j) {

        if (!canChoose) {
            int coloumn = ((currInnerGrid % 10) * 3) + i;
            int row = ((currInnerGrid / 10) * 3) + j;
            gameBoard.placePiece(coloumn, row, i, j, currTurn);

            if(currTurn == Piece.X) currTurn = Piece.O;
            else if (currTurn == Piece.O) currTurn = Piece.X;
        }
        currInnerGrid = i + (j * 10);
    }

    public Piece getPiece(int i, int j) {
       return gameBoard.getPiece(i, j, i, j);
    }

    private char endGame() {
        if (gameBoard.playerWon('X')) return 'x';
        if (gameBoard.playerWon('O')) return 'o';
        return '_';
    }
}
