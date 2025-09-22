package fsm.oned.comm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fsm.oned.comm.UserMenuAuthVO;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.service.CommonService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.EgovStringUtil;
import fsm.oned.comm.util.UserDetailsHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SuppressWarnings({"unchecked","rawtypes"})
@RequestMapping("/comm")
public class CommonController {

    @Autowired
    private CommonService service;

    @Autowired
    private CommonUtil commonUtil;
    	
    /** 암호화서비스 */
    @Resource(name = "egovEnvCryptoService")
    EgovEnvCryptoService cryptoService;
     
    /**
     * 로그인 페이지
     * @param
     * @return "comm/topMenu.do"
     * @exception Exception
     */
   @RequestMapping("/actionTmpLogin")
   @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public ModelAndView actionTmpLogin(ModelMap model,HttpServletRequest request) throws Exception {
       ModelAndView mav = new ModelAndView();

       //request.getSession().invalidate();  //사용자 로그인 세션 제거
       //UserDetailsHelper.removeAuthenticatedUser();  //공통 세션 제거
       mav.setViewName("comm/tmpLogin");
       return mav;
   }
   /**
    *
    * 로그인 처리
    * @param
    * @return "comm/topMenu.do"
    * @exception Exception
    */
   @RequestMapping("/selectLoginInfo")
    @LpAuthCheck(isLoginChk = false, isAuthChk = false)
    @ResponseBody
    public Map<String, Object> selectLoginInfo(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request) {
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("Result", service.selectLoginInfo(paramMap, request));
        return resBodyMap;
    }

    /**
     * 로그인 처리
     * @param model
     * @return ModelAndView
     * @exception Exception
     */
    @RequestMapping("sessionShare.do")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public ModelAndView sessionShare(ModelMap model, @RequestParam Map<String,Object> map, HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();

        String userInfoJsonStr = EgovStringUtil.isNullToString(map.get("OTHER_INFO"));

        if(!userInfoJsonStr.equals("")) {
            String decryptUserINfoJsonStr = cryptoService.decrypt(userInfoJsonStr);

            Map<String, Object> USER_INFO = service.selectLoginInfoSso((Map<String, Object>)jsonstrToObject(decryptUserINfoJsonStr), request);

            UserDetailsHelper.setAuthenticatedUser(USER_INFO);
        }

        mav.addObject("userId", UserDetailsHelper.getAuthenticatedUserId());
        mav.setViewName("comm/blank");

        return mav;
    }
    
    private Object jsonstrToObject(String jsonStr) {
        if (jsonStr.equals(""))
            return null;
        //System.out.println("~~~");
        Object obj = null;
        ObjectMapper mapper = new ObjectMapper();
        
        Logger logger = null;
        try {
            if (jsonStr.trim().startsWith("[")) {
                obj = mapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {});
            } else {
                obj = mapper.readValue(jsonStr, Map.class);
            }
        } catch (JsonParseException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return obj;
    }

    /**
     * 공통코드 조회
     * @param
     * @return "comm/commCodeList.do"
     * @throws Exception
     */
    @RequestMapping(value = "comm/commCodeList.do")
    @LpAuthCheck(isAuthChk = false)
    public ModelAndView commCodeList(ModelMap model, @RequestParam Map<String, Object> map) throws Exception {
        ModelAndView mav = new ModelAndView();
        //Map<String, Object> codeMap = flicCommonService.selectCommCodeList(map);
        //mav.addObject("code", codeMap);
        mav.setViewName("jsonView");
        return mav;
    }

    /**
     * 공통코드 콤보
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "/selectCodeList")
    @ResponseBody
    @LpAuthCheck(isAuthChk = false, isLoginChk = false)
    public Map<String, Object> selectCodeList(@LpRequestParam Map<String, Object> paramMap) {
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list", service.getCodeList(paramMap));
        return resBodyMap;
    }

    /**
     * 사용자 정의 콤보
     * @param hm
     * @return
     */
    @RequestMapping(value="/selectUserCodeList")
    @ResponseBody
    @LpAuthCheck(isAuthChk=false, isLoginChk = false)
    public Map<String, Object> selectUserCodeList(@LpRequestParam Map<String, Object> paramMap){
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list",service.getUserCodeList(paramMap));
        return resBodyMap;
    }

    /**
     * 주소팝업
     *
     * @param paramMap
     */
    @RequestMapping("/actionJusoPop")
    @LpAuthCheck(isAuthChk=false, isLoginChk = false)
    public String actionJusoPop(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "comm/jusoPopup";
    }

    /**
     * 주소팝업 Mobile
     *
     * @param paramMap
     */
    @RequestMapping("/SearchJusoMobilePopup")
    @LpAuthCheck(isAuthChk=false)
    public String SearchJusoMobilePopup(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "comm/jusoMobilePopup";
    }

}
