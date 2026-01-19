package com.project.quizapp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeMode extends QuizMode {
    private final int TIME_PER_QUESTION;
    private final int BAR_LENGTH = 25;
    QuizApplication quizApp;

    public TimeMode(int timePerQuestion) {
        super(QuizGameModes.Time_Mode, "Answer the questions within time limit");
        this.TIME_PER_QUESTION = timePerQuestion;
        this.quizApp = new QuizApplication();
    }
    
    public QuizResult executeQuiz(Quiz quiz, User user) {
        quiz.shuffleQuestions();
        ArrayList<Question> questions = quiz.getQuestions();
        int score = 0;
        int totalPoints = 0;
        for (Question q : questions) 
            totalPoints += q.getPoints();
        
        ArrayList<Question> failed = new ArrayList<>();
        System.out.println(ColorCode.colored("Magenta", ColorCode.boxDouble("  ⏰ Timed Quiz: " + quiz.getQuizTitle() + "  ")));
        System.out.println(ColorCode.colored("Yellow", "Time per question: " + TIME_PER_QUESTION + " seconds"));
        System.out.println(ColorCode.separator(50));

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println(ColorCode.colored("yellow", "\n━━━ Question " + (i+1) + "/" + questions.size() + " ━━━"));
            System.out.println(ColorCode.colored("White", q.getQuestionText()));
            System.out.println(ColorCode.DIM + "[" + q.getDifficulty() + " | " + q.getPoints() + " points]" + ColorCode.RESET);

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println(ColorCode.colored("Cyan", (j+1) + ". " + options[j]));
            }
            System.out.println(ColorCode.colored("red", "\n⏰ You have " + TIME_PER_QUESTION + " seconds!"));
            int answer = getTimedAnswer(options.length, TIME_PER_QUESTION);
            
            if (answer == 0) {
                System.out.println("\n"+ColorCode.warning("Quiz exited!"));
                return new QuizResult(score, totalPoints, questions.size(), failed.size());
            }
            
            if (answer == -1) {
                System.out.println(ColorCode.colored("Red", "⏰ Time's up!" ));
                failed.add(q);
            }
            else if (answer - 1 == q.getCorrectOption()) {
                System.out.println(ColorCode.right("correct! +" + q.getPoints() + " points"));
                score += q.getPoints();
            }
            else {
                System.out.println(ColorCode.error("Incorrect!"));
                System.out.println(ColorCode.colored("cyan", "Correct Option: " + q.getOptions()[q.getCorrectOption()]));
                failed.add(q);
            }
            System.out.println(ColorCode.separator(50));
        }
        if (user instanceof RegisteredUser regUser) {
            for (Question q : failed) {
                regUser.addFailedQuestion(q);
            }
        }
        return new QuizResult(score, totalPoints, questions.size(), failed.size());
    }

    private int getTimedAnswer(int maxOptions, int timeLimit) {

        AtomicBoolean timeUp = new AtomicBoolean(false);
        AtomicInteger chosenOption = new AtomicInteger(-1);

        Thread inputThread = new Thread(() -> {
            try {
                int val = quizApp.getIntInRange(
                        "\n Enter your option (1-" + maxOptions + ", 0 to exit): ",
                        0, maxOptions
                );
                chosenOption.set(val);
            } catch (Exception ignored) {
            }
        });

        Thread timerThread = new Thread(() -> {
            for (int t = timeLimit; t >= 0; t--) {

                if (chosenOption.get() != -1) return;

                printProgressBar(t, timeLimit);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }

            timeUp.set(true);
            clearCurrentLine();
        });

        inputThread.start();
        timerThread.start();

        while (true) {
            if (chosenOption.get() != -1) break;
            if (timeUp.get()) break;

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        inputThread.interrupt();
        timerThread.interrupt();

        if (timeUp.get()) return -1;
        return chosenOption.get();
    }


    private void printProgressBar(int timeLeft, int total) {
        int filled = (int) (((total - timeLeft) / (double) total) * BAR_LENGTH);
        String bar = "█".repeat(filled) + "░".repeat(BAR_LENGTH - filled);
        System.out.printf("\r⏳ [%s] %ds ", bar, timeLeft);
        System.out.flush();
    }

    private void clearCurrentLine() {
        System.out.print("\r" + " ".repeat(80) + "\r");
    }
}