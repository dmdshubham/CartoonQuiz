package com.karunendu.cartoonquiz;

/**
 * Created by Karunendu Mishra on 1/4/2017.
 */

public class LevelData
{
    private String title;
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int numOfQuestion;
    private int numOfAnswered;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumOfQuestion() {
        return numOfQuestion;
    }

    public void setNumOfQuestion(int numOfQuestion) {
        this.numOfQuestion = numOfQuestion;
    }

    public int getNumOfAnswered() {
        return numOfAnswered;
    }

    public void setNumOfAnswered(int numOfAnswered) {
        this.numOfAnswered = numOfAnswered;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
