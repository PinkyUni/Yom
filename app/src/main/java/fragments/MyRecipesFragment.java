package fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.MyRecyclerViewAdapter;
import com.yom.R;
import com.yom.Recipe;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.yom.MainActivity.typefaceRegular;

public class MyRecipesFragment extends Fragment {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myAdapter;
    public FragmentTransaction fragmentTransaction;
    private TextView emptyList;
    private ArrayList<Recipe> myRecipes;

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
            Recipe recipe = new Recipe(dbId, dbName, d, dbDuration, dbCalories, dbPortions, arrIngredients, stepsArray);
            arrayList.add(recipe);
        }
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
        mDb = mDBHelper.getWritableDatabase();
        ((MainActivity) getActivity()).allRecipesFragment.setCurrentMainItem(3);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
        TextView toolbarTitle = (TextView) ((MainActivity) getActivity()).toolbar.getChildAt(0);
        toolbarTitle.setText(getActivity().getResources().getString(R.string.txt_my_recipes));
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Cursor c = mDb.query("myRecipes", null, null, null, null, null, null);
        if (c.getCount() != 0) {
            if (c.moveToFirst()) {
                view = inflater.inflate(R.layout.all_recipes_fragment, null);
                myRecipes = new ArrayList<>();
                getDataFromDatabase("myRecipes", myRecipes);
                recyclerView = view.findViewById(R.id.Recipes);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myAdapter = new MyRecyclerViewAdapter(getActivity(), myRecipes);
                myAdapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Recipe recipe = myAdapter.getItem(position);
                        RecipeFragment recipeFragment = new RecipeFragment();
                        recipeFragment.setRecipe(recipe);
                        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.txt_my_recipes));
                        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_24dp);
                        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, recipeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                recyclerView.setAdapter(myAdapter);
            }
            c.close();
        } else {
            view = inflater.inflate(R.layout.my_recipes_fragment, null);
            emptyList = view.findViewById(R.id.empty);
            emptyList.setGravity(Gravity.CENTER);
            emptyList.setTypeface(typefaceRegular);
        }
        return view;
    }

    private void getMyRecipesFromDatabase() {

    }

}
