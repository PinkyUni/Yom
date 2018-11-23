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
import android.widget.Toast;

import com.yom.MainActivity;
import com.yom.MainViewModel;
import com.yom.MyRecyclerViewAdapter;
import com.yom.R;
import com.yom.Recipe;

import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {

    public FragmentTransaction fragmentTransaction;
    public MainViewModel mainViewModel;
    private MyRecyclerViewAdapter mainAdapter, adapter;
    private ArrayList<Recipe> recipeBook;
    private ArrayList<Recipe> cakeRecipes;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeBook = new ArrayList<>();

        recipeBook.add(new Recipe("Торты", getResources().getDrawable(R.drawable.cakes)));
        recipeBook.add(new Recipe("Пирожные", getResources().getDrawable(R.drawable.brownies)));
        recipeBook.add(new Recipe("Печенье", getResources().getDrawable(R.drawable.cookies)));
        recipeBook.add(new Recipe("Блины", getResources().getDrawable(R.drawable.pancakes)));
        recipeBook.add(new Recipe("Пироги", getResources().getDrawable(R.drawable.pies)));
        recipeBook.add(new Recipe("Вафли", getResources().getDrawable(R.drawable.waffels)));
        recipeBook.add(new Recipe("Маффины", getResources().getDrawable(R.drawable.maffins)));
    }

    public ArrayList<Recipe> getRecipeBook() {
        return recipeBook;
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
                switch (position) {
                    case 0:
                        cakeRecipes = new ArrayList<>();
                        cakeRecipes.add(new Recipe(getResources().getString(R.string.cake_napoleon), getResources().getDrawable(R.drawable.napoleon)));
                        cakeRecipes.add(new Recipe(getResources().getString(R.string.cake_graph_ruins), getResources().getDrawable(R.drawable.graph_ruins)));
                        onChangeArrayList(cakeRecipes, getResources().getString(R.string.title_cakes));
                        break;
                }
            }
        });
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public void onChangeArrayList(ArrayList<Recipe> arrayList, String title) {
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
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
                    Toast.makeText(getActivity(), "You clicked " + adapter.getItemName(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}

