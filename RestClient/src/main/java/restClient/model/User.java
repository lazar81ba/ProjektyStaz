package restClient.model;

import org.springframework.beans.factory.annotation.Autowired;

import restClient.exceptions.AuthorizationException;
import restClient.exceptions.ExpireTokenException;
import restClient.repository.UserRepository;

public class User {
	private String username;
	private String password;
	private AuthToken token;
	private HttpHeadersWrapper headerWrapper;
	
	@Autowired
	public void setHeaderWrapper(HttpHeadersWrapper headerWrapper) {
		this.headerWrapper = headerWrapper;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthToken getToken() {
		return token;
	}

	public void setToken(AuthToken token) {
		this.token = token;
	}

	private boolean hasToken() {
		if (token != null)
			return true;
		return false;
	}

	public void initializeUserToken() throws AuthorizationException {
		if (!hasToken()) {
			AuthToken tokenInfo = headerWrapper.sendTokenRequest(this.username, this.password);
			UserRepository.setUserToken(this.username, tokenInfo);
		}
	}
	
	public void checkTokenExpireTime() throws ExpireTokenException, AuthorizationException {
		AuthToken tokenInfo = headerWrapper.sendTokenRequest(this.username, this.password);
		if(tokenInfo.getExpires_in()<=0)
			throw new ExpireTokenException("Token expired");
	}

	public void refreshUserToken() throws AuthorizationException {
		if (hasToken()) {
			AuthToken tokenInfo = headerWrapper.refreshToken(token);
			UserRepository.setUserToken(this.username, tokenInfo);
		}

	}
}
