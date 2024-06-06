package katachi.spring.exercise.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import katachi.spring.exercise.userwithcode.UserWithCode;

@Component
public class SecurityUtil {

	public UserWithCode getCurrentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (UserWithCode) authentication.getPrincipal();
	}

}
