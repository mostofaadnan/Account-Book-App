package com.foodorder.accountbook.Model;

public class BankActivityModels {
    String bank_name,Account_number,input_date,amount,type,description;
    int id,payment_id;

    public BankActivityModels(String bank_name, String account_number, String input_date, String amount, String type, String description, int id, int payment_id) {
        this.bank_name = bank_name;
        Account_number = account_number;
        this.input_date = input_date;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.id = id;
        this.payment_id = payment_id;
    }
    public BankActivityModels() {

    }
    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return Account_number;
    }

    public void setAccount_number(String account_number) {
        Account_number = account_number;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
}
