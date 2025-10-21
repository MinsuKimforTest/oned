package fsm.oned.edu.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.service.CommonService;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.edu.service.EduApplyServiceFactory;
import fsm.oned.edu.service.EduService;
import fsm.oned.edu.service.PlorEduService;
import fsm.oned.edu.service.SafetyEduService;
import fsm.oned.organicCert.service.OrganicCertService;
import fsm.oned.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 교육 Controller
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
@RequestMapping("/edu")
public class EduController {
    @Autowired
    private EduService service;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganicCertService organicCertService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private FlicCommonFileService flicCommonFileService;

    @Autowired
    private PlorEduService plorEduService;

    @Autowired
    private SafetyEduService safetyEduService;

    @Autowired
    private EduApplyServiceFactory eduApplyServiceFactory;

    /**
     * 교육신청 - 리스트 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "edu/eduPlanList";
    }

    /**
     * 교육신청 - 리스트 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanHaccpList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanHaccpList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "edu/haccpedu/eduPlanHaccpList";
    }

    /**
     * 교육신청 - 리스트 조회
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectEduPlanList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduPlanList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("list", service.selectEduPlanList(paramMap));
        return resBodyMap;
    }

    /**
     * HACCP 교육신청 - 리스트 조회
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectEduPlanHaccpList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduPlanHaccpList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("list", service.selectEduPlanHaccpList(paramMap));
        return resBodyMap;
    }

    /**
     * 교육신청 - 상세 조회
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        model.put("paramMap", paramMap);

        //교육정보
        Map result = service.selectEduPlanDetail(paramMap);
        model.put("result", result);

        //강의 목차
        List<Map<String, Object>> list = service.selectEduContentsList(paramMap);
        model.put("classList", list);

        return "edu/eduPlanDetail";

    }

    /**
     * HACCP 교육신청 - 상세 조회
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanHaccpDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanHaccpDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        model.put("paramMap", paramMap);

        //교육정보
        Map result = service.selectEduPlanDetail(paramMap);
        model.put("result", result);

        //강의 목차
        List<Map<String, Object>> list = service.selectEduContentsList(paramMap);
        model.put("classList", list);

        return "edu/haccpedu/eduPlanHaccpDetail";

    }

    /**
     * 교육신청서 - 폼 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduApplyForm")
        @LpAuthCheck(isLoginChk=true,isAuthChk=false)
        public String actionEduApplyForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        if(paramMap.get("GBN").equals("2")) {
            boolean check = duplicateCheck(paramMap);

            if (!check) {
                throw new UserException("P:중복된 강의가 있습니다.");
            }
        }

        model.put("paramMap", paramMap);

        //교육정보
        model.put("eduData", service.selectEduPlanDetail(paramMap));
        //업체정보
        model.put("compData", userService.selectUserCompanyInfo(paramMap));
        //교육 신청서 정보
        model.put("applyData", service.selectEduApplyInfo(paramMap));
        //공통코드(인증종류)
        String[] code = {"FS_451"};
        paramMap.put("MSTR_CD", code);
        model.put("certCd", commonService.getCodeList(paramMap));

        if(paramMap.get("GBN").equals("1")){
            return "edu/eduApplyForm";
        }else{
            return "edu/haccpedu/eduApplyHaccpForm";
        }

    }

    private boolean duplicateCheck(Map<String, Object> paramMap) {
        boolean check = true;
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        paramMap.put("year", year);
        int count = service.duplicateCheck(paramMap);
        if(count>0){
            check = false;
        }
        return check;
    }

    /**
     * 교육신청서 - 저장
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/saveEduApply")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduApply(@LpRequestParam Map<String, Object> paramMap){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("eduApplyNo", service.saveEduApply(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value="/selectEduApplyWorkOptions")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduApplyWorkOptions(@LpRequestParam Map<String, Object> paramMap){
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list",service.selectEduApplyWorkOptions(paramMap));
        return resBodyMap;
    }

    /**
     * 나의 강의실 - 리스트 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduClassRoomList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassRoomList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        model.put("paramMap", paramMap);
        return "edu/eduClassRoomList";
    }

    /**
     * 나의 강의실 - 리스트 조회
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectEduClassRoomList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduClassRoomList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        //학습상태
        resBodyMap.put("statusList", service.selectStudyStatusList(paramMap));
        resBodyMap.put("list", service.selectEduClassRoomList(paramMap));
        return resBodyMap;
    }

    /**
     * 나의 강의실 - 상세 조회
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduClassRoomDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassRoomDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        //강의 기본정보
        model.put("applyData", service.selectEduClassRoomDetail(paramMap));

        //강의 리스트
        model.put("classData", service.selectEduClassStatusList(paramMap));

        return "edu/eduClassRoomDetail";
    }

    /**
     * 강의 학습하기 - 화면이동
     * @param paramMap
     */
    @RequestMapping("/actionEduClassDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        paramMap.put("EDU_BOARD_ID", "EDUBOARD002");
        Map result = service.selectEduClassStatusData(paramMap);
        model.addAttribute("result", result);
        try {
            Map fileParamMap = new HashMap();
            fileParamMap.put("fileId", result.get("ATTACH_FILE_ID"));
            List<Map<String, Object>> fileList = flicCommonFileService.selectAtchFile(fileParamMap);
            model.addAttribute("fileList", fileList);
        } catch(NullPointerException e) {
			throw new UserException(e,"관리자에게 문의 바랍니다.");
        } catch (Exception e) {
            throw new UserException(e,"관리자에게 문의 바랍니다.");
        }

        //강의 리스트
        model.put("classData", service.selectEduClassStatusList(paramMap));
        model.put("paramMap", paramMap);
        return "edu/eduClassDetail";
    }

