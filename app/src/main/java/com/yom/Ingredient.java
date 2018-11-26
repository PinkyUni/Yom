package com.yom;

public class Ingredient {
    private String name;
    private int amount;

    public Ingredient(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }
}
