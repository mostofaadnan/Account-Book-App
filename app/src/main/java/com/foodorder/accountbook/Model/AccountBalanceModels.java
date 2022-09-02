package com.foodorder.accountbook.Model;

public class AccountBalanceModels {
    String bank_name,account_number;
    float credit,debit,balance;

    public AccountBalanceModels(String bank_name, String account_number, float credit, float debit, float balance) {
        this.bank_name = bank_name;
        this.account_number = account_number;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
    }

    public AccountBalanceModels() {

    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public float getCredit() {
        return credit;
    }

    public float setCredit(float credit) {
        this.credit = credit;
        return credit;
    }

    public float getDebit() {
        return debit;
    }

    public float setDebit(float debit) {
        this.debit = debit;
        return debit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
