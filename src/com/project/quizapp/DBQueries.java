package com.project.quizapp;

public class DBQueries {

    // ==================== USER QUERIES ====================
    public static final String INSERT_USER =
            "INSERT INTO Users (username, password, accessLevel, score, quizAttempts) VALUES (?, ?, ?, 0, 0)";

    public static final String LOGIN_USER =
            "SELECT userId, username, accessLevel, score, quizAttempts FROM Users WHERE username = ? AND password = ?";

    public static final String GET_USER_BY_ID =
            "SELECT userId, username, password, accessLevel, score, quizAttempts FROM Users WHERE userId = ?";

    public static final String GET_USER_BY_USERNAME =
            "SELECT userId, username, password, accessLevel, score, quizAttempts FROM Users WHERE username = ?";

    public static final String UPDATE_USER_SCORE =
            "UPDATE Users SET score = score + ?, quizAttempts = quizAttempts + 1 WHERE userId = ?";

    public static final String PROMOTE_USER_TO_ADMIN =
            "UPDATE Users SET accessLevel = 'ADMIN' WHERE userId = ?";

    public static final String GET_ALL_USERS =
            "SELECT userId, username, password, accessLevel, score, quizAttempts FROM Users ORDER BY score DESC";

    public static final String GET_TOP_USERS =
            "SELECT userId, username, score, quizAttempts FROM Users WHERE score > 0 ORDER BY score DESC LIMIT ?";

    public static final String NO_OF_ADMINS = "SELECT COUNT(*) FROM Users WHERE accessLevel = 'ADMIN'";

    // ==================== CATEGORY QUERIES ====================
    public static final String GET_ALL_CATEGORIES =
            "SELECT categoryId, categoryName, categoryDescription FROM Categories";

    public static final String GET_CATEGORY_BY_ID =
            "SELECT categoryId, categoryName, categoryDescription FROM Categories WHERE categoryId = ?";

    public static final String GET_CATEGORY_BY_NAME =
            "SELECT categoryId, categoryName, categoryDescription FROM Categories WHERE categoryName = ?";

    public static final String INSERT_CATEGORY =
            "INSERT INTO Categories (categoryName, categoryDescription) VALUES (?, ?)";

    public static final String UPDATE_CATEGORY =
            "UPDATE Categories SET categoryName = ?, categoryDescription = ? WHERE categoryId = ?";

    public static final String DELETE_CATEGORY =
            "DELETE FROM Categories WHERE categoryId = ?";

    public static final String COUNT_QUESTIONS_IN_CATEGORY =
            "SELECT COUNT(*) FROM Questions WHERE categoryId = ?";

    public static final String NO_OF_CATEGORIES =
            "SELECT COUNT(*) FROM Categories";

    // ==================== QUESTION QUERIES ====================
    public static final String GET_ALL_QUESTIONS =
            "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions";

    public static final String GET_QUESTIONS_BY_CATEGORY =
            "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE categoryId = ?";

    public static final String GET_QUESTION_BY_ID_CATEGORY =
            "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE categoryId = ? AND questionId = ?";

    public static final String GET_QUESTIONS_BY_DIFFICULTY =
            "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE categoryId = ? AND difficulty = ? ORDER BY RAND()";

    public static final String GET_QUESTION_BY_ID =
            "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE questionId = ?";

    public static final String INSERT_QUESTION =
            "INSERT INTO Questions (categoryId, questionText, difficulty, points) VALUES (?, ?, ?, ?)";

    public static final String UPDATE_QUESTION =
            "UPDATE Questions SET questionText = ?, difficulty = ?, points = ? WHERE questionId = ?";

    public static final String UPDATE_QUESTION_CATEGORY =
            "UPDATE Questions SET categoryId = ? WHERE questionId = ?";

    public static final String DELETE_QUESTION =
            "DELETE FROM Questions WHERE questionId = ?";

    public static final String COUNT_TOTAL_QUESTIONS =
            "SELECT COUNT(*) FROM Questions";

    public static final String COUNT_QUESTIONS_BY_DIFFICULTY =
            "SELECT difficulty, COUNT(*) as count FROM Questions WHERE categoryId = ? GROUP BY difficulty";

    // ==================== OPTION QUERIES ====================
    public static final String GET_OPTIONS_BY_QUESTION =
            "SELECT optionId, optionText, isCorrect FROM Options WHERE questionId = ? ORDER BY optionId";

    public static final String INSERT_OPTION =
            "INSERT INTO Options (questionId, optionText, isCorrect) VALUES (?, ?, ?)";

    public static final String UPDATE_OPTION =
            "UPDATE Options SET optionText = ? WHERE optionId = ?";

    public static final String UPDATE_CORRECT_OPTION =
            "UPDATE Options SET isCorrect = ? WHERE optionId = ?";

    public static final String DELETE_OPTIONS_BY_QUESTION =
            "DELETE FROM Options WHERE questionId = ?";

    public static final String GET_CORRECT_OPTION =
            "SELECT optionId, optionText FROM Options WHERE questionId = ? AND isCorrect = 1";

    // ==================== FAILED QUESTIONS QUERIES ====================
    public static final String INSERT_FAILED_QUESTION =
            "INSERT IGNORE INTO FailedQuestions (userId, questionId) VALUES (?, ?)";

    public static final String GET_FAILED_QUESTIONS_BY_USER =
            "SELECT DISTINCT q.questionId, q.categoryId, q.questionText, q.difficulty, q.points " +
                    "FROM FailedQuestions f JOIN Questions q ON f.questionId = q.questionId WHERE f.userId = ?";

    public static final String DELETE_FAILED_QUESTION =
            "DELETE FROM FailedQuestions WHERE userId = ? AND questionId = ?";

    public static final String DELETE_ALL_FAILED_QUESTIONS =
            "DELETE FROM FailedQuestions WHERE userId = ?";

    public static final String COUNT_FAILED_QUESTIONS =
            "SELECT COUNT(*) FROM FailedQuestions WHERE userId = ?";

    // ==================== HISTORY QUERIES ====================
    public static final String INSERT_HISTORY =
            "INSERT INTO Histories (userId, gameMode, totalScore, earnedScore, quizDate) VALUES (?, ?, ?, ?, CURDATE())";

    public static final String GET_HISTORY_BY_USER =
            "SELECT historyId, gameMode, totalScore, earnedScore, quizDate FROM Histories WHERE userId = ? ORDER BY quizDate DESC LIMIT ?";

    public static final String GET_ALL_HISTORY_BY_USER =
            "SELECT historyId, gameMode, totalScore, earnedScore, quizDate FROM Histories WHERE userId = ? ORDER BY quizDate DESC";

    public static final String COUNT_TOTAL_QUIZ_ATTEMPTS =
            "SELECT COUNT(*) FROM Histories";

    public static final String GET_TOTAL_POINTS_EARNED =
            "SELECT SUM(earnedScore) FROM Histories";
}