package fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.R;
import com.yom.Recipe;

import java.util.ArrayList;

import static com.yom.MainActivity.typefaceJura;
import static com.yom.MainActivity.typefaceMedium;
import static com.yom.MainActivity.typefaceRegular;

public class RecipeFragment extends Fragment {

    private String name;
    private Drawable image;
    private String duration;
    private Integer calories;
    private Integer portions;
    private String[] ingredients;
    private String[] cookingSteps;
    private FloatingActionButton btnFav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setRecipe(Recipe recipe) {
        name = recipe.getName();
        image = recipe.getImg();
        duration = recipe.getDuration();
        calories = recipe.getCalories();
        portions = recipe.getPortions();
        ingredients = recipe.getIngredients();
        cookingSteps = recipe.getCookingSteps();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, null);
        final ImageView imageView = view.findViewById(R.id.img);
        imageView.setImageDrawable(image);
        final TextView ingredientList = view.findViewById(R.id.ingredient_list);

        ingredientList.setTypeface(typefaceJura);
        for (int i = 0; i < ingredients.length; i++) {
            ingredientList.append("- " + ingredients[i] + "\n");
        }
        TextView titleCooking = view.findViewById(R.id.title_cooking);
        titleCooking.setTypeface(typefaceMedium);

        final TextView cookingStepsList = view.findViewById(R.id.cooking_steps_list);
        cookingStepsList.setTypeface(typefaceJura);
        for (int i = 0; i < cookingSteps.length; i++) {
            cookingStepsList.append(Integer.toString(i + 1) + ". " + cookingSteps[i] + "\n");
        }

        final TextView titleName = view.findViewById(R.id.name);
        titleName.setText(name);
        titleName.setTypeface(typefaceMedium);

        TextView titleIngredients = view.findViewById(R.id.title_ingredients);
        titleIngredients.setTypeface(typefaceMedium);

        TextView titleTime = view.findViewById(R.id.title_time);
        titleTime.setTypeface(typefaceRegular);
        final TextView time = view.findViewById(R.id.time);
        time.setTypeface(typefaceRegular);
        time.setText(duration);

        TextView titleCal = view.findViewById(R.id.title_cal);
        titleCal.setTypeface(typefaceRegular);
        final TextView cal = view.findViewById(R.id.cal);
        cal.setTypeface(typefaceRegular);
        String tmpCalories = calories.toString();
        cal.setText(tmpCalories);

        TextView titlePortion = view.findViewById(R.id.title_portion);
        titlePortion.setTypeface(typefaceRegular);
        final TextView portion = view.findViewById(R.id.portion);
        portion.setTypeface(typefaceRegular);
        portion.setText(String.valueOf(portions));

        btnFav = view.findViewById(R.id.btn_fav);


//            int nameColIndex = c.getColumnIndex("name");
//            int imgColIndex = c.getColumnIndex("img");
//            int timeColIndex = c.getColumnIndex("time");
//            int calColIndex = c.getColumnIndex("cal");
//            int portionColIndex = c.getColumnIndex("portion");
//            int ingredientsColIndex = c.getColumnIndex("ingredients");
//            int cookingColIndex = c.getColumnIndex("cooking");
//            do {
//                String dbIngredients = c.getString(ingredientsColIndex);
//                if (dbIngredients.contains("Молоко")) {

//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put("name", nameColIndex);
//                    contentValues.put("img", imgColIndex);
//                    contentValues.put("time", timeColIndex);
//                    contentValues.put("cal", calColIndex);
//                    contentValues.put("portion", portionColIndex);
//                    contentValues.put("ingredients", ingredientsColIndex);
//                    contentValues.put("cooking", cookingColIndex);
//                    mDb.insert("myFavouriteRecipes", null, contentValues);
//                }
//            } while (c.moveToNext());
//        }
//        c.close();
        if (recipeIsFavourite()) {
            btnFav.setImageResource(R.drawable.ic_favorite_selected_24dp);
        }
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
                SQLiteDatabase mDb = mDBHelper.getWritableDatabase();

