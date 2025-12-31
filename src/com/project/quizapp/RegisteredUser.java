package com.project.quizapp;

import java.util.ArrayList;

public class RegisteredUser extends User{
    ArrayList<Question> failedQuestion;
    int maxFailedQuestions;
    ArrayList<QuizAttempt> quizHistory;

    public RegisteredUser(int userId, String userName, String password) {
        super(userId, userName, password, "USER");
        this.failedQuestion = new ArrayList<>();
        this.quizHistory = new ArrayList<>();
        this.maxFailedQuestions = 50;
    }

    @Override
    public boolean canAccessAdminFeature() {
        return false;
    }

    @Override
    public boolean canSaveProgress() {
        return true;
    }

    public ArrayList<Question> getFailedQuestion() {
        return failedQuestion;
    }

    public ArrayList<QuizAttempt> getQuizHistory() {
        return quizHistory;
    }

    public void addFailedQuestion(Question question) {
        boolean exist = false;
        for (Question value : failedQuestion) {
            if (value.getQuestionId().equals(question.getQuestionId())) {
                exist = true;
                break;
            }
        }
        if (!exist && failedQuestion.size() < maxFailedQuestions) {
            failedQuestion.add(question);
        }
    }

    public void clearFailedQuestions() {
        failedQuestion.clear();
    }

    public void addQuizAttempt(QuizAttempt attempt) {
        quizHistory.add(attempt);
    }

    public int getMaxFailedQuestionsCount() {
        return failedQuestion.size();
    }

    public double getAverageScore() {
        if (quizHistory.isEmpty()) return 0.0;
        int total = 0;
        for (QuizAttempt attempt : quizHistory) {
            total += attempt.getScore();
        }
        return (double) total / quizHistory.size() * 100;
    }
}
