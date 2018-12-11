package fragments;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.R;

import static android.app.Activity.RESULT_OK;
import static com.yom.MainActivity.typefaceJura;
import static com.yom.MainActivity.typefaceMedium;
import static com.yom.MainActivity.typefaceRegular;

public class AddFragment extends Fragment {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    static final int GALLERY_REQUEST = 1;

    public static final int PICK_IMAGE = 1;
    private ImageView imageView;
    private TextInputEditText txtName, txtIngredients, txtCooking;
    private EditText txtTime, txtCal, txtPortions;
    private static final int SELECT_PHOTO = 100;
    private Bitmap bitmap;

    public boolean isEmptyEditText() {
        return TextUtils.isEmpty(txtName.getText().toString()) || TextUtils.isEmpty(txtTime.getText().toString()) || TextUtils.isEmpty(txtCal.getText().toString())
                || TextUtils.isEmpty(txtPortions.getText().toString()) || TextUtils.isEmpty(txtIngredients.getText().toString()) || TextUtils.isEmpty(txtCooking.getText().toString());
    }

    public void setFocusOnEmptyEdit() {
        if (txtName.getText().toString().equals("")) {
            txtName.requestFocus();
        } else {
            if (txtTime.getText().toString().equals("")) {
                txtTime.requestFocus();
            } else {
                if (txtCal.getText().toString().equals("")) {
                    txtCal.requestFocus();
                } else {
                    if (txtPortions.getText().toString().equals("")) {
                        txtPortions.requestFocus();
                    } else {
                        if (txtIngredients.getText().toString().equals("")) {
                            txtIngredients.requestFocus();
                        } else {
                            if (txtCooking.getText().toString().equals("")) {
                                txtCooking.requestFocus();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_fragment, null);
        imageView = view.findViewById(R.id.img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
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
        String dbTime = txtTime.getText().toString();
        Integer dbCal = Integer.parseInt(txtCal.getText().toString());
        Integer dbPortions = Integer.parseInt(txtPortions.getText().toString());
        String dbIngredients = txtIngredients.getText().toString();
        String dbCooking = txtCooking.getText().toString();

        mDBHelper = ((MainActivity) getActivity()).allRecipesFragment.getmDBHelper();
        mDb = mDBHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
//        contentValues.put("_id", 1);
        contentValues.put("name", dbName);
        contentValues.put("img", "pies.jpg");
        contentValues.put("time", dbTime);
        contentValues.put("cal", dbCal);
        contentValues.put("portion", dbPortions);
        contentValues.put("ingredients", dbIngredients);
        contentValues.put("cooking", dbCooking);

        long id = mDb.insert("myRecipes", null, contentValues);
        Toast.makeText(getContext(), "DONE", Toast.LENGTH_SHORT).show();

//        Cursor c = mDb.query("myRecipes", null, null, null, null, null, null);
//        if (c.moveToFirst()) {
//            int nameColIndex = c.getColumnIndex("name");
//            do {
//                Toast.makeText(getContext(), c.getString(nameColIndex), Toast.LENGTH_SHORT).show();
//            } while (c.moveToNext());
//        }
//        c.close();
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch(requestCode) {
//            case SELECT_PHOTO:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    InputStream imageStream = null;
//                    try {
//                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//                    imageView.setImageBitmap(yourSelectedImage);
//                }
//        }
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Uri picUri = data.getData();
//        Bitmap thePic = null;
//        try {
//            thePic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        ImageView picView = (ImageView) form.findViewById(R.id.image);
//        imageView.setImageBitmap(thePic);
//    }
//        @Override
//        public void onActivityResult ( int requestCode, int resultCode, Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
////        TODO каким то магическим образом надо подгружать картинку из галереи
//            if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO) {
//                Uri imageUri = data.getData();
//                imageView.setImageURI(imageUri);
//            }
//        }

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;
    //ADDED
    private String filemanagerstring;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                filemanagerstring = selectedImageUri.getPath();
                selectedImagePath = getPath(selectedImageUri);
                if (selectedImagePath != null)
                    imageView.setImageURI(selectedImageUri);
                else
                    imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.empty_img));
            }
        }
    }

    //UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }
}
