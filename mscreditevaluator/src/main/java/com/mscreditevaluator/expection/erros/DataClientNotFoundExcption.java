package com.mscreditevaluator.expection.erros;

public class DataClientNotFoundExcption extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataClientNotFoundExcption(String msg) {

        super(msg);
    }

}