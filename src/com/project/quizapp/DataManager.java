package com.project.quizapp;

import java.util.ArrayList;

public class DataManager {
    ArrayList<Category> categories;
    ArrayList<User> users;
    DataBaseManager fileManage;
    QuestionBank entireQuestions;

    public DataManager() {
        this.categories = new ArrayList<>();
        this.users = new ArrayList<>();
        this.fileManage = new DataBaseManager();
        this.entireQuestions = new QuestionBank();
    }

    public void initializeData() {
        categories = fileManage.loadCategories();

        if (categories.isEmpty())
            initializeDefaultCategories();

        ArrayList<Question> allQuestions = fileManage.loadQuestions();

        if (allQuestions.isEmpty()) {
            allQuestions = entireQuestions.generateAllQuestions();
            fileManage.saveQuestions(allQuestions);
        }

        for (Question q : allQuestions) {
            Category cat = findCategoryById(q.getCategory());
            if (cat != null)
                cat.addQuestion(q);
        }

        users = fileManage.loadUsers();
        if (users.isEmpty()) {
            // creating default admin
            AdminUser admin = new AdminUser("ADMIN001", "admin", "admin@123", "SUPER");
            users.add(admin);
            fileManage.saveUsers(users);
        }

        for (User user : users) {
            if (user instanceof RegisteredUser regUser) {
                ArrayList<QuizAttempt> history = fileManage.loadQuizHistory(user.getUserId());
                for (QuizAttempt attempt : history) {
                    regUser.addQuizAttempt(attempt);
                }

                ArrayList<Question> failedQuestions = fileManage.loadFailedQuestions(user.getUserId());
                for (Question q : failedQuestions) {
                    regUser.addFailedQuestion(q);
                }
            }
        }
    }

    void initializeDefaultCategories() {
        categories.add(new Category("CAT001", "Java Programming", "Core Java concepts and OOPs"));
        categories.add(new Category("CAT002", "Biology", "Life sciences and human body"));
        categories.add(new Category("CAT003", "English Grammar", "Grammar rules and vocabulary"));
        categories.add(new Category("CAT004", "JavaScript", "JS concept and client side data side management"));
        categories.add(new Category("CAT005", "Python", "Python programming fundamentals"));
        categories.add(new Category("CAT006", "Mathematics", "Algebra, geometry, and calculations"));
        categories.add(new Category("CAT007", "History", "World history and historical events"));
        categories.add(new Category("CAT008", "Science", "Physics, chemistry, and general science"));
        categories.add(new Category("CAT009", "Geography", "World geography and locations"));
        fileManage.saveCategories(categories);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public Category findCategoryById(String categoryId) {
        for (Category cat : categories) {
            if (cat.getCategoryId().equals(categoryId))
                return cat;
        }
        return null;
    }

    public Category findCategoryByName(String name) {
        for (Category cat : categories) {
            if (cat.getCategoryName().equalsIgnoreCase(name))
                return cat;
        }
        return null;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }

    public User findUserByUserId(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId))
                return user;
        }
        return null;
    }

    public void addUser(User u) {
        users.add(u);
        fileManage.saveUsers(users);
    }

    public void updateUser() {
        fileManage.saveUsers(users);
        for (User u : users) {
            if (u instanceof RegisteredUser regUser) {
                fileManage.saveFailedQuestions(regUser.getFailedQuestion(), u.getUserId());
            }
        }
    }

    public void updateCategory() {
        fileManage.saveCategories(categories);
    }

    public void promoteToAdmin(User user) {
        if (user instanceof RegisteredUser) {
            users.remove(user);
            AdminUser newAdmin = new AdminUser(user.getUserId(), user.getUserName(), user.getPassword(), "PROMOTED");
            newAdmin.addToTotalScore(user.getTotalScore());
            for (int i = 0; i < user.getQuizzesTaken(); i++) {
                newAdmin.incrementQuizzesTaken();
            }
            users.add(newAdmin);
            fileManage.saveUsers(users);
        }
    }

    public void addCategory(Category cat) {
        categories.add(cat);
        fileManage.saveCategories(categories);
    }

    public void removeCategory(String categoryId) {
        Category cat = findCategoryById(categoryId);
        if (cat != null) {
            categories.remove(cat);
            fileManage.saveCategories(categories);
            saveAllQuestions();
        }
    }

    public void addQuestion(Question q) {
        Category cat = findCategoryById(q.getCategory());
        if (cat != null) {
            cat.addQuestion(q);
            saveAllQuestions();
        }
    }

    public void removeQuestions(String categoryId, String questionId) {
        Category cat = findCategoryById(categoryId);
        if (cat != null) {
            cat.removeQuestion(questionId);
            saveAllQuestions();
        }
    }

    public void moveQuestion(String questionId, String fromCategoryId, String toCategoryId) {
        Category fromCat = findCategoryById(fromCategoryId);
        Category toCat = findCategoryById(toCategoryId);

        if (fromCat != null && toCat != null) {
            Question quest = null;
            for (Question q : fromCat.getQuestions()) {
                if (q.getQuestionId().equals(questionId)) {
                    quest = q;
                    break;
                }
            }

            if (quest != null) {
                fromCat.removeQuestion(questionId);
                quest.setCategory(toCategoryId);
                toCat.addQuestion(quest);
                saveAllQuestions();
            }
        }
    }

    public void saveAllQuestions() {
        ArrayList<Question> allQuestions = new ArrayList<>();
        for (Category category : categories) {
            allQuestions.addAll(category.getQuestions());
        }
        fileManage.saveQuestions(allQuestions);
    }

    public int getTotalQuestionsCount() {
        int count = 0;
        for (Category cat : categories) {
            count += cat.getTotalQuestions();
        }
        return count;
    }

    public String generateNextCategoryId() {
        int maxId = 0;
        for (Category cat : categories) {
            String id = cat.getCategoryId().replace("CAT", "");
            try {
                int num = Integer.parseInt(id);
                maxId = Math.max(maxId, num);
            } catch (NumberFormatException e) {
                // Skips invalid ids
            }
        }
        return String.format("CAT%03d", maxId+1);
    }

    public String generateNextQuestionId(String categoryId) {
        Category cat = findCategoryById(categoryId);
        if (cat == null) return categoryId + "_Q001";

        int maxId = 0;
        for (Question q : cat.getQuestions()) {
            String id = q.getQuestionId().replace(categoryId + "_Q", "");
            try {
                int num = Integer.parseInt(id);
                maxId = Math.max(maxId, num);
            } catch (NumberFormatException e) {
                // Skips invalid ids
            }
        }
        return String.format("%s_Q%03d", categoryId, maxId+1);
    }
}