package com.example.assignment3;

public class Rank {
    int matchType;
    int division;
    String achievementDate;

    public Rank(int matchType, int division, String achievementDate) {
        this.matchType = matchType;
        this.division = division;
        this.achievementDate = achievementDate;
    }
    public Rank(){}


    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public void setAchievementDate(String achievementDate) {
        this.achievementDate = achievementDate;
    }

}
