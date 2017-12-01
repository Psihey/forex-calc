package com.bei.andrii.forexcalculator;

/**
 * Created by Psihey on 30.11.2017.
 */

public interface MainActivityPresenter {

    void bind (MainActivity mainActivity);

    void unbind();

    void calculate(int sum, float enterPrice, float stopPrice, float profitPrice, int risk, String tool);
}
