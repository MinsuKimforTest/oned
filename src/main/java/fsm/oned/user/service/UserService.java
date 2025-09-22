package fsm.oned.user.service;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.Globals;
import fsm.oned.comm.util.UserDetailsHelper;
import fsm.oned.login.mapper.LoginMapper;
import fsm.oned.organicCert.service.OrganicCertService;
import fsm.oned.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 Service
 * @author 라이온 플러스 허은미
 * @since 2021.07.15
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2021.07.15    허은미    최초 생성
 *
 * </pre>
 */

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    @Autowired
    private OrganicCertService organicCertService;

    /**
     * 회원정보
     * @param paramMap
     * @return Map
     */
    public Map<String, Object> selectUserInfo(Map<String,Object> paramMap) {
        Map<String,Object> retMap = mapper.selectUserInfo(paramMap);
        return retMap;
    }

    /**
     * 회원정보 - 저장
     * @param paramMap
     * @return String
     */
    public String saveUserInfo(Map<String, Object> paramMap){
        Map<String, Object> map = mapper.selectUserPassword(paramMap);

        if (map != null) {
            //회원정보 수정
            mapper.updateUserInfo(paramMap);

            //업체정보 수정 > 세션에 담기
            if(!"".equals(paramMap.get("COMP_NO_BLNG"))){
                Map<String, Object> USER_INFO = UserDetailsHelper.getAuthenticatedUser();

                if("5".equals(USER_INFO.get("USER_TYPE")) || "6".equals(USER_INFO.get("USER_TYPE"))){
                    USER_INFO.put("COMP_NO_BLNG", paramMap.get("COMP_NO_BLNG"));
                    USER_INFO.put("CERT_ID", paramMap.get("COMP_NO_BLNG"));
                    UserDetailsHelper.setAuthenticatedUser(USER_INFO);
                }
            }

        } else {
            throw new UserException("기존 비밀번호를 확인하시기 바랍니다.");
        }

        return paramMap.get("USER_ID").toString();
    }

    /**
     * 회원정보 - 비밀번호 수정
     * @param paramMap
     * @return String
     */
    public String saveUserPassword(Map<String, Object> paramMap){
        paramMap.put("PASSWORD", paramMap.get("OLD_PASSWORD"));
        Map<String, Object> map = mapper.selectUserPassword(paramMap);
        String newPassword = paramMap.get("NEW_PASSWORD").toString();

        //유효성 확인 (비밀번호 정규식)
        boolean isValid = newPassword.matches(Globals.REGEXP_USER_PASSWORD);
        if(!isValid){
            throw new UserException("신규 비밀번호는 최소 하나의 영문자, 숫자, 특수문자를 포함하여 9~30자리로 입력해주십시오.");
        }

        if (map != null) {
            //비밀번호 수정
            mapper.updateUserPassword(paramMap);
        } else {
            throw new UserException("기존 비밀번호를 확인하시기 바랍니다.");
        }

        return paramMap.get("USER_ID").toString();
    }

    /**
     * 회원정보 - 업체정보 조회
     * @param paramMap
     * @return List
     */
    public List<Map<String,Object>> selectCompanyList(Map<String,Object> paramMap) {
        List<Map<String,Object>> list = mapper.selectCompanyList(paramMap);
        return list;
    }

    /**
     * 업체정보(아이디)
     * @param paramMap
     * @return Map
     */
    public Map<String, Object> selectUserCompanyInfo(Map<String,Object> paramMap) {
        //인증 관련 사용자 정보
        Map<String, Object> certUserMap = organicCertService.selectUserInfoForCert(paramMap);
        paramMap.put("certUserMap", certUserMap);

        Map<String, Object> USER_INFO = mapper.selectUserCompanyInfo(paramMap);
        return USER_INFO;
    }
}
