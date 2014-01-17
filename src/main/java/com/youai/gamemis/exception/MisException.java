package com.youai.gamemis.exception;

public class MisException extends Exception{
    private static final long serialVersionUID = 1L;
    private int errorId;
    
    public MisException(int errorCode) {
        this.errorId = errorCode;
    }
    
    public MisException(String message){
    	super( message );
    }
    public int getErrorCode() {
        return errorId;
    }
}
