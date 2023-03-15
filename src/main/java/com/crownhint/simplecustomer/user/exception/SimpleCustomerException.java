package com.crownhint.simplecustomer.user.exception;

public class SimpleCustomerException extends RuntimeException{

    private int statusCode;
    public SimpleCustomerException(String message) {
        super(message);
    }

    public SimpleCustomerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public SimpleCustomerException(int statusCode) {
        this.statusCode = statusCode;
    }
}
