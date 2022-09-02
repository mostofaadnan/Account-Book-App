package com.foodorder.accountbook.Model;

public class BankInfoModels {
    String name,branchname,accountno,status;
    int id;

    public BankInfoModels(String name, String branchname, String accountno, String status, int id) {
        this.name = name;
        this.branchname = branchname;
        this.accountno = accountno;
        this.status = status;
        this.id = id;
    }

    public BankInfoModels() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
