package com.bei.andrii.forexcalculator.impl;

import com.bei.andrii.forexcalculator.MainActivity;
import com.bei.andrii.forexcalculator.MainActivityPresenter;

/**
 * Created by Psihey on 30.11.2017.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private MainActivity mMainActivity;

    @Override
    public void bind(MainActivity mainActivity) {
        mMainActivity = mainActivity;

    }

    @Override
    public void unbind() {
        mMainActivity = null;
    }
}
