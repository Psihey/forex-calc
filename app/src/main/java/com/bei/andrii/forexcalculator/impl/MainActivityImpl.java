package com.bei.andrii.forexcalculator.impl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bei.andrii.forexcalculator.MainActivity;
import com.bei.andrii.forexcalculator.MainActivityPresenter;
import com.bei.andrii.forexcalculator.R;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    private MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivityPresenter = new MainActivityPresenterImpl();
        mMainActivityPresenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
