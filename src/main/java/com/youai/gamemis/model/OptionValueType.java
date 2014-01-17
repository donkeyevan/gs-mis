package com.youai.gamemis.model;


import java.util.List;

public class OptionValueType{
	//type=0 ：value来自于用户输入的values，type=1：value来自于关联的类属性值
	int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<OptionValue> getOptions() {
		return options;
	}
	public void setOptions(List<OptionValue> options) {
		this.options = options;
	}
	List<OptionValue> options;
}