                if (!recipeIsFavourite()) {
                    btnFav.setImageResource(R.drawable.ic_favorite_selected_24dp);
                    int category = ((MainActivity) getActivity()).allRecipesFragment.getCurrentMainItemId();
                    int recipeId = ((MainActivity) getActivity()).allRecipesFragment.getCurrentRecipe();
//                    ((MainActivity) getActivity()).allRecipesFragment.getRecipeBook();
//                    ArrayList<Recipe> arrayList = new ArrayList<>();
                    String tableName = null;
                    switch (category) {
                        case 1:
                            tableName = "cakeRecipes";
                            break;
                        case 2:
                            tableName = "brownieRecipes";
                            break;
                        case 3:
                            tableName = "pancakeRecipes";
                            break;
                        case 4:
                            tableName = "cookieRecipes";
                            break;
                        case 5:
                            tableName = "muffinRecipes";
                            break;
                        case 6:
                            tableName = "waffleRecipes";
                            break;
                        case 7:
                            tableName = "bunRecipes";
                            break;
                        case 8:
                            tableName = "cheesecakeRecipes";
                            break;
                    }
                    Cursor c = mDb.rawQuery("SELECT * FROM " + tableName + " WHERE _id = " + String.valueOf(recipeId), null);
                    c.moveToFirst();
//                    int idColIndex = c.getColumnIndex("_id");
////                    if (c.getString(nameColIndex).equals(recipeId)) {
//                    String dbName = c.getString(c.getColumnIndex("name"));
                    String dbImg = c.getString(c.getColumnIndex("img"));
//                    String dbTime = c.getString(c.getColumnIndex("time"));
//                    Integer dbCal = c.getInt(c.getColumnIndex("cal"));
//                    Integer dbPortions = c.getInt(c.getColumnIndex("portion"));
//                    String dbIngredients = c.getString(c.getColumnIndex("ingredients"));
//                    String dbCooking = c.getString(c.getColumnIndex("cooking"));
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put("name", dbName);
//                    contentValues.put("img", dbImg);
//                    contentValues.put("time", dbTime);
//                    contentValues.put("cal", dbCal);
//                    contentValues.put("portion", dbPortions);
//                    contentValues.put("ingredients", dbIngredients);
//                    contentValues.put("cooking", dbCooking);
//                    mDb.insert("myFavouriteRecipes", null, contentValues);
                    c.close();
                    String dbName = titleName.getText().toString();
                    String dbTime = time.getText().toString();
                    Integer dbCal = Integer.parseInt(cal.getText().toString());
                    Integer dbPortions = Integer.parseInt(portion.getText().toString());
                    String dbIngredients = ingredientList.getText().toString();
                    String dbCooking = cookingStepsList.getText().toString();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", dbName);
                    contentValues.put("img", dbImg);
                    contentValues.put("time", dbTime);
                    contentValues.put("cal", dbCal);
                    contentValues.put("portion", dbPortions);
                    contentValues.put("ingredients", dbIngredients);
                    contentValues.put("cooking", dbCooking);
                    mDb.insert("myFavouriteRecipes", null, contentValues);
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.txt_added_to_faves), Toast.LENGTH_SHORT).show();
                } else {
                    btnFav.setImageResource(R.drawable.ic_favorite_24dp);
                    mDb.delete("myFavouriteRecipes", "name = ?", new String[]{name});
                }
            }
        });
        return view;
    }

    private boolean recipeIsFavourite() {
        DatabaseHelper mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
        SQLiteDatabase mDb = mDBHelper.getWritableDatabase();
        Cursor c = mDb.query("myFavouriteRecipes", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                if (c.getString(nameColIndex).equals(this.name)) {
                    return true;
                }
            } while (c.moveToNext());
        }
        return false;
    }
}
