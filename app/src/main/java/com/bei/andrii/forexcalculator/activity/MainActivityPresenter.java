package com.bei.andrii.forexcalculator.activity;

public interface MainActivityPresenter {

    void bind (MainActivity mainActivity);

    void unbind();

    void calculate(long sum, float enterPrice, float stopPrice, float profitPrice, int risk, String tool);
}
