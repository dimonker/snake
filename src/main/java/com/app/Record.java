package com.app;

public class Record {
    private Integer position;
    private Integer score;
    private String date;

    public Record(int pos, int score, String date){
        this.position = pos;
        this.score = score;
        this.date = date;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
