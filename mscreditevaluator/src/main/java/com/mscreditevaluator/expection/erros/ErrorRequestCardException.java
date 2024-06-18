package com.mscreditevaluator.expection.erros;

public class ErrorRequestCardException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErrorRequestCardException(String msg) {
        super(msg);
    }

}