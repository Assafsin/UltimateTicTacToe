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

    public void resetBoard() {
        for(InnerBoard[] innerArray : mainBoard) {
            for (InnerBoard board : innerArray) {
                board.resetInnerBoard();
            }
        }
    }

    //checks if a given player has won overall in the match
    public boolean playerWon(char checking) {
        if (mainBoard[0][0].isWon(checking) && mainBoard[1][1].isWon(checking) && mainBoard[2][2].isWon(checking)) return true;
        if (mainBoard[0][0].isWon(checking) && mainBoard[1][0].isWon(checking) && mainBoard[2][0].isWon(checking)) return true;
        if (mainBoard[0][0].isWon(checking) && mainBoard[0][1].isWon(checking) && mainBoard[0][2].isWon(checking)) return true;
        if (mainBoard[1][0].isWon(checking) && mainBoard[1][1].isWon(checking) && mainBoard[1][2].isWon(checking)) return true;
        if (mainBoard[2][0].isWon(checking) && mainBoard[2][1].isWon(checking) && mainBoard[2][2].isWon(checking)) return true;
        if (mainBoard[0][1].isWon(checking) && mainBoard[1][1].isWon(checking) && mainBoard[1][2].isWon(checking)) return true;
        if (mainBoard[0][2].isWon(checking) && mainBoard[1][2].isWon(checking) && mainBoard[2][2].isWon(checking)) return true;
        if (mainBoard[2][0].isWon(checking) && mainBoard[1][1].isWon(checking) && mainBoard[0][2].isWon(checking)) return true;
        return false;
    }
}
