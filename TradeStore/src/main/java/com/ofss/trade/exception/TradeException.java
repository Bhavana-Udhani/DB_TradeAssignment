package com.ofss.trade.exception;

public class TradeException extends RuntimeException{
    private String message;

    public TradeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
