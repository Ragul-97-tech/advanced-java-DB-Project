package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class QuizMode {
    protected static final Logger logger = LogManager.getLogger(QuizMode.class);
    protected String modeName;
    protected String description;

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