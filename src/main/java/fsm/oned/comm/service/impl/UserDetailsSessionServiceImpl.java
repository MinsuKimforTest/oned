package fsm.oned.comm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fsm.oned.comm.service.UserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;

/**
 * 
 * @author mulder
 * @since 2020.07.22
 * @version 1.0
 * @see
 *
 */

public class UserDetailsSessionServiceImpl implements UserDetailsService {
	
	 /** 암호화서비스 */
    @Resource(name = "egovEnvCryptoService")
    EgovEnvCryptoService cryptoService;
    
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public void setAuthenticatedUser(Map<String, Object> userInfo) {
		getSession().setAttribute("USER_INFO", userInfo);
	}
	
	public void invalidate() {
		getSession().removeAttribute("USER_INFO");
		getSession().removeAttribute("USER_AUTH");
		getSession().invalidate();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAuthenticatedUser() {

		if (getRequest() == null) {
			return null;
		}

		return (Map<String, Object>)getSession().getAttribute("USER_INFO");
	}
	
	@Override
	public String getAuthenticatedUserId() {
		Map<String, Object> userInfo = getAuthenticatedUser();
		
		return (userInfo == null ? null : userInfo.get("USER_ID").toString());
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAuthorities() {
		// 권한 설정을 리턴한다.
		if (getRequest() == null) {
			return null;
		}

		return (List<Map<String, Object>>)getSession().getAttribute("USER_AUTH");
	}
	

	@Override
	public void setAuthorities(List<Map<String, Object>> authList)  {
		getSession().setAttribute("USER_AUTH", authList);
	}

	public Boolean isAuthenticated() {
		// 인증된 유저인지 확인한다.
		if (getRequest() == null) {
			return false;
		} else {

			if ( getSession().getAttribute("USER_INFO") == null) {
				return false;
			} else {
				return true;
			}
		}

	}
	
	public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession(true);
    }
    
    
    public String getEncryptSessionJsonStr() {
    	String sessionJsonStr = objectToJsonStr(getSession().getAttribute("USER_INFO"));
    	
    	return cryptoService.encrypt(sessionJsonStr);
    }
    

    private String objectToJsonStr(Object obj) {
    	String retJsonStr = "";
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		retJsonStr = mapper.writeValueAsString(obj);
    	}catch (JsonProcessingException jpe) {
    		logger.error(jpe.getMessage());
    		return "";
    	}catch (Exception e) {
    		logger.error(e.getMessage());
    		return "";
		}
    	return retJsonStr;
    }
    
 
    
}
