package restClient.model;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import restClient.exceptions.AuthorizationException;

public class HttpHeadersWrapper {

	private URLRepresentation restURL;

	private  String AUTH_SERVER_URI = "/oauth/token";

	private final String QPM_PASSWORD_GRANT = "?grant_type=password&username=";

	private final String TOKEN_REFRESH = "?grant_type=refresh_token&refresh_token=";
	
	@Autowired
	public HttpHeadersWrapper(URLRepresentation restURL) {
		this.restURL=restURL;
	}
	
	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
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

	private HttpHeaders getHeadersWithClientCredentials() {
		String plainClientCredentials = "RestClient:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
	private AuthToken mapToToken(LinkedHashMap<String, Object> map) throws AuthorizationException {
		AuthToken tokenInfo = null;

		if (map != null) {
			tokenInfo = new AuthToken();
			tokenInfo.setAccess_token((String) map.get("access_token"));
			tokenInfo.setToken_type((String) map.get("token_type"));
			tokenInfo.setRefresh_token((String) map.get("refresh_token"));
			tokenInfo.setExpires_in((int) map.get("expires_in"));
			tokenInfo.setScope((String) map.get("scope"));
			return tokenInfo;
		} else
			throw new AuthorizationException("No user exists");		
	}

	public AuthToken sendTokenRequest(String username, String password) throws AuthorizationException {
		RestTemplate restTemplate = new RestTemplate();
		String PASSWORD_CONTENT = username + "&password=" + password;
		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = restTemplate.exchange(restURL.getUri()+AUTH_SERVER_URI + QPM_PASSWORD_GRANT + PASSWORD_CONTENT
				, HttpMethod.POST, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		return mapToToken(map);
	}
	
	
	
	public AuthToken refreshToken(AuthToken token) throws AuthorizationException {
		RestTemplate restTemplate = createRestTemplate();
		HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
		ResponseEntity<Object> response = restTemplate.exchange(restURL.getUri()+AUTH_SERVER_URI +TOKEN_REFRESH + token.getRefresh_token()
				, HttpMethod.POST, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		return mapToToken(map);
	}
}
