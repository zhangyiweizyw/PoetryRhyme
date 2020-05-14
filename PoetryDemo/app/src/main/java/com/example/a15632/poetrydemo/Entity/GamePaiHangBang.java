package com.example.a15632.poetrydemo.Entity;

public class GamePaiHangBang {
    private int id;
    private String name;
    private int score;
    private int gameimg;

    public GamePaiHangBang(int id,String name,int gameimg,int score){
        this.id=id;
        this.name=name;
        this.gameimg=gameimg;
        this.score=score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGameimg() {
        return gameimg;
    }

    public void setGameimg(int gameimg) {
        this.gameimg = gameimg;
    }
}
