package restClient.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import restClient.exceptions.ExpireTokenException;
import restClient.exceptions.RestException;

public class HttpStatusHandler {
	
	private void processUnauthorized(ResponseEntity<String> response)
			throws  RestException, ExpireTokenException {
		String message =  response.getBody();
		if (message.contains("Access token expired"))
			throw new ExpireTokenException("Token expired");
		if(message.contains("unauthorized"))
			throw new RestException("An Authentication object was not found (access token)");
		if(message.contains("invalid_token"))
			throw new RestException("Invalid access token");
	}

	public void processResponse(ResponseEntity<String> response)
			throws RestException, ExpireTokenException {
		if (response.getStatusCode().compareTo(HttpStatus.NOT_FOUND) == 0)
			throw new RestException("Given request not found");
		if (response.getStatusCode().compareTo(HttpStatus.UNPROCESSABLE_ENTITY) == 0)
			throw new RestException("Given data cannot be processed");
		if (response.getStatusCode().compareTo(HttpStatus.METHOD_NOT_ALLOWED) == 0)
			throw new RestException("Given method is not allowed");
		if (response.getStatusCode().compareTo(HttpStatus.UNAUTHORIZED) == 0)
			processUnauthorized(response);
	}
}
