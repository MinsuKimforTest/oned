package fsm.oned.login.controller;

import fsm.oned.board.service.BoardService;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.util.UserDetailsHelper;
import fsm.oned.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 로그인 Controller
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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService service;

    /**
     * 로그인 - 처리
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/actionLogin")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> actionLogin(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        Map resBodyMap = service.selectLoginInfo(paramMap); // 리턴 결과값
        if(resBodyMap.containsKey("failtime")){
            throw new UserException("비밀번호 5회 오류입니다. 30초 뒤 다시 시도해주세요.");
        }else if(resBodyMap.containsKey("fail")){
            if(resBodyMap.get("fail")!=null) {
                if (Integer.parseInt(String.valueOf(resBodyMap.get("fail"))) > 0) {
                    throw new UserException("비밀번호 " + (Integer.parseInt(String.valueOf(resBodyMap.get("fail")))+1)+ "회 오류입니다. 로그인정보를 확인해주세요.");
                } else {
                    throw new UserException("로그인정보를 확인해주세요.");
                }
            }else{
                throw new UserException("로그인정보를 확인해주세요.");
            }
        }else{
            return resBodyMap;
        }

    }

    /**
     * 로그아웃
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionLogout")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionLogout(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        UserDetailsHelper.invalidate();
        return "redirect:/main/actionMain.do";
    }

}