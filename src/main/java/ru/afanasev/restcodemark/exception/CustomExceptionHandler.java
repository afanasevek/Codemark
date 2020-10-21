package ru.afanasev.restcodemark.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<NoUserException> handleEntityNotFoundException() {

		return new ResponseEntity<>(new NoUserException("There is no such user"), HttpStatus.NOT_FOUND);
	}

	private static class NoUserException {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public NoUserException(String message) {
			super();
			this.message = message;
		}

	}
}
