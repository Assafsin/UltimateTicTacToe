package com.example.ultimatetictactoe;

public enum Piece {

    X('X'), O('O'), EMPTY('_');

    private char piece;

    Piece(char piece) {
        this.piece = piece;
    }

    public char getAsChar() {
        return this.piece;
    }

    public static Piece charToPiece(char symbol) {
        if (symbol == 'X') return X;
        if(symbol == 'O') return O;
        return EMPTY;
    }

}
