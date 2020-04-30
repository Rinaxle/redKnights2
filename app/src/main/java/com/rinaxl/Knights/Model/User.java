package com.rinaxl.Knights.Model;

public class User {

    private String staffUsername,staffName;
    private String staffPassword;
    private String staffPrivilege;
    private String staffBranch;

    public User(){}

    public User(String staffUsername, String staffName, String staffPassword, String staffPrivilege, String staffBranch) {
        this.staffUsername = staffUsername;
        this.staffName = staffName;
        this.staffPassword = staffPassword;
        this.staffPrivilege = staffPrivilege;
        this.staffBranch = staffBranch;
    }

    public String getStaffUsername() {
        return staffUsername;
    }

    public void setStaffUsername(String staffUsername) {
        this.staffUsername = staffUsername;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffPrivilege() {
        return staffPrivilege;
    }

    public void setStaffPrivilege(String staffPrivilege) {
        this.staffPrivilege = staffPrivilege;
    }

    public String getStaffBranch() {
        return staffBranch;
    }

    public void setStaffBranch(String staffBranch) {
        this.staffBranch = staffBranch;
    }
}
