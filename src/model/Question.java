package model;

public class Question {
    private String text;
    private String[] options; // A, B, C, D
    private char correctAnswer;

    public Question(String text, String[] options, char correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public char getCorrectAnswer() { return correctAnswer; }
}
