package com.project.quizapp;

public abstract class QuizMode {
    String modeName;
    String description;

    public QuizMode(String modeName, String description) {
        this.modeName = modeName;
        this.description = description;
    }

    public String getModeName() {
        return modeName;
    }

    public String getDescription() {
        return description;
    }
    public abstract QuizResult executeQuiz(Quiz quiz, User user);
}