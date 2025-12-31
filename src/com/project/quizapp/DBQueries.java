package com.project.quizapp;

public class DBQueries {

    /* User Queries such as:
    * INSERT_USER
    * LOGIN_USER
    * GET_USER_BY_ID
    * GET_USER_BY_NAME
    * GET_ALL_USERS
    * UPDATE_USER_SCORE
    * NO_OF_ADMINS
    * PROMOTE_USER_TO_ADMIN
    * GET_TOP_USERS
    * GET_ALL_CATEGORIES
    * */
    public static final String INSERT_USER = "INSERT INTO Users (username, password, accessLevel) VALUES(?, ?, ?)";

    public static final String LOGIN_USER = "SELECT userId, username, accessLevel, score, quizAttempts FROM Users WHERE userId = ? and password = ?";

    public static final String GET_USER_BY_ID = "SELECT userId, username, accessLevel FROM Users WHERE userId = ?";

    public static final String GET_USER_BY_NAME = "SELECT userId, username, accessLevel FROM Users WHERE userName = ?";

    public static final String GET_ALL_USERS = "SELECT userId, username, accessLevel, score, quizAttempts FROM Users ORDER BY score DESC";

    public static final String UPDATE_USER_SCORE = "UPDATE Users SET score = score + ?, quizAttempts = quizAttempts + 1 WHERE userId = ?";

    public static final String NO_OF_ADMINS = "SELECT COUNT(*) FROM Users WHERE accessLevel = 'ADMIN'";

    public static final String PROMOTE_USER_TO_ADMIN = "UPDATE Users SET accessLevel = 'ADMIN' WHERE userId = ?";

    public static final String GET_TOP_USERS = "SELECT userId, username, score, quizAttempts FROM Users Where score > 0 ORDER BY score DESC LIMIT ?";

    /*
    * Category Queries such as
    * */
    public static final String INSERT_CATEGORY = "INSERT INTO Categories (categoryName, categoryDescription) VALUES (?, ?)";

    public static final String UPDATE_CATEGORY = "UPDATE CATEGORY SET categoryName = ?, categoryDescription = ? WHERE categoryId = ?";

    public static final String DELETE_CATEGORY = "DELETE FROM Categories where categoryId = ?";

    public static final String GET_ALL_CATEGORIES = "SELECT categoryId, categoryName FROM Categories";

    public static final String NO_OF_CATEGORIES = "SELECT count(*) FROM Categories";

    public static final String GET_CATEGORY_BY_ID = "SELECT categoryId, categoryName, categoryDescription FROM Categories WHERE categoryId = ?";

    public static final String GET_CATEGORY_BY_NAME = "SELECT categoryId, categoryName, categoryDescription FROM Categories WHERE categoryName = ?";

    public static final String COUNT_QUESTIONS_IN_CATEGORY = "SELECT COUNT(*) FROM Questions WHERE categoryId = ?";

    /*
    * Question Queries such as
    * */

    public static final String GET_ALL_QUESTIONS = "SELECT questionId, questionText, difficulty, points FROM Questions ORDER BY RAND()";

    public static final String GET_QUESTIONS_BY_CATEGORY = "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE categoryId = ? ORDER BY RAND()";

    public static final String GET_QUESTIONS_BY_DIFFICULTY = "SELECT questionID, categoryId, questionText, difficulty, points FROM Questions WHERE categoryId = ? AND DIFFICULTY = ? ORDER BY RAND()";

    public static final String GET_QUESTION_BY_ID = "SELECT questionId, categoryId, questionText, difficulty, points FROM Questions WHERE questionId = ?";

    public static final String INSERT_QUESTION = "INSERT INTO Questions (categoryId, questionText, difficulty, points) VALUES (?, ?, ?, ?)";

    public static final String UPDATE_QUESTION = "UPDATE Questions SET questionText = ?, difficulty = ?, points = ? WHERE questionId = ?";

    public static final String UPDATE_QUESTION_CATEGORY = "UPDATE Questions SET categoryId = ? WHERE questionId = ?";

    public static final String DELETE_QUESTION = "DELETE FROM Questions WHERE questionId = ?";

    public static final String COUNT_TOTAL_QUESTIONS = "SELECT * FROM Questions";

    public static final String COUNT_QUESTIONS_BY_DIFFICULTY = "SELECT difficulty, COUNT(*) as count FROM Questions WHERE categoryId = ? GROUP BY difficulty";

    /*
    * Option Queries such as:
    * */

    public static final String GET_OPTIONS_BY_QUESTION = "SELECT optionId, optionText, isCorrect FROM Options WHERE questionId = ? ORDER BY optionId";

    public static final String INSERT_OPTION = "INSERT INTO Options (questionId, optionText, isCorrect) VALUES (?, ?, ?)";

    public static final String UPDATE_OPTION = "UPDATE Options SET optionText = ? WHERE optionId = ?";

    public static final String UPDATE_CORRECT_OPTION = "UPDATE Options SET isCorrect = ? WHERE optionId = ?";

    public static final String DELETE_OPTION_BY_QUESTION = "DELETE FROM Options WHERE questionId = ?";

    public static final String GET_CORRECT_OPTION = "SELECT optionId, optionText FROM Options WHERE questionId = ? AND isCorrect = TRUE";

    public static final String CHECK_OPTION_CORRECT = "SELECT isCorrect FROM Options WHERE optionId = ?";

    /* FailedQuestion Queries such as:
    * */
    public static final String INSERT_FAILED_QUESTION = "INSERT IGNORE INTO FailedQuestions (userId, questionId) VALUES (?, ?)";

    public static final String CHECK_FAILED_QUESTION = "SELECT 1 FROM FailedQuestions WHERE userId = ? AND questionId = ?";

    public static final String GET_FAILED_QUESTIONS_BY_USER = "SELECT DISTINCT q.questionId, q.categoryId, q.questionText, q.difficulty, q.points FROM FailedQuestions f JOIN Questions q ON q.questionId = f.questionId WHERE f.userId = ?";

    public static final String DELETE_FAILED_QUESTION = "DELETE FROM FailedQuestions WHERE userId = ? AND questionId = ?";

    public static final String DELETE_ALL_FAILED_QUESTIONS = "DELETE FROM FailedQuestions WHERE userId = ?";

    public static final String COUNT_FAILED_QUESTIONS = "SELECT COUNT(*) FROM FailedQuestions WHERE userId = ?";

    public static final String GET_TOP_FAILED_QUESTIONS = "SELECT q.questionText, COUNT(*) AS failCount FROM FailedQuestions f JOIN Questions q ON q.questionId = f.questionId GROUP BY f.questionId ORDER BY failCount DESC";

    /*
    * History Queries such as:
    * */

    public static final String INSERT_HISTORY = "INSERT INTO Histories (userId, gameMode, totalScore, earnedScore, quizDate) VALUES (?, ?, ?, ?, CURDATE())";

    public static final String GET_HISTORY_BY_USER = "SELECT historyId, gameMode, totalScore, earnedScore, quizDate FROM Histories WHERE userId = ? ORDER BY quizDate DESC LIMIT ?";

    public static final String GET_ALL_HISTORY_BY_USER = "SELECT historyID, gameMode, totslScore, earnedScore, quizDate FROM Histories WHERE userid = ? ORDER BY quizDate DES";

    public static final String COUNT_TOTAL_QUIZ_ATTEMPTS = "SELECT COUNT(*) FROM Histories";

    public static final String GET_TOTAL_POINTS_EARNED = "SELECT SUM(earnedScore) FROM Histories";
}
