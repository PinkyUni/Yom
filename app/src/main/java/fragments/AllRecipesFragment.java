package fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.MyRecyclerViewAdapter;
import com.yom.R;
import com.yom.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public FragmentTransaction fragmentTransaction;
    private MyRecyclerViewAdapter mainAdapter, adapter;
    private ArrayList<Recipe> recipeBook, cakeRecipes, brownieRecipes, cookieRecipes, pancakeRecipes, pieRecipes, waffleRecipes, muffinRecipes;
    private RecyclerView recyclerView;
    private ActionBar actionbar;

    private int currentRecipe;
    private int currentMainItem;
    private RecipeFragment recipeFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeFragment = new RecipeFragment();
        actionbar = ((MainActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        recipeBook = new ArrayList<>();
        mDBHelper = new DatabaseHelper(getContext());
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        getDataFromDatabase("main", recipeBook);
    }

    private void getDataFromDatabase(String tableName, ArrayList<Recipe> arrayList) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + tableName, null);
        while (cursor.moveToNext()) {
            String dbName = cursor.getString(cursor
                    .getColumnIndex("name"));
            String dbImg = cursor.getString(cursor
                    .getColumnIndex("img"));
            InputStream inputStream = null;
            Drawable d = null;
            try {
                inputStream = getActivity().getApplicationContext().getAssets().open(dbImg);
                d = Drawable.createFromStream(inputStream, null);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            String tempName = getStringValue(dbName);
            dbName = tempName;
            Float dbDuration = 0f;
            Integer dbCalories = 0;
            Integer dbPortions = 0;
            String[] arrIngredients = new String[0];
            String[] stepsArray = new String[0];
            if (!tableName.equals("main")) {
                dbDuration = cursor.getFloat(cursor.getColumnIndex("time"));
                dbCalories = cursor.getInt(cursor.getColumnIndex("cal"));
                dbPortions = cursor.getInt(cursor.getColumnIndex("portion"));

                String dbIngredients = cursor.getString(cursor.getColumnIndex("ingredients"));
                arrIngredients = dbIngredients.split("\n");

                String dbCooking = cursor.getString(cursor.getColumnIndex("cooking"));
                stepsArray = dbCooking.split("\n");
            }
            Recipe recipe = new Recipe(dbName, d, dbDuration, dbCalories, dbPortions, arrIngredients, stepsArray);
            arrayList.add(recipe);
        }
        cursor.close();
    }

    public String getStringValue(String key) {
        // Retrieve the resource id
        String packageName = getContext().getPackageName();
        Resources resources = getContext().getResources();
        int stringId = resources.getIdentifier(key, "string", packageName);
        if (stringId == 0) {
            return null;
        }
        // Return the string value based on the res id
        return resources.getString(stringId);
    }

    public ArrayList<Recipe> getRecipeBook() {
        return recipeBook;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public FragmentTransaction getFragmentTransaction() {
        return fragmentTransaction;
    }

    public int getCurrentMainItem() {
        return currentMainItem;
    }

    public int getCurrentRecipe() {
        return currentRecipe;
    }

    public void setMainItem(int position) {
        currentMainItem = position;
        switch (position) {
            case 0:
                cakeRecipes = new ArrayList<>();
                getDataFromDatabase("cakeRecipes", cakeRecipes);
                onChangeArrayList(cakeRecipes, getResources().getString(R.string.title_cakes));
                break;
            case 1:
                brownieRecipes = new ArrayList<>();
                getDataFromDatabase("brownieRecipes", brownieRecipes);
                onChangeArrayList(brownieRecipes, getResources().getString(R.string.title_brownies));
                break;
            case 2:
                pancakeRecipes = new ArrayList<>();
                getDataFromDatabase("pancakeRecipes", pancakeRecipes);
                onChangeArrayList(pancakeRecipes, getResources().getString(R.string.title_pancakes));
                break;
            case 3:
                cookieRecipes = new ArrayList<>();
                getDataFromDatabase("cookieRecipes", cookieRecipes);
                onChangeArrayList(cookieRecipes, getResources().getString(R.string.title_cookies));
                break;
            case 4:
                waffleRecipes = new ArrayList<>();
//                        waffleRecipes.add(new Recipe(getResources().getString(R.string.title_waffles), getResources().getDrawable(R.drawable.waffles)));
//                        waffleRecipes.add(new Recipe(getResources().getString(R.string.title_waffles), getResources().getDrawable(R.drawable.waffles)));
                onChangeArrayList(waffleRecipes, getResources().getString(R.string.title_waffles));
                break;
            case 5:
                muffinRecipes = new ArrayList<>();
//                        muffinRecipes.add(new Recipe(getResources().getString(R.string.title_muffins), getResources().getDrawable(R.drawable.muffins)));
//                        muffinRecipes.add(new Recipe(getResources().getString(R.string.title_muffins), getResources().getDrawable(R.drawable.muffins)));
                onChangeArrayList(muffinRecipes, getResources().getString(R.string.title_muffins));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_recipes_fragment, null);
        recyclerView = view.findViewById(R.id.Recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mainAdapter = new MyRecyclerViewAdapter(getActivity(), recipeBook);
        mainAdapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentMainItem = position;
                setMainItem(position);
            }
        });
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void onChangeArrayList(ArrayList<Recipe> arrayList, String title) {
        actionbar.setTitle(title);
        if (title.equals(getResources().getString(R.string.app_name))) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
            recyclerView.setAdapter(mainAdapter);
        } else {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_24dp);
            adapter = new MyRecyclerViewAdapter(getActivity(), arrayList);
            adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    currentRecipe = position;
                    Recipe recipe = adapter.getItem(position);
                    recipeFragment.setRecipe(recipe);
                    actionbar.setTitle(adapter.getItemName(position));
                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, recipeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}