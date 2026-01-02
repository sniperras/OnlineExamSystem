package service;

import java.io.*;
import java.util.Scanner;

public class AdminService {

    private static final String USERS_DIR = "data/users/";
    private static final String QUESTIONS_DIR = "data/questions/";
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public boolean adminLogin(Scanner sc) {
        System.out.print("Admin Username: ");
        String u = sc.nextLine();
        System.out.print("Admin Password: ");
        String p = sc.nextLine();
        return ADMIN_USER.equals(u) && ADMIN_PASS.equals(p);
    }

    public void assignExamToStudent(Scanner sc) {
        System.out.print("Student ID: ");
        String id = sc.nextLine();

        System.out.print("Topic name: ");
        String topic = sc.nextLine();

        try (PrintWriter pw = new PrintWriter(new FileWriter("data/assignments.txt", true))) {
            pw.println(id + "=" + topic);
            System.out.println("Exam assigned successfully.");
        } catch (IOException e) {
            System.out.println("Error assigning exam.");
        }
    }

    public void registerStudent(Scanner sc) {
        System.out.print("Student ID (e.g adma/1311/24): ");
        String id = sc.nextLine();
        System.out.print("Student Name: ");
        String name = sc.nextLine();
        System.out.print("Initial Password: ");
        String pass = sc.nextLine();

        String fileName = USERS_DIR + id.replace("/", "_") + ".txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println(id);
            pw.println(name);
            pw.println(pass);
            System.out.println("Student registered successfully.");
        } catch (IOException e) {
            System.out.println("Error registering student.");
        }
    }

    public void createTopic(Scanner sc) {
        System.out.print("Enter topic name: ");
        String topic = sc.nextLine();
        File file = new File(QUESTIONS_DIR + topic + ".txt");
        try {
            if (file.createNewFile()) {
                System.out.println("Topic created.");
            } else {
                System.out.println("Topic already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error creating topic.");
        }
    }

    public void addQuestion(Scanner sc) {
        System.out.print("Enter topic name: ");
        String topic = sc.nextLine();
        File file = new File(QUESTIONS_DIR + topic + ".txt");

        if (!file.exists()) {
            System.out.println("Topic does not exist.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            System.out.print("Question: ");
            pw.println(sc.nextLine());
            for (char c = 'A'; c <= 'D'; c++) {
                System.out.print("Option " + c + ": ");
                pw.println(sc.nextLine());
            }
            System.out.print("Correct answer (A-D): ");
            pw.println(sc.nextLine());
            pw.println("---");
            System.out.println("Question added.");
        } catch (IOException e) {
            System.out.println("Error adding question.");
        }
    }
}
