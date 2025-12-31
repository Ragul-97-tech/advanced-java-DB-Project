package com.project.quizapp;

public class Question {
    private int questionId;
    private String questionText;
    private String[] options;
    private int correctOption;
    private String category;
    private String difficulty;
    private int points;

    public Question(int questionId, String questionText, String[] options, int correctOption, int category, String difficulty, int points) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
        this.category = category;
        this.difficulty = difficulty;
        this.points = points;
    }

    public int getQuestionId() {
        return questionId;
    }
    public String getQuestionText() {
        return questionText;
    }
    public String[] getOptions() {
        return options;
    }
    public int getCorrectOption() {
        return correctOption;
    }
    public String getCategory() {
        return category;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public int getPoints() {
        return points;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setOptions(String[] options) {
        this.options = options;
    }
    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String toFileFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionId).append("|").append(questionText).append("|");
        for (int i = 0; i < options.length; i++) {
            sb.append(options[i]);
            if (i < options.length - 1) sb.append("~");
        }
        sb.append("|").append(correctOption).append("|").append(category).append("|").append(difficulty).append("|").append(points);
        return sb.toString();
    }
    public static Question fromFileFormat(String line) {
        String[] parts = line.split("\\|");
        String id = parts[0];
        String text = parts[1];
        String[] opts = parts[2].split("~");
        int correct = Integer.parseInt(parts[3]);
        String cat = parts[4];
        String diff = parts[5];
        int pts = Integer.parseInt(parts[6]);
        return new Question(id, text, opts, correct, cat, diff, pts);
    }
}
