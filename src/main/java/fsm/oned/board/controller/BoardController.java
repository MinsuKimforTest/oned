package fsm.oned.board.controller;

import fsm.oned.board.service.BoardService;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 게시판 Controller
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
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService service;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 게시판 - 리스트 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionBoardList")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionBoardList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        //게시판 정보
        Map<String, Object> boardInfo = service.selectBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);

        return "board/boardList";
    }

    /**
     * 게시판 - 리스트 조회
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectBoardList")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectBoardList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("list", service.selectBoardList(paramMap));
        return resBodyMap;
    }

    /**
     * 게시글 - 상세 조회
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "actionBoardDetail")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionBoardDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        HashMap<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("USER_INFO");
        //게시판 정보
        Map<String, Object> boardInfo = service.selectBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);

        Map result = service.selectBoardDetail(paramMap);
        model.put("result", result);
        
        //비밀번호 체크
        if(result.get("TEXT_PASSWORD") != null && !result.get("TEXT_PASSWORD").toString().isEmpty()) {
        	if(!result.get("ORD_NO").toString().equals(session.getAttribute("ORD_NO")) || session.getAttribute("ORD_NO") == null) {
        		throw new UserException("비정상적인 접근입니다.");
        	}
        	
            if(paramMap.get("TEXT_PASSWORD") == null || !result.get("TEXT_PASSWORD").toString().equals(commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()))) {
                throw new UserException("비밀번호가 일치하지 않습니다.");
            }
            session.removeAttribute("ORD_NO");
        }

        return "board/boardDetail";
    }

    /**
     * 게시판 - 폼 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionBoardForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionBoardForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        HashMap<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("USER_INFO");
        //게시판 정보
        Map<String, Object> boardInfo = service.selectBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);
        Map result = service.selectBoardDetail(paramMap);
        // 작성자 ID 체크
        if(paramMap.get("ORD_NO") !=null){
            if(!result.get("REG_ID").equals(userInfo.get("USER_ID"))){
                throw new UserException("권한이 없습니다.");
            }
        }
        //비밀번호 체크
        if(result.get("TEXT_PASSWORD") != null && !result.get("TEXT_PASSWORD").toString().isEmpty()) {
        	if(!result.get("ORD_NO").toString().equals(session.getAttribute("ORD_NO"))) {
        		throw new UserException("비정상적인 접근입니다.");
        	}
        	session.removeAttribute("ORD_NO");
        }
        model.put("result", result);

        return "board/boardForm";
    }

    /**
     * 게시글 - 저장 
     * @param paramMap
     * @param request
     * @return Map
     */
    @RequestMapping(value = "/saveBoardData")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveBoardData(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
    	Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("ordNo", service.saveBoardData(paramMap, request));
        HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        return resBodyMap;
    }
    
    /**
     * 게시글 - 비밀번호 확인
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/checkBoardPassword")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> checkBoardPassword(@LpRequestParam Map<String, Object> paramMap){
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("ordNo", service.selectCheckBoardPassword(paramMap));
        return resBodyMap;
    }
    
    

}