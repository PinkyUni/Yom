package com.yom;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private static Typeface typefaceMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typefaceMedium = Typeface.createFromAsset(getAssets(), "fonts/Podkova-Medium.ttf");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(typefaceMedium);
        textView.setTextSize(24);

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

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItemName(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
