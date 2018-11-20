package com.yom;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> mCurrentFragment;

    public MutableLiveData<Integer> getFragment() {
        return mCurrentFragment;
    }

    public void setFragmentNumber(View view) {
        int curFragment = 1;
//        switch (view.getId()) {
////
//        }
        mCurrentFragment.setValue(curFragment);
    }

    public MutableLiveData<Integer> getCurrentFragment() {
        if (mCurrentFragment == null) {
            mCurrentFragment = new MutableLiveData<>();
            mCurrentFragment.setValue(3);
        }
        return mCurrentFragment;
    }

}

