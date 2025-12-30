package com.project.quizapp;

import java.util.ArrayList;

public class AdminPanel {
    DataManager metaData;
    AuthenticationManager auth;
    QuizApplication quizApp;

    public AdminPanel(DataManager metaData, AuthenticationManager auth) {
        this.metaData = metaData;
        this.auth = auth;
        quizApp = new QuizApplication();
    }

    public void showMenu() {
        User admin = auth.getCurrentUser();

        int choice;
        while (true) {
            System.out.println();
            System.out.println(ColorCode.colored("cyan",ColorCode.boxDouble("              👑 ADMIN:"  + admin.getUserName() + "                             ")));

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
            ArrayList<Question> questions = cat.getQuestions();
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
        String questionId = metaData.generateNextQuestionId(selectedCat.getCategoryId());
        Question newQuestion = new Question(questionId, questionText, options, correctOption, selectedCat.getCategoryId(), difficulty, points);
        metaData.addQuestion(newQuestion);
        System.out.println("\n"+ColorCode.right("Question added successfully! ID: " + questionId));
    }

    void editQuestion() {
        System.out.println("\n"+ColorCode.colored("blue", ColorCode.boxDouble("    Edit Question    ")));
        String questId = quizApp.getString(ColorCode.colored("cyan", "\n\uD83D\uDD0D Enter Question ID or 0 to exit: "));
        if (questId.trim().equals("0")) return;

        Question foundQuest = null;
//        Category foundCat = null;

        ArrayList<Category> categories = metaData.getCategories();
        for (Category cat : categories) {
            for (Question q : cat.getQuestions()) {
                if (q.getQuestionId().equalsIgnoreCase(questId)) {
                    foundQuest = q;
//                    foundCat = cat;
                    break;
                }
            }
            if (foundQuest != null) break;
        }
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
                System.out.println(ColorCode.BOLD + ColorCode.YELLOW + " " + (i+1) + ". " + foundQuest.getOptions()[i] + ColorCode.RESET);
            }

            int chosenOption = quizApp.getIntInRange("\n➤ Which option to edit (1-" + foundQuest.getOptions().length + "): ", 1, foundQuest.getOptions().length) - 1;
            String newOption = quizApp.getString("Enter new option instead of this " + foundQuest.getOptions()[chosenOption] + ": ");
            
            if (quizApp.getYesNo(ColorCode.colored("magenta", "Are you wants to change the correct option? (y/n): "))) {
                int correctOption = quizApp.getIntInRange("Enter correct option number (1-" + foundQuest.getOptions().length + ") or 0 to exit: " , 1, foundQuest.getOptions().length) - 1;
                if (correctOption == 0) return;
                foundQuest.setCorrectOption(correctOption);
            }
            foundQuest.getOptions()[chosenOption] = newOption;
        }
        if (!newText.trim().isEmpty()) {
            foundQuest.setQuestionText(newText);
        }

