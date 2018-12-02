package com.yom;

import android.graphics.drawable.Drawable;

public class Recipe {
    private String name;
    private Drawable img;
    private Float duration;
    private Integer calories;
    private String level;
    private Ingredient[] ingredients;
    private String[] cookingSteps;

    public Recipe(String name, Drawable img, Float duration, Integer calories, String level, Ingredient[] ingredients, String[] cookingSteps) {
        this.name = name;
        this.img = img;
        this.duration = duration;
        this.calories = calories;
        this.level = level;
        this.ingredients = ingredients;
        this.cookingSteps = cookingSteps;
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

    public Ingredient[] getIngredients() {
        return this.ingredients;
    }

    public String[] getCookingSteps() {
        return this.cookingSteps;
    }

    public String getName() {
        return this.name;
    }

    public Drawable getImg() {
        return this.img;
    }
}

