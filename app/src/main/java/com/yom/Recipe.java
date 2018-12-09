package com.yom;

import android.graphics.drawable.Drawable;

public class Recipe {
    private Integer id;
    private String name;
    private Drawable img;
    private Float duration;
    private Integer calories;
    private Integer portions;
    private String[] ingredients;
    private String[] cookingSteps;

    public Recipe(Integer id, String name, Drawable img, Float duration, Integer calories, Integer portions, String[] ingredients, String[] cookingSteps) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.duration = duration;
        this.calories = calories;
        this.portions = portions;
        this.ingredients = ingredients;
        this.cookingSteps = cookingSteps;
    }

    public Integer getId() {
        return this.id;
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

