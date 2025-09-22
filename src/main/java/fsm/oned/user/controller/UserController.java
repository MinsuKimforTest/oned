package fsm.oned.user.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.aop.PrivacyLog;
import fsm.oned.comm.service.CommonService;
import fsm.oned.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 Controller
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

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Resource(name = "commonService")
    private CommonService commonService;


    /**
     * 회원정보 - 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionUserInfoForm")
    @LpAuthCheck(isLoginChk=true, isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    public String actionUserForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        Map result = service.selectUserInfo(paramMap);
        model.put("result", result);
        return "/user/userInfoForm";
    }

    /**
     * 회원정보 - 저장
     * @param paramMap
     * @param request
     * @return Map
     */
    @RequestMapping(value = "/saveUserInfo")
    @LpAuthCheck(isLoginChk=true, isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    @ResponseBody
    public Map<String, Object> saveUserInfo(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("userId", service.saveUserInfo(paramMap));
        return resBodyMap;
    }

    /**
     * 회원정보 - 비밀번호 수정
     * @param paramMap
     * @param request
     * @return Map
     */
    @RequestMapping(value = "/saveUserPassword")
    @LpAuthCheck(isLoginChk=true, isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    @ResponseBody
    public Map<String, Object> saveUserPassword(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("userId", service.saveUserPassword(paramMap));
        return resBodyMap;
    }

    /**
     * 회원정보 - 업체정보 조회
     * @param paramMap
     * @param request
     * @return Map
     */
    @RequestMapping(value = "/selectCompanyList")
    @LpAuthCheck(isLoginChk=true, isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    @ResponseBody
    public Map<String, Object> selectCompanyList(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("retData", service.selectCompanyList(paramMap));
        return resBodyMap;
    }
}