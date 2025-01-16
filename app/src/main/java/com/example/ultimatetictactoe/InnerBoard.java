package com.example.ultimatetictactoe;

public class InnerBoard {

    private Piece[][] miniBoard;

    public InnerBoard() {
        miniBoard = new Piece[3][3];
        initialize();
    }

    private void initialize () {
        for (Piece[] charArray:miniBoard) {
            for (Piece symbol:charArray) {
                symbol = Piece.EMPTY;

            }
        }
    }

    public void resetInnerBoard() {
        initialize();
    }

    public void placePiece(int i, int j, Piece player) {
        miniBoard[i][j] = player;
    }

    public Piece getPiece(int i, int j) {
        return miniBoard[i][j];
    }


    // checks if the given player (symbol- 'X' or 'O') has won the innerBoard
    public boolean isWon(Piece player) {
        if (miniBoard[0][0] == player && miniBoard[1][1] == player && miniBoard[2][2] == player) return true;
        if (miniBoard[0][0] == player && miniBoard[1][0] == player && miniBoard[2][0] == player) return true;
        if (miniBoard[0][0] == player && miniBoard[0][1] == player && miniBoard[0][2] == player) return true;
        if (miniBoard[1][0] == player && miniBoard[1][1] == player && miniBoard[1][2] == player) return true;
        if (miniBoard[2][0] == player && miniBoard[2][1] == player && miniBoard[2][2] == player) return true;
        if (miniBoard[0][1] == player && miniBoard[1][1] == player && miniBoard[1][2] == player) return true;
        if (miniBoard[0][2] == player && miniBoard[1][2] == player && miniBoard[2][2] == player) return true;
        if (miniBoard[2][0] == player && miniBoard[1][1] == player && miniBoard[0][2] == player) return true;
        return false;
    }

    public boolean isFull () {
        for (Piece[] charArray:miniBoard) {
            for (Piece symbol:charArray) {
                if (symbol == Piece.EMPTY) return false;
            }
        }
        return true;
    }
}