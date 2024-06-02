package edu.microservices.msusers.exception.erros;

public class DuplicateClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateClientException(String msg) {
        super(msg);
    }

}