package com.yom;

import android.graphics.drawable.Drawable;

public class Recipe {
    private String name;
    private Drawable img;
    private Float duration;
    private Integer calories;
    private Integer portions;
    private String level;
    private String[] ingredients;
    private String[] cookingSteps;

    public Recipe(String name, Drawable img, Float duration, Integer calories, Integer portions, String level, String[] ingredients, String[] cookingSteps) {
        this.name = name;
        this.img = img;
        this.duration = duration;
        this.calories = calories;
        this.portions = portions;
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

    public Integer getPortions() {
        return this.portions;
    }

    public String getLevel() {
        return this.level;
    }

    public String[] getIngredients() {
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

