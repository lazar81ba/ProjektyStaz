package restClient.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.kamsoft.nfz.project.model.Phone;
import restClient.exceptions.AuthorizationException;
import restClient.exceptions.ExpireTokenException;
import restClient.exceptions.LocalUserRepositoryException;
import restClient.exceptions.PhoneServiceException;
import restClient.exceptions.RestException;
import restClient.model.AuthToken;

public interface Service {
	public void refreshToken() throws LocalUserRepositoryException, AuthorizationException, ExpireTokenException;
	public Phone getPhone(String parameter) throws PhoneServiceException, ExpireTokenException, RestException;
	public void updatePhone(Phone phone) throws PhoneServiceException, ExpireTokenException, RestException;
	public void deletePhone(String parameter) throws PhoneServiceException, ExpireTokenException, RestException;
	public void addPhone(Phone phone) throws PhoneServiceException, ExpireTokenException, RestException;
	public List<Phone> getPhoneByModel(String parameter) throws PhoneServiceException, ExpireTokenException, RestException;
	}
