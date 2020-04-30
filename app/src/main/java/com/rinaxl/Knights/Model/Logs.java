package com.rinaxl.Knights.Model;

public class Logs {

    private String patientCount, inventoryCount;

    public Logs(){}

    public Logs(String patientCount, String inventoryCount) {
        this.patientCount = patientCount;
        this.inventoryCount = inventoryCount;
    }

    public String getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(String patientCount) {
        this.patientCount = patientCount;
    }

    public String getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(String inventoryCount) {
        this.inventoryCount = inventoryCount;
    }
}
