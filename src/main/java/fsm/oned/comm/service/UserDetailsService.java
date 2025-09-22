package fsm.oned.comm.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

public interface UserDetailsService {

	/**
	 * 로그인한 사용자 정보를 세션에 저장한다.
	 * @return 
	 */
	public void setAuthenticatedUser(Map<String, Object> userInfo);
	public void invalidate();
	
	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public Map<String, Object> getAuthenticatedUser();
	
	public String getAuthenticatedUserId();

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 * @return List - 사용자 권한정보 목록
	 */
	public List<Map<String, Object>> getAuthorities();
	public void setAuthorities(List<Map<String, Object>> authList);
	
	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)	
	 */
	public Boolean isAuthenticated(); 
	
	public HttpSession getSession();	
	
	public String getEncryptSessionJsonStr();

}
