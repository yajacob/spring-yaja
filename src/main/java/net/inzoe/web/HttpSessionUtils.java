package net.inzoe.web;

import javax.servlet.http.HttpSession;

import net.inzoe.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;
		}
		
		//User sessionedUser = (User)session.getAttribute(USER_SESSION_KEY) ;
		//return sessionUser;
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
