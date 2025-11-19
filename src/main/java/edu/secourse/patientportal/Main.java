package edu.secourse.patientportal;

import edu.secourse.patientportal.models.*;
import edu.secourse.patientportal.services.UserService;
import edu.secourse.patientportal.controllers.UserController;
import edu.secourse.patientportal.services.AppointmentService;
import edu.secourse.patientportal.controllers.AppointmentController;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        boolean state = false;
        UserService userService = new UserService();
        AppointmentService appointmentService = new AppointmentService();
        UserController userController = new UserController(userService);
        AppointmentController appointmentController = new AppointmentController(appointmentService);

        while(state == false) {
            Scanner input = new Scanner(System.in);
            int choice = 0;

            //For testing/demo
            /*Patient newUser = new Patient("john123", "John Smith", "pass123", "johnsmith123@gmail.com");
            Doctor newUser2 = new Doctor("jack123", "Jack Smith", "pass123", "jack123@gmail.com");
            LocalDateTime time = LocalDateTime.of(2025, 12, 12, 8, 30);
            Appointment app = new Appointment(newUser, newUser2, time);
            appointmentService.createAppointment(app);
            userService.createUser(newUser);
            userService.createUser(newUser2);*/

            System.out.println("********************************");
            System.out.println("Patient Portal - Admin Dashboard");
            System.out.println("********************************");
            System.out.println("1. Create User");
            System.out.println("2. Print User");
            System.out.println("3. Delete User ");
            System.out.println("4. Update User Details");
            System.out.println("5. Create Appointment");
            System.out.println("6. Modify Appointment");
            System.out.println("7. Cancel Appointment");
            System.out.println("8. Find Appointment");
            System.out.println("9. Exit");
            System.out.println("********************************");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {

                System.out.println("Please enter a username: ");
                String username = input.nextLine().trim();
                System.out.println("Please enter a password: ");
                String password = input.nextLine().trim();
                System.out.println("Please enter a name: ");
                String name = input.nextLine().trim();
                System.out.println("Please enter a email: ");
                String email = input.nextLine().trim();
                System.out.println("Please enter a role: patient, doctor, admin ");
                String role = input.nextLine().toLowerCase();

                //Need to test more
                if (role.equals("patient")) {
                    User user = new Patient(username, name, password, email);
                    user.setRole(role);
                    userController.createUser(user);
                } else if (role.equals("doctor")) {
                    User user = new Doctor(username, name, password, email);
                    user.setRole(role);
                    userController.createUser(user);
                } else if (role.equals("admin")) {
                    User user = new Admin(username, name, password, email);
                    user.setRole(role);
                    userController.createUser(user);
                } else {
                    System.out.println("Invalid role");
                }
            } else if (choice == 2) {
                System.out.print("Please enter a username: ");
                String username = input.nextLine().trim();

                userService.printUser(username);
            } else if (choice == 3) {
                System.out.print("Please enter a username: ");
                String username = input.nextLine().trim();
                User user = userService.getUser(username);

                userService.printUser(username);

                System.out.println("\nAre you sure you want to delete this user");
                System.out.print("Re-enter the username to confirm or type no to exit: ");
                String confirm = input.nextLine().trim();

                if (confirm.equals(username)) {
                    userService.removeUser(user);
                    System.out.println("User removed successfully");
                } else {
                    System.out.println("Deletion cancelled");
                }
            } else if (choice == 4) {
                System.out.print("Please enter a username: ");
                String oldUsername = input.nextLine().trim();

                System.out.print("Please enter new username: ");
                String newUsername = input.nextLine().trim();

                System.out.print("Please enter new password: ");
                String password = input.nextLine().trim();
                System.out.print("Please enter new name: ");
                String name = input.nextLine().trim();
                System.out.print("Please enter new email: ");
                String email = input.nextLine().trim();

                boolean success = userController.updateUser(oldUsername, newUsername, password, name, email);

                if (success) {
                    System.out.println("\nNew user details are:");
                    userService.printUser(newUsername);
                }
            } else if (choice == 5) {
                try {
                    System.out.print("Please enter patient username: ");
                    String patientUsername = input.nextLine().trim();
                    User patientUser = userService.getUser(patientUsername);

                    System.out.print("Please enter doctor username: ");
                    String doctorUsername = input.nextLine().trim();
                    User doctorUser = userService.getUser(doctorUsername);

                    // Validate patient and doctor
                    if (!(patientUser instanceof Patient)) {
                        System.out.println("Invalid patient username.");
                        return;
                    }

                    if (!(doctorUser instanceof Doctor)) {
                        System.out.println("Invalid doctor username.");
                        return;
                    }

                    System.out.print("Please enter date and time in this format (Year,Month,Day,Hour,Minute): ");
                    String[] sections = input.nextLine().trim().split(",");

                    LocalDateTime appointmentDateTime = LocalDateTime.of(
                            Integer.parseInt(sections[0]),
                            Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Integer.parseInt(sections[3]),
                            Integer.parseInt(sections[4])
                    );

                    Appointment appointment = new Appointment((Patient) patientUser, (Doctor) doctorUser, appointmentDateTime);

                    boolean success = appointmentController.createAppointment(appointment);
                    if (success) {

                        appointmentController.printAppointment(appointment);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error creating the appointment: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }
            } else if (choice == 6) {
                try {
                    System.out.print("Enter appointment ID to modify: ");
                    int appointmentId = input.nextInt();
                    input.nextLine();

                    System.out.print("Please enter patient username: ");
                    String patientUsername = input.nextLine().trim();
                    User patientUser = userService.getUser(patientUsername);

                    System.out.print("Please enter doctor username: ");
                    String doctorUsername = input.nextLine().trim();
                    User doctorUser = userService.getUser(doctorUsername);

                    // Validate patient and doctor
                    if (!(patientUser instanceof Patient)) {

                        System.out.println("Invalid patient username.");
                        return;
                    }
                    if (!(doctorUser instanceof Doctor)) {

                        System.out.println("Invalid doctor username.");
                        return;
                    }

                    System.out.print("Please enter new date and time in this format (Year,Month,Day,Hour,Minute): ");
                    String[] sections = input.nextLine().trim().split(",");

                    LocalDateTime newDateTime = LocalDateTime.of(
                            Integer.parseInt(sections[0]),
                            Integer.parseInt(sections[1]),
                            Integer.parseInt(sections[2]),
                            Integer.parseInt(sections[3]),
                            Integer.parseInt(sections[4])
                    );

                    appointmentController.modifyAppointment(
                            appointmentId,
                            (Patient) patientUser,
                            (Doctor) doctorUser,
                            newDateTime
                    );

                } catch (IllegalArgumentException e) {

                    System.out.println("Error modifying appointment: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }
            } else if (choice == 7) {
                System.out.print("Enter appointment ID to cancel: ");
                int appointmentId = input.nextInt();
                input.nextLine();

                boolean success = appointmentController.cancelAppointment(appointmentId);
            } else if (choice == 8) {
                System.out.print("Enter username: ");
                String username = input.nextLine().trim();
                User user = userService.getUser(username);

                if (user != null) {

                    appointmentController.getAppointmentsForUser(user);
                } else {
                    System.out.println("User not found.");
                }
            } else  if (choice == 9) {
                state = true;
                return;
            }
        }
    }
}