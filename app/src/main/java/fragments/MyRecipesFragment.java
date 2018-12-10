package fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yom.DatabaseHelper;
import com.yom.MainActivity;
import com.yom.R;

import static com.yom.MainActivity.typefaceRegular;

public class MyRecipesFragment extends Fragment {

    private TextView emptyList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_recipes_fragment, null);
        emptyList = view.findViewById(R.id.empty);
        emptyList.setGravity(Gravity.CENTER);
        emptyList.setTypeface(typefaceRegular);
        getMyRecipesFromDatabase();
        return view;
    }

    private void getMyRecipesFromDatabase() {
        DatabaseHelper databaseHelper = ((MainActivity)getActivity()).allRecipesFragment.getmDBHelper();

        String selectQuery = "SELECT name FROM myRecipes";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

//        Cursor c = db.query("myRecipes", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int nameColIndex = c.getColumnIndex("name");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                emptyList.append(c.getString(nameColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
        c.close();
        databaseHelper.close();
    }

}
