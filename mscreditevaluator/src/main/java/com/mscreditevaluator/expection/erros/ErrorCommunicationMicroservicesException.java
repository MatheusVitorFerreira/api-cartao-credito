package com.mscreditevaluator.expection.erros;

public class ErrorCommunicationMicroservicesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErrorCommunicationMicroservicesException(String msg) {
        super(msg);
    }

}