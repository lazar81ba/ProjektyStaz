package restClient.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import restClient.exceptions.ExpireTokenException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	public static final String DEFAULT_ERROR_VIEW = "jsp/error";

	private String stackToString(StackTraceElement[] stack) {
		String exception = "";
		for (StackTraceElement s : stack) {
			exception = exception + System.lineSeparator() + s.toString();
		}
		return exception;
	}

	@ExceptionHandler(SQLException.class)
	public ModelAndView handleDatabaseExceptions(HttpServletRequest request, Exception e) {
		logger.info("Database Exception, message:" + e.getLocalizedMessage() + " URL=" + request.getRequestURL());
		logger.info(stackToString(e.getStackTrace()));
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
		mav.addObject("datetime", new Date());
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		return mav;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleDefaultExceptions(HttpServletRequest request, Exception e) {
		logger.info("Exception, message:" + e.getLocalizedMessage() + " URL=" + request.getRequestURL());
		logger.info(stackToString(e.getStackTrace()));
		ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
		mav.addObject("datetime", new Date());
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		return mav;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(Exception e) {
		logger.error("IOException handler executed");
		logger.info("Exception, message:" + e.getLocalizedMessage() );
		logger.info(stackToString(e.getStackTrace()));
	}

	@ExceptionHandler(ExpireTokenException.class)
	public String logoutAfterTokenExpire(HttpServletRequest request, Exception e) {
		return "redirect:/logout";
	}
	
}
