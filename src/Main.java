/*
* STUDENT NAME                              ID
1. NATNAEL BIZUNEH                  ADMA/1311/24    ADMA/0699/25
2. BEZANESH MELKA                   ADMA/0523/23
3. MESKEREME ABAKO                  ADMA/1637/23
4. GENETE AMSAL                     ADMA/1628/23
5. MAHLET TAMIRAT                   ADMA/1534/23
*
* */


import model.Student;
import service.AdminService;
import service.Exam;
import service.StudentService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AdminService adminService = new AdminService();
        StudentService studentService = new StudentService();
        Exam examService = new Exam();

        while (true) {

            System.out.println("\n===== ONLINE EXAMINATION SYSTEM =====");
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Quit");
            System.out.print("Select option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            // ================= STUDENT LOGIN =================
            if (choice == 1) {

                Student student = studentService.login(sc);
                if (student == null) continue;

                while (true) {
                    System.out.println("\n----- STUDENT DASHBOARD -----");
                    System.out.println("1. Take Exam");
                    System.out.println("2. View Result");
                    System.out.println("3. Change Password");
                    System.out.println("4. Logout");
                    System.out.print("Select option: ");

                    int sChoice;
                    try {
                        sChoice = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    if (sChoice == 1) {
                        // Exam duration can later be made dynamic per topic
                        examService.startExam(student, 5);
                    }
                    else if (sChoice == 2) {
                        studentService.viewResult(student);
                    }
                    else if (sChoice == 3) {
                        studentService.changePassword(student, sc);
                    }
                    else if (sChoice == 4) {
                        System.out.println("Logged out successfully.");
                        break;
                    }
                    else {
                        System.out.println("Invalid option.");
                    }
                }
            }

            // ================= ADMIN LOGIN =================
            else if (choice == 2) {

                if (!adminService.adminLogin(sc)) {
                    System.out.println("Invalid admin credentials.");
                    continue;
                }

                while (true) {
                    System.out.println("\n----- ADMIN DASHBOARD -----");
                    System.out.println("1. Register Student");
                    System.out.println("2. Create Topic");
                    System.out.println("3. Add Question to Topic");
                    System.out.println("4. Assign Exam to Student");
                    System.out.println("5. Logout");
                    System.out.print("Select option: ");

                    int aChoice;
                    try {
                        aChoice = Integer.parseInt(sc.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                        continue;
                    }

                    if (aChoice == 1) {
                        adminService.registerStudent(sc);
                    }
                    else if (aChoice == 2) {
                        adminService.createTopic(sc);
                    }
                    else if (aChoice == 3) {
                        adminService.addQuestion(sc);
                    }
                    else if (aChoice == 4) {
                        adminService.assignExamToStudent(sc);
                    }
                    else if (aChoice == 5) {
                        System.out.println("Admin logged out.");
                        break;
                    }
                    else {
                        System.out.println("Invalid option.");
                    }
                }
            }

            // ================= EXIT =================
            else if (choice == 3) {
                System.out.println("Exiting system. Goodbye.");
                break;
            }

            else {
                System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}
