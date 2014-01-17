package com.youai.gamemis.model;

public class OptionValue{
	Object key;
	Object parentKey;
	String value;
	String parentValue;
	
	/*
	 * 关联的输入框
	 */
	String relatedInput;
	
	/*
	 * 关联的输入框的label名称
	 */
	String relatedInputLabel;
	
	/*
	 * 上级输入框的名称
	 */
	String parentInput;
	
	/*
	 * 上级输入框的值
	 */
	String parentInputValue;
	
	
	public String getRelatedInput() {
		return relatedInput;
	}

	public void setRelatedInput(String relatedInput) {
		this.relatedInput = relatedInput;
	}

	public String getRelatedInputLabel() {
		return relatedInputLabel;
	}

	public void setRelatedInputLabel(String relatedInputLabel) {
		this.relatedInputLabel = relatedInputLabel;
	}

	public String getParentInput() {
		return parentInput;
	}

	public void setParentInput(String parentInput) {
		this.parentInput = parentInput;
	}

	public String getParentInputValue() {
		return parentInputValue;
	}

	public void setParentInputValue(String parentInputValue) {
		this.parentInputValue = parentInputValue;
	}

	public OptionValue() {
		super();
	}

	public OptionValue(Object key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	
	public Object getParentKey() {
		return parentKey;
	}
	public void setParentKey(Object parentKey) {
		this.parentKey = parentKey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParentValue() {
		return parentValue;
	}
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}
	
	
}