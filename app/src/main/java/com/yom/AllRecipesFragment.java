package com.yom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {

    public FragmentTransaction fragmentTransaction;
    public MainViewModel mainViewModel;
    private MyRecyclerViewAdapter adapter;
    private ArrayList<Recipe> recipeBook;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_recipes_fragment, null);
        RecyclerView recyclerView = view.findViewById(R.id.Recipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyRecyclerViewAdapter(getActivity(), recipeBook);
        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "You clicked " + adapter.getItemName(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

}

