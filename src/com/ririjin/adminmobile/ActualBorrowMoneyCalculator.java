package com.ririjin.adminmobile;

import com.ririjin.adminmobile.adapter.MoneyDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class ActualBorrowMoneyCalculator extends Activity {

	/**
	 * 实际划转资金
	 */
	public double Money_ActualBorrow;
	/**
	 * 评估价格
	 */
	public double Money_Assess;
	EditText MoneyAssessEditText;
	/**
	 * 借款周期
	 */
	public double Money_LoanPeriod;
	Spinner MoneyLoanPeriodSpinner;
	String loanPeridoType[] = { "22天", "30天", "月" };

	EditText MoneyLoanPeriodEditText;
	/**
	 * 借款利率
	 */
	public double Money_LoanRates;
	EditText MoneyLoanRatesEditText;
	/**
	 * 代收手续费
	 */
	public double Money_CollectingFees;
	EditText MoneyCollectingFeesEditText;
	/**
	 * 代收保证金
	 */
	public double Money_CollectingMargin;
	EditText MoneyCollectingMarginEditText;
	/**
	 * 停车费
	 */
	public double Money_Parking;
	EditText MoneyParkingEditText;
	/**
	 * 违章扣除
	 */
	public double Money_IllegalDeduction;
	EditText MoneyIllegalDeductionEditText;

	/**
	 * 其它扣除
	 */
	public double Money_OtherDeduction;
	EditText MoneyOtherDeductionEditText;

	TextView BackButton;
	TextView MingXIText;
	Button RestButton;
	Button DetermineButton;

	public String AssetID;
	MoneyDetails moneyDetails = new MoneyDetails();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		Money_LoanRates = 0.1;
		Money_LoanPeriod = 22;

		BackButton = (TextView) findViewById(R.id.back);
		MingXIText = (TextView) findViewById(R.id.mingxi_text);
		RestButton = (Button) findViewById(R.id.reset_btn);
		DetermineButton = (Button) findViewById(R.id.determine_btn);

		MoneyLoanPeriodEditText = (EditText) findViewById(R.id.money_loan_period_edit);
		MoneyAssessEditText = (EditText) findViewById(R.id.money_assess_edit);
		MoneyLoanRatesEditText = (EditText) findViewById(R.id.money_loan_rates_edit);

		MoneyCollectingFeesEditText = (EditText) findViewById(R.id.money_collecting_fees_edit);
		MoneyCollectingMarginEditText = (EditText) findViewById(R.id.money_collecting_margin_edit);

		MoneyParkingEditText = (EditText) findViewById(R.id.money_parking_edit);
		MoneyIllegalDeductionEditText = (EditText) findViewById(R.id.money_illegal_deduction_edit);
		MoneyOtherDeductionEditText = (EditText) findViewById(R.id.money_other_deduction_edit);
		MoneyLoanPeriodSpinner = (Spinner) findViewById(R.id.cycle_type_spinner);

		BackButton.setOnClickListener(listener);
		RestButton.setOnClickListener(listener);
		DetermineButton.setOnClickListener(listener);

		MoneyLoanPeriodEditText.setOnFocusChangeListener(focusChangeListener);
		MoneyAssessEditText.setOnFocusChangeListener(focusChangeListener);
		MoneyLoanRatesEditText.setOnFocusChangeListener(focusChangeListener);
		MoneyCollectingFeesEditText
				.setOnFocusChangeListener(focusChangeListener);
		MoneyCollectingMarginEditText
				.setOnFocusChangeListener(focusChangeListener);
		MoneyParkingEditText.setOnFocusChangeListener(focusChangeListener);
		MoneyIllegalDeductionEditText
				.setOnFocusChangeListener(focusChangeListener);
		MoneyOtherDeductionEditText
				.setOnFocusChangeListener(focusChangeListener);

		MoneyLoanPeriodEditText.setOnEditorActionListener(editorActionListener);
		MoneyAssessEditText.setOnEditorActionListener(editorActionListener);
		MoneyLoanRatesEditText.setOnEditorActionListener(editorActionListener);
		MoneyCollectingFeesEditText
				.setOnEditorActionListener(editorActionListener);
		MoneyCollectingMarginEditText
				.setOnEditorActionListener(editorActionListener);
		MoneyParkingEditText.setOnEditorActionListener(editorActionListener);
		MoneyIllegalDeductionEditText
				.setOnEditorActionListener(editorActionListener);
		MoneyOtherDeductionEditText
				.setOnEditorActionListener(editorActionListener);

		MoneyLoanPeriodSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						Log.i("" + position, MoneyLoanPeriodSpinner
								.getSelectedItem().toString());

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

	}

	OnEditorActionListener editorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub

			if (actionId == EditorInfo.IME_ACTION_DONE
					|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
				// do something;

				v.clearFocus();
				return true;
			}
			return false;
		}
	};
	OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean focus) {
			// TODO Auto-generated method stub
			if (!focus) {
				switch (v.getId()) {
				case R.id.money_assess_edit:
					if (MoneyAssessEditText.getText().toString() != null
							&& !MoneyAssessEditText.getText().toString()
									.equals("")) {
						Money_Assess = Double.parseDouble(MoneyAssessEditText
								.getText().toString()) * 10000;
					} else {
						Money_Assess = 0;
					}
					break;

				case R.id.money_loan_period_edit:
					if (MoneyLoanPeriodEditText.getText().toString() != null
							&& !MoneyLoanPeriodEditText.getText().toString()
									.equals("")) {
						Money_LoanPeriod = Double
								.parseDouble(MoneyLoanPeriodEditText.getText()
										.toString());
					} else {
						Money_LoanPeriod = 0;
					}

					break;

				case R.id.money_loan_rates_edit:
					if (MoneyLoanRatesEditText.getText().toString() != null
							&& !MoneyLoanRatesEditText.getText().toString()
									.equals("")) {
						Money_LoanRates = Double
								.parseDouble(MoneyLoanRatesEditText.getText()
										.toString());
					} else {
						Money_LoanRates = 0;
					}

					break;
				case R.id.money_collecting_fees_edit:
					if (MoneyCollectingFeesEditText.getText().toString() != null
							&& !MoneyCollectingFeesEditText.getText()
									.toString().equals("")) {
						Money_CollectingFees = Double
								.parseDouble(MoneyCollectingFeesEditText
										.getText().toString());
					} else {
						Money_CollectingFees = 0;
					}

					break;
				case R.id.money_collecting_margin_edit:
					if (MoneyCollectingMarginEditText.getText().toString() != null
							&& !MoneyCollectingMarginEditText.getText()
									.toString().equals("")) {
						Money_CollectingMargin = Double
								.parseDouble(MoneyCollectingMarginEditText
										.getText().toString());

					} else {
						Money_CollectingMargin = 0;
					}

					break;
				case R.id.money_parking_edit:
					if (MoneyParkingEditText.getText().toString() != null
							&& !MoneyParkingEditText.getText().toString()
									.equals("")) {
						Money_Parking = Double.parseDouble(MoneyParkingEditText
								.getText().toString());
					} else {
						Money_Parking = 0;
					}

					break;
				case R.id.money_illegal_deduction_edit:
					if (MoneyIllegalDeductionEditText.getText().toString() != null
							&& !MoneyIllegalDeductionEditText.getText()
									.toString().equals("")) {
						Money_IllegalDeduction = Double
								.parseDouble(MoneyIllegalDeductionEditText
										.getText().toString());
					} else {
						Money_IllegalDeduction = 0;
					}

					break;
				case R.id.money_other_deduction_edit:
					if (MoneyOtherDeductionEditText.getText().toString() != null
							&& !MoneyOtherDeductionEditText.getText()
									.toString().equals("")) {
						Money_OtherDeduction = Double
								.parseDouble(MoneyOtherDeductionEditText
										.getText().toString());
					} else {
						Money_OtherDeduction = 0;
					}

					break;
				default:
					break;
				}
			}

		}
	};

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				Intent intent1 = new Intent();
				intent1.putExtra("actual_borrow", 0);
				setResult(1001, intent1);
				finish();
				break;
			case R.id.reset_btn:
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("money_details", moneyDetails);
				intent.putExtras(bundle);
				setResult(1001, intent);
				finish();

				break;
			case R.id.determine_btn:
				MoneyLoanPeriodEditText.clearFocus();
				MoneyAssessEditText.clearFocus();
				MoneyLoanRatesEditText.clearFocus();
				MoneyCollectingFeesEditText.clearFocus();
				MoneyCollectingMarginEditText.clearFocus();

				MoneyParkingEditText.clearFocus();
				MoneyIllegalDeductionEditText.clearFocus();
				MoneyOtherDeductionEditText.clearFocus();
				MoneyLoanPeriodSpinner.clearFocus();

				GetActualBorrowMoney();

				break;

			default:
				break;
			}
		}
	};

	// 实际划转资金计算算法

	// 车商评估价格 - 车商评估价格 * 借款周期 * 借款利率（%） - 车商评估价格 * 代收手续费（%） - 车商评估价格 *代收保证金（%） -
	// 停车费 - 违章扣除 - 其它扣除
	public double GetActualBorrowMoney() {
		if (!MoneyLoanPeriodSpinner.getSelectedItem().toString().equals("22天")) {
			Money_LoanPeriod = 30;
		} else {
			Money_LoanPeriod = 22;
		}

		Money_ActualBorrow = (Money_Assess - Money_Assess * Money_LoanPeriod
				* Money_LoanRates / 100 - Money_Assess * Money_CollectingFees
				/ 100 - Money_Assess * Money_CollectingMargin / 100
				- Money_Parking - Money_IllegalDeduction - Money_OtherDeduction) / 10000;

		MingXIText.setText("计算明细:" + "\n" + "车商评估价格:" + Money_Assess + "元"
				+ "\n" + "头息:"
				+ (Money_Assess * Money_LoanPeriod * Money_LoanRates / 100)
				+ "元" + "\n" + "代收手续费"
				+ Money_Assess * Money_CollectingFees / 100 + "元" + "\n"
				+ "代收保证金元" + Money_Assess * Money_CollectingMargin / 100 + "元"
				+ "\n" + "停车费" + Money_Parking + "元" + "\n" + "违章扣除"
				+ Money_IllegalDeduction + "元" + "\n" + "其它扣除"
				+ Money_OtherDeduction + "元" + "\n" + "实际支付金额"
				+ Money_ActualBorrow + "万元");

		moneyDetails.setActual_borrow_money(Money_ActualBorrow + "万元");
		moneyDetails.setAssess_price("" + Money_Assess);
		moneyDetails.setCollecting_fees("" + Money_Assess
				* Money_CollectingFees / 100 + "元");
		moneyDetails.setCollecting_margin(Money_Assess * Money_CollectingMargin
				/ 100 + "元");
		moneyDetails.setIllegal_deduction("" + Money_IllegalDeduction + "元");
		moneyDetails.setLoan_period("" + Money_LoanPeriod);
		moneyDetails.setLoan_period_type("" + Money_LoanPeriod);
		moneyDetails.setLoan_rates("" + Money_LoanRates);
		moneyDetails.setOther_deduction("" + Money_OtherDeduction + "元");
		moneyDetails.setParking("" + Money_Parking + "元");

		Toast.makeText(getApplicationContext(), "明细已列出，滑动到底部查看",
				Toast.LENGTH_SHORT).show();
		return Money_ActualBorrow;
	}
}
