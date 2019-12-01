package com.rinaxl.Knights.Model;

public class User {
    private String studentUsername;
    private String studentPassword;

    public User(){

    }

    public User(String studentUsername, String studentPassword) {
        this.studentUsername = studentUsername;
        this.studentPassword = studentPassword;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }
}
