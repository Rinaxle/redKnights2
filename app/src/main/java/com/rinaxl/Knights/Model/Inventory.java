package com.rinaxl.Knights.Model;

public class Inventory {

    private String inventoryCode, inventoryDate, item, itemType, quantity, amount, branch, staffName, accountName;

    public Inventory(){}

    public Inventory(String inventoryCode, String inventoryDate, String item, String itemType, String quantity, String amount, String branch, String staffName, String accountName) {
        this.inventoryCode = inventoryCode;
        this.inventoryDate = inventoryDate;
        this.item = item;
        this.itemType = itemType;
        this.quantity = quantity;
        this.amount = amount;
        this.branch = branch;
        this.staffName = staffName;
        this.accountName = accountName;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
