package serverApplication.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.kamsoft.nfz.project.exceptions.*;


@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	public static final String DEFAULT_ERROR_VIEW = "error";

	private String stackToString(StackTraceElement[] stack) {
		String exception = "";
		for (StackTraceElement s : stack) {
			exception = exception + System.lineSeparator() + s.toString();
		}
		return exception;
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<?> handleDatabaseExceptions(HttpServletRequest request, Exception e) {
		logger.info("Database Exception, message:" + e.getLocalizedMessage() + " URL=" + request.getRequestURL());
		logger.info(stackToString(e.getStackTrace()));
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InputException.class)
	public ResponseEntity<?> handleInputExceptions(HttpServletRequest request, Exception e) {
		logger.info("Database Exception, message:" + e.getLocalizedMessage() + " URL=" + request.getRequestURL());
		logger.info(stackToString(e.getStackTrace()));
		return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
