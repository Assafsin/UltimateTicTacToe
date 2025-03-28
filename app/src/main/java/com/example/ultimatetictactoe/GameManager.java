package com.example.ultimatetictactoe;

public class GameManager {
    private GameBoard gameBoard;
    private Piece currTurn;
    private int currInnerGridI;
    private int currInnerGridJ;
    private boolean canChoose;
    private boolean[][] isWon;
    private boolean gameEnded;

    public GameManager() {
        gameBoard = new GameBoard();
        currTurn = Piece.X;
        canChoose = true;
        gameEnded = false;
        isWon = new boolean[3][3];
        for (int i = 0; i < isWon.length; i++) {
            for (int j = 0; j < isWon[0].length; j++) {
                isWon[i][j] = false;
            }
        }
    }

    public void turn(int i, int j) {
        if (!canChoose && !isWon[currInnerGridI][currInnerGridJ]) {
            if (gameBoard.getPiece(currInnerGridI, currInnerGridJ, i, j).equals(Piece.EMPTY)) {
                gameBoard.placePiece(currInnerGridI, currInnerGridJ, i, j, currTurn);
                if (gameBoard.isWon(currInnerGridI, currInnerGridJ, currTurn)) {
                    gameBoard.wonInnerBoard(currInnerGridI, currInnerGridJ, currTurn);
                    isWon[currInnerGridI][currInnerGridJ] = true;
                    if(isWon[i][j]) canChoose= true;
                    //check if the match was won and if it was, stops the match and gives a messages that tells who won
                    matchWon(currTurn);
                }
                if (currTurn == Piece.X) currTurn = Piece.O;
                else if (currTurn == Piece.O) currTurn = Piece.X;
            }
            else {
                return;
            }
        } else {
            canChoose = false;
        }
        currInnerGridI = i;
        currInnerGridJ = j;
    }

    private void matchWon(Piece player) {
        if (gameBoard.playerWon(player)) {
            System.out.println("player " + player + " won, congratulations");
            gameEnded = true;
        }
    }


    public boolean getGameEnded() {
        return gameEnded;
    }

    public Piece getPiece(int i, int j) {
        return gameBoard.getPiece(i / 3, j / 3, i % 3, j % 3);
    }

    public Piece endGame() {
        if (gameBoard.playerWon(Piece.X)) return Piece.X;
        if (gameBoard.playerWon(Piece.O)) return Piece.O;
        return Piece.EMPTY;
    }
}
