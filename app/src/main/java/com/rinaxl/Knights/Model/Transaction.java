package com.rinaxl.Knights.Model;

public class Transaction {

    private String transactionDate,transactionType,transactionStart,transactionEnd, transactionCode;

    public Transaction(){}

    public Transaction(String transactionDate, String transactionType, String transactionStart, String transactionEnd, String transactionCode) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionStart = transactionStart;
        this.transactionEnd = transactionEnd;
        this.transactionCode = transactionCode;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
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


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }


}
