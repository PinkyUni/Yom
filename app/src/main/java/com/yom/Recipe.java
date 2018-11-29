package com.yom;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private Drawable img;
    private ArrayList<Ingredient> ingregients;
    private ArrayList<String> cookingSteps;

    public Recipe(String name, Drawable img, ArrayList<Ingredient> ingregients, ArrayList<String> cookingSteps) {
        this.name = name;
        this.img = img;
        this.ingregients = ingregients;
        this.cookingSteps = cookingSteps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingregients;
    }

    public ArrayList<String> getCookingSteps() {
        return this.cookingSteps;
    }

    public String getName() {
        return this.name;
    }

    public Drawable getImg() {
        return this.img;
    }
}

