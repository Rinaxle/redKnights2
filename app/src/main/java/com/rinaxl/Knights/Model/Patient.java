package com.rinaxl.Knights.Model;

import java.util.Date;

public class Patient {

    private String patientName;
    private String patientAge;
    private String patientBranch;
    private String patientCode;
    private String transactionCount;
    //   private String patientAddress;
    //   private Date patientBirthday;
    //  private String patientTransaction;


    public Patient(){}

    public Patient( String patientCode, String patientName, String patientAge, String patientBranch,String transactionCount) {
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientBranch = patientBranch;
        this.patientCode = patientCode;
        this.transactionCount = transactionCount;
    }

    public String getTransactionCount(){return transactionCount;}

    public void setTransactionCount(String transactionCount){this.transactionCount = transactionCount;}

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientBranch() {
        return patientBranch;
    }

    public void setPatientBranch(String patientBranch) {
        this.patientBranch = patientBranch;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }
}
