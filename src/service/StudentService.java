package service;

import model.Student;

import java.io.*;
import java.util.Scanner;

public class StudentService {

    private static final String USERS_DIR = "data/users/";
    private static final String RESULTS_DIR = "data/results/";

    public Student login(Scanner sc) {
        System.out.print("Student ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Password: ");
        String pass = sc.nextLine().trim();

        String fileName = USERS_DIR + id.replace("/", "_") + ".txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("❌ Student ID not found. Please check your ID and try again.");
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String name = br.readLine();
            String fid = br.readLine();
            String storedPass = br.readLine();

            if (!pass.equals(storedPass)) {
                System.out.println("❌ Incorrect password. Please try again.");
                return null;
            }

            System.out.println("✅ Login successful. Welcome, " + name + "!");
            return new Student(fid, name, storedPass);

        } catch (IOException e) {
            System.out.println("⚠️ Unable to login at the moment. Please try again later.");
            return null;
        }
    }

    public void changePassword(Student s, Scanner sc) {

        System.out.print("New Password: ");
        String newPass = sc.nextLine().trim();

        String fileName = USERS_DIR + s.getId().replace("/", "_") + ".txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println(s.getName());
            pw.println(s.getId());
            pw.println(newPass);
            System.out.println("✅ Password updated successfully.");
        } catch (IOException e) {
            System.out.println("⚠️ Error updating password.");
        }
    }

    public void viewResult(Student s) {

        File file = new File(RESULTS_DIR + s.getId().replace("/", "_") + ".txt");

        if (!file.exists()) {
            System.out.println("ℹ️ No result found for your account yet.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("⚠️ Error reading result file.");
        }
    }
}
