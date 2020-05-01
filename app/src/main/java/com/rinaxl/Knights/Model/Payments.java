package com.rinaxl.Knights.Model;

public class Payments {

    private String paymentCode,paymentDate, paymentNotes, paymentMethod, paymentAmount, pPatientName, pPatientCode, staffName, accountName;

    public Payments(){}

    public Payments(String paymentCode, String paymentDate, String paymentNotes, String paymentMethod, String paymentAmount, String pPatientName, String pPatientCode, String staffName, String accountName) {
        this.paymentCode = paymentCode;
        this.paymentDate = paymentDate;
        this.paymentNotes = paymentNotes;
        this.paymentMethod = paymentMethod;
        this.paymentAmount = paymentAmount;
        this.pPatientName = pPatientName;
        this.pPatientCode = pPatientCode;
        this.staffName = staffName;
        this.accountName = accountName;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPPatientName() {
        return pPatientName;
    }

    public void setPPatientName(String patientName) {
        this.pPatientName = patientName;
    }

    public String getPPatientCode() {
        return pPatientCode;
    }

    public void setPPatientCode(String patientCode) {
        this.pPatientCode = patientCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
