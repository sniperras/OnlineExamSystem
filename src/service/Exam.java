package service;

import model.Question;
import model.Student;

import java.io.*;
import java.util.*;

public class Exam {

    private static final String QUESTIONS_DIR = "data/questions/";
    private static final String RESULTS_DIR = "data/results/";
    private static final String ASSIGNMENTS_FILE = "data/assignments.txt";
    private static final String ATTEMPTS_FILE = "data/attempts.txt";

    private static final int MAX_ATTEMPTS = 2;

    // ================= MAIN EXAM ENTRY =================
    public void startExam(Student student, int durationMinutes) {

        String topic = getAssignedTopic(student.getId());

        if (topic == null) {
            System.out.println("No exam assigned to you.");
            return;
        }

        if (!canAttempt(student.getId(), topic)) {
            System.out.println("Maximum attempts reached for topic: " + topic);
            return;
        }

        List<Question> questions = loadQuestionsByTopic(topic);

        if (questions.isEmpty()) {
            System.out.println("No questions available for this topic.");
            return;
        }

        Collections.shuffle(questions); // RANDOMIZATION

        ExamTimer timer = new ExamTimer(durationMinutes);
        timer.start();

        Scanner sc = new Scanner(System.in);
        int score = 0;

        System.out.println("\n===== EXAM STARTED =====");
        System.out.println("Topic: " + topic);
        System.out.println("Duration: " + durationMinutes + " minutes");

        for (Question q : questions) {

            if (timer.isTimeUp()) break;

            System.out.println("\n" + q.getText());
            char opt = 'A';
            for (String o : q.getOptions()) {
                System.out.println(opt++ + ") " + o);
            }

            System.out.print("\nYour Answer (A/B/C/D): ");
            String input = sc.nextLine().trim();


            if (timer.isTimeUp()) break;

            if (!input.isEmpty()) {
                char ans = Character.toUpperCase(input.charAt(0));
                if (ans == q.getCorrectAnswer()) {
                    score++;
                }
            }
        }

        timer.interrupt();
        recordAttempt(student.getId(), topic);
        saveResult(student, topic, score, questions.size());
    }

    // ================= ASSIGNMENT =================
    private String getAssignedTopic(String studentId) {
        File file = new File(ASSIGNMENTS_FILE);
        if (!file.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts[0].equals(studentId)) {
                    return parts[1];
                }
            }
        } catch (IOException ignored) {}

        return null;
    }

    // ================= ATTEMPT LIMIT =================
    private boolean canAttempt(String studentId, String topic) {
        File file = new File(ATTEMPTS_FILE);
        if (!file.exists()) return true;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(studentId + "|" + topic)) {
                    int count = Integer.parseInt(line.split("=")[1]);
                    return count < MAX_ATTEMPTS;
                }
            }
        } catch (IOException ignored) {}

        return true;
    }

    private void recordAttempt(String studentId, String topic) {
        int current = 0;
        File file = new File(ATTEMPTS_FILE);

        try {
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(studentId + "|" + topic)) {
                        current = Integer.parseInt(line.split("=")[1]);
                    }
                }
                br.close();
            }

            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.println(studentId + "|" + topic + "=" + (current + 1));
            pw.close();

        } catch (IOException ignored) {}
    }

    // ================= QUESTIONS =================
    private List<Question> loadQuestionsByTopic(String topic) {
        List<Question> list = new ArrayList<>();
        File file = new File(QUESTIONS_DIR + topic + ".txt");

        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (true) {
                String q = br.readLine();
                if (q == null) break;

                String[] opts = new String[4];
                for (int i = 0; i < 4; i++) {
                    opts[i] = br.readLine();
                }

                char correct = br.readLine().charAt(0);
                br.readLine(); // separator (---)

                list.add(new Question(q, opts, correct));
            }
        } catch (IOException ignored) {}

        return list;
    }

    // ================= RESULT =================
    private void saveResult(Student student, String topic, int score, int total) {

        File file = new File(RESULTS_DIR + student.getId().replace("/", "_") + ".txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("Student Name: " + student.getName());
            pw.println("Student ID: " + student.getId());
            pw.println("Topic: " + topic);
            pw.println("Score: " + score + " / " + total);
            pw.println("Date: " + new Date());
            System.out.println("\nExam submitted successfully.");
        } catch (IOException e) {
            System.out.println("Error saving result.");
        }
    }
}
