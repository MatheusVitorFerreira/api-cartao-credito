package edu.microservices.mscartao.expection;

import java.util.List;

public class ValidationError extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private List<FieldMessage> fieldMessages;

	public ValidationError(String msg) {
		super(msg);
	}

	public ValidationError(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValidationError(List<FieldMessage> fieldMessages) {
		super("Ocorreu um erro de importação duplicada.");
		this.fieldMessages = fieldMessages;
	}

	public List<FieldMessage> getFieldMessages() {
		return fieldMessages;
	}
}

