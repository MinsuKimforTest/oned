package fsm.oned.organicCert.controller;

import fsm.oned.organicCert.service.OrganicCertService;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.aop.PrivacyLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 인증번호 Controller
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
@RequestMapping("/organicCert")
public class OrganicCertController {
    @Autowired
    private OrganicCertService service;

    /**
     * 친환경 인증번호 - 관리화면 이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionOrganicCertNoForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    public String actionEduPlanList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        Map result = service.selectOrganicCertNo(paramMap);
        model.put("result", result);

        return "organicCert/organicCertNoForm";
    }

    /**
     * 친환경 인증번호 - 등록 확인
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/checkOrganicCertNo")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    @ResponseBody
    public Map<String, Object> checkOrganicCertNo(@LpRequestParam Map<String, Object> paramMap){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("retData", service.selectOrganicCertNo(paramMap));
        return resBodyMap;
    }

    /**
     * 친환경 인증번호 - 저장
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/saveOrganicCertNo")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @PrivacyLog(parameterNames = {"CERT_NO"})
    @ResponseBody
    public Map<String, Object> saveOrganicCertNo(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("retData", service.saveOrganicCertNo(paramMap, request));
        return resBodyMap;
    }

    /**
     * 친환경 인증번호 - 유기수산물인증 정보
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/selectOrganicCertInfo")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @PrivacyLog(parameterNames = {})
    @ResponseBody
    public Map<String, Object> selectOrganicCertInfo(@LpRequestParam Map<String, Object> paramMap){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("retData", service.selectOrganicCertInfo(paramMap));
        return resBodyMap;
    }

}