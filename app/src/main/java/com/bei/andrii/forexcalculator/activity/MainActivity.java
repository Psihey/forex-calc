package com.bei.andrii.forexcalculator.activity;

public interface MainActivity {

    void showResultForStep(int mPPrice, int mPStop, int mPTake);

    void showResultAmount(float mAmountEnter, double mExpectedProfit, double mExpectedLesion, float mExpectedProfitPercent, float mExpectedLesionPercent);

    void showResultComment(int mComment);

    void showErrorSnackBarDivideOnZero();

}
