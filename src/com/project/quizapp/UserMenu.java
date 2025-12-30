package com.project.quizapp;

import java.util.ArrayList;

public class UserMenu {
    DataManager metaData;
    AuthenticationManager auth;
    QuizApplication quizApp;

    public UserMenu(DataManager metaData, AuthenticationManager auth) {
        this.metaData = metaData;
        this.auth = auth;
        quizApp = new QuizApplication();
    }

    public void show() {
        User user = auth.getCurrentUser();
        System.out.println();
        System.out.println(ColorCode.colored("blue",ColorCode.boxDouble("           Welcome, " +  user.getUserName() + "           ")));
        System.out.println(ColorCode.colored("green", "👤 Role: " + user.getRole() +
                " | 🎯 Total Score: " + user.getTotalScore() +
                " | 📝 Quizzes Taken: " + user.getQuizzesTaken()));


        int choice;
        while (true) {
            System.out.println();
            System.out.println("\n" + ColorCode.colored("cyan", ColorCode.boxDouble("           🎮 USER MENU 🎮           ")));
            System.out.println(ColorCode.colored("yellow", " 1. 🎯 Start New Quiz"));
            System.out.println(ColorCode.colored("yellow", " 2. 📝 Practice Failed Questions"));
            System.out.println(ColorCode.colored("yellow", " 3. 📊 View My Statistics"));
            System.out.println(ColorCode.colored("yellow", " 4. 📜 View Quiz History"));
            System.out.println(ColorCode.colored("yellow", " 5. 🏆 View Leaderboard"));
            System.out.println(ColorCode.colored("yellow", " 6. 📚 Browse Categories"));
            System.out.println(ColorCode.colored("red",    " 0. 🔙 Back to Login / Exit"));
            System.out.println(ColorCode.separator(50));

            choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Enter your choice: "), 0, 6);

            switch (choice) {
                case 1:
                    startQuiz(user);
                    break;
                case 2:
                    practiceFailedQuestions(user);
                    break;
                case 3:
                    System.out.println(viewStatistics(user));
                    break;
                case 4:
                    System.out.println(viewQuizHistory(user));
                    break;
                case 5:
                    System.out.println(viewLeaderboard());
                    break;
                case 6:
                    System.out.println(browseCategories());
                    break;
                case 0:
                    auth.logout();
                    return;
            }

        }
    }

    public void startQuiz(User u) {
        Category selectedCategory = selectCategory();
        if (selectedCategory == null) return;

        String difficulty = selectDifficulty();
        if (difficulty == null) return;

        QuizMode mode = selectMode();
        if (mode == null) return;

        ArrayList<Question> filteredQuestions;
        if (difficulty.equals("ALL"))
            filteredQuestions = selectedCategory.getQuestions();
        else
            filteredQuestions = selectedCategory.getQuestionsByDifficulty(difficulty);
        if (filteredQuestions.isEmpty()) {
            System.out.println(ColorCode.error("No questions available for this difficulty"));
            return;
        }

        String quizId = "QUIZ_" + System.currentTimeMillis();
        Quiz quiz = new Quiz(quizId, selectedCategory.getCategoryName() + " Quiz", selectedCategory.getCategoryId(), filteredQuestions, mode.getModeName(), 0);
        quiz.shuffleQuestions();

        QuizResult result = mode.executeQuiz(quiz, u);
        displayResult(result, quiz.getQuizTitle());

        if (u instanceof RegisteredUser regUser) {
            regUser.addToTotalScore(result.getScore());
            regUser.incrementQuizzesTaken();

            String attemptId = "ATT_" + System.currentTimeMillis();
            QuizAttempt attempt = new QuizAttempt(attemptId, quiz.getQuizId(), quiz.getQuizTitle(), result.getScore(), result.getTotalPoints(), quiz.getCategory(), mode.getModeName());
            regUser.addQuizAttempt(attempt);
            metaData.updateUser();
        }
    }

