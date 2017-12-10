package com.bei.andrii.forexcalculator.activity.impl;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bei.andrii.forexcalculator.Const;
import com.bei.andrii.forexcalculator.R;
import com.bei.andrii.forexcalculator.activity.MainActivity;
import com.bei.andrii.forexcalculator.activity.MainActivityPresenter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

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
    @BindView(R.id.text_field_boxes_sum)
    TextFieldBoxes mBoxesSum;
    @BindView(R.id.text_field_enter_price)
    TextFieldBoxes mBoxesEnterPrice;
    @BindView(R.id.text_field_stop_price)
    TextFieldBoxes mBoxesStopPrice;
    @BindView(R.id.text_field_profit_price)
    TextFieldBoxes mBoxesFieldProfit;


    private MainActivityPresenter mMainActivityPresenter;
    private String mRiskValue;
    private String mTool;
    private int clickcount;

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

        if (!mEditTextSum.getText().toString().equals("") && !mEditTextEnterPrice.getText().toString().equals("") && !mEditTextStopPrice.getText().toString().equals("") && !mEditTextProfitPrice.getText().toString().equals("")) {
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
        } else {
            Snackbar.make(mTextViewAmountEnter, R.string.main_activity_empty_enter_fields, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showResultForStep(float mPPrice, int mPStop, int mPTake) {
        mTextViewPriceStep.setText(getResources().getString(R.string.tv_price_step, String.valueOf(roundFloat(mPPrice))));
        mTextViewPriceStop.setText(getResources().getString(R.string.tv_price_stop, mPStop));
        mTextViewProfitStep.setText(getResources().getString(R.string.tv_profit_steps, mPTake));
    }

    @Override
    public void showResultAmount(float mAmountEnter, float mExpectedProfit, float mExpectedLesion, float mExpectedProfitPercent, float mExpectedLesionPercent) {
        mTextViewAmountEnter.setText(getResources().getString(R.string.tv_amount_enter, String.valueOf(roundFloat(mAmountEnter))));
        mTextViewExpectedProfit.setText(getResources().getString(R.string.tv_expected_profit, String.valueOf(roundFloat(mExpectedProfit))));
        mTextViewExpectedLesion.setText(getResources().getString(R.string.tv_expected_lesion, String.valueOf(roundFloat(mExpectedLesion))));
        mTextViewPercentExpectedProfit.setText(getResources().getString(R.string.tv_percent_expected_profit, String.valueOf(roundFloat(mExpectedProfitPercent))));
        mTextViewPercentExpectedLesion.setText(getResources().getString(R.string.tv_percent_expected_lesion, String.valueOf(roundFloat(mExpectedLesionPercent))));
    }

    @Override
    public void showResultComment(int mComment) {
        if (mComment == R.string.main_presenter_no_sens_to_risk) {
            changeTextCommentCondition(mComment, R.color.comment_red);
            clearAmount();
        } else if (mComment == R.string.main_presenter_doesnt_be_idiot) {
            changeTextCommentCondition(mComment, R.color.comment_orange);
        } else if (mComment == R.string.main_presenter_lets_go) {
            changeTextCommentCondition(mComment, R.color.comment_green);
        } else if (mComment == R.string.main_presenter_not_amount_for_condition) {
            changeTextCommentCondition(mComment, R.color.comment_red);
            clearAmount();
        } else if (mComment == R.string.main_presenter_check_enter_condition_again) {
            changeTextCommentCondition(mComment, R.color.comment_red);
            clearAmount();
        }
    }

    @Override
    public void showErrorSnackBarDivideOnZero() {
        Snackbar.make(mTextViewAmountEnter, R.string.main_activity_divide_on_null, Snackbar.LENGTH_LONG).show();
    }

    @OnClick({R.id.et_enter_price, R.id.et_sum, R.id.et_stop_price, R.id.et_profit_price})
    public void clearTextField(View view) {
        int id = view.getId();
        if (id == R.id.et_enter_price) {
            cleanView(view);
        } else if (id == R.id.et_sum) {
            cleanView(view);
        } else if (id == R.id.et_stop_price) {
            cleanView(view);
        } else if (id == R.id.et_profit_price) {
            cleanView(view);
        }
    }

    private void clearAmount() {
        mTextViewAmountEnter.setText("0");
        mTextViewExpectedProfit.setText("0");
        mTextViewPercentExpectedProfit.setText("0");
        mTextViewExpectedLesion.setText("0");
        mTextViewPercentExpectedLesion.setText("0");
    }

    private void cleanView(View view) {
        clickcount = 0;
        clickcount++;
        ((ExtendedEditText) view).setText("");
        clickcount = 0;
    }

    private void changeTextCommentCondition(int mComment, int color) {
        mTextViewComment.setBackgroundColor(getResources().getColor(color));
        mTextViewComment.setText(mComment);
    }

    private void initSpinners() {
        mRiskValue = DEFAULT_RISK_VALUE;
        mTool = Const.EUR_USD_TOOL;
        mSpinnerRisk.setItems(getResources().getStringArray(R.array.spinner_string_array_risk));
        mSpinnerRisk.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, getString(R.string.main_activity_risk_chosen) + " " + item, Snackbar.LENGTH_SHORT).show();
                mRiskValue = item.toString();
            }
        });
        mSpinnerTool.setItems(getResources().getStringArray(R.array.spinner_string_array_tools));
        mSpinnerTool.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(mTextViewAmountEnter, getString(R.string.main_activity_tool_chosen) + " " + item, Snackbar.LENGTH_SHORT).show();
                mTool = item.toString();
            }
        });
    }

    private float roundFloat(float number) {
        number = number * 100;
        int i = Math.round(number);
        return (float) i / 100;
    }

}