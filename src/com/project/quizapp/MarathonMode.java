package com.project.quizapp;

import java.util.ArrayList;

public class MarathonMode extends QuizMode {
    QuizApplication quizApp;
    private static final int ENERGY_START = 100;
    private static final int ENERGY_LOSS_WRONG = 15;
    private static final int ENERGY_GAIN_CORRECT = 5;

    public MarathonMode() {
        super("Marathon Mode", "Answer as many as you can before energy runs out!");
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

        ArrayList<Question> failed = new ArrayList<>();
        int energy = ENERGY_START;
        int questionsAnswered = 0;

        System.out.println(ColorCode.colored("cyan",ColorCode.boxDouble("      ⚡ MARATHON: " + quiz.getQuizTitle() + "    ")));
        System.out.println(ColorCode.colored("green", "⚡ Energy: " + ENERGY_START + " | Correct: +" + ENERGY_GAIN_CORRECT + " | Wrong: -" + ENERGY_LOSS_WRONG));
        System.out.println(ColorCode.separator(50));

        for (int i = 0; i < questions.size() && energy > 0; i++) {
            Question q = questions.get(i);
            questionsAnswered++;

            System.out.println("\n" + ColorCode.colored("yellow", "━━━ Question " + questionsAnswered + " ━━━"));

            String energyBar = getEnergyBar(energy);
            System.out.println(ColorCode.colored("green", "⚡ Energy: " + energyBar + " " + energy + "%"));

            System.out.println(ColorCode.BOLD + ColorCode.WHITE + q.getQuestionText() + ColorCode.RESET);
            System.out.println(ColorCode.DIM + "[" + q.getDifficulty() + " | " + q.getPoints() + " points]" + ColorCode.RESET);

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println(ColorCode.CYAN + "  " + (j + 1) + ") " + ColorCode.RESET + options[j]);
            }

            int answer = quizApp.getIntInRange(ColorCode.colored("white", "\n➤ Answer (1-" + options.length + " or 0 to exit): "), 0, options.length);

            if (answer == 0) {
                System.out.println(ColorCode.colored("yellow", "\n⚠ Quiz exited!"));
                return new QuizResult(score, totalPoints, questionsAnswered, failed.size());
            }

            if (answer - 1 == q.getCorrectOption()) {
                energy = Math.min(100, energy + ENERGY_GAIN_CORRECT);
                System.out.println(ColorCode.right("Correct! +" + q.getPoints() + " points | ⚡ +" + ENERGY_GAIN_CORRECT + " energy"));
                score += q.getPoints();
            } else {
                energy -= ENERGY_LOSS_WRONG;
                failed.add(q);
                System.out.println(ColorCode.error("Wrong! Correct: " + options[q.getCorrectOption()] + " | ⚡ -" + ENERGY_LOSS_WRONG + " energy"));

                if (energy <= 0) {
                    System.out.println(ColorCode.colored("red", "\n⚡ OUT OF ENERGY! Marathon ended!"));
                    break;
                }
            }
        }

        if (user instanceof RegisteredUser regUser) {
            for (Question q : failed) {
                regUser.addFailedQuestion(q);
            }
        }

        return new QuizResult(score, totalPoints, questionsAnswered, failed.size());
    }

    private String getEnergyBar(int energy) {
        int bars = energy / 10;
        String filled = "█".repeat(bars);
        String empty = "░".repeat(10 - bars);

        if (energy > 60) return ColorCode.GREEN + filled + empty + ColorCode.RESET;
        else if (energy > 30) return ColorCode.YELLOW + filled + empty + ColorCode.RESET;
        else return ColorCode.RED + filled + empty + ColorCode.RESET;
    }
}
