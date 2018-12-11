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
import android.widget.TextView;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.MyRecyclerViewAdapter;
import com.yom.R;
import com.yom.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllRecipesFragment extends Fragment {

    public DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public FragmentTransaction fragmentTransaction;
    private MyRecyclerViewAdapter mainAdapter, adapter;
    private ArrayList<Recipe> recipeBook;
    private RecyclerView recyclerView;
    private ActionBar actionbar;

    private int currentRecipe;
    private int currentMainItem;
    private RecipeFragment recipeFragment;

    public final SQLiteDatabase getmDb() {
        return mDb;
    }

    public DatabaseHelper getmDBHelper() {
        return mDBHelper;
    }

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
        toSort(recipeBook);
    }

    public void getDataFromDatabase(String tableName, ArrayList<Recipe> arrayList) {
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + tableName, null);
        while (cursor.moveToNext()) {
            Integer dbId = cursor.getInt(cursor.getColumnIndex("_id"));
            String dbName = cursor.getString(cursor.getColumnIndex("name"));
            String dbImg = cursor.getString(cursor.getColumnIndex("img"));
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
            String dbDuration = null;
            Integer dbCalories = 0;
            Integer dbPortions = 0;
            String[] arrIngredients = new String[0];
            String[] stepsArray = new String[0];
            if (!tableName.equals("main")) {
                dbDuration = cursor.getString(cursor.getColumnIndex("time"));
                dbCalories = cursor.getInt(cursor.getColumnIndex("cal"));
                dbPortions = cursor.getInt(cursor.getColumnIndex("portion"));

                String dbIngredients = cursor.getString(cursor.getColumnIndex("ingredients"));
                arrIngredients = dbIngredients.split("\n");

                String dbCooking = cursor.getString(cursor.getColumnIndex("cooking"));
                stepsArray = dbCooking.split("\n");
            }
            Recipe recipe = new Recipe(dbId, dbName, d, dbDuration, dbCalories, dbPortions, arrIngredients, stepsArray);
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

    public void setCurrentMainItem(int item) {
        this.currentMainItem = item;
    }

    public int getCurrentRecipe() {
        return currentRecipe;
    }

    public void toSort(ArrayList<Recipe> arrayList) {
        Collections.sort(arrayList, new Comparator<Recipe>() {
            public int compare(Recipe recipe1, Recipe recipe2) {
                return recipe1.getName().compareTo(recipe2.getName());
            }
        });
    }

    public void setMainItem(int position) {
        currentMainItem = position;
        String curName = mainAdapter.getItem(position).getName();
        ArrayList<Recipe> arrayList = new ArrayList<>();
        switch (mainAdapter.getItem(position).getId()) {
            case 1:
                getDataFromDatabase("cakeRecipes", arrayList);
                break;
            case 2:
                getDataFromDatabase("brownieRecipes", arrayList);
                break;
            case 3:
                getDataFromDatabase("pancakeRecipes", arrayList);
                break;
            case 4:
                getDataFromDatabase("cookieRecipes", arrayList);
                break;
            case 5:
                getDataFromDatabase("muffinRecipes", arrayList);
                break;
            case 6:
                getDataFromDatabase("waffleRecipes", arrayList);
                break;
            case 7:
                getDataFromDatabase("bunRecipes", arrayList);
                break;
            case 8:
                getDataFromDatabase("cheesecakeRecipes", arrayList);
                break;
        }
        toSort(arrayList);
        onChangeArrayList(arrayList, curName);
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
//        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
//        TextView toolbarTitle = (TextView) ((MainActivity) getActivity()).toolbar.getChildAt(0);
//        toolbarTitle.setText(title);
        if (title.equals(getResources().getString(R.string.app_name))) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
            recyclerView.setAdapter(mainAdapter);
        } else {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_back_24dp);
            actionbar.setDisplayHomeAsUpEnabled(true);
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