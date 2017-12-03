package com.bei.andrii.forexcalculator.activity.impl;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bei.andrii.forexcalculator.Const;
import com.bei.andrii.forexcalculator.activity.MainActivity;
import com.bei.andrii.forexcalculator.activity.MainActivityPresenter;
import com.bei.andrii.forexcalculator.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class MainActivityImpl extends AppCompatActivity implements MainActivity {

    private static final String DEFAULT_RISK_VALUE = "2";

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
        long sum = Long.valueOf(mEditTextSum.getText().toString());
        float enterPrice = Float.valueOf(mEditTextEnterPrice.getText().toString());
        float stopPrice = Float.valueOf(mEditTextStopPrice.getText().toString());
        float profitPrice = Float.valueOf(mEditTextProfitPrice.getText().toString());
        int risk = Integer.valueOf(mRiskValue);

        if (sum != 0 && enterPrice != 0.0 && stopPrice != 0.0 && profitPrice != 0.0) {
            mMainActivityPresenter.calculate(sum, enterPrice, stopPrice, profitPrice, risk, mTool);
        } else {
            Snackbar.make(mTextViewAmountEnter, R.string.main_activity_error_data, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showResultForStep(int mPPrice, int mPStop, int mPTake) {
        mTextViewPriceStep.setText(getResources().getString(R.string.tv_price_step, mPPrice));
        mTextViewPriceStop.setText(getResources().getString(R.string.tv_price_stop, mPStop));
        mTextViewProfitStep.setText(getResources().getString(R.string.tv_profit_steps, mPTake));
    }

    @Override
    public void showResultAmount(long mAmountEnter, float mExpectedProfit, float mExpectedLesion, float mExpectedProfitPercent, float mExpectedLesionPercent) {
        mTextViewAmountEnter.setText(getResources().getString(R.string.tv_amount_enter, String.valueOf(mAmountEnter)));
        mTextViewExpectedProfit.setText(getResources().getString(R.string.tv_expected_profit, String.valueOf(mExpectedProfit)));
        mTextViewExpectedLesion.setText(getResources().getString(R.string.tv_expected_lesion, String.valueOf(mExpectedLesion)));
        mTextViewPercentExpectedProfit.setText(getResources().getString(R.string.tv_percent_expected_profit, String.valueOf(mExpectedProfitPercent)));
        mTextViewPercentExpectedLesion.setText(getResources().getString(R.string.tv_percent_expected_lesion, String.valueOf(mExpectedLesionPercent)));
    }

    @Override
    public void showResultComment(int mComment) {
        mTextViewComment.setText(mComment);
    }

    @Override
    public void showErrorSnackBarDivideOnZero() {
        Snackbar.make(mTextViewAmountEnter, R.string.main_activity_divide_on_null, Snackbar.LENGTH_LONG).show();
    }

    private void initSpinners() {
        mRiskValue = DEFAULT_RISK_VALUE;
        mTool = Const.EUR_USD_TOOL;
        mSpinnerRisk.setItems(getResources().getStringArray(R.array.spinner_string_array_risk));
        mSpinnerRisk.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, getString(R.string.main_activity_risk_chosen) + item, Snackbar.LENGTH_SHORT).show();
                mRiskValue = item.toString();
            }
        });
        mSpinnerTool.setItems(getResources().getStringArray(R.array.spinner_string_array_tools));
        mSpinnerTool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, getString(R.string.main_activity_tool_chosen) + item, Snackbar.LENGTH_SHORT).show();
                mTool = item.toString();
            }
        });
    }

}