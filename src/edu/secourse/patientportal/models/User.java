package edu.secourse.patientportal.models;

import java.util.Objects;

public abstract class User {

    private int accountNumber;
    public static int nextAccountNumber = 1;
    private String username;
    private String hashedPassword;
    private String name;
    private String email;
    private String role;


    public User(String username, String hashedPassword, String name, String email, String role){
       this.accountNumber = nextAccountNumber++;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.email = email;
        this.role = role;
    }


    public int getAccountNumber(){


        return accountNumber;

    }



    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getHashedPassword(){
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}


