package fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yom.Ingredient;
import com.yom.R;
import com.yom.Recipe;

import java.util.ArrayList;

import static com.yom.MainActivity.typefaceBold;
import static com.yom.MainActivity.typefaceMedium;
import static com.yom.MainActivity.typefaceRegular;

public class RecipeFragment extends Fragment {

    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> cookingSteps;
    private Typeface typefaceJura;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typefaceJura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/v.ttf");
//        ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient("hz", 3));
//        ingredients.add(new Ingredient("escho shto ta", 13));
//        cookingSteps = new ArrayList<>();
//        cookingSteps.add("mix all");
    }

    public void setRecipe(Recipe recipe) {
        name = recipe.getName();
        ingredients = recipe.getIngredients();
        cookingSteps = recipe.getCookingSteps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, null);

        TextView ingredientList = view.findViewById(R.id.ingredient_list);
        ingredientList.setTypeface(typefaceJura);
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientList.append("- " + ingredients.get(i).getName() + "\n");
        }
        TextView titleCooking = view.findViewById(R.id.title_cooking);
        titleCooking.setTypeface(typefaceMedium);

        TextView cookingStepsList = view.findViewById(R.id.cooking_steps_list);
        cookingStepsList.setTypeface(typefaceJura);
        for (int i = 0; i < cookingSteps.size(); i++) {
            cookingStepsList.append(Integer.toString(i + 1)+ ". " + cookingSteps.get(i) + "\n");
        }

        TextView titleName = view.findViewById(R.id.name);
        titleName.setText(name);
        titleName.setTypeface(typefaceMedium);

        TextView titleIngredients = view.findViewById(R.id.title_ingredients);
        titleIngredients.setTypeface(typefaceMedium);

        TextView time = view.findViewById(R.id.time);
        TextView cal = view.findViewById(R.id.cal);
        TextView lvl = view.findViewById(R.id.lvl);
        time.setTypeface(typefaceRegular);
        cal.setTypeface(typefaceRegular);
        lvl.setTypeface(typefaceRegular);
        return view;
    }
}
