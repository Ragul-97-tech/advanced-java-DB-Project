package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class DataManager {
    private static final Logger logger = LogManager.getLogger(DataManager.class);
    private final Connection connection;

    public DataManager() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public void initializeData() {
        logger.info("Initializing application data");

        if (!checkAdminExists()) {
            createDefaultAdmin();
        }
        if (getCategoryCount() == 0) {
            initializeDefaultCategories();
        }
        logger.info("Data initialization completed");
    }

    private int getCategoryCount() {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.NO_OF_CATEGORIES);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("Error on getting category count", e);
        }
        return 0;
    }

    private void createDefaultAdmin() {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_USER);
            stmt.setString(1, "Ragul");
            stmt.setString(2, BCrypt.hashpw("Ragul@123", BCrypt.gensalt(10)));
            stmt.setString(3, "ADMIN");
            stmt.executeUpdate();
            logger.info("Default admin user created");
        } catch (SQLException e) {
            logger.error("Error creating default admin", e);
        }
    }

    private boolean checkAdminExists() {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.NO_OF_ADMINS);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException e) {
            logger.error("Error checking admin existence", e);
        }
        return false;
    }

    private void initializeDefaultCategories() {
        String[][] categories = {
            {"Java Programming", "Core Java concepts and OOPs"},
            {"Biology", "Life sciences and human body"},
            {"English Grammar", "Grammar rules and vocabulary"},
            {"JavaScript", "JS concept and client side data side management"},
            {"Python", "Python programming fundamentals"},
            {"Mathematics", "Algebra, geometry, and calculations"},
            {"History", "World history and historical events"},
            {"Science", "Physics, chemistry, and general science"},
            {"Geography", "World geography and locations"}
        };
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_CATEGORY);
            for (String[] cat : categories) {
                stmt.setString(1, cat[0]);
                stmt.setString(2, cat[1]);
                stmt.executeUpdate();
            }
            logger.info("Default categories initialized");
        } catch (SQLException e) {
            logger.error("Error on initializing categories",e);
        }
    }

    public User findUserByUsername(String username) {
       try {
           PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_USER_BY_USERNAME);
           stmt.setString(1, username);
           ResultSet rs = stmt.executeQuery();
           if (rs.next())
               return createUserFromResultSet(rs);
       } catch (SQLException e) {
           logger.error("Error on finding user by username: " + username, e);
       }
       return null;
    }

    public User findUserByUserId(int userId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_USER_BY_ID);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return createUserFromResultSet(rs);
        } catch (SQLException e) {
            logger.error("Error on finding user by ID: {}", userId, e);
        }
        return null;
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        int userId = rs.getInt("userId");
        String username = rs.getString("username");
        String password = rs.getString("password");
        AccessLevel accessLevel = AccessLevel.valueOf(rs.getString("accessLevel"));
        int score = rs.getInt("score");
        int attempts = rs.getInt("quizAttempts");

        User user = switch (accessLevel) {
            case ADMIN -> new AdminUser(String.valueOf(userId), username, password, "SUPER");
            case USER -> new RegisteredUser(String.valueOf(userId), username, password);
            case GUEST -> new GuestUser(String.valueOf(userId));
        };

        user.setTotalScore(score);
        user.setQuizzesTaken(attempts);
        return user;
    }

    public boolean addUser(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUserName());
            String crypt = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
            stmt.setString(2, crypt);
            stmt.setString(3, user.getRole());

            int affected = stmt.executeUpdate();

            if (affected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    user.setUserId(String.valueOf(keys.getInt(1)));
                }
                logger.info("User added: " + user.getUserId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error on adding user: " + user.getUserId(), e);
        }
        return false;
    }

    public void updateUserScore(int userId, int scoreToAdd) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.UPDATE_USER_SCORE);
            stmt.setInt(1, scoreToAdd);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            logger.info("User score update for userId: " + userId);
        } catch (SQLException e) {
            logger.error("Error on updating user score",e);
        }
    }

    public void promoteToAdmin(int userId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.PROMOTE_USER_TO_ADMIN);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            logger.info("User promoted to admin: {}", userId);
        } catch (SQLException e) {
            logger.error("Error on promoting user to admin",e);
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_ALL_USERS, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all users",e);
        }
        return users;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_ALL_CATEGORIES);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("categoryId");
                String name = rs.getString("categoryName");
                String desc = rs.getString("categoryDescription");
                categories.add(new Category(String.valueOf(id), name, desc));
            }
        } catch (SQLException e) {
            logger.error("Error on getting categories",e);
        }
        return categories;
    }

    public Category findCategoryByName(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_USER_BY_USERNAME);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return new Category(String.valueOf(rs.getInt("categoryId")), rs.getString("categoryName"), rs.getString("categoryDescription"));
        } catch (SQLException e) {
            logger.error("Error on finding category by category name: {}", name, e);
        }
        return null;
    }

    public Category findCategoryById(int categoryId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_CATEGORY_BY_ID);
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(String.valueOf(rs.getInt("categoryId")), rs.getString("categoryName"), rs.getString("categoryDescription"));
            }
        } catch (SQLException e) {
            logger.error("Error on finding category by ID: {}", categoryId, e);
        }
        return null;
    }

    public boolean addCategory(String name, String description) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_CATEGORY);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.executeUpdate();
            logger.info("Category added: " + name);
            return true;
        } catch (SQLException e) {
            logger.error("Error on adding new category",e);
            return false;
        }
    }

    public void updateCategory(int categoryId, String name, String description) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.UPDATE_CATEGORY);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, categoryId);
            stmt.executeUpdate();
            logger.info("Category updated: " + categoryId);
        } catch (SQLException e) {
            logger.error("Error on updating category",e);
        }
    }

    public void removeCategory(int categoryId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.DELETE_CATEGORY);
            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
            logger.info("Category deleted: " + categoryId);
        } catch (SQLException e) {
            logger.error("Error on deleting category", e);
        }
    }

    public int getCategoryQuestionCount(int categoryId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.COUNT_QUESTIONS_IN_CATEGORY);
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error on counting category questions",e);
        }
        return 0;
    }

    public ArrayList<Question> getQuestionsByCategory(int categoryId) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_QUESTIONS_BY_CATEGORY);
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = createQuestionFromResultSet(rs);
                if (q != null) {
                    questions.add(q);
                }
            }
        } catch (SQLException e) {
            logger.error("Error on getting questions by category",e);
        }
        return questions;
    }

    public ArrayList<Question> getQuestionByDifficulty(int categoryId, String difficulty) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_QUESTIONS_BY_DIFFICULTY);
            stmt.setInt(1, categoryId);
            stmt.setString(2, difficulty);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = createQuestionFromResultSet(rs);
                if (q != null) {
                    questions.add(q);
                }
            }
        } catch (SQLException e) {
            logger.error("Error on getting questions by difficulty",e);
        }
        return questions;
    }

    public Question getQuestionById(int questionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_QUESTION_BY_ID);
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createQuestionFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error on getting question by ID",e);
        }
        return null;
    }

    public Question getQuestionByIdInCategory(int questionId, int categoryId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_QUESTION_BY_ID_CATEGORY);
            stmt.setInt(1, categoryId);
            stmt.setInt(2, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createQuestionFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Error on getting question by ID",e);
        }
        return null;
    }

    private Question createQuestionFromResultSet(ResultSet rs) throws SQLException {
        int questionId = rs.getInt("questionId");
        String questionText = rs.getString("questionText");
        String difficulty = rs.getString("difficulty");
        int points = rs.getInt("points");
        int categoryId = rs.getInt("categoryId");

        String[] options = getOptionsForQuestion(questionId);
        int correctOption = getCorrectOptionIndex(questionId);

        if (options != null && correctOption != -1) {
            return new Question(String.valueOf(questionId), questionText, options, correctOption, String.valueOf(categoryId), difficulty, points);
        }
        return null;
    }

    private String[] getOptionsForQuestion(int questionId) {
        ArrayList<String> optionsList = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_OPTIONS_BY_QUESTION);
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                optionsList.add(rs.getString("optionText"));
            }
            optionsList.trimToSize();
            return optionsList.toArray(new String[0]);
        } catch (SQLException e) {
            logger.error("Error on getting options for question");
        }
        return null;
    }

    private int getCorrectOptionIndex(int questionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_OPTIONS_BY_QUESTION);
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            int index = 0;
            while (rs.next()) {
                if (rs.getBoolean("isCorrect")) {
                    return index;
                }
                index++;
            }
        } catch (SQLException e) {
            logger.error("Error on getting correct option index",e);
        }
        return -1;
    }

    public int addQuestion(int categoryId, String questionText, String difficulty, int points, String[] options, int correctOption) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_QUESTION, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, categoryId);
            stmt.setString(2, questionText);
            stmt.setString(3, difficulty);
            stmt.setInt(4, points);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                int questionId = keys.getInt(1);
                addOptionsForQuestion(questionId, options, correctOption);
                logger.info("Question added with ID: " + questionId);
                return questionId;
            }
        } catch (SQLException e) {
            logger.error("Error on adding question",e);
        }
        return -1;
    }

    private void addOptionsForQuestion(int questionId, String[] options, int correctOption) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_OPTION);
            for (int i = 0; i < options.length; i++) {
                stmt.setInt(1, questionId);
                stmt.setString(2, options[i]);
                stmt.setBoolean(3, i == correctOption);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Error on adding options",e);
        }
    }

    public void updateQuestion(int questionId, String questionText, String difficulty, int points) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.UPDATE_QUESTION);
            stmt.setString(1, questionText);
            stmt.setString(2, difficulty);
            stmt.setInt(3, points);
            stmt.setInt(4, questionId);
            stmt.executeUpdate();
            logger.info("Question updated: " + questionId);
        } catch (SQLException e) {
            logger.error("Error on updating question",e);
        }
    }

    public void updateQuestionCategory(int questionId, int newCategoryId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.UPDATE_QUESTION_CATEGORY);
            stmt.setInt(1, newCategoryId);
            stmt.setInt(2, questionId);
            stmt.executeUpdate();
            logger.info("Question category updated: " + questionId);
        } catch (SQLException e) {
            logger.error("Error on updating question category", e);
        }
    }

    public void removeQuestions(int questionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.DELETE_QUESTION);
            stmt.setInt(1, questionId);
            stmt.executeUpdate();
            logger.info("Question deleted: " + questionId);
        } catch (SQLException e) {
            logger.error("Error on deleting question",e);
        }
    }

    public int getTotalQuestionsCount() {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.COUNT_TOTAL_QUESTIONS);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error on counting total questions",e);
        }
        return 0;
    }

    public void addFailedQuestion(int userId, int questionId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_FAILED_QUESTION);
            stmt.setInt(1, userId);
            stmt.setInt(2, questionId);
            stmt.executeUpdate();
            logger.info("Failed question added for user: " + userId);
        } catch (SQLException e) {
            logger.error("Error on adding failed question",e);
        }
    }

    public ArrayList<Question> getFailedQuestions(int userId) {
        ArrayList<Question> failedQuestions = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_FAILED_QUESTIONS_BY_USER);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question q = createQuestionFromResultSet(rs);
                if (q != null) {
                    failedQuestions.add(q);
                }
            }
        } catch (SQLException e) {
            logger.error("Error on getting failed question",e);
        }
        return failedQuestions;
    }

    public void clearFailedQuestions(int userId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.DELETE_ALL_FAILED_QUESTIONS);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            logger.info("Failed questions cleared for user: " + userId);
        } catch (SQLException e) {
            logger.error("Error on clearing failed questions",e);
        }
    }

    public int getFailedQuestionsCount(int userId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.COUNT_FAILED_QUESTIONS);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error on counting failed questions", e);
        }
        return 0;
    }

    public void addQuizHistory(int userId, String gameMode, int totalScore, int earnedScore) {
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.INSERT_HISTORY);
            stmt.setInt(1, userId);
            stmt.setString(2, gameMode);
            stmt.setInt(3, totalScore);
            stmt.setInt(4, earnedScore);
            stmt.executeUpdate();
            logger.info("Quiz history added for user: " + userId);
        } catch (SQLException e) {
            logger.error("Error on adding quiz history",e);
        }
    }

    public ArrayList<QuizAttempt> getQuizHistory(int userId, int limit) {
        ArrayList<QuizAttempt> history = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(DBQueries.GET_HISTORY_BY_USER);
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String attemptId = String.valueOf(rs.getInt("historyId"));
                String mode = rs.getString("gameMode");
                int totalScore = rs.getInt("totalScore");
                int earnedScore = rs.getInt("earnedScore");
                String date = rs.getString("quizDate");

                QuizAttempt attempt = new QuizAttempt(attemptId, "", mode, earnedScore, totalScore, "", mode, date);
                history.add(attempt);
            }
        } catch (SQLException e) {
            logger.error("Error on getting quiz history",e);
        }
        return history;
    }
}