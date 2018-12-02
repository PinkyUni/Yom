package com.yom;

public class Ingredient {
    private String name;
    private Float amount;

    public Ingredient(String name, Float amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }
}
