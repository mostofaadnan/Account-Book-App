package com.foodorder.accountbook.Model;

public class personalLoanModels {
    String description, name, type, inputdate;
    float amount;
    int id;

    public personalLoanModels(String description, String name, String type, String inputdate, float amount, int id) {
        this.description = description;
        this.name = name;
        this.type = type;
        this.inputdate = inputdate;
        this.amount = amount;
        this.id = id;
    }

    public personalLoanModels() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
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
