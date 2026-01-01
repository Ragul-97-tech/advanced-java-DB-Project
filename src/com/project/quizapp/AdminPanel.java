package com.project.quizapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AdminPanel {
    private static final Logger logger = LogManager.getLogger(AdminPanel.class);
    private DataManager metaData;
    private AuthenticationManager auth;
    private QuizApplication quizApp;

    public AdminPanel(DataManager metaData, AuthenticationManager auth) {
        this.metaData = metaData;
        this.auth = auth;
        this.quizApp = new QuizApplication();
    }

    public void showMenu() {
        User admin = auth.getCurrentUser();

        int choice;
        while (true) {
            System.out.println();
            System.out.println(ColorCode.colored("cyan",ColorCode.boxDouble("              👑 ADMIN:"  + admin.getUserName() + "              ")));

            System.out.println(ColorCode.colored("YELLOW", " 1.") + " \uD83D\uDCDD Manage Questions");
            System.out.println(ColorCode.colored("YELLOW", " 2.") + " \uD83D\uDCDA Manage Categories");
            System.out.println(ColorCode.colored("YELLOW", " 3.") + " 👥 View All Users");
            System.out.println(ColorCode.colored("YELLOW", " 4.") + " 👑 Promote User to Admin");
            System.out.println(ColorCode.colored("YELLOW", " 5.") + " 📊 View System Statistics");
            System.out.println(ColorCode.colored("RED", " 0.") + " 🚪 Back to Login");
            System.out.println();
            choice = quizApp.getIntInRange(ColorCode.colored("white", "➤ Enter your choice (0-5): "), 0, 5);

            switch (choice) {
                case 1: manageQuestionsMenu(); break;
                case 2: manageCategoriesMenu(); break;
                case 3: System.out.println(viewAllUsers()); break;
                case 4: promoteUserToAdmin(); break;
                case 5: System.out.println(viewSystemStatistics()); break;
                case 0: auth.logout(); return;
                default: System.out.println(ColorCode.error("Invalid Choice! Try Again"));
            }
        }
    }

    private void manageQuestionsMenu() {
        int choice;
        while (true) {
            System.out.println(ColorCode.colored("cyan", ColorCode.boxDouble("           Manage Questions           ")));
            System.out.println(ColorCode.colored("YELLOW", " 1.") + " \uD83D\uDC41\uFE0F  View All Questions");
            System.out.println(ColorCode.colored("YELLOW", " 2.") + " ➕ Add New Question");
            System.out.println(ColorCode.colored("YELLOW", " 3.") + " ✏\uFE0F  Edit Question");
            System.out.println(ColorCode.colored("YELLOW", " 4.") + " \uD83D\uDDD1\uFE0F  Delete Question");
            System.out.println(ColorCode.colored("YELLOW", " 5.") + " \uD83D\uDD00 Move Question to Another Category");
            System.out.println(ColorCode.colored("RED", " 0.") + " \uD83D\uDD19 Back to Admin Menu");

            choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Enter your choice (0-5): "), 0, 5);

            switch (choice) {
                case 1: System.out.println(viewAllQuestions()); break;
                case 2: addNewQuestion(); break;
                case 3: editQuestion(); break;
                case 4: deleteQuestion(); break;
                case 5: moveQuestion(); break;
                case 0: return;
                default:
            }
        }
    }

    String viewAllQuestions() {
        StringBuilder sb = new StringBuilder();
        ArrayList<Category> categories = metaData.getCategories();
        sb.append(ColorCode.colored("cyan", ColorCode.boxDouble("       \uD83D\uDCCB ALL QUESTIONS \uD83D\uDCCB       "))).append("\n");

        int totalQuestions = 0;
        for (Category cat : categories) {
            int categoryId = Integer.parseInt(cat.getCategoryId());
            ArrayList<Question> questions = metaData.getQuestionsByCategory(categoryId);
            if (questions.isEmpty()) continue;

            sb.append(ColorCode.colored("yellow", "\n\uD83D\uDCDA   " + cat.getCategoryName() + " (" + questions.size() + " questions)")).append("\n");
            sb.append(ColorCode.thinSeparator(50)).append("\n");
            for (Question q : questions) {
                sb.append(ColorCode.colored("cyan", q.getQuestionId() + ": ")).append(q.getQuestionText()).append("\n");
                sb.append(ColorCode.DIM + "Difficulty: ").append(q.getDifficulty()).append("| Points: ").append(q.getPoints()).append(ColorCode.RESET).append("\n");
            }
            totalQuestions += questions.size();
        }
        sb.append(ColorCode.separator(50)).append("\n");
        sb.append(ColorCode.colored("green", "\nTotal Questions: " + totalQuestions)).append("\n");
        sb.append(ColorCode.separator(50)).append("\n");
        return sb.toString();
    }

    private void addNewQuestion() {
        System.out.println(ColorCode.colored("Green", ColorCode.boxDouble("       ➕ ADD NEW QUESTION ➕       ")));

        ArrayList<Category> categories = metaData.getCategories();
        System.out.println(ColorCode.header("blue", "\uD83D\uDCDA Available Categories:"));
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(ColorCode.colored("yellow", (i+1) + ". " + categories.get(i).getCategoryName()));
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));
        int catChoice = quizApp.getIntInRange("\nSelect category (0-" + categories.size() + "): ", 0, categories.size());
        if (catChoice == 0) return;

        Category selectedCat = categories.get(catChoice - 1);
        int categoryId = Integer.parseInt(selectedCat.getCategoryId());

        String questionText = quizApp.getString(ColorCode.colored("magenta", "Enter question text or 0 to exit      : "));
        if (questionText.trim().equals("0") || questionText.trim().isEmpty()) return;

        int optionsCount = quizApp.getIntInRange(ColorCode.colored("magenta","\uD83D\uDCCB Number of options (2-4): "), 2, 4);
        String[] options = new String[optionsCount];

        System.out.println();
        for (int i = 0; i < options.length; i++) {
            options[i] = quizApp.getString(ColorCode.YELLOW + "Option " + ColorCode.BLUE+(i+1) + ": " + ColorCode.RESET);
            if (options[i].trim().isEmpty()) {
                System.out.println(ColorCode.error("Option cannot be empty!"));
                return;
            }
        }

        int correctOption = quizApp.getIntInRange(ColorCode.right("Correct option number (1-" + optionsCount + "): ") , 1, optionsCount) - 1;

        int diffChoice, points;
        String difficulty;
        outerLoop: while (true) {
            System.out.println(ColorCode.colored("yellow", "\n⭐ Select Difficulty:"));
            System.out.println("1. Easy (5 points)");
            System.out.println("2. Medium (8 points)");
            System.out.println("3. Hard (10 points)");
            diffChoice = quizApp.getIntInRange(ColorCode.colored("white", "➤ Choose (1-3): "), 1, 3);
            switch (diffChoice) {
                case 1:
                    difficulty = "EASY";
                    points = 5;
                    break outerLoop;
                case 2:
                    difficulty = "MEDIUM";
                    points = 8;
                    break outerLoop;
                case 3:
                    difficulty = "HARD";
                    points = 10;
                    break outerLoop;
                default:
                    System.out.println(ColorCode.error("Invalid difficulty!"));
                    break;
            }
        }
        int questionId = metaData.addQuestion(categoryId, questionText, difficulty, points, options, correctOption);
        if (questionId > 0) {
            System.out.println("\n" + ColorCode.right("Question added successfully! ID: " + questionId));
            logger.info("Question added with ID: " + questionId);
        } else {
            System.out.println(ColorCode.error("Failed to add question!"));
            logger.error("Failed to add question");
        }
    }

    void editQuestion() {
        System.out.println("\n" + ColorCode.colored("blue", ColorCode.boxDouble("    Edit Question    ")));
        String questId = quizApp.getString(ColorCode.colored("cyan", "\n\uD83D\uDD0D Enter Question ID or 0 to exit: "));
        if (questId.trim().equals("0")) return;

        try {
            int questionId = Integer.parseInt(questId);
            Question foundQuest = metaData.getQuestionById(questionId);

            if (foundQuest == null) {
                System.out.println(ColorCode.warning("Question not found!"));
                return;
            }

            System.out.println(ColorCode.colored("cyan", "\n\uD83D\uDCDD Current Question:"));
            System.out.println(ColorCode.BOLD + foundQuest.getQuestionText() + ColorCode.RESET);
            System.out.println(ColorCode.DIM + "[" + foundQuest.getDifficulty() + " | " + foundQuest.getPoints() + " points]" + ColorCode.RESET);

            String newText = quizApp.getString(ColorCode.colored("magenta", "\n✏\uFE0F  New question text (Enter to keep current, 0 to cancel): "));
            if (newText.trim().equals("0")) return;

            if (quizApp.getYesNo("Are you wants to update options? (y/n): ")) {
                for (int i = 0; i < foundQuest.getOptions().length; i++) {
                    System.out.println(ColorCode.BOLD + ColorCode.YELLOW + " " + (i + 1) + ". " + foundQuest.getOptions()[i] + ColorCode.RESET);
                }

                int chosenOption = quizApp.getIntInRange("\n➤ Which option to edit (1-" + foundQuest.getOptions().length + "): ", 1, foundQuest.getOptions().length) - 1;
                String newOption = quizApp.getString("Enter new option instead of this " + foundQuest.getOptions()[chosenOption] + ": ");
//                newOption.trim().isEmpty() ? metaData.;

                if (quizApp.getYesNo(ColorCode.colored("magenta", "Are you wants to change the correct option? (y/n): "))) {
                    int correctOption = quizApp.getIntInRange("Enter correct option number (1-" + foundQuest.getOptions().length + ") or 0 to exit: ", 1, foundQuest.getOptions().length) - 1;
                    if (correctOption == 0) return;
                    foundQuest.setCorrectOption(correctOption);
                }
                foundQuest.getOptions()[chosenOption] = newOption;
            }

            String newDifficulty = foundQuest.getDifficulty();
            int newPoints = foundQuest.getPoints();

            if (quizApp.getYesNo("Update difficulty and points? (y/n): ")) {
                System.out.println("1. Easy (5 points)");
                System.out.println("2. Medium (8 points)");
                System.out.println("3. Hard (10 points)");
                int diffChoice = quizApp.getIntInRange("Choose (1-3): ", 1, 3);

                switch (diffChoice) {
                    case 1:
                        newDifficulty = "EASY";
                        newPoints = 5;
                        break;
                    case 2:
                        newDifficulty = "MEDIUM";
                        newPoints = 8;
                        break;
                    case 3:
                        newDifficulty = "HARD";
                        newPoints = 10;
                        break;
                }
            }

            if (!newText.trim().isEmpty()) {
                metaData.updateQuestion(questionId, newText, newDifficulty, newPoints);
            } else {
                metaData.updateQuestion(questionId, foundQuest.getQuestionText(), newDifficulty, newPoints);
            }

            System.out.println(ColorCode.right("Question updated successfully!"));
            logger.info("Question updated: " + questionId);
        } catch (NumberFormatException e) {
            System.out.println(ColorCode.error("Invalid question ID!"));
        }
    }

    public void deleteQuestion() {
        System.out.println(ColorCode.colored("blue", ColorCode.boxDouble("       🗑️  DELETE QUESTION 🗑️        ")));

        String questId = quizApp.getString(ColorCode.colored("cyan", "\n\uD83D\uDD0D Enter Question ID or 0 to exit: "));
        if (questId.trim().equals("0")) return;

       try {
           int questionId = Integer.parseInt(questId);
           Question q = metaData.getQuestionById(questionId);
           if (q != null) {
               System.out.println(ColorCode.colored("yellow", "Found: " + q.getQuestionText()));

               if (quizApp.getYesNo(ColorCode.colored("red", "Are you sure you want to delete? (y/n): "))) {
                   metaData.removeQuestions(questionId);
                   System.out.println(ColorCode.right("Question deleted!"));
                   logger.info("Question deleted: " + questionId);
               }
           } else {
               System.out.println(ColorCode.warning("Question not found!"));
           }
       } catch (NumberFormatException e) {
           System.out.println(ColorCode.error("Invalid question ID!"));
       }
    }

    private void promoteUserToAdmin() {
        System.out.println("\n" + ColorCode.colored("magenta", ColorCode.boxDouble("       👑 PROMOTE TO ADMIN 👑       ")));

        ArrayList<User> users = metaData.getAllUsers();
        ArrayList<User> regularUsers = new ArrayList<>();

        for (User u : users) {
            if (!u.getRole().equals("ADMIN")) {
                regularUsers.add(u);
            }
        }

        if (regularUsers.isEmpty()) {
            System.out.println(ColorCode.warning("No regular users to promote!"));
            return;
        }

        System.out.println(ColorCode.colored("cyan", "Regular Users:"));
        for (int i = 0; i < regularUsers.size(); i++) {
            User u = regularUsers.get(i);
            System.out.println(ColorCode.colored("yellow", (i+1) + ". " + u.getUserName() + ColorCode.DIM + "(Score: " + u.getTotalScore() +")" + ColorCode.RESET));
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));

        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select user (0-" + regularUsers.size() + "): "), 0, regularUsers.size());
        if (choice == 0) return;

        User selectedUser = regularUsers.get(choice - 1);
        System.out.println();
        System.out.println(ColorCode.warning("Promote " + selectedUser.getUserName() + " to Admin?"));

        if (quizApp.getYesNo(ColorCode.colored("green", "Confirm? (y/n): "))) {
            int userId = Integer.parseInt(selectedUser.getUserId());
            metaData.promoteToAdmin(userId);
            System.out.println(ColorCode.right(selectedUser.getUserName() + " is now an Admin! \uD83D\uDC51"));
            logger.info("User promoted to admin: " + userId);
        }
        else
            System.out.println(ColorCode.colored("yellow", "Promotion cancelled."));
    }

    private void moveQuestion() {
        System.out.println("\n" + ColorCode.colored("magenta", ColorCode.boxDouble("        MOVE QUESTION        ")));

        String questId = quizApp.getString(ColorCode.colored("cyan", "\n🔍 Enter Question ID (0 to cancel): "));
        if (questId.trim().equals("0")) return;

        try {
            int questionId = Integer.parseInt(questId);
            Question foundQuest = metaData.getQuestionById(questionId);

            if (foundQuest == null) {
                System.out.println(ColorCode.right("Question not found!"));
                return;
            }

            int currentCategoryId = Integer.parseInt(foundQuest.getCategory());
            Category fromCategory = metaData.findCategoryById(currentCategoryId);

            System.out.println(ColorCode.colored("yellow", "\n📝 Question: " + foundQuest.getQuestionText()));
            System.out.println(ColorCode.colored("cyan", "Current Category: " + fromCategory.getCategoryName()));

            ArrayList<Category> categories = metaData.getCategories();
            System.out.println(ColorCode.colored("green", "\n📚 Move to:"));

            int displayIndex = 1;
            for (Category cat : categories) {
                if (!cat.getCategoryId().equals(foundQuest.getCategory())) {
                    System.out.println(ColorCode.colored("yellow", displayIndex + ". " + cat.getCategoryName()));
                    displayIndex++;
                }
            }
            System.out.println(ColorCode.colored("red", "0. Cancel"));

            int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select destination (0-" + (displayIndex - 1) + "): "), 0, displayIndex - 1);

            if (choice == 0) return;

            int selectedIndex = 0;
            Category toCategory = null;
            for (Category cat : categories) {
                if (!cat.getCategoryId().equals(foundQuest.getCategory())) {
                    selectedIndex++;
                    if (selectedIndex == choice) {
                        toCategory = cat;
                        break;
                    }
                }
            }

            if (toCategory != null) {
                int newCategoryId = Integer.parseInt(toCategory.getCategoryId());
                metaData.updateQuestionCategory(questionId, newCategoryId);
                System.out.println(ColorCode.right("Question moved to " + toCategory.getCategoryName() + "!"));
                logger.info("Question moved: " + questionId + " to category: " + newCategoryId);
            }

        } catch (NumberFormatException e) {
            System.out.println(ColorCode.error("Invalid question ID!"));
        }
    }

    private void manageCategoriesMenu() {
        int choice;
        while (true) {
            System.out.println("\n" + ColorCode.colored("cyan", ColorCode.boxDouble("       📚 MANAGE CATEGORIES 📚       ")));
            System.out.println(ColorCode.colored("YELLOW", " 1.") + " \uD83D\uDC41\uFE0F  View All Categories");
            System.out.println(ColorCode.colored("YELLOW", " 2.") + " ➕ Add New Question");
            System.out.println(ColorCode.colored("YELLOW", " 3.") + " ✏\uFE0F  Edit Category");
            System.out.println(ColorCode.colored("YELLOW", " 4.") + " \uD83D\uDDD1\uFE0F  Delete Category");
            System.out.println(ColorCode.colored("RED", " 0.") + " \uD83D\uDD19 Back to Admin Menu");

            choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Enter choice (0-4): "), 0, 4);

            switch (choice) {
                case 1: System.out.println(viewAllCategories()); break;
                case 2: addNewCategory(); break;
                case 3: editCategory(); break;
                case 4: deleteCategory(); break;
                case 0: return;
            }
        }
    }

    String viewAllCategories() {
        StringBuilder sb = new StringBuilder("\n"+ColorCode.colored("blue", ColorCode.boxDouble("     All Categories    "))).append("\n");
        ArrayList<Category> categories = metaData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            int categoryId = Integer.parseInt(cat.getCategoryId());
            int totalQuestions = metaData.getCategoryQuestionCount(categoryId);
            sb.append(ColorCode.BOLD).append(ColorCode.WHITE).append(i+1).append(". ").append(cat.getCategoryName()).append("\n");
            sb.append("   ID             : ").append(cat.getCategoryId()).append("\n");
            sb.append("   Description    : ").append(cat.getDescription()).append("\n");
            sb.append("   Total Questions: ").append(totalQuestions).append(ColorCode.RESET).append("\n");

            ArrayList<Question> allQuestions = metaData.getQuestionsByCategory(categoryId);
            int easy = 0, medium = 0, hard = 0;
            for (Question q : allQuestions) {
                String diff = q.getDifficulty().toUpperCase();
                if (diff.equalsIgnoreCase("EASY")) easy++;
                else if (diff.equalsIgnoreCase("MEDIUM")) medium++;
                else if (diff.equalsIgnoreCase("HARD")) hard++;
            }
            sb.append(ColorCode.header("magenta", "Questions in each level")).append("\n");
            sb.append(ColorCode.colored("green","Easy  : " + easy)).append("\n");
            sb.append(ColorCode.colored("yellow","Medium: " + medium)).append("\n");
            sb.append(ColorCode.colored("red","Hard  : " + hard)).append("\n");
            sb.append(ColorCode.separator(50)).append("\n");
        }
        return sb.toString();
    }

    String viewAllUsers() {
        StringBuilder sb = new StringBuilder("\n"+ColorCode.colored("blue", ColorCode.boxDouble("     All users     "))).append("\n");
        ArrayList<User> users = metaData.getAllUsers();
        sb.append(ColorCode.colored("white", "Total Users: " + users.size())).append("\n");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            String roleColor = u.getRole().equals("ADMIN") ? ColorCode.MAGENTA : ColorCode.GREEN;
            sb.append(ColorCode.colored("cyan", "\n" + (i+1) + ". " + u.getUserName())).append("\n");
            sb.append("   Role         : ").append(roleColor).append(u.getRole()).append("\n");
            sb.append("   Total Score  : ").append(u.getTotalScore()).append("\n");
            sb.append("   Quizzes Taken: ").append(u.getQuizzesTaken()).append("\n");
            sb.append(ColorCode.colored("blue",ColorCode.separator(50))).append("\n");
        }
        return sb.toString();
    }

    private void addNewCategory() {
        System.out.println("\n" + ColorCode.colored("green", ColorCode.boxDouble("       ➕ ADD NEW CATEGORY ➕       ")));

        String catName = quizApp.getString(ColorCode.colored("magenta", "\n\uD83D\uDCDD Category Name (0 to cancel): "));
        if (catName.trim().equals("0") || catName.trim().isEmpty()) return;

        if (metaData.findCategoryByName(catName) != null) {
            System.out.println(ColorCode.error("Category already exist!"));
            return;
        }

        String description = quizApp.getString(ColorCode.colored("magenta","📋 Description: "));
        if (description.trim().isEmpty()) {
            description = "New Category - " + catName;
        }

        if (metaData.addCategory(catName, description)) {
            System.out.println("\n" + ColorCode.right("Category added successfully!"));
            logger.info("Category added: " + catName);
        } else {
            System.out.println(ColorCode.error("Failed to add category!"));
        }
    }

    private void editCategory() {
        System.out.println("\n" + ColorCode.colored("blue", ColorCode.boxDouble("       ✏️  EDIT CATEGORY ✏️        ")));

        ArrayList<Category> categories = metaData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(ColorCode.colored("yellow", (i+1) + ". " + categories.get(i).getCategoryName()));
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));

        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select category (0-" + categories.size() + "): "), 0, categories.size());
        if (choice == 0) return;

        Category selectedCat = categories.get(choice - 1);
        System.out.println(ColorCode.colored("cyan", "\n📚 Current: " + selectedCat.getCategoryName()));
        System.out.println(ColorCode.DIM + selectedCat.getDescription() + ColorCode.RESET);

        String newName = quizApp.getString(ColorCode.colored("magenta", "\n✏\uFE0F  New name (Enter to keep, 0 to cancel): "));
        if (newName.trim().equals("0")) return;

        String newDescribe = quizApp.getYesNo("Are you wants to change the description? (y/n)") ? quizApp.getString(ColorCode.colored("magenta","✏\uFE0F  New description (Enter to keep): ")) : "";

        String finalName = newName.trim().isEmpty() ? selectedCat.getCategoryName() : newName;
        String finalDesc = newDescribe.trim().isEmpty() ? selectedCat.getDescription() : newDescribe;

        int categoryId = Integer.parseInt(selectedCat.getCategoryId());
        metaData.updateCategory(categoryId, finalName, finalDesc);

        System.out.println("\n" + ColorCode.right("Category updated successfully!"));
        logger.info("Category updated: " + categoryId);
    }

    private void deleteCategory() {
        System.out.println("\n" + ColorCode.colored("red", ColorCode.boxDouble("       🗑️  DELETE CATEGORY 🗑️        ")));

        ArrayList<Category> categories = metaData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            int categoryId = Integer.parseInt(cat.getCategoryId());
            int questionCount = metaData.getCategoryQuestionCount(categoryId);
            System.out.println(ColorCode.colored("yellow", (i + 1) + ". ") + cat.getCategoryName() +
                    ColorCode.DIM + " (" + questionCount + " questions)" + ColorCode.RESET);
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));

        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select category (0-" + categories.size() + "): "), 0, categories.size());
        if (choice == 0) return;

        Category selectedCat = categories.get(choice - 1);
        int categoryId = Integer.parseInt(selectedCat.getCategoryId());
        int questionCount = metaData.getCategoryQuestionCount(categoryId);

        System.out.println("\n"+ColorCode.warning("Category: " + selectedCat.getCategoryName()));
        System.out.println(ColorCode.warning("This will delete " + questionCount + " questions!"));

        if (quizApp.getYesNo(ColorCode.colored("red", "⚠️  Confirm deletion? (y/n): "))) {
            metaData.removeCategory(categoryId);
            System.out.println(ColorCode.right("Category deleted successfully!"));
        } else {
            System.out.println(ColorCode.warning("Deletion cancelled."));
        }
    }

    String viewSystemStatistics() {
        StringBuilder sb = new StringBuilder(ColorCode.colored("blue", ColorCode.boxDouble("      System Statistics      "))).append("\n");
        ArrayList<User> users = metaData.getAllUsers();
        ArrayList<Category> categories = metaData.getCategories();
        int totalQuestions = metaData.getTotalQuestionsCount();

        sb.append(ColorCode.colored("yellow", "Total Users     : " + users.size())).append("\n");
        sb.append(ColorCode.colored("yellow", "Total Categories: " + categories.size())).append("\n");
        sb.append(ColorCode.colored("yellow", "Total Questions : " + totalQuestions)).append("\n");
        int admins = 0, regularUser =  0;
        for (User u : users) {
            if (u.getRole().equals("ADMIN")) admins++;
            else regularUser++;
        }
        sb.append(ColorCode.MAGENTA).append("Admins       : ").append(admins).append(ColorCode.RESET).append(" | ");
        sb.append(ColorCode.GREEN).append("Regular Users: ").append(regularUser).append(ColorCode.RESET).append("\n");
        int totalQuizzes = 0, totalScore = 0;
        for (User u : users) {
            totalQuizzes += u.getQuizzesTaken();
            totalScore += u.getTotalScore();
        }
        sb.append(ColorCode.colored("magenta", "Total Quizzes Taken: " + totalQuizzes)).append("\n");
        sb.append(ColorCode.colored("cyan","Total Points Earned: " + totalScore)).append("\n");
        sb.append(ColorCode.colored("blue",ColorCode.separator(50))).append("\n");
        return sb.toString();
    }
}
