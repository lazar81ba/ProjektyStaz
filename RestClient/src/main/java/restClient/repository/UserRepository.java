package restClient.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import restClient.exceptions.LocalUserRepositoryException;
import restClient.model.AuthToken;
import restClient.model.User;

public class UserRepository {
	private static ConcurrentHashMap<String, User> userMap = new ConcurrentHashMap<String, User>();

	@Autowired
	public UserRepository(ConcurrentHashMap<String, User> map) {
		UserRepository.userMap = map;
	}

	public static User getCurrentUser() throws LocalUserRepositoryException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth instanceof AnonymousAuthenticationToken) 
			throw new LocalUserRepositoryException("Anonymous user loged in");
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			ConcurrentHashMap<String, User> userMap2 = userMap;
			if (!UserRepository.userMap.containsKey(username)) {
				User user = new User();
				user.setUsername(username);
				String pass = ((UserDetails) principal).getPassword();
				user.setPassword(pass);
				UserRepository.userMap.put(username, user);
				return user;
			}
			return getUser(username);
		}else throw new LocalUserRepositoryException("Security Context cannoc get instance of UserDetails");
	}

	public static User getUser(String username) {
		return UserRepository.userMap.get(username);
	}

	public static void setUserToken(String username, AuthToken token) {
		UserRepository.userMap.get(username).setToken(token);
	}
}
