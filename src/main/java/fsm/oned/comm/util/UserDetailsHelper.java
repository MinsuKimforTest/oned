package fsm.oned.comm.util;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import fsm.oned.comm.service.UserDetailsService;

/**
 * UserDetails Helper 클래스
 *
 * @author mulder
 * @since 2020.07.22
 * @version 1.0
 * @see
 *
 */

public class UserDetailsHelper {

	static UserDetailsService userDetailsService;

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		UserDetailsHelper.userDetailsService = userDetailsService;
	}
	
	/**
	 * 로그인한 사용자 정보를 세션에 저장한다.
	 * @return 
	 */
	public static void setAuthenticatedUser(Map<String, Object> userInfo) {
		userDetailsService.setAuthenticatedUser(userInfo);
	}
	
	public static void invalidate() {
		userDetailsService.invalidate();
	}

	/**
	 * 인증된 사용자객체를  가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Map<String, Object> getAuthenticatedUser() {
		return userDetailsService.getAuthenticatedUser();
	}
	
	public static String getAuthenticatedUserId() {
		return userDetailsService.getAuthenticatedUserId();
	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 *
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<Map<String, Object>> getAuthorities() {
		return userDetailsService.getAuthorities();
	}
	
	public static void setAuthorities(List<Map<String, Object>> authList) {
		userDetailsService.setAuthorities(authList);
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		return userDetailsService.isAuthenticated();
	}
	
	public static HttpSession getSession() {
		return userDetailsService.getSession();
	}
	
	
	public static String getEncryptSessionJsonStr() {
		return userDetailsService.getEncryptSessionJsonStr();
	}
}
