package com.example.ultimatetictactoe;

public class GameManager {
    private GameBoard gameBoard;
    private Piece currTurn;
    private int currInnerGridI;
    private int currInnerGridJ;
    private boolean canChoose;
    private boolean[][] isWon;

    public GameManager() {
        gameBoard = new GameBoard();
        currTurn = Piece.X;
        canChoose = true;
        isWon = new boolean[3][3];
        for (int i = 0; i < isWon.length; i++) {
            for (int j = 0; j < isWon[0].length; j++) {
                isWon[i][j] = false;
            }
        }
    }

    public void turn (int i, int j) {
        if (isWon[currInnerGridI][currInnerGridJ]) canChoose = true;
        if (!canChoose) {
            if(currTurn == Piece.X) currTurn = Piece.O;
            else if (currTurn == Piece.O) currTurn = Piece.X;
            gameBoard.placePiece(currInnerGridI, currInnerGridJ, i, j, currTurn);
            if (gameBoard.isWon(currInnerGridI,currInnerGridJ, currTurn)) isWon[currInnerGridI][currInnerGridJ] = true;
        }
        else {
            canChoose = false;
        }
        currInnerGridI = i;
        currInnerGridJ = j;
    }

    public boolean getIsWon () {
        return isWon[currInnerGridI][currInnerGridJ];
    }

    public int getCurrInnerGridI () {
        return currInnerGridI;
    }

    public int getCurrInnerGridJ () {
        return currInnerGridJ;
    }

    public Piece getPiece(int i, int j) {
       return gameBoard.getPiece(i / 3,j / 3,i % 3,j % 3);
    }

    private Piece endGame() {
        if (gameBoard.playerWon(Piece.X)) return Piece.X;
        if (gameBoard.playerWon(Piece.O)) return Piece.O;
        return Piece.EMPTY;
    }
}
