package com.shajib.interactivelearningforkids;

public class User {
    private String name;
    private String email;

    private String marks;

    // Default constructor required for Firebase Realtime Database
    public User() {}

    // Constructor
    public User(String name, String email, String marks) {
        this.name = name;
        this.email = email;
        this.marks = marks;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarks(){
        return marks;
    }
    public void setMarks(String marks) {
        this.name = marks;
    }

}
