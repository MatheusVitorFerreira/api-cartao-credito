package edu.microservices.msusers.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.microservices.msusers.exception.erros.ClientNotFoundException;
import edu.microservices.msusers.exception.erros.DuplicateEmployeeException;
import edu.microservices.msusers.exception.erros.EmployeeNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<StandardError> ClinicaNotFoundException(EmployeeNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DuplicateEmployeeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<StandardError> ClinicaNotFoundException(DuplicateEmployeeException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	@ExceptionHandler(ClientNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<StandardError> ClinicaNotFoundException(ClientNotFoundException e,
			HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), "Acesso negado",
				System.currentTimeMillis());
		((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(err));

	}
}
