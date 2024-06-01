package edu.microservices.msusers.exception.erros;

public class DuplicateEmployeeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateEmployeeException(String msg) {
        super(msg);
    }

}