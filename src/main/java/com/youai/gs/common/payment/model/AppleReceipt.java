package com.youai.gs.common.payment.model;

import java.io.Serializable;

public class AppleReceipt implements Serializable {

	int status;
	String latest_receipt;
	AppleStore 	receipt;
	AppleStore latest_receipt_info;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLatest_receipt() {
		return latest_receipt;
	}
	public void setLatest_receipt(String latestReceipt) {
		latest_receipt = latestReceipt;
	}
	public AppleStore getReceipt() {
		return receipt;
	}
	public void setReceipt(AppleStore receipt) {
		this.receipt = receipt;
	}
	public AppleStore getLatest_receipt_info() {
		return latest_receipt_info;
	}
	public void setLatest_receipt_info(AppleStore latestReceiptInfo) {
		latest_receipt_info = latestReceiptInfo;
	}
	
}
