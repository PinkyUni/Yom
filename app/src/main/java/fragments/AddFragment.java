package fragments;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.yom.MainActivity.typefaceJura;
import static com.yom.MainActivity.typefaceMedium;
import static com.yom.MainActivity.typefaceRegular;

public class AddFragment extends Fragment {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private Toolbar toolbar;
    static final int GALLERY_REQUEST = 1;
    private ImageView imageView;
    private TextInputEditText txtName, txtIngredients, txtCooking;
    private EditText txtTime, txtCal, txtPortions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment, null);
        imageView = view.findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
        txtName = view.findViewById(R.id.name);
        txtName.setTypeface(typefaceRegular);
        txtTime = view.findViewById(R.id.time);
        txtTime.setTypeface(typefaceRegular);
        txtCal = view.findViewById(R.id.cal);
        txtCal.setTypeface(typefaceRegular);
        txtPortions = view.findViewById(R.id.portion);
        txtPortions.setTypeface(typefaceRegular);

        TextView titlePortions = view.findViewById(R.id.title_portion);
        titlePortions.setTypeface(typefaceRegular);
        TextView titleTime = view.findViewById(R.id.title_time);
        titleTime.setTypeface(typefaceRegular);
        TextView titleCal = view.findViewById(R.id.title_cal);
        titleCal.setTypeface(typefaceRegular);

        txtIngredients = view.findViewById(R.id.ingredient_list);
        txtIngredients.setTypeface(typefaceJura);
        txtCooking = view.findViewById(R.id.cooking_steps_list);
        txtCooking.setTypeface(typefaceJura);

        TextView titleIngredients = view.findViewById(R.id.title_ingredients);
        titleIngredients.setTypeface(typefaceMedium);
        TextView titleCooking = view.findViewById(R.id.title_cooking);
        titleCooking.setTypeface(typefaceMedium);

        return view;
    }

    public void addMyRecipeDatabaseItem() {
        String dbName = txtName.getText().toString();
        Float dbTime = 0f;
        Integer dbCal = 0;
        Integer dbPortions = 4;
//        Integer dbTime = Integer.parseInt(txtTime.getText().toString());
//        Integer dbCal = Integer.parseInt(txtCal.getText().toString());
//        Integer dbPortions = Integer.parseInt(txtPortions.getText().toString());
        String dbIngredients = txtIngredients.getText().toString();
        String dbCooking = txtCooking.getText().toString();

        mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
//        mDb = ((MainActivity)getActivity()).allRecipesFragment.getmDb();
        mDb = mDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", 1);
        contentValues.put("name", dbName);
        contentValues.put("img", "puk");
        contentValues.put("time", dbTime);
        contentValues.put("cal", dbCal);
        contentValues.put("portion", dbPortions);
        contentValues.put("ingredients", dbIngredients);
        contentValues.put("cooking", dbCooking);

        long id = mDb.insert("myRecipes", null, contentValues);
        Toast.makeText(getContext(), "DONE", Toast.LENGTH_SHORT).show();

//        mDBHelper.close();

//        mDb = ((MainActivity)getActivity()).allRecipesFragment.getmDb();
        Cursor c = mDb.query("myRecipes", null, null, null, null, null, null);
        int count = c.getCount();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int nameColIndex = c.getColumnIndex("name");

            do {
                Toast.makeText(getContext(), c.getString(nameColIndex), Toast.LENGTH_SHORT).show();

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        c.close();
//        mDBHelper.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Bitmap bitmap = null;
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);
                }
        }
    }

}