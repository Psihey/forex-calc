package com.bei.andrii.forexcalculator.activity.impl;

import com.bei.andrii.forexcalculator.Const;
import com.bei.andrii.forexcalculator.R;
import com.bei.andrii.forexcalculator.activity.MainActivity;
import com.bei.andrii.forexcalculator.activity.MainActivityPresenter;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final int PPRICE_FOR_EUR_USD = 10;
    private static final int NOT_AMOUNT_FOR_CONDITION = 0;

    private MainActivity mMainActivity;
    private int mPPrice;
    private long mAmountEnterTemp;
    private int mPStop;
    private int mPTake;
    private long mSumOfRisk;

    @Override
    public void bind(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void unbind() {
        mMainActivity = null;
    }

    @Override
    public void calculate(long sum, float enterPrice, float stopPrice, float profitPrice, int risk, String tool) {
        if (tool.equals(Const.EUR_USD_TOOL)) {
            mPPrice = PPRICE_FOR_EUR_USD;
        }
        mAmountEnterTemp = -1;
        mSumOfRisk = sum * risk / 100;

        calculateResultForStep(enterPrice, stopPrice, profitPrice);

        if (mAmountEnterTemp > 1) {
            calculateAmountResult(sum);
        } else {
            setComment(NOT_AMOUNT_FOR_CONDITION);
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
        try {
            mAmountEnterTemp = mSumOfRisk / (mPStop * mPPrice) * 100;
            mMainActivity.showResultForStep(mPPrice, mPStop, mPTake);
        } catch (ArithmeticException ex) {
            mMainActivity.showErrorSnackBarDivideOnZero();
        }
    }

    private void calculateAmountResult(long sum) {
        float mExpectedProfitPercent;
        float mExpectedLesionPercent;
        float profitDivideLesion;
        long mAmountEnter = mAmountEnterTemp / 100;
        float mExpectedProfit = mPTake * mAmountEnterTemp * mPPrice;
        float mExpectedLesion = mPStop * mAmountEnterTemp * mPPrice;
        try {
            mExpectedProfitPercent = mExpectedProfit / sum * 100;
            mExpectedLesionPercent = mExpectedLesion / sum * 100;
            profitDivideLesion = mExpectedProfit / mExpectedLesion;
            mMainActivity.showResultAmount(mAmountEnter, mExpectedProfit, mExpectedLesion, mExpectedProfitPercent, mExpectedLesionPercent);
            setComment(profitDivideLesion);
        } catch (ArithmeticException ex) {
            mMainActivity.showErrorSnackBarDivideOnZero();
        }
    }

    private void setComment(float profitDivideLesion) {
        int comment;
        if (profitDivideLesion < 3) {
            comment = R.string.main_presenter_no_sens_to_risk;
        } else if (profitDivideLesion > 5) {
            comment = R.string.main_presenter_doesnt_be_idiot;
        } else if (profitDivideLesion == NOT_AMOUNT_FOR_CONDITION) {
            comment = R.string.main_presenter_not_amount_for_condition;
        } else {
            comment = R.string.main_presenter_check_enter_condition_again;
        }
        mMainActivity.showResultComment(comment);
    }
}
