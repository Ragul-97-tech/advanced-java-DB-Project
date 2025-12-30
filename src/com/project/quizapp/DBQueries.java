package com.project.quizapp;

public class DBQueries {



    public static final String INSERT_USER = "INSERT INTO Users (username, password, accessLevel) VALUES(?, ?, ?)";

    public static final String LOGIN_USER = "SELECT userId, accessLevel FROM Users WHERE userId = ? and password = ?";

    public static final String GET_USER_BY_ID = "SELECT userId, username, accessLevel FROM Users WHERE userId = ?";

    public static final String GET_ALL_CATEGORIES = "SELECT categoryId, categoryName FROM Categories";

    public static final String GET_QUESTIONS_BY_CATEGORY = "SELECT questionId, questionText, difficulty, points FROM Questions WHERE categoryId = ?";

    public static final String GET_ALL_QUESTIONS = "SELECT questionId, questionText, difficulty, points FROM Questions ORDER BY RAND()";

    public static final String GET_OPTIONS_BY_QUESTION = "SELECT optionId, optionText FROM Options WHERE questionId = ?";

    public static final String CHECK_OPTION_CORRECT = "SELECT isCorrect FROM Options WHERE optionId = ?";

    public static final String GET_CORRECT_OPTION = "SELECT optionText FROM Options WHERE questionId = ? AND isCorrect = TRUE";

    public static final String INSERT_FAILED_QUESTION = "INSERT IGNORE INTO FailedQuestions (userId, questionId) VALUES (?, ?)";

    public static final String CHECK_FAILED_QUESTION = "SELECT 1 FROM FailedQuestions WHERE userId = ? AND questionId = ?";

    public static final String GET_FAILED_QUESTIONS_BY_USER = "SELECT q.questionId, q.questionText, q.difficulty FROM FailedQuestions f JOIN Questions q ON q.questionId = f.questionId WHERE f.userId = ?";

    public static final String DELETE_FAILED_QUESTION = "DELETE FROM FailedQuestions WHERE userId = ? AND questionId = ?";

    public static final String COUNT_FAILED_QUESTIONS = "SELECT COUNT(*) FROM FailedQuestions WHERE userId = ?";

    public static final String GET_TOP_FAILED_QUESTIONS = "SELECT q.questionText, COUNT(*) AS failCount FROM FailedQuestions f JOIN Questions q ON q.questionId = f.questionId GROUP BY f.questionId ORDER BY failCount DESC";

    public static final String INSERT_CATEGORY = "INSERT INTO Categories (categoryName, categoryDescription) VALUES (?, ?)";

    public static final String INSERT_QUESTION = "INSERT INTO Questions (categoryId, questionText, difficulty, points) VALUES (?, ?, ?, ?)";

    public static final String INSERT_OPTION = "INSERT INTO Options (questionId, optionText, isCorrect) VALUES (?, ?, ?)";

    public static final String DELETE_QUESTION = "DELETE FROM Questions WHERE questionId = ?";

}
