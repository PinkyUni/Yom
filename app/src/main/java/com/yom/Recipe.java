package com.yom;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private Drawable img;
    private Float duration;
    private Integer calories;
    private String level;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> cookingSteps;

    public Recipe(String name, Drawable img, Float duration, Integer calories, String level, ArrayList<Ingredient> ingredients, ArrayList<String> cookingSteps) {
        this.name = name;
        this.img = img;
        if (duration != null) {
            this.duration = duration;
        }
        if (calories != null) {
            this.calories = calories;
        }
        if (level != null) {
            this.level = level;
        }
        if (ingredients != null) {
            this.ingredients = ingredients;
        }
        if (cookingSteps != null) {
            this.cookingSteps = cookingSteps;
        }
    }

    public Float getDuration() {
        return this.duration;
    }

    public Integer getCalories() {
        return this.calories;
    }

    public String getLevel() {
        return this.level;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
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

