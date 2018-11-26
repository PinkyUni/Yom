package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yom.Ingredient;
import com.yom.R;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {

    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> cookingSteps;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("hz", 3));
        ingredients.add(new Ingredient("escho shto ta", 13));
        cookingSteps = new ArrayList<>();
        cookingSteps.add("mix all");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, null);
        TextView ingredientList = view.findViewById(R.id.ingredient_list);
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientList.append(ingredients.get(i).getName() + "\n");
        }

        return view;
    }
}
