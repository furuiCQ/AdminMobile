package com.ririjin.adminmobile.adapter;

import java.io.Serializable;

public class MoneyDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String assess_price = "";
	public String loan_period_type = "";
	public String loan_period = "";
	public String loan_rates = "";
	public String collecting_fees = "";
	public String collecting_margin = "";
	public String parking = "";
	public String illegal_deduction = "";
	public String other_deduction = "";
	public String actual_borrow_money = "";

	public String getAssess_price() {
		return assess_price;
	}

	public void setAssess_price(String assess_price) {
		this.assess_price = assess_price;
	}

	public String getLoan_period_type() {
		return loan_period_type;
	}

	public void setLoan_period_type(String loan_period_type) {
		this.loan_period_type = loan_period_type;
	}

	public String getLoan_period() {
		return loan_period;
	}

	public void setLoan_period(String loan_period) {
		this.loan_period = loan_period;
	}

	public String getLoan_rates() {
		return loan_rates;
	}

	public void setLoan_rates(String loan_rates) {
		this.loan_rates = loan_rates;
	}

	public String getCollecting_fees() {
		return collecting_fees;
	}

	public void setCollecting_fees(String collecting_fees) {
		this.collecting_fees = collecting_fees;
	}

	public String getCollecting_margin() {
		return collecting_margin;
	}

	public void setCollecting_margin(String collecting_margin) {
		this.collecting_margin = collecting_margin;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getIllegal_deduction() {
		return illegal_deduction;
	}

	public void setIllegal_deduction(String illegal_deduction) {
		this.illegal_deduction = illegal_deduction;
	}

	public String getOther_deduction() {
		return other_deduction;
	}

	public void setOther_deduction(String other_deduction) {
		this.other_deduction = other_deduction;
	}

	public String getActual_borrow_money() {
		return actual_borrow_money;
	}

	public void setActual_borrow_money(String actual_borrow_money) {
		this.actual_borrow_money = actual_borrow_money;
	}

}
