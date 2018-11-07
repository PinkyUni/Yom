package com.yom;

import android.graphics.drawable.Drawable;

public class Recipe {
    private String name;
    private Drawable img;

    public Recipe(String name, Drawable img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return this.name;
    }

    public Drawable getImg() {
        return this.img;
    }
}

