package com.bei.andrii.forexcalculator.activity.impl;

import com.bei.andrii.forexcalculator.Const;
import com.bei.andrii.forexcalculator.R;
import com.bei.andrii.forexcalculator.activity.MainActivity;
import com.bei.andrii.forexcalculator.activity.MainActivityPresenter;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private static final int PPRICE_FOR_EUR_USD = 1;
    private static final int LOT_FOR_EUR_USD = 100000;
    private static final int NOT_AMOUNT_FOR_CONDITION = 0;

    private MainActivity mMainActivity;
    private int mPPrice;
    private long mAmountEnterTemp;
    private int mPStop;
    private int mPTake;
    private long mSumOfRisk;
    private int mPLOt;
    private long mSum;

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
            mPLOt = LOT_FOR_EUR_USD;
        }
        mSumOfRisk = sum * risk;
        mSum = sum;
        calculateResultForStep(enterPrice, stopPrice, profitPrice);
    }

    private void calculateResultForStep(float enterPrice, float stopPrice, float profitPrice) {

        if (enterPrice > stopPrice && enterPrice < profitPrice) {
            float pStop = (enterPrice - stopPrice) * mPLOt;
            mPStop = Math.round(pStop);
            float pTake = (profitPrice - enterPrice) * mPLOt;
            mPTake = Math.round(pTake);
            fairyDataCalculate();
        } else if (enterPrice < stopPrice && enterPrice > profitPrice) {
            float pStop = (stopPrice - enterPrice) * mPLOt;
            mPStop = (int) pStop;
            float pTake = (enterPrice - profitPrice) * mPLOt;
            mPTake = (int) pTake;
            fairyDataCalculate();
        } else {
            mMainActivity.showResultComment(R.string.main_presenter_check_enter_condition_again);
        }
    }

    private void fairyDataCalculate() {
        try {
            mAmountEnterTemp = mSumOfRisk / (mPStop * mPPrice);
            mMainActivity.showResultForStep(mPPrice, mPStop, mPTake);
        } catch (ArithmeticException ex) {
            mMainActivity.showErrorSnackBarDivideOnZero();
        }

        if (mAmountEnterTemp >= 1) {
            calculateAmountResult(mSum);
        } else {
            setComment(NOT_AMOUNT_FOR_CONDITION);
        }
    }

    private void calculateAmountResult(long sum) {
        float mExpectedProfitPercent;
        float mExpectedLesionPercent;
        float profitDivideLesion;
        float mAmountEnter = (float) (mAmountEnterTemp * 0.01);
        float mExpectedProfit = mPTake * mAmountEnter * mPPrice;
        float mExpectedLesion = mPStop * mAmountEnter * mPPrice;
        try {
            mExpectedProfitPercent = (float) (mExpectedProfit / sum * 100);
            mExpectedLesionPercent = (float) (mExpectedLesion / sum * 100);
            profitDivideLesion = (float) (mExpectedProfit / mExpectedLesion);
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
            comment = R.string.main_presenter_lets_go;
        }
        mMainActivity.showResultComment(comment);
    }

}
