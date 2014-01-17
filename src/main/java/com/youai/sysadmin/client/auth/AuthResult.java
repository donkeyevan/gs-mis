package com.youai.sysadmin.client.auth;
import com.youai.sysadmin.client.auth.AuthResultValue;
public class AuthResult {
	
	public AuthResultValue getResult() {
		return result;
	}
	public void setResult(AuthResultValue result) {
		this.result = result;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public AuthResultValue result;
	public String failReason;
	public int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
