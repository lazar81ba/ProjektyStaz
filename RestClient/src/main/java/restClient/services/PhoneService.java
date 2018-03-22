package restClient.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.kamsoft.nfz.project.model.Phone;
import restClient.exceptions.AuthorizationException;
import restClient.exceptions.ExpireTokenException;
import restClient.exceptions.LocalUserRepositoryException;
import restClient.exceptions.PhoneServiceException;
import restClient.exceptions.RestException;
import restClient.model.HttpHeadersWrapper;
import restClient.model.URLRepresentation;
import restClient.model.User;
import restClient.repository.UserRepository;

public class PhoneService implements Service {
	private URLRepresentation restURL;
	private HttpHeadersWrapper headerWrapper;
	private final String QPM_ACCESS_TOKEN = "?access_token=";
	private final HttpStatusHandler statusHandler = new HttpStatusHandler();
	
	@Autowired
	public PhoneService (URLRepresentation restURL,HttpHeadersWrapper headerWrapper ) {
		this.restURL=restURL;
		this.headerWrapper=headerWrapper;
	}

	public void refreshToken() throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException {
		User user = UserRepository.getCurrentUser();
		user.setHeaderWrapper(headerWrapper);
		user.checkTokenExpireTime();
		user.refreshUserToken();
	}

	private RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
			protected boolean hasError(HttpStatus statusCode) {
				return false;
			}
		});
		return restTemplate;
	}

	@Override
	public Phone getPhone(String parameter) throws PhoneServiceException, ExpireTokenException, RestException {
		try {
			User user = UserRepository.getCurrentUser();
			user.setHeaderWrapper(headerWrapper);
			user.initializeUserToken();
			ObjectMapper objectMapper = new ObjectMapper();
			RestTemplate restTemplate = createRestTemplate();
			
			HttpEntity<String> request = new HttpEntity<String>(headerWrapper.getHeaders());
			String param = restURL.getUri() + "/phones/" + parameter + QPM_ACCESS_TOKEN
					+ user.getToken().getAccess_token();
			ResponseEntity<String> response = restTemplate.exchange(param, HttpMethod.GET, request, String.class);
			statusHandler.processResponse(response);
			Phone phone = objectMapper.readValue(response.getBody(), new TypeReference<Phone>() {
			});
			return phone;
		} catch (RestException e) {
			throw e;
		} catch (ExpireTokenException e) {
			throw e;
		} catch (Exception e) {
			throw new PhoneServiceException("Phone Service error", e);
		}
	}

	@Override
	public void updatePhone(Phone phone) throws PhoneServiceException, ExpireTokenException, RestException {
		try {
			User user = UserRepository.getCurrentUser();
			user.setHeaderWrapper(headerWrapper);
			user.initializeUserToken();
			RestTemplate restTemplate = createRestTemplate();
			HttpEntity<Object> request = new HttpEntity<Object>(phone, headerWrapper.getHeaders());
			String param = restURL.getUri() + "/phones/" + phone.getId() + QPM_ACCESS_TOKEN
					+ user.getToken().getAccess_token();
			ResponseEntity<String> response = restTemplate.exchange(param, HttpMethod.PUT, request, String.class);
			statusHandler.processResponse(response);
		} catch (RestException e) {
			throw e;
		} catch (ExpireTokenException e) {
			throw e;
		} catch (Exception e) {
			throw new PhoneServiceException("Phone Service error", e);
		}
	}

	@Override
	public void deletePhone(String parameter) throws PhoneServiceException, ExpireTokenException, RestException {
		try {
			User user = UserRepository.getCurrentUser();
			user.setHeaderWrapper(headerWrapper);
			user.initializeUserToken();
			RestTemplate restTemplate = createRestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(headerWrapper.getHeaders());
			String param = restURL.getUri() + "/phones/" + parameter + QPM_ACCESS_TOKEN
					+ user.getToken().getAccess_token();
			ResponseEntity<String> response = restTemplate.exchange(param, HttpMethod.DELETE, request, String.class);
			statusHandler.processResponse(response);
		} catch (RestException e) {
			throw e;
		} catch (ExpireTokenException e) {
			throw e;
		} catch (Exception e) {
			throw new PhoneServiceException("Phone Service error", e);
		}
	}

	@Override
	public void addPhone(Phone phone) throws PhoneServiceException, ExpireTokenException, RestException {
		try {
			User user = UserRepository.getCurrentUser();
			user.setHeaderWrapper(headerWrapper);
			user.initializeUserToken();
			RestTemplate restTemplate = createRestTemplate();
			HttpEntity<Object> request = new HttpEntity<Object>(phone, headerWrapper.getHeaders());
			String adress = restURL.getUri() + "/phones" + QPM_ACCESS_TOKEN + user.getToken().getAccess_token();
			ResponseEntity<String> response = restTemplate.postForEntity(adress, request, String.class);
			statusHandler.processResponse(response);
		} catch (RestException e) {
			throw e;
		} catch (ExpireTokenException e) {
			throw e;
		} catch (Exception e) {
			throw new PhoneServiceException("Phone Service error", e);
		}
	}

	@Override
	public List<Phone> getPhoneByModel(String parameter)
			throws PhoneServiceException, ExpireTokenException, RestException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			User user = UserRepository.getCurrentUser();
			user.setHeaderWrapper(headerWrapper);
			user.initializeUserToken();
			RestTemplate restTemplate = createRestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(headerWrapper.getHeaders());
			String param = restURL.getUri() + "/phones/model/" + parameter + QPM_ACCESS_TOKEN
					+ user.getToken().getAccess_token();
			ResponseEntity<String> response = restTemplate.exchange(param, HttpMethod.GET, request, String.class);
			statusHandler.processResponse(response);
			List<Phone> list = objectMapper.readValue(response.getBody(), new TypeReference<List<Phone>>() {
			});
			return list;
		} catch (RestException e) {
			throw e;
		} catch (ExpireTokenException e) {
			throw e;
		} catch (Exception e) {
			throw new PhoneServiceException("Phone Service error", e);
		}
	}
}
