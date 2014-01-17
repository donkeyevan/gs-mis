package com.youai.gamemis.exception;

/**
 * 枚举未找到异常
 * @author mini5
 *
 */
public class EnumNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private Class clazz;
    private int key;
    
    public EnumNotFoundException(Class clazz, int key) {
        this.key = key;
        System.out.println("在" + clazz.getName() + "中没有找到key为" + key + "对应的枚举！");
    }
    
    public int getKey() {
        return key;
    }
    
    public Class clazz(){
    	return clazz;
    }
}
