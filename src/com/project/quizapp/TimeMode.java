package com.project.quizapp;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class TimeMode extends QuizMode {
    int timePerQuestion;
    QuizApplication quizApp;

    public TimeMode(int timePerQuestion) {
        super(QuizGameModes.Time_Mode, "Answer the questions within time limit");
        this.timePerQuestion = timePerQuestion;
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
        System.out.println(ColorCode.colored("Yellow", "Time per question: " + timePerQuestion + " seconds"));
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
            System.out.println(ColorCode.colored("red", "\n⏰ You have " + timePerQuestion + " seconds!"));
            int answer = getTimedAnswer(options.length, timePerQuestion);
            
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

    int getTimedAnswer(int maxOptions, int timeLimit) {
        final int[] answerHolder = { -1 };  
        final boolean[] answered = { false };
        final Object lock = new Object();

        System.out.print(ColorCode.colored("White", "                      Enter your answer (1-" + maxOptions + ", or 0 to quit): "));
        System.out.flush();

        Thread inputThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    synchronized(lock) {
                        if (answered[0]) break;
                    }
                    
                    int input = quizApp.getIntInRange("", 0, maxOptions);
                    
                    synchronized(lock) {
                        if (!answered[0]) {
                            answerHolder[0] = input;
                            answered[0] = true;
                            lock.notifyAll();
                        }
                    }
                    break;
                    
                } catch (InputMismatchException e) {
                    System.out.print("\r" + ColorCode.colored("Red", "⚠ Invalid input. Enter a number (1-" + maxOptions + "): "));
                    System.out.flush();
                } catch (Exception e) {
                    break;
                }
            }
        });

        Thread timerThread = new Thread(() -> {
            try {
                for (int i = timeLimit; i > 0; i--) {
                    synchronized(lock) {
                        if (answered[0]) return;
                    }
                    System.out.print("\r⏳ Time left: " + i + "s ");
                    System.out.flush();
                    Thread.sleep(1000);
                }
                
                synchronized(lock) {
                    if (!answered[0]) {
                        answered[0] = true;
                        lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                // Timer interrupted, exit gracefully
            }
        });

        inputThread.start();
        timerThread.start();

        // Wait for either answer or timeout
        synchronized(lock) {
            while (!answered[0]) {
                try {
                    lock.wait(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        // Clean up threads
        inputThread.interrupt();
        timerThread.interrupt();
        
        try {
            inputThread.join(500);
            timerThread.join(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Clear the timer line for clean output
        System.out.print("\r                                                 \r");
        System.out.flush();

        return answerHolder[0];
    }
}