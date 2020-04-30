package com.rinaxl.Knights.Model;

public class Transaction {

    private String transactionKey,transactionDate,transactionType,transactionStart,transactionEnd, transactionCode,patientCode,patientName, patientBranch;
    private String imgName;
    private String imgUrl;


    public Transaction(){}

    public Transaction(String transactionKey,String transactionDate, String transactionType, String transactionStart, String transactionEnd, String transactionCode, String patientCode, String patientName,String patientBranch, String imgName, String imgUrl) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionStart = transactionStart;
        this.transactionEnd = transactionEnd;
        this.transactionCode = transactionCode;
        this.transactionKey = transactionKey;
        this.patientCode = patientCode;
        this.patientName = patientName;
        this.patientBranch = patientBranch;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public String getPatientBranch() {
        return patientBranch;
    }

    public void setPatientBranch(String patientBranch) {
        this.patientBranch = patientBranch;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStart() {
        return transactionStart;
    }

    public void setTransactionStart(String transactionStart) {
        this.transactionStart = transactionStart;
    }

    public String getTransactionEnd() {
        return transactionEnd;
    }

    public void setTransactionEnd(String transactionEnd) {
        this.transactionEnd = transactionEnd;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
