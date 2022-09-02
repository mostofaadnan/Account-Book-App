package com.foodorder.accountbook.Model;

public class ExpensesActivityModels {
    String inputdate,expensesType,source,bank,accountNo,description;
    float amount;
    int id;
    public ExpensesActivityModels(String inputdate, String expensesType, String source, String bank, String accountNo, String description, float amount, int id) {
        this.inputdate = inputdate;
        this.expensesType = expensesType;
        this.source = source;
        this.bank = bank;
        this.accountNo = accountNo;
        this.description = description;
        this.amount = amount;
        this.id = id;
    }

    public ExpensesActivityModels() {

    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }

    public String getExpensesType() {
        return expensesType;
    }

    public void setExpensesType(String expensesType) {
        this.expensesType = expensesType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
