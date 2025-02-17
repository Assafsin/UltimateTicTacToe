package com.example.ultimatetictactoe;

public class Player {
    private String name;
    private int score;

    public Player (String name) {
        this.name = name;
        this.score = 0 ;
    }

    public void won () {
        score++;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public String getName () {
        return name;
    }

    public int getScore () {
        return score;
    }
}
