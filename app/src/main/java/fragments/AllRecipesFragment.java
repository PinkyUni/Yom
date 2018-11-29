package fragments;

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

import com.yom.Ingredient;
import com.yom.MainActivity;
import com.yom.MyRecyclerViewAdapter;
import com.yom.R;
import com.yom.Recipe;

import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {

    public FragmentTransaction fragmentTransaction;
    private MyRecyclerViewAdapter mainAdapter, adapter;
    private ArrayList<Recipe> recipeBook, cakeRecipes, brownieRecipes, cookieRecipes, pancakeRecipes, pieRecipes, waffleRecipes, muffinRecipes;
    private RecyclerView recyclerView;
    private ActionBar actionbar;

    private int currentRecipe;
    private int currentFragment;

    private Recipe recipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionbar = ((MainActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        recipeBook = new ArrayList<>();
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(new Ingredient("milk", 2));
        ingredientArrayList.add(new Ingredient("eggs", 4));
        ArrayList<String> stepsArrayList = new ArrayList<>();
        stepsArrayList.add("mix all");
        stepsArrayList.add("DONE!");
        recipe = new Recipe("Napoleon", getResources().getDrawable(R.drawable.napoleon), ingredientArrayList, stepsArrayList);
        recipeBook.add(recipe);

//        recipeBook.add(new Recipe("Торты", getResources().getDrawable(R.drawable.cakes)));
//        recipeBook.add(new Recipe("Блины", getResources().getDrawable(R.drawable.pancakes_dark)));
//        recipeBook.add(new Recipe("Маффины", getResources().getDrawable(R.drawable.muffins_dark)));
//
//        recipeBook.add(new Recipe("Пирожные", getResources().getDrawable(R.drawable.brownies)));
//        recipeBook.add(new Recipe("Печенье", getResources().getDrawable(R.drawable.cookies)));
//        recipeBook.add(new Recipe("Блины", getResources().getDrawable(R.drawable.pancakes_dark)));
//        recipeBook.add(new Recipe("Пироги", getResources().getDrawable(R.drawable.pies)));
//        recipeBook.add(new Recipe("Вафли", getResources().getDrawable(R.drawable.waffles)));
//        recipeBook.add(new Recipe("Маффины", getResources().getDrawable(R.drawable.muffins_dark)));
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

    public int getCurrentFragmentId() {
        return currentFragment;
    }

    public int getCurrentRecipe() {
        return currentRecipe;
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
                currentFragment = position;
                switch (position) {
                    case 0:
                        cakeRecipes = new ArrayList<>();
                        cakeRecipes.add(recipe);
                        cakeRecipes.add(recipe);
//                        cakeRecipes.add(new Recipe(getResources().getString(R.string.cake_napoleon), getResources().getDrawable(R.drawable.napoleon)));
//                        cakeRecipes.add(new Recipe(getResources().getString(R.string.cake_graph_ruins), getResources().getDrawable(R.drawable.graph_ruins)));
                        onChangeArrayList(cakeRecipes, getResources().getString(R.string.title_cakes));
                        break;
                    case 1:
                        brownieRecipes = new ArrayList<>();
//                        brownieRecipes.add(new Recipe(getResources().getString(R.string.title_brownies), getResources().getDrawable(R.drawable.brownies)));
//                        brownieRecipes.add(new Recipe(getResources().getString(R.string.title_brownies), getResources().getDrawable(R.drawable.brownies)));
                        onChangeArrayList(brownieRecipes, getResources().getString(R.string.title_brownies));
                        break;
                    case 2:
                        cookieRecipes = new ArrayList<>();
//                        cookieRecipes.add(new Recipe(getResources().getString(R.string.title_cookies), getResources().getDrawable(R.drawable.cookies)));
//                        cookieRecipes.add(new Recipe(getResources().getString(R.string.title_cookies), getResources().getDrawable(R.drawable.cookies)));
                        onChangeArrayList(cookieRecipes, getResources().getString(R.string.title_cookies));
                        break;
                    case 3:
                        pancakeRecipes = new ArrayList<>();
//                        pancakeRecipes.add(new Recipe(getResources().getString(R.string.title_pancakes), getResources().getDrawable(R.drawable.pancakes)));
//                        pancakeRecipes.add(new Recipe(getResources().getString(R.string.title_pancakes), getResources().getDrawable(R.drawable.pancakes)));
                        onChangeArrayList(pancakeRecipes, getResources().getString(R.string.title_pancakes));
                        break;
                    case 4:
                        pieRecipes = new ArrayList<>();
//                        pieRecipes.add(new Recipe(getResources().getString(R.string.title_pies), getResources().getDrawable(R.drawable.pies)));
//                        pieRecipes.add(new Recipe(getResources().getString(R.string.title_pies), getResources().getDrawable(R.drawable.pies)));
                        onChangeArrayList(pieRecipes, getResources().getString(R.string.title_pies));
                        break;
                    case 5:
                        waffleRecipes = new ArrayList<>();
//                        waffleRecipes.add(new Recipe(getResources().getString(R.string.title_waffles), getResources().getDrawable(R.drawable.waffles)));
//                        waffleRecipes.add(new Recipe(getResources().getString(R.string.title_waffles), getResources().getDrawable(R.drawable.waffles)));
                        onChangeArrayList(waffleRecipes, getResources().getString(R.string.title_waffles));
                        break;
                    case 6:
                        muffinRecipes = new ArrayList<>();
//                        muffinRecipes.add(new Recipe(getResources().getString(R.string.title_muffins), getResources().getDrawable(R.drawable.muffins)));
//                        muffinRecipes.add(new Recipe(getResources().getString(R.string.title_muffins), getResources().getDrawable(R.drawable.muffins)));
                        onChangeArrayList(muffinRecipes, getResources().getString(R.string.title_muffins));
                        break;
                }
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
                    RecipeFragment recipeFragment = new RecipeFragment();
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

