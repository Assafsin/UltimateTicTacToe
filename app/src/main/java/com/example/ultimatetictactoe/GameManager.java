package com.example.ultimatetictactoe;

public class GameManager {
    private GameBoard gameBoard;

    public GameManager() {
        gameBoard = new GameBoard();

    }

    public void turn () {

    }

    private char endGame() {
        if (gameBoard.playerWon('X')) return 'x';
        if (gameBoard.playerWon('O')) return 'o';
        return '_';
    }
}
