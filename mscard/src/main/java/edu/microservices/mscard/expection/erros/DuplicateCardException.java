package edu.microservices.mscard.expection.erros;

public class DuplicateCardException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateCardException(String msg) {
        super(msg);
    }

}