    /**
     * 강의 학습하기 - 진도 체크
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/saveStudyTime")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveStudyTime(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        Map checkTime = new HashMap(); // 리턴 결과값

        //30초이상인지 검사
        boolean checksStudyTime = service.checkStudyTime(paramMap);

        paramMap.put("STUDY_CHECKTIME", new Date().getTime());

        double studyRate = service.checkStudyRate(paramMap);

        //진도 체크
        paramMap.put("STUDY_RATE",studyRate);

        if(checksStudyTime) {
            service.saveStudyTime(paramMap);
        }

        //진도율 100%
        Map<String,Object> classMap = service.selectEduClassRoomDetail(paramMap);


        if(((BigDecimal)classMap.get("STUDY_RATE_AVG")).intValue() >= 100 && classMap.get("EDU_CERT_NO").equals("")){
            //이수증 발급
            Map<String,Object> applyMap = service.selectEduApplyInfo(paramMap);
            String gbn = applyMap.get("GBN").toString();

            log.info("selectEduApplyInfo gbn : " + gbn);

            switch (gbn) {
                case "1":
                    resBodyMap.put("eduCertNo", service.saveEduCertInfo(applyMap));
                    service.sendEduCompleteSms(applyMap);
                    break;
                case "2":
                    resBodyMap.put("eduCertNo", service.saveEduCertHaccpInfo(applyMap));
                    service.sendEduCompleteSms(applyMap);
                    break;

                default:
                    resBodyMap.put("eduCertNo", eduApplyServiceFactory.getService(gbn).saveEduCertInfo(applyMap));

            }
            //교육이수 완료 문자발송


            //최초사업자인 경우 친환경 인증번호 확인
            Map<String, Object> orgCertMap = organicCertService.selectOrganicCertNo(paramMap);
            if(orgCertMap.get("CERT_GUBUN").equals("1")){
                organicCertService.saveCheckOrganicCertNo(paramMap);
            }
        }
        
        return resBodyMap;
    }

    /**
     * 교육이수증 - 폼 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduCertForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduCertForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        model.put("result", service.selectEduCertInfo(paramMap));
        if(paramMap.get("GBN").equals("1")){
            return "edu/eduCertForm";
        }else{
            return "edu/haccpedu/eduCertHaccpForm";
        }
    }

    /**
     * 교육 참여현황 - 리스트 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduStatusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduStatusList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "edu/eduStatusList";
    }

    /**
     * 교육 참여현황 - 리스트 조회
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectEduStatusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduStatusList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("list", service.selectEduStatusList(paramMap));
        return resBodyMap;
    }

    /**
      * 교육 참여현황 - 리스트 조회
      * @param paramMap
      * @param model
      * @return Map
      */
     @RequestMapping(value = "/selectEduStatusHaccpList")
     @LpAuthCheck(isLoginChk=true,isAuthChk=false)
     @ResponseBody
     public Map<String, Object> selectEduStatusHaccpList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
         Map resBodyMap = new HashMap(); // 리턴 결과값
         resBodyMap.put("list", service.selectEduStatusHaccpList(paramMap));
         return resBodyMap;
     }

    /**
     * 교육 게시판 - 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping("/actionEduBoardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduRoom(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("boardInfo", service.selectEduBoardInfo(paramMap));
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        return "edu/eduBoardList";
    }

    /**
     * 교육 게시판  - 리스트 조회
     * @param paramMap
     * @return Map
     */
    @RequestMapping("/selectEduBoardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduBoardList(@LpRequestParam Map<String, Object> paramMap) {
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list", service.selectEduBoardList(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 게시판 - 상세 조회
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduBoardDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    	HttpSession session = request.getSession();
    	//교육 게시판 정보
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);

        Map result = service.selectEduBoardDetail(paramMap);
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

        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);

        return "edu/eduBoardDetail";
    }

    /**
     * 교육 게시판 - 폼 화면이동
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduBoardForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        HashMap<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("USER_INFO");
        //교육 게시판 정보
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);
        Map result = service.selectEduBoardDetail(paramMap);
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
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);

        return "edu/eduBoardForm";
    }

    /**
     * 교육 게시글 - 저장
     * @param paramMap
     * @param request
     * @return Map
     */
    @RequestMapping(value = "/saveEduBoardData")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduBoardData(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("ordNo", service.saveEduBoardData(paramMap, request));
    	HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        return resBodyMap;
    }

    /**
     * 교육 게시글 - 비밀번호 확인
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/checkEduBoardPassword")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> checkEduBoardPassword(@LpRequestParam Map<String, Object> paramMap){
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        Map resBodyMap = new HashMap(); // 리턴 결과값
        resBodyMap.put("ordNo", service.selectCheckEduBoardPassword(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value="/selectEduApplyHaccpWorkOptions")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduApplyHaccpWorkOptions(@LpRequestParam Map<String, Object> paramMap){
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list",service.selectEduApplyHaccpWorkOptions(paramMap));
        return resBodyMap;
    }



}