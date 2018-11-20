package com.yom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yom.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    public FragmentTransaction fragmentTransaction;
    public AllRecipesFragment allRecipesFragment;
    private DrawerLayout mDrawerLayout;
    public Toolbar toolbar;
    public static Typeface typefaceRegular, typefaceMedium, typefaceBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allRecipesFragment = new AllRecipesFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, allRecipesFragment);
        fragmentTransaction.commit();

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setViewModel(mainViewModel);
        mainViewModel.getCurrentFragment().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                onChangeFragment();
            }
        });

        typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Podkova-Regular.ttf");
        typefaceMedium = Typeface.createFromAsset(getAssets(), "fonts/Podkova-Medium.ttf");
        typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/Podkova-Bold.ttf");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(typefaceBold);
        textView.setTextSize(24);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView personName = headerView.findViewById(R.id.txt_person);
        personName.setText(R.string.person_name);
        personName.setTypeface(typefaceBold);

        // set up the RecyclerView

        mDrawerLayout = findViewById(R.id.drawer_layout);

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
    protected void onRestart() {
        super.onRestart();
        onChangeFragment();
    }

    public void onChangeFragment() {
        int currentFragment = mainViewModel.getCurrentFragment().getValue();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TextView toolbarTxt = (TextView) toolbar.getChildAt(0);
        switch (currentFragment) {
            case 1:
                fragmentTransaction.replace(R.id.content_frame, allRecipesFragment);
                toolbarTxt.setText(getResources().getString(R.string.app_name));
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
