package model;

public class Student {
    private String id;          // Example: adma/1311/24
    private String name;
    private String password;
    private int score;

    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.score = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
