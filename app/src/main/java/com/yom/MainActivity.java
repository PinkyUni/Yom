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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yom.databinding.ActivityMainBinding;

import fragments.AboutFragment;
import fragments.AddFragment;
import fragments.AllRecipesFragment;
import fragments.FavouriteFragment;
import fragments.MyRecipesFragment;
import fragments.SearchFragment;
import fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    public FragmentTransaction fragmentTransaction;
    public AllRecipesFragment allRecipesFragment;
    public SettingsFragment settingsFragment;
    public AboutFragment aboutFragment;
    public AddFragment addFragment;
    public MyRecipesFragment myRecipesFragment;
    public SearchFragment searchFragment;
    public FavouriteFragment favouriteFragment;
    private MenuItem btnAdd;

    private DrawerLayout mDrawerLayout;
    public Toolbar toolbar;
    public static Typeface typefaceRegular, typefaceMedium, typefaceBold, typefaceJura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        typefaceJura = Typeface.createFromAsset(getAssets(), "fonts/v.ttf");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(typefaceBold);
        textView.setTextSize(24);
//
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView personName = headerView.findViewById(R.id.txt_person);
        personName.setText(R.string.person_name);
        personName.setTypeface(typefaceBold);

        // set up the RecyclerView

        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView.setCheckedItem(R.id.nav_recipes);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        mainViewModel.setFragmentNumber(menuItem.getItemId());
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
    }

    public void onChangeFragment() {
        int currentFragment = mainViewModel.getCurrentFragment().getValue();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        TextView toolbarTxt = (TextView) toolbar.getChildAt(0);
        switch (currentFragment) {
            case 1:
                allRecipesFragment = new AllRecipesFragment();
                fragmentTransaction.replace(R.id.content_frame, allRecipesFragment);
                toolbarTxt.setText(getResources().getString(R.string.app_name));
                break;
            case 2:
                searchFragment = new SearchFragment();
                fragmentTransaction.replace(R.id.content_frame, searchFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_search_recipe));
                break;
            case 3:
                myRecipesFragment = new MyRecipesFragment();
                fragmentTransaction.replace(R.id.content_frame, myRecipesFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_my_recipes));
                break;
            case 4:
                favouriteFragment = new FavouriteFragment();
                fragmentTransaction.replace(R.id.content_frame, favouriteFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_favourite));
                break;
            case 5:
                addFragment = new AddFragment();
                fragmentTransaction.replace(R.id.content_frame, addFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_add_recipe));
                btnAdd.setVisible(true);
                break;
            case 6:
                settingsFragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.content_frame, settingsFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_settings));
                break;
            case 7:
                aboutFragment = new AboutFragment();
                fragmentTransaction.replace(R.id.content_frame, aboutFragment);
                toolbarTxt.setText(getResources().getString(R.string.txt_about));
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
            if (relatedToMainRecipes()) {
                allRecipesFragment.onChangeArrayList(allRecipesFragment.getRecipeBook(), getResources().getString(R.string.app_name));
                allRecipesFragment.getRecyclerView().getLayoutManager().scrollToPosition(allRecipesFragment.getCurrentMainItem());
                allRecipesFragment.onChangeArrayList(allRecipesFragment.getRecipeBook(), getResources().getString(R.string.app_name));
            } else {
                super.onBackPressed();
                allRecipesFragment.setMainItem(allRecipesFragment.getCurrentMainItem());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportActionBar().getTitle().equals(getResources().getString(R.string.app_name))) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    if (relatedToMainRecipes()) {
                        allRecipesFragment.onChangeArrayList(allRecipesFragment.getRecipeBook(), getResources().getString(R.string.app_name));
                        allRecipesFragment.getRecyclerView().getLayoutManager().scrollToPosition(allRecipesFragment.getCurrentMainItem());
                        allRecipesFragment.onChangeArrayList(allRecipesFragment.getRecipeBook(), getResources().getString(R.string.app_name));
                    } else {
                        super.onBackPressed();
                        allRecipesFragment.setMainItem(allRecipesFragment.getCurrentMainItem());
                    }
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean relatedToMainRecipes() {
        int i = 0;
        do {
            if (allRecipesFragment.getRecipeBook().get(i).getName().equals(getSupportActionBar().getTitle())) {
                return true;
            }
            i++;
        } while (i < allRecipesFragment.getRecipeBook().size());
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        btnAdd = menu.findItem(R.id.btn_add).setVisible(false);
        btnAdd.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addFragment.addMyRecipeDatabaseItem();
                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

}
