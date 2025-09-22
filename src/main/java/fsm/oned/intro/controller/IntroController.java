package fsm.oned.intro.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.main.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 소개 Controller
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
@RequestMapping("/intro")
public class IntroController {

    /**
     * 교육소개 > 친환경교육 - 화면이동
     *
     * @param paramMap
     */
    @RequestMapping("/actionEduPurpose")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionEduPurpose(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        return "intro/eduPurpose";
    }

    /**
     * 교육소개 > HACCP 교육 - 화면이동
     *
     * @param paramMap
     */
    @RequestMapping("/actionEduHaccpPurpose")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionEduHaccpPurpose(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        return "intro/eduHaccpPurpose";
    }

}