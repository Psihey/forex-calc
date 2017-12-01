package com.bei.andrii.forexcalculator.impl;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bei.andrii.forexcalculator.MainActivity;
import com.bei.andrii.forexcalculator.MainActivityPresenter;
import com.bei.andrii.forexcalculator.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    TextView mTextViewPriceStop;
    @BindView(R.id.tv_profit_step)
    TextView mTextViewProfitStep;
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
    private String mRiskValue;
    private String mTool;

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

    @OnClick(R.id.btn_calculate)
    public void calculate() {

        int sum = Integer.valueOf(mEditTextSum.getText().toString());
        float enterPrice = Float.valueOf(mEditTextEnterPrice.getText().toString());
        float stopPrice = Float.valueOf(mEditTextStopPrice.getText().toString());
        float profitPrice = Float.valueOf(mEditTextProfitPrice.getText().toString());
        int risk = Integer.valueOf(mRiskValue);

        if (sum != 0 && enterPrice != 0.0 && stopPrice != 0.0 && profitPrice != 0.0) {
            mMainActivityPresenter.calculate(sum, enterPrice, stopPrice, profitPrice,risk,mTool);
        } else {
            Snackbar.make(mTextViewAmountEnter, "Неверные даные", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showResultForStep(int mPPrice, int mPStop, int mPTake) {
        mTextViewPriceStep.setText(getResources().getString(R.string.tv_price_step,mPPrice));
        mTextViewPriceStop.setText(getResources().getString(R.string.tv_price_stop,mPStop));
        mTextViewProfitStep.setText(getResources().getString(R.string.tv_profit_steps,mPTake));
    }

    @Override
    public void showResultAmount() {

    }

    @Override
    public void showResultComment() {

    }

    private void initSpinners() {
        mRiskValue = "2";
        mTool = "EUR/USD";
        mSpinnerRisk.setItems(getResources().getStringArray(R.array.spinner_string_array_risk));
        mSpinnerRisk.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, "Выбран риск: " + item, Snackbar.LENGTH_SHORT).show();
                mRiskValue = item.toString();
            }
        });
        mSpinnerTool.setItems(getResources().getStringArray(R.array.spinner_string_array_tools));
        mSpinnerTool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, "Выбран инструмент: " + item, Snackbar.LENGTH_SHORT).show();
                mTool = item.toString();
            }
        });
    }


}
