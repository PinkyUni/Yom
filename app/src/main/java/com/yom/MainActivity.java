package com.yom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Recipe> recipeBook = new ArrayList<>();
        recipeBook.add(new Recipe("Cupcake", getResources().getDrawable(R.drawable.img_cake)));
        recipeBook.add(new Recipe("Cake", getResources().getDrawable(R.drawable.cake)));
        recipeBook.add(new Recipe("Cherry cake", getResources().getDrawable(R.drawable.cherry_cake)));
        recipeBook.add(new Recipe("Cookies", getResources().getDrawable(R.drawable.cookies)));
        recipeBook.add(new Recipe("Fruits", getResources().getDrawable(R.drawable.plums)));

        recipeBook.add(new Recipe("Pancakes", getResources().getDrawable(R.drawable.pancakes)));
        recipeBook.add(new Recipe("Berries", getResources().getDrawable(R.drawable.berries)));
        recipeBook.add(new Recipe("Cake", getResources().getDrawable(R.drawable.cake)));
        recipeBook.add(new Recipe("Chocolate", getResources().getDrawable(R.drawable.choco)));
        recipeBook.add(new Recipe("Croissant", getResources().getDrawable(R.drawable.croissant)));

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.Recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, recipeBook);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItemName(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
