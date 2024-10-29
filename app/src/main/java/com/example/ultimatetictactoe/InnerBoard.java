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

    // checks if the given player (symbol- 'X' or 'O') has won in the innerBoard
    public boolean isWon(char symbol) {
        Piece checking = Piece.charToPiece(symbol);
        if (miniBoard[0][0] == checking && miniBoard[1][1] == checking && miniBoard[2][2] == checking) return true;
        if (miniBoard[0][0] == checking && miniBoard[1][0] == checking && miniBoard[2][0] == checking) return true;
        if (miniBoard[0][0] == checking && miniBoard[0][1] == checking && miniBoard[0][2] == checking) return true;
        if (miniBoard[1][0] == checking && miniBoard[1][1] == checking && miniBoard[1][2] == checking) return true;
        if (miniBoard[2][0] == checking && miniBoard[2][1] == checking && miniBoard[2][2] == checking) return true;
        if (miniBoard[0][1] == checking && miniBoard[1][1] == checking && miniBoard[1][2] == checking) return true;
        if (miniBoard[0][2] == checking && miniBoard[1][2] == checking && miniBoard[2][2] == checking) return true;
        if (miniBoard[2][0] == checking && miniBoard[1][1] == checking && miniBoard[0][2] == checking) return true;
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