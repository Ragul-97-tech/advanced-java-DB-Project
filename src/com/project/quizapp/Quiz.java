package com.project.quizapp;

import java.util.ArrayList;

public class Quiz {
    String quizId;
    String quizTitle;
    String category;
    ArrayList<Question> questions;
    int totalQuestions;
    String quizMode;
    int timeLimit;

    public Quiz(String quizId, String quizTitle, String category, ArrayList<Question> questions, String quizMode, int timeLimit) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.category = category;
        this.questions = questions;
        this.totalQuestions = questions.size();
        this.quizMode = quizMode;
        this.timeLimit = timeLimit;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public String getQuizMode() {
        return quizMode;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void shuffleQuestions() {
        for (int i = questions.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i+1));
            Question temp = questions.get(i);
            questions.set(i, questions.get(j));
            questions.set(i, temp);
        }
    }

    public ArrayList<Question> getSubset(int count) {
        ArrayList<Question> subset = new ArrayList<>();
        int limit = Math.min(count, questions.size());
        for (int i = 0; i < limit; i++) {
            subset.add(questions.get(i));
        }
        return subset;
    }
}
