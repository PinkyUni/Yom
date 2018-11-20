package com.yom;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> mCurrentFragment;

    public MutableLiveData<Integer> getFragment() {
        return mCurrentFragment;
    }

    public void setFragmentNumber(int id) {
        int curFragment = 1;
        switch (id) {
            case R.id.nav_recipes:
                curFragment = 1;
                break;
            case R.id.nav_search:
                curFragment = 2;
                break;
            case R.id.nav_my_recipes:
                curFragment = 3;
                break;
            case R.id.nav_favourite:
                curFragment = 4;
                break;
            case R.id.nav_add:
                curFragment = 5;
                break;
            case R.id.nav_settings:
                curFragment = 6;
                break;
            case R.id.nav_about:
                curFragment = 7;
                break;
        }
        mCurrentFragment.setValue(curFragment);
    }

    public MutableLiveData<Integer> getCurrentFragment() {
        if (mCurrentFragment == null) {
            mCurrentFragment = new MutableLiveData<>();
            mCurrentFragment.setValue(1);
        }
        return mCurrentFragment;
    }

}

