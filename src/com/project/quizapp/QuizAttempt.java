package com.project.quizapp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuizAttempt {
    private String attemptId;
    private String quizId;
    private String quizTitle;
    private int score;
    private int totalQuestionsMarks;
    private String timestamp;
    private String category;
    private String mode;
//    DateTimeFormatter formatter;

    public QuizAttempt(String attemptId, String quizId, String quizTitle, int score, int totalQuestionsMarks, String category, String mode, String timestamp) {
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.score = score;
        this.totalQuestionsMarks = totalQuestionsMarks;
        this.category = category;
        this.mode = mode;
//        this.formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = timestamp;
    }

    public String getAttemptId() {
        return attemptId;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestionsMarks() {
        return totalQuestionsMarks;
    }

    public String getCategory() {
        return category;
    }

    public String getMode() {
        return mode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getPercentage() {
        if (totalQuestionsMarks == 0) return 0.0;
        return (double) score / totalQuestionsMarks * 100;
    }
}