        metaData.saveAllQuestions();
        System.out.println(ColorCode.right("Question updated successfully!"));
        System.out.println(ColorCode.colored("blue","════════════════════════════════════════\n"));
    }

    void deleteQuestion() {
        System.out.println(ColorCode.colored("blue", ColorCode.boxDouble("       🗑️  DELETE QUESTION 🗑️        ")));

        String questId = quizApp.getString(ColorCode.colored("cyan", "\n\uD83D\uDD0D Enter Question ID or 0 to exit: "));
        if (questId.trim().equals("0")) return;

        boolean found = false;
        ArrayList<Category> categories = metaData.getCategories();

        for (Category cat : categories) {
            for (Question q : cat.getQuestions()) {
                if (q.getQuestionId().equalsIgnoreCase(questId)) {
                    System.out.println(ColorCode.colored("yellow", "Found: " + q.getQuestionText()));
                    if (quizApp.getYesNo((ColorCode.colored("red", "Are you confirm deletion? (y/n): ")))) {
                        metaData.removeQuestions(cat.getCategoryId(), questId);
                        System.out.println(ColorCode.right("Question deleted!"));
                    }
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        if (!found)
            System.out.println(ColorCode.warning("Question not found!"));
    }

    private void promoteUserToAdmin() {
        System.out.println("\n" + ColorCode.colored("magenta", ColorCode.boxDouble("       👑 PROMOTE TO ADMIN 👑       ")));

        ArrayList<User> users = metaData.getUsers();
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
            metaData.promoteToAdmin(selectedUser);
            System.out.println(ColorCode.right(selectedUser.getUserName() + " is now an Admin! \uD83D\uDC51"));
        }
        else
            System.out.println(ColorCode.colored("yellow", "Promotion cancelled."));
    }

    private void moveQuestion() {
        System.out.println("\n" + ColorCode.colored("magenta", ColorCode.boxDouble("        MOVE QUESTION        ")));

        String questId = quizApp.getString(ColorCode.colored("cyan", "\n🔍 Enter Question ID (0 to cancel): "));
        if (questId.trim().equals("0")) return;

        Question foundQuest = null;
        Category fromCategory = null;

        for (Category cat : metaData.getCategories()) {
            for (Question q : cat.getQuestions()) {
                if (q.getQuestionId().equalsIgnoreCase(questId)) {
                    foundQuest = q;
                    fromCategory = cat;
                    break;
                }
            }
            if (foundQuest != null) break;
        }

        if (foundQuest == null) {
            System.out.println(ColorCode.right("Question not found!"));
            return;
        }

        System.out.println(ColorCode.colored("yellow", "\n📝 Question: " + foundQuest.getQuestionText()));
        System.out.println(ColorCode.colored("cyan", "Current Category: " + fromCategory.getCategoryName()));

        ArrayList<Category> categories = metaData.getCategories();
        System.out.println(ColorCode.colored("green", "\n\uD83D\uDCDA Move to:"));
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(fromCategory.getCategoryId())) continue;
            System.out.println(ColorCode.colored("yellow", (i+1) + ". " + categories.get(i).getCategoryName()));
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));
        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select destination (0-" + categories.size() + "): "), 0, categories.size());

        if (choice == 0) return;

        Category toCategory = categories.get(choice-1);
        if (toCategory.getCategoryId().equals(fromCategory.getCategoryId())) {
            System.out.println(ColorCode.warning("Same category selected!"));
            return;
        }

        metaData.moveQuestion(questId, fromCategory.getCategoryId(), toCategory.getCategoryId());
        System.out.println(ColorCode.right("Question moved to " + toCategory.getCategoryName() + "!"));
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
            sb.append(ColorCode.BOLD).append(ColorCode.WHITE).append(i+1).append(". ").append(cat.getCategoryName()).append("\n");
            sb.append("   ID             : ").append(cat.getCategoryId()).append("\n");
            sb.append("   Description    : ").append(cat.getDescription()).append("\n");
            sb.append("   Total Questions: ").append(cat.getTotalQuestions()).append(ColorCode.RESET).append("\n");
            int easy = 0, medium = 0, hard = 0;
            for (Question q : cat.getQuestions()) {
                String diff = q.getDifficulty();
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
        ArrayList<User> users = metaData.getUsers();
        sb.append(ColorCode.colored("white", "Total Users: " + users.size())).append("\n");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            String roleColor = u.getRole().equals("ADMIN") ? ColorCode.MAGENTA : ColorCode.GREEN;
            sb.append(ColorCode.colored("cyan", "\n" + (i+1) + ". " + u.getUserName())).append("\n");
            sb.append("   Role         : ").append(roleColor).append(u.getRole()).append("\n");
            sb.append("   Total Score  : ").append(u.getTotalScore()).append("\n");
            sb.append("   Quizzes Taken: ").append(u.getQuizzesTaken()).append("\n");
            sb.append(ColorCode.colored("blue","════════════════════════════════════════")).append("\n");
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

        String categoryId = metaData.generateNextCategoryId();
        Category newCategory = new Category(categoryId, catName, description);

        metaData.addCategory(newCategory);
        System.out.println("\n"+ColorCode.right("Category added successfully! Category ID: " + categoryId));
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
        if (!newName.trim().isEmpty()) {
            selectedCat.setCategoryName(newName);
        }

        String newDescribe = quizApp.getString(ColorCode.colored("magenta","✏\uFE0F  New description (Enter to keep): "));
        if (!newDescribe.trim().isEmpty()) {
            selectedCat.setDescription(newDescribe);
        }
        metaData.updateCategory();
        System.out.println("\n"+ColorCode.right("Category updated successfully!"));
    }

    private void deleteCategory() {
        System.out.println("\n" + ColorCode.colored("red", ColorCode.boxDouble("       🗑️  DELETE CATEGORY 🗑️        ")));

        ArrayList<Category> categories = metaData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            System.out.println(ColorCode.colored("yellow", (i + 1) + ". ") + cat.getCategoryName() +
                    ColorCode.DIM + " (" + cat.getTotalQuestions() + " questions)" + ColorCode.RESET);
        }
        System.out.println(ColorCode.colored("red", "0. Cancel"));

        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select category (0-" + categories.size() + "): "), 0, categories.size());
        if (choice == 0) return;

        Category selectedCat = categories.get(choice - 1);

        System.out.println("\n"+ColorCode.warning("Category: " + selectedCat.getCategoryName()));
        System.out.println(ColorCode.warning("This will delete " + selectedCat.getTotalQuestions() + " questions!"));

        if (quizApp.getYesNo(ColorCode.colored("red", "⚠️  Confirm deletion? (y/n): "))) {
            metaData.removeCategory(selectedCat.getCategoryId());
            System.out.println(ColorCode.right("Category deleted successfully!"));
        } else {
            System.out.println(ColorCode.warning("Deletion cancelled."));
        }
    }

    String viewSystemStatistics() {
        StringBuilder sb = new StringBuilder(ColorCode.colored("blue", ColorCode.boxDouble("      System Statistics      "))).append("\n");
        ArrayList<User> users = metaData.getUsers();
        ArrayList<Category> categories = metaData.getCategories();
        sb.append(ColorCode.colored("yellow", "Total Users     : " + users.size())).append("\n");
        sb.append(ColorCode.colored("yellow", "Total Categories: " + categories.size())).append("\n");
        sb.append(ColorCode.colored("yellow", "Total Questions : " + metaData.getTotalQuestionsCount())).append("\n");
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
        sb.append(ColorCode.colored("blue","════════════════════════════════════════")).append("\n");
        return sb.toString();
    }
}
