package com.foodorder.accountbook.Model;

public class CashActivityModels {
    String inpudate,type,description;
    float amount,balance;
    int id,paymentId;

    public CashActivityModels(String inpudate, String type, String description, float amount, float balance, int id, int paymentId) {
        this.inpudate = inpudate;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
        this.id = id;
        this.paymentId = paymentId;
    }
    public CashActivityModels() {

    }
    public String getInpudate() {
        return inpudate;
    }

    public void setInpudate(String inpudate) {
        this.inpudate = inpudate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
