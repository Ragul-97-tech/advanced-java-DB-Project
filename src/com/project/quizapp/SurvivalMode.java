package com.project.quizapp;

import java.util.ArrayList;

public class SurvivalMode extends QuizMode {
    int maxLives;
    QuizApplication quizApp;

    public SurvivalMode(int maxLives) {
        super("Survival Mode", "Answer until you lose all lives");
        this.maxLives = maxLives;
        this.quizApp = new QuizApplication();
    }

    @Override
    public QuizResult executeQuiz(Quiz quiz, User user) {
        quiz.shuffleQuestions();
        ArrayList<Question> questions = quiz.getQuestions();
        int score = 0;
        int totalPoints = 0;
        for (Question q : questions) 
            totalPoints += q.getPoints();

        int lives = maxLives;
        int questionsAnswered = 0;
        ArrayList<Question> failed = new ArrayList<>();

        System.out.println("\n"+ColorCode.colored("red", ColorCode.boxDouble("\uD83D\uDC80 Survival Mode: " + quiz.getQuizTitle())));
        System.out.println(ColorCode.colored("red", "Lives: " + lives));
        System.out.println(ColorCode.warning("One wrong answer = one life lost!"));
        System.out.println(ColorCode.separator(50));

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            questionsAnswered++;

            System.out.println(ColorCode.colored("yellow", "\n━━━ Question " + questionsAnswered + " ━━━"));
            System.out.println(ColorCode.colored("red", "♥ ".repeat(lives) + ColorCode.DIM + " ♡".repeat(maxLives - lives) + " Lives"));
            System.out.println(ColorCode.colored("white", q.getQuestionText()));
            System.out.println(ColorCode.DIM + "[" + q.getDifficulty() + " | " + q.getPoints() + " points]" + ColorCode.RESET);

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println(ColorCode.colored("cyan", " " + (j+1) + ". " + options[j]));
            }

            int answer = quizApp.getIntInRange(ColorCode.colored("white", "➤ Your answer (1-" + options.length + "): "), 1, options.length);

            if (answer == 0) {
                System.out.println(ColorCode.warning("Quiz exited!"));
                return new QuizResult(score, totalPoints, questionsAnswered, questions.size() - questionsAnswered);
            }


            if (answer - 1 == q.getCorrectOption()) {
                System.out.println(ColorCode.right("Correct! +" +q.getPoints() + " points"));
                score += q.getPoints();
            }
            else {
                lives--;
                failed.add(q);
                System.out.println(ColorCode.error("Wrong! Lost a life! Correct: ") + ColorCode.colored("green", options[q.getCorrectOption()]));
                if (lives == 0) {
                    System.out.println(ColorCode.colored("red", "\n\uD83D\uDC80 GAME OVER! No lives remaining"));
                    return new QuizResult(score, totalPoints, questionsAnswered, questions.size() - questionsAnswered);
                }
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
}