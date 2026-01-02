package com.project.quizapp;

import java.util.ArrayList;

public class ChallengeMode extends QuizMode {
    private QuizApplication quizApp;
    private static final int STREAK_BONUS = 5;

    public ChallengeMode() {
        super(QuizGameModes.Challenge_Mode, "Earn bonus points for answer streaks!");
        this.quizApp = new QuizApplication();
    }

    @Override
    public QuizResult executeQuiz(Quiz quiz, User user) {
        quiz.shuffleQuestions();
        ArrayList<Question> questions = quiz.getQuestions();
        int score = 0;
        int totalPoints = 0;

        for (Question q : questions) {
            totalPoints += q.getPoints();
        }

        ArrayList<Integer> failedQuestionIds = new ArrayList<>();
        int streak = 0;
        int maxStreak = 0;

        System.out.println(ColorCode.colored("Cyan"," 🔥 CHALLENGE "));
        System.out.println(ColorCode.colored("magenta", "🎯 Get " + STREAK_BONUS + " bonus points for every 3-answer streak!"));
        System.out.println(ColorCode.separator(50));

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);

            System.out.println("\n" + ColorCode.colored("cyan", "━━━ Question " + (i + 1) + "/" + questions.size() + " ━━━"));
            if (streak > 0) {
                System.out.println(ColorCode.colored("yellow", "🔥 Streak: " + streak + " | " + (3 - (streak % 3)) + " more for bonus!"));
            }
            System.out.println(ColorCode.BOLD + ColorCode.WHITE + q.getQuestionText() + ColorCode.RESET);
            System.out.println(ColorCode.DIM + "[" + q.getDifficulty() + " | " + q.getPoints() + " points]" + ColorCode.RESET);

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println(ColorCode.CYAN + "  " + (j + 1) + ") " + ColorCode.RESET + options[j]);
            }

            int answer = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Answer (1-" + options.length + " or 0 to exit): "), 0, options.length);

            if (answer == 0) {
                System.out.println(ColorCode.warning("\nQuiz exited!"));
                return new QuizResult(score, totalPoints, i, failedQuestionIds.size());
            }

            if (answer - 1 == q.getCorrectOption()) {
                streak++;
                maxStreak = Math.max(maxStreak, streak);
                int points = q.getPoints();

                if (streak % 3 == 0) {
                    points += STREAK_BONUS;
                    System.out.println(ColorCode.right("Correct! +" + q.getPoints() + " points + " + STREAK_BONUS + " STREAK BONUS! 🔥"));
                } else {
                    System.out.println(ColorCode.right("Correct! +" + points + " points"));
                }
                score += points;
            } else {
                if (streak > 0) {
                    System.out.println(ColorCode.error("Incorrect! Streak broken! Correct: " + ColorCode.colored("green",options[q.getCorrectOption()])));
                } else {
                    System.out.println(ColorCode.error("Incorrect! Correct: " + ColorCode.colored("green",options[q.getCorrectOption()])));
                }
                streak = 0;
                failedQuestionIds.add(Integer.parseInt(q.getQuestionId()));
            }
        }

        if (maxStreak >= 3) {
            System.out.println(ColorCode.colored("yellow", "\n🏆 Max Streak: " + maxStreak + "!"));
        }

        if (user.canSaveProgress()) {
            int userId = Integer.parseInt(user.getUserId());
            DataManager dm = new DataManager();
            for (Integer questionId : failedQuestionIds) {
                dm.addFailedQuestion(userId, questionId);
            }
        }

        return new QuizResult(score, totalPoints, questions.size(), failedQuestionIds.size());
    }
}
