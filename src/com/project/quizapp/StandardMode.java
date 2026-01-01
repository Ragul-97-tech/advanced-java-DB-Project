package com.project.quizapp;

import java.util.ArrayList;

public class StandardMode extends QuizMode {
    private QuizApplication quizApp;

    public StandardMode() {
        super("Standard Mode", "Answer all questions at own pace");
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
        
        ArrayList<Integer> failedQuestionIds = new ArrayList<>();
        System.out.println("\n"+ColorCode.colored("cyan", ColorCode.boxDouble("   Starting: " + quiz.getQuizTitle()+ "   ")));
        System.out.println(ColorCode.colored("Cyan", "Total Questions: " + questions.size()));
        System.out.println();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println(ColorCode.colored("yellow", "\n━━━ Question " + (i+1) + "/" + questions.size() + " ━━━"));
            System.out.println(ColorCode.WHITE + q.getQuestionText() + ColorCode.RESET);
            System.out.println("(Difficulty: " + q.getDifficulty() + "| Points: " + q.getPoints() + ")");

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                System.out.println(ColorCode.colored("Cyan"," " +(j+1) + ". " + options[j]));
            }

            int answer = quizApp.getIntInRange(ColorCode.colored("white", "Enter your answer or 0 to exit(0-" + options.length + "): "), 0, options.length);

            if (answer == 0) {
                logger.info("User exited quiz early");
                return new QuizResult(score, totalPoints, questions.size(), failedQuestionIds.size());
            }

            if (answer - 1 == q.getCorrectOption()) {
                System.out.println(ColorCode.right("Correct! +" + q.getPoints() + " points"));
                score += q.getPoints();
            }
            else {
                System.out.println(ColorCode.error("Incorrect! Correct answer: " + ColorCode.colored("green", options[q.getCorrectOption()])));
                failedQuestionIds.add(Integer.parseInt(q.getQuestionId()));
            }
            System.out.println(ColorCode.separator(50));
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
