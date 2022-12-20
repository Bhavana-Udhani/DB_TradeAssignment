package com.ofss.trade.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TradeException extends RuntimeException{

    public TradeException(String message) {
        super(message);
    }

}
