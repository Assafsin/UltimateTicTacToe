package com.example.ultimatetictactoe;

public enum Piece {

    X('X'), O('O'), EMPTY('_');

    private char piece;

    Piece(char piece) {
        this.piece = piece;
    }
}
