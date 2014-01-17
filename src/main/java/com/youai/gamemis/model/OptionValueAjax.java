package com.youai.gamemis.model;


import java.util.List;

public class OptionValueAjax{
	
	/*
	 * 预清除input列表
	 */
	String[] preCleanInputs;
	
	List<OptionValue> options;
	
	public String[] getPreCleanInputs() {
		return preCleanInputs;
	}
	public void setPreCleanInputs(String[] preCleanInputs) {
		this.preCleanInputs = preCleanInputs;
	}
	public List<OptionValue> getOptions() {
		return options;
	}
	public void setOptions(List<OptionValue> options) {
		this.options = options;
	}
}
