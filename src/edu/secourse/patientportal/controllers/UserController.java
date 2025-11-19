package edu.secourse.patientportal.controllers;

import edu.secourse.patientportal.models.User;
import edu.secourse.patientportal.services.UserService;

public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    //Create User
    public void createUser(User user) {

        if (!validateUser(user)) {
            System.out.println("Invalid User");
            return;
        }


        try {
            boolean success = userService.createUser(user);
            if (success) {
                System.out.println("User created successfully");
            }
        }catch (IllegalArgumentException e){

                System.out.println("There was an error while creating the user: " + e.getMessage());


            }

        }

        public void printUser(User user) {



            userService.printUser(user);

        }



    public boolean updateUser(String oldUsername, String newUsername, String hashedPassword, String name, String email) {

        try {


            boolean success = userService.updateUser(oldUsername, newUsername, hashedPassword, name, email);
            if (success) {

                System.out.println("User updated successfully");
                return true;

            } else {

                System.out.println("User update failed. User not found");
                return false;
            }

        }catch (IllegalArgumentException e){

            System.out.println("There was an error while updating the user: " + e.getMessage());
            return false;
        }

        }


    public void removeUser(User user) {

            boolean success = userService.removeUser(user);
            if (success) {
                System.out.println("User deleted successfully");
            }

            else{

                System.out.println("Could not delete user. User not found.");

            }







    }

    public boolean validateUser(User user) {

        boolean isValid = true;

        String role = user.getRole();


        if (user.getUsername() == null || user.getUsername().isBlank()) {

            System.out.println("Enter in a valid username");
            isValid = false;


        }

        if (user.getHashedPassword() == null || user.getHashedPassword().isBlank()) {

            System.out.println("Enter in a valid password");
            isValid = false;


        }

        if (user.getName() == null || user.getName().isBlank() || !user.getName().trim().matches("^[A-Za-z\\s'-]+$")) {

            System.out.println("Enter in a name without numbers or special characters");
            isValid = false;


        }


        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            System.out.println("Enter in a valid email address");
            isValid = false;


        }

        if (user.getRole().isBlank() || (!role.equals("patient") && !role.equals("doctor") && !role.equals("admin"))) {

            System.out.println("Enter in a valid role");
            isValid = false;


        }

        return isValid;
    }


}
