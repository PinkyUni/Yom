package fragments;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private Drawable image;
    private Float duration;
    private Integer calories;
    private String level;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> cookingSteps;
    private Typeface typefaceJura;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typefaceJura = Typeface.createFromAsset(getActivity().getAssets(), "fonts/v.ttf");
    }

    public void setRecipe(Recipe recipe) {
        name = recipe.getName();
        image = recipe.getImg();
        duration = recipe.getDuration();
        calories = recipe.getCalories();
        level = recipe.getLevel();
        ingredients = recipe.getIngredients();
        cookingSteps = recipe.getCookingSteps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, null);
        ImageView imageView = view.findViewById(R.id.img);
        imageView.setImageDrawable(image);
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
            cookingStepsList.append(Integer.toString(i + 1) + ". " + cookingSteps.get(i) + "\n");
        }

        TextView titleName = view.findViewById(R.id.name);
        titleName.setText(name);
        titleName.setTypeface(typefaceMedium);

        TextView titleIngredients = view.findViewById(R.id.title_ingredients);
        titleIngredients.setTypeface(typefaceMedium);

        TextView time = view.findViewById(R.id.time);
        time.setTypeface(typefaceRegular);
        int hours = duration.intValue();
        time.setText(String.valueOf(hours));
        time.append(" " + getResources().getString(R.string.txt_hour) + " ");
        Float tmp = (duration - hours) * 100;
        Integer minutes = tmp.intValue();
        time.append(String.valueOf(minutes)+ " " + getResources().getString(R.string.txt_min));

        TextView cal = view.findViewById(R.id.cal);
        cal.setTypeface(typefaceRegular);
        String tmpCalories = calories.toString()+ " " + getResources().getString(R.string.txt_calories);
        cal.setText(tmpCalories);

        TextView lvl = view.findViewById(R.id.lvl);
        lvl.setTypeface(typefaceRegular);
        lvl.setText(level);
        return view;
    }
}