    private Category selectCategory() {
        ArrayList<Category> categories = metaData.getCategories();
        System.out.println("\n"+ColorCode.colored("cyan", ColorCode.boxDouble("       \uD83D\uDCDA SELECT CATEGORY \uD83D\uDCDA       ")));
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            System.out.println(ColorCode.colored("yellow", (i+1) + ". ") + ColorCode.colored("white", cat.getCategoryName()) + ColorCode.DIM + " (" + cat.getTotalQuestions() + " questions)");
            System.out.println("   " + cat.getDescription() + ColorCode.RESET);
        }
        System.out.println(ColorCode.colored("red", "0 to Cancel"));
        System.out.println(ColorCode.separator(50));
        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Select category (0-" + categories.size() + "): "), 0, categories.size());
        if (choice == 0) return null;
        return categories.get(choice-1);
    }

    private String selectDifficulty() {
        System.out.println("\n"+ColorCode.colored("cyan", ColorCode.boxDouble("      ⭐ Select Difficulty ⭐      ")));
        System.out.println(ColorCode.BOLD + ColorCode.GREEN + " 1." + ColorCode.RESET + " ⭐ Easy    - Perfect for beginners");
        System.out.println(ColorCode.BOLD + ColorCode.YELLOW + " 2." + ColorCode.RESET + " ⭐ ⭐ Medium - Test your knowledge");
        System.out.println(ColorCode.BOLD + ColorCode.RED + " 3." + ColorCode.RESET + " ⭐ ⭐ ⭐ Hard    - Challenge yourself!");
        System.out.println(ColorCode.BOLD + ColorCode.CYAN + " 4." + ColorCode.RESET + " \uD83C\uDF1F All Levels - Mixed difficulty");
        System.out.println(ColorCode.BOLD + ColorCode.RED + " 0. to Cancel" + ColorCode.RESET);
        System.out.println(ColorCode.separator(50));
        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\nSelect Difficulty (0-4): "), 0, 4);

        switch (choice) {
            case 0: return null;
            case 1: return "EASY";
            case 2: return "MEDIUM";
            case 3: return "HARD";
            case 4: return "ALL";
            default: return null;
        }
    }

    QuizMode selectMode() {
        System.out.println("\n" + ColorCode.colored("cyan", ColorCode.boxDouble("         🎮 SELECT QUIZ MODE 🎮         ")));
        System.out.println(ColorCode.colored("green",   " 1. 📝 Standard Mode - Take your time, no pressure"));
        System.out.println(ColorCode.colored("magenta", " 2. ⏰ Timed Mode - Race against 15s per question"));
        System.out.println(ColorCode.colored("red",     " 3. 💀 Survival Mode - 3 lives, one mistake = -1 life"));
        System.out.println(ColorCode.colored("yellow",  " 4. 🔥 Challenge Mode - Streak bonuses for 3+ correct"));
        System.out.println(ColorCode.colored("blue",    " 5. ⚡ Marathon Mode - Keep going until energy runs out"));
        System.out.println(ColorCode.colored("red",     " 0. ← Cancel"));
        System.out.println(ColorCode.separator(50));
        int choice = quizApp.getIntInRange(ColorCode.colored("white", "\nSelect mode (0-3): "), 0, 5);

        switch (choice) {
            case 0: return null;
            case 1: return new StandardMode();
            case 2: return new TimeMode(15);
            case 3: return new SurvivalMode(3);
            case 4: return new ChallengeMode();
            case 5: return new MarathonMode();
            default: return null;
        }
    }

    void displayResult(QuizResult result, String quizTitle) {
        System.out.println("\n" + ColorCode.colored("cyan","╔════════════════════════════════════════════════════╗"));
        System.out.println(ColorCode.colored("cyan","║              🎉 QUIZ COMPLETED! 🎉                 ║"));
        System.out.println(ColorCode.colored("cyan","╚════════════════════════════════════════════════════╝"));

        System.out.println(ColorCode.colored("cyan", "📝 Quiz: " + quizTitle));
        System.out.println(ColorCode.colored("green", "🎯 Score: " + result.getScore() + "/" + result.getTotalPoints()));
        System.out.println(ColorCode.colored("yellow", "📊 Percentage: " + String.format("%.2f", result.getPercentage()) + "%"));
        System.out.println(ColorCode.colored("magenta", "🏆 Grade: " + result.getGrade()));
        System.out.println(ColorCode.colored("red", "❌ Failed: " + result.getFailedCount() + " questions"));
        System.out.println(ColorCode.separator(50));
    }

    void practiceFailedQuestions(User user) {
        if (!(user instanceof RegisteredUser regUser)) {
            System.out.println(ColorCode.error("This feature is only available for registered users!"));
            return;
        }
        ArrayList<Question> failedQuestions = regUser.getFailedQuestion();
        if (failedQuestions.isEmpty()) {
            System.out.println(ColorCode.right("Great! You have no failed questions."));
            return;
        }
        System.out.println(ColorCode.YELLOW + "\n\uD83D\uDCDDYou have " + failedQuestions.size() + " failed questions." + ColorCode.RESET);
        if (!quizApp.getYesNo("Are you wants to Practice now? (y/n): ")) return;
        String quizId = "PRACTICE_" + System.currentTimeMillis();
        Quiz practiceQuiz = new Quiz(quizId, "Practice Failed Questions", "MIXED", failedQuestions, "Standard", 0);

        QuizMode mode = new StandardMode();
        QuizResult result = mode.executeQuiz(practiceQuiz, user);
        displayResult(result, "Practice Quiz");
        
        if (result.getFailedCount() == 0) {
            regUser.clearFailedQuestions();
            metaData.updateUser();
            System.out.println(ColorCode.GREEN + "\uD83C\uDF89 Congratulations! All practice questions answered correctly!" + ColorCode.RESET);
        }
    }

    private String viewStatistics(User u) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(ColorCode.colored("cyan", ColorCode.boxDouble("    Your statistics    "))).append("\n");
        sb.append(ColorCode.YELLOW).append(ColorCode.BOLD).append("\uD83D\uDC64Username        : ").append(ColorCode.GREEN).append(u.getUserName()).append(ColorCode.RESET).append("\n");
        sb.append(ColorCode.YELLOW).append(ColorCode.BOLD).append("\uD83C\uDFAFTotal Score     : ").append(ColorCode.GREEN).append(u.getTotalScore()).append(ColorCode.RESET).append("\n");
        sb.append(ColorCode.YELLOW).append(ColorCode.BOLD).append("\uD83D\uDCDDQuizzes Taken   : ").append(ColorCode.GREEN).append(u.getQuizzesTaken()).append(ColorCode.RESET).append("\n");
        if (u instanceof  RegisteredUser regUser) {
            sb.append(ColorCode.MAGENTA).append(ColorCode.BOLD).append("Average Score   : ").append(ColorCode.GREEN).append(String.format("%.2f",regUser.getAverageScore())).append(ColorCode.RESET).append("\n");
//            sb.append(ColorCode.MAGENTA).append(ColorCode.BOLD).append("Average percent : ").append(ColorCode.GREEN).append(String.format("%.2f",regUser.getAveragePercentage())).append(ColorCode.RESET).append("\n");
            sb.append(ColorCode.YELLOW).append("Failed Questions: ").append(regUser.getMaxFailedQuestionsCount()).append("\n");
        }
        sb.append(ColorCode.separator(50));
        return sb.toString();
    }

    private String viewQuizHistory(User user) {
        StringBuilder sb = new StringBuilder();
        if (!(user instanceof RegisteredUser regUser)) {
            sb.append(ColorCode.error("Quiz history is only available for registered users!")).append("\n");
            return sb.toString();
        }
        ArrayList<QuizAttempt> history = regUser.getQuizHistory();
        if (history.isEmpty()) {
            sb.append(ColorCode.YELLOW).append("📜 No quiz history available").append(ColorCode.RESET).append("\n");
            return sb.toString();
        }
        sb.append("\n").append(ColorCode.colored("cyan", ColorCode.boxDouble("      📜 Quiz History 📜     "))).append("\n");
        sb.append(ColorCode.colored("yellow", "Showing last " + Math.min(10, history.size()) + " attempts")).append("\n");
        sb.append(ColorCode.separator(50));
        for (int i = history.size() - 1; i >= 0 && i >= history.size() - 10; i--) {
            QuizAttempt attempt = history.get(i);
            sb.append(ColorCode.CYAN).append("\n").append(history.size() - i).append(". ").append(ColorCode.BOLD).append(attempt.getQuizTitle());
            sb.append(" |  Score: ").append(attempt.getScore()).append("/").append(attempt.getTotalQuestionsMarks()).append("(").append(String.format("%.2f", attempt.getPercentage())).append(")");
            sb.append(" |  Mode : ").append(attempt.getMode()).append(" | Date: ").append(attempt.getTimestamp()).append("\n");
        }
        sb.append(ColorCode.separator(50));
        return sb.toString();
    }

    private String viewLeaderboard() {

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(ColorCode.colored("cyan",ColorCode.boxDouble("             🏆 LEADERBOARD (TOP 10) 🏆            "))).append("\n");

        ArrayList<User> users = metaData.getUsers();
        for (int i = 0; i < users.size(); i++) {
            int currentIdx = i;
            if (users.get(currentIdx).getTotalScore() == 0) continue;
            while (currentIdx > 0 && (users.get(currentIdx).getTotalScore() > users.get(currentIdx - 1).getTotalScore())) {
                User temp = users.get(currentIdx);
                users.set(currentIdx, users.get(currentIdx-1));
                users.set(currentIdx-1, temp);
                currentIdx--;
            }
        }
        int count = 0;
        for (int i = 0; i < users.size() && count < 10; i++) {
            User u = users.get(i);
            if (u.getTotalScore() == 0) continue;

            count++;
            String medal = count == 1 ? "🥇" : count == 2 ? "🥈" : count == 3 ? "🥉" : "  ";
            String color = count <= 3 ? ColorCode.YELLOW : ColorCode.CYAN;

            sb.append(color).append(medal).append(" ").append(count).append(". ").append(ColorCode.BOLD).append(u.getUserName()).append(ColorCode.RESET).append(ColorCode.GREEN).append(" - ").append(u.getTotalScore()).append(" points").append(ColorCode.RESET).append(ColorCode.DIM).append(" (").append(u.getQuizzesTaken()).append(" quizzes)").append(ColorCode.RESET).append("\n");
        }

        if (count == 0) {
            sb.append(ColorCode.colored("yellow", "No scores yet. Be the first!")).append("\n");
        }
        return sb.toString();
    }

    private String browseCategories() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(ColorCode.colored("cyan", ColorCode.boxDouble("       📚 ALL CATEGORIES 📚       ")));
        ArrayList<Category> categories = metaData.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            Category cat = categories.get(i);
            sb.append(ColorCode.colored("yellow", "\n" + (i+1) + ". " + cat.getCategoryName())).append("\n");
            sb.append("   ").append(ColorCode.DIM).append(cat.getDescription()).append(ColorCode.RESET).append("\n");
            sb.append("  \uD83D\uDCDD Questions: ").append(cat.getTotalQuestions()).append("\n");

            int easy = 0, medium = 0, hard = 0;
            for (Question q : cat.getQuestions()) {
                String diff = q.getDifficulty().toUpperCase();
                if (diff.equals("EASY")) easy++;
                else if (diff.equals("MEDIUM")) medium++;
                else if (diff.equals("HARD")) hard++;
            }
            sb.append("   " + ColorCode.GREEN + "Easy: ").append(easy).append(ColorCode.RESET).append(" | ").append(ColorCode.YELLOW).append("Medium: ").append(medium).append(ColorCode.RESET).append(" | ").append(ColorCode.RED).append("Hard: ").append(hard).append(ColorCode.RESET).append("\n");
        }
        sb.append(ColorCode.separator(50));
        return sb.toString();
    }
}
