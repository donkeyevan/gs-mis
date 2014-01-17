package com.youai.gamemis.model;

public enum ActionType {
	UPDATE_PLAYER_GOLD(1,"更新玩家财富"),
	UPDATE_PLAYER_MONEY(2,"更新玩家现金"),
	UPDATE_PLAYER_ESLE(3,"更新玩家其他"),
	ADD_PLAYER_GOOODS(4,"添加玩家物品");
	
	private int type;
	private String desc;
	private ActionType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
