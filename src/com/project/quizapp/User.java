package com.project.quizapp;

public abstract class User {
    private int userId;
    private final String userName;
    private final String password;
    private String role;
    private int totalScore;
    private int quizzesTaken;

    public User(int userId, String userName, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.totalScore = 0;
        this.quizzesTaken = 0;
    }

    public int getUserId() {
        return userId;
    }

    public int setUserId(int id) {
        return userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getQuizzesTaken() {
        return quizzesTaken;
    }

    public void setQuizzesTaken(int quizzesTaken) {
        this.quizzesTaken = quizzesTaken;
    }

    public void incrementQuizzesTaken() {
        this.quizzesTaken++;
    }

    public void addToTotalScore(int score) {
        this.totalScore += score;
    }

    public abstract boolean canAccessAdminFeature();
    public abstract boolean canSaveProgress();

    public String toFileFormat() {
        return userId + "|" + userName + "|" + password + "|" + role + "|" + totalScore + "|" + quizzesTaken;
    }
}
