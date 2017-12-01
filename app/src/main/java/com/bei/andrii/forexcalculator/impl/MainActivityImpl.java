package com.bei.andrii.forexcalculator.impl;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bei.andrii.forexcalculator.MainActivity;
import com.bei.andrii.forexcalculator.MainActivityPresenter;
import com.bei.andrii.forexcalculator.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    @BindView(R.id.et_sum)
    ExtendedEditText mEditTextSum;
    @BindView(R.id.spinner_risk)
    MaterialSpinner mSpinnerRisk;
    @BindView(R.id.spinner_tool)
    MaterialSpinner mSpinnerTool;
    @BindView(R.id.et_enter_price)
    ExtendedEditText mEditTextEnterPrice;
    @BindView(R.id.et_stop_price)
    ExtendedEditText mEditTextStopPrice;
    @BindView(R.id.et_profit_price)
    ExtendedEditText mEditTextProfitPrice;
    @BindView(R.id.tv_price_step)
    TextView mTextViewPriceStep;
    @BindView(R.id.tv_price_stop)
    TextView mTextViewPriceSop;
    @BindView(R.id.tv_profit_step)
    TextView mTextViewProfitStep;
    @BindView(R.id.btn_calculate)
    Button mButtonCalculate;
    @BindView(R.id.tv_amount_enter)
    TextView mTextViewAmountEnter;
    @BindView(R.id.tv_expected_profit)
    TextView mTextViewExpectedProfit;
    @BindView(R.id.tv_percent_expected_profit)
    TextView mTextViewPercentExpectedProfit;
    @BindView(R.id.tv_expected_lesion)
    TextView mTextViewExpectedLesion;
    @BindView(R.id.tv_percent_expected_lesion)
    TextView mTextViewPercentExpectedLesion;
    @BindView(R.id.tv_comment)
    TextView mTextViewComment;


    private MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainActivityPresenter = new MainActivityPresenterImpl();
        mMainActivityPresenter.bind(this);
        initSpinners();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainActivityPresenter.unbind();
    }

    private void initSpinners() {
        mSpinnerRisk.setItems(getResources().getStringArray(R.array.spinner_string_array_risk));
        mSpinnerRisk.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter,"Выбран риск: " + item,Snackbar.LENGTH_SHORT).show();
            }
        });
        mSpinnerTool.setItems(getResources().getStringArray(R.array.spinner_string_array_tools));
        mSpinnerTool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter,"Выбран инструмент: " + item,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
