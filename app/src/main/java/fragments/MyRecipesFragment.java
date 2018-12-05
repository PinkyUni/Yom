package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return view;
    }

}
