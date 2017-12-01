package com.bei.andrii.forexcalculator.impl;

import com.bei.andrii.forexcalculator.MainActivity;
import com.bei.andrii.forexcalculator.MainActivityPresenter;

/**
 * Created by Psihey on 30.11.2017.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private MainActivity mMainActivity;
    private int mPPrice;
    private int mSumOfRisk;
    private int mPStop;
    private int mPTake;

    @Override
    public void bind(MainActivity mainActivity) {
        mMainActivity = mainActivity;

    }

    @Override
    public void unbind() {
        mMainActivity = null;
    }

    @Override
    public void calculate(int sum, float enterPrice, float stopPrice, float profitPrice, int risk, String tool) {
        if (tool.equals("EUR/USD")) {
            mPPrice = 10;
            mSumOfRisk = sum * risk / 100;
            calculateResultForStep(enterPrice,stopPrice,profitPrice);
        }
    }

    private void calculateResultForStep(float enterPrice, float stopPrice, float profitPrice) {
        if (enterPrice > stopPrice) {
            mPStop = (int) (enterPrice - stopPrice) * 100000;
            mPTake = (int) (profitPrice - enterPrice) * 100000;
        } else {
            mPStop = (int) (stopPrice - enterPrice) * 100000;
            mPTake = (int) (enterPrice - profitPrice) * 100000;
        }
        mMainActivity.showResultForStep(mPPrice,mPStop,mPTake);
    }

}
