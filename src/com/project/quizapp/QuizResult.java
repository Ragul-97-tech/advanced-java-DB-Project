package com.project.quizapp;

public class QuizResult {
    private int score;
    private int totalPoints;
    private int totalQuestions;
    private int failedCount;

    public QuizResult(int score,int totalPoints , int totalQuestions, int failedCount) {
        this.score = score;
        this.totalPoints = totalPoints;
        this.totalQuestions = totalQuestions;
        this.failedCount = failedCount;
    }

    public int getScore() {
        return score;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public double getPercentage() {
        if (totalPoints == 0) return 0.0;
        return (double) score / totalPoints * 100;
    }

    public String getGrade() {
        double percent = getPercentage();
        if (percent >= 90) return "A+";
        if (percent >= 80) return "A";
        if (percent >= 70) return "B";
        if (percent >= 60) return "C";
        if (percent >= 50) return "D";
        return "F";
    }
}
