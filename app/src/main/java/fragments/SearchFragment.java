package fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private MyRecyclerViewAdapter adapter;
    private FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_recipes_fragment, null);
        ((MainActivity) getActivity()).allRecipesFragment.setCurrentMainItem(3);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getActivity().getResources().getString(R.string.app_name));
        TextView toolbarTitle = (TextView) ((MainActivity) getActivity()).toolbar.getChildAt(0);
        toolbarTitle.setText(getActivity().getResources().getString(R.string.txt_search_recipe));
        ((MainActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = view.findViewById(R.id.Recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Recipe> arrayList = searchRecipes("шоколад");
        adapter = new MyRecyclerViewAdapter(getActivity(), arrayList);
        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Recipe recipe = adapter.getItem(position);
                RecipeFragment recipeFragment = new RecipeFragment();
                recipeFragment.setRecipe(recipe);
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, recipeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<Recipe> searchRecipes(String criterion) {
        DatabaseHelper mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
        SQLiteDatabase mDb = mDBHelper.getWritableDatabase();
        ArrayList<Recipe> arrayList = new ArrayList<>();
        Cursor c = mDb.query("cakeRecipes", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int ingredientsColIndex = c.getColumnIndex("ingredients");
            do {
                if (c.getString(ingredientsColIndex).contains(criterion)) {
                    int nameColIndex = c.getColumnIndex("name");
                    int imgColIndex = c.getColumnIndex("img");
                    int timeColIndex = c.getColumnIndex("time");
                    int calColIndex = c.getColumnIndex("cal");
                    int portionColIndex = c.getColumnIndex("portion");
                    String[] ingredientList = c.getString(ingredientsColIndex).split("\n");
                    int cookingColIndex = c.getColumnIndex("cooking");
                    String[] cookingList = c.getString(cookingColIndex).split("\n");
                    int id = arrayList.size();
                    arrayList.add(new Recipe(id, c.getString(nameColIndex), getActivity().getResources().getDrawable(R.drawable.napoleon),
                            c.getFloat(timeColIndex), c.getInt(calColIndex), c.getInt(portionColIndex), ingredientList, cookingList));
                }
            } while (c.moveToNext());
        }
        c.close();
        return arrayList;
    }
}
