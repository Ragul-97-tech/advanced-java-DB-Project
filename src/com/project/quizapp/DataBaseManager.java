package com.project.quizapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseManager {

    Connection connection = DBConnection.getInstance().getConnection();
    public ArrayList<Question> loadQuestions()  {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(DBQueries.GET_ALL_QUESTIONS).executeQuery();
            while(resultSet.next()){
                questions.add(new Question());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    public void saveQuestions(ArrayList<Question> questions) {
        try {
            writer = new BufferedWriter(new FileWriter(QUESTIONS_FILES));
            for (Question q : questions) {
                writer.write(q.toFileFormat());
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error on saving questions: " + e.getMessage()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }
        }
    }

    public ArrayList<Category> loadCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        File f = new File(CATEGORY_FILES);

        if (!f.exists())
            return categories;

        try {
            reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    categories.add(new Category(parts[0], parts[1], parts[2]));
                }
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error on loading categories: " + e.getMessage()));
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(ColorCode.warning("Resource still open"));
            }
        }
        return categories;
    }

    public void saveCategories(ArrayList<Category> categories) {
        try {
            writer = new BufferedWriter(new FileWriter(CATEGORY_FILES));
            for (Category cat : categories) {
                writer.write(cat.getCategoryId() + "|" + cat.getCategoryName() + "|" + cat.getDescription());
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error on saving categories: " + e.getMessage()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }
        }
    }

    public ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File f = new File(USERS_FILE);

        if (!f.exists()) return users;
        try {
            reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4) {
                        User user;
                        if (parts[3].equals("ADMIN")) {
                            user = new AdminUser(parts[0], parts[1], parts[2], "SUPER");
                        }
                        else {
                            user = new RegisteredUser(parts[0], parts[1], parts[2]);
                        }
                        if (parts.length > 4) {
                            user.addToTotalScore(Integer.parseInt(parts[4]));
                        }
                        if (parts.length > 5) {
                            for (int i = 0; i < Integer.parseInt(parts[5]); i++) {
                                user.incrementQuizzesTaken();
                            }
                        }
                        users.add(user);
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error on loading user details: " + e.getMessage()));
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(ColorCode.warning("Resource still open"));
            }
        }
        return users;
    }

    public void saveUsers(ArrayList<User> users) {
        try {
            writer = new BufferedWriter(new FileWriter(USERS_FILE));
            for (User user : users) {
                writer.write(user.toFileFormat());
                writer.newLine();
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error on saving users: " + e.getMessage()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }
        }
    }

    public ArrayList<QuizAttempt> loadQuizHistory(String userId) {
        ArrayList<QuizAttempt> history = new ArrayList<>();
        File historyFile = new File(HISTORY_FILE);

        if (!historyFile.exists()) return history;
        try {
            reader = new BufferedReader(new FileReader(historyFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 9 && parts[8].equals(userId)) {
                        history.add(QuizAttempt.fromFileFormat(line));
                    }
                }
            }
        }
        catch (IOException e) {
            System.err.println(ColorCode.error("Error loading quiz History: " + e.getMessage()));
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(ColorCode.warning("Resource still open"));
            }
        }
        return history;
    }

    public void saveQuizAttempt(QuizAttempt attempt, String userId){
        try {
            writer = new BufferedWriter(new FileWriter(HISTORY_FILE, true));
            writer.write(attempt.toFileFormat() + "|" + userId);
            writer.newLine();
        }
        catch (IOException e) {
            System.out.println(ColorCode.error("Error on saving quiz attempt: " + e.getMessage()));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }
        }
    }

    public ArrayList<Question> loadFailedQuestions(String userId) {
        ArrayList<Question> failed = new ArrayList<>();
        File failedQuestFile = new File(FAILED_QUESTIONS_FILE);

        if (!failedQuestFile.exists()) return failed;
        try {
            reader = new BufferedReader(new FileReader(failedQuestFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split("\\|\\|");
                    if (parts.length >= 2 && parts[1].equals(userId)) {
                        try {
                            failed.add(Question.fromFileFormat(parts[0]));
                        }
                        catch (Exception e) {
                            // Skip invalid questions
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println(ColorCode.error("Error on Loading failed Questions: " + e.getMessage()));
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println(ColorCode.warning("Resource still open"));
            }
        }
        return failed;
    }

    public void saveFailedQuestions(ArrayList<Question> questions, String userId) {
        ArrayList<String> allLines = new ArrayList<>();
        File f = new File(FAILED_QUESTIONS_FILE);

        if (f.exists()) {
            try {
                reader = new BufferedReader(new FileReader(f));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\|\\|");
                        if (parts.length >= 2 && !parts[1].equals(userId))
                            allLines.add(line);
                    }
                }
            }
            catch (IOException e) {
                System.out.println(ColorCode.error("Error on reading failed Questions: " + e.getMessage()));
            }
            finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }

            for (Question q : questions) {
                allLines.add(q.toFileFormat() + "||" + userId);
            }

            try {
                writer = new BufferedWriter(new FileWriter(FAILED_QUESTIONS_FILE));
                for (String line : allLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            catch (IOException e) {
                System.out.println(ColorCode.error("Error on saving failed Questions: " + e.getMessage()));
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(ColorCode.warning("Resource still open"));
                }
            }
        }
    }
}
