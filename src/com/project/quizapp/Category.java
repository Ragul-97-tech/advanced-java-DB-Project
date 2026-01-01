package com.project.quizapp;

import java.util.ArrayList;

public class Category {
    private final String categoryId;
    private String categoryName;
    private String description;
    private final ArrayList<Question> questions;

    public Category(String categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public int getTotalQuestions() {
//        return questions.size();
//    }
//    public ArrayList<Question> getQuestionsByDifficulty(String difficulty) {
//        ArrayList<Question> filtered = new ArrayList<>();
//        for (Question question : questions) {
//            if (question.getDifficulty().equalsIgnoreCase(difficulty))
//                filtered.add(question);
//        }
//        return filtered;
//    }
//
//    public Question findQuestionById(String id) {
//        Question qFound = null;
//        for (Question q : questions) {
//            if (q.getQuestionId().equalsIgnoreCase(id))
//                qFound = q;
//        }
//        return qFound;
//    }
}
