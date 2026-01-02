package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class QuizMode {
    protected static final Logger logger = LogManager.getLogger(QuizMode.class);
    protected QuizGameModes modeName;
    protected String description;

    public QuizMode(QuizGameModes modeName, String description) {
        this.modeName = modeName;
        this.description = description;
    }

    public String getModeName() {
        return modeName.name();
    }
    public String getDescription() {
        return description;
    }
    public abstract QuizResult executeQuiz(Quiz quiz, User user);
}