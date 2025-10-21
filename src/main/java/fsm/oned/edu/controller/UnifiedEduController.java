package fsm.oned.edu.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.service.CommonService;
import fsm.oned.edu.service.UnifiedEduService;
import fsm.oned.edu.service.EduApplyServiceFactory;
import fsm.oned.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 통합 교육 Controller (PLOR, SAFETY)
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2025.01.15    개발자    최초 생성
 *
 * </pre>
 */

@Slf4j
@Controller
@RequestMapping("/edu/unified")
public class UnifiedEduController {
    
    // 허용된 교육 타입을 DB에서 동적으로 가져오기 위해 제거
    // private static final Set<String> ALLOWED_EDU_TYPES = ...
    
    @Autowired
    private UnifiedEduService service;

    @Autowired
    private EduApplyServiceFactory eduApplyServiceFactory;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    // CommonUtil 주입은 현재 클래스에서 미사용이므로 제거 가능

    /**
     * 교육 타입 유효성 검증 (DB에서 동적으로 확인)
     * @param eduType 교육 타입 (PLOR, SAFETY, etc.)
     * @throws IllegalArgumentException 유효하지 않은 교육 타입인 경우
     */
    private void validateEduType(String eduType) {
        if (eduType == null || eduType.trim().isEmpty()) {
            log.warn("Empty education type attempted");
            throw new IllegalArgumentException("교육 타입이 필요합니다.");
        }
        
        try {
            // DB에서 해당 교육 타입이 존재하는지 확인
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
            
            List<Map<String, Object>> result = service.selectValidEduTypes(paramMap);
            
            // COUNT(*) 결과 확인
            if (result == null || result.isEmpty()) {
                log.warn("Invalid education type attempted (no result): {}", eduType);
                throw new IllegalArgumentException("유효하지 않은 교육 타입입니다: " + eduType);
            }
            
            int count = ((Number) result.get(0).get("CNT")).intValue();
            if (count == 0) {
                log.warn("Invalid education type attempted (count=0): {}", eduType);
                throw new IllegalArgumentException("유효하지 않은 교육 타입입니다: " + eduType);
            }
            
            log.debug("Valid education type confirmed: {} (count={})", eduType, count);
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error validating education type: {}", eduType, e);
            throw new IllegalArgumentException("교육 타입 검증 중 오류가 발생했습니다.");
        }
    }

    /**
     * 교육신청 - 리스트 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanList(
            @RequestParam("eduType") String eduType) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        return "edu/" + eduTypeLower + "/eduPlanList";
    }

    /**
     * 교육신청 - 리스트 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY) , 25.10.20 메뉴체계로인해 Mapping 따로 생성
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanSafetyList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanSafetyList(
            @RequestParam("eduType") String eduType) {

        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();

        return "edu/" + eduTypeLower + "/eduPlanList";
    }

    /**
     * 교육신청 - 리스트 조회
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/selectPlanList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduPlanList(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        resBodyMap.put("list", service.selectEduPlanList(paramMap));
        return resBodyMap;
    }

    /**
     * 교육신청 - 상세 조회
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanDetail(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        model.put("paramMap", paramMap);
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());

        //교육정보
        Map<String, Object> result = service.selectEduPlanDetail(paramMap);
        model.put("result", result);

        //강의 목차
        List<Map<String, Object>> list = service.selectEduContentsList(paramMap);
        model.put("classList", list);

        return "edu/" + eduTypeLower + "/eduPlanDetail";
    }

    /**
     * 교육신청 - 상세 조회
     * @param eduType 교육 타입 (PLOR, SAFETY) , 25.10.20 메뉴체계로인해 Mapping 따로 생성
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduPlanSafetyDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanSafetyDetail(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap,
            ModelMap model) {

        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();

        model.put("paramMap", paramMap);
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());

        //교육정보
        Map<String, Object> result = service.selectEduPlanDetail(paramMap);
        model.put("result", result);

        //강의 목차
        List<Map<String, Object>> list = service.selectEduContentsList(paramMap);
        model.put("classList", list);

        return "edu/" + eduTypeLower + "/eduPlanDetail";
    }

    /**
     * 교육신청서 - 폼 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduApplyForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduApplyForm(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
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

        return "edu/" + eduTypeLower + "/eduApplyForm";
    }

    /**
     * 교육신청서 - 저장
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/saveEduApply")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduApply(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        String eduTypeUpper = eduType.trim().toUpperCase();
        paramMap.put("EDU_TYPE", eduTypeUpper);

        String eduApplyNo = eduApplyServiceFactory
                .getService(eduTypeUpper)
                .saveEduApply(paramMap);
        log.debug("{} 교육신청 완료: {}", eduTypeUpper, eduApplyNo);

        resBodyMap.put("eduApplyNo", eduApplyNo);
        return resBodyMap;
    }

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value="/selectEduApplyWorkOptions")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduApplyWorkOptions(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        resBodyMap.put("list", service.selectEduApplyWorkOptions(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 참여현황 - 리스트 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduStatusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduStatusList(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        return "edu/" + eduTypeLower + "/eduStatusList";
    }

    /**
     * 교육 참여현황 - 리스트 조회
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return Map
     */
    @RequestMapping(value = "/selectEduStatusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduStatusList(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        resBodyMap.put("list", service.selectEduStatusList(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 게시판 - 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping("/actionEduBoardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardList(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("boardInfo", service.selectEduBoardInfo(paramMap));
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        
        return "edu/" + eduTypeLower + "/eduBoardList";
    }

    /**
     * 교육 게시판 - 리스트 조회
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping("/selectEduBoardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduBoardList(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        resBodyMap.put("list", service.selectEduBoardList(paramMap));
        return resBodyMap;
    }

    /**
     * 교육 게시판 - 상세 조회
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduBoardDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardDetail(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        //교육 게시판 정보
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);

        Map<String, Object> result = service.selectEduBoardDetail(paramMap);
        model.put("result", result);

        //비밀번호 체크는 기존 로직과 동일하게 처리 (세션 체크)
        // TODO: 비밀번호 체크 로직 필요시 EduController 참고하여 추가

        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);

        return "edu/" + eduTypeLower + "/eduBoardDetail";
    }

    /**
     * 교육 게시판 - 폼 화면이동
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @param model
     * @return String
     */
    @RequestMapping(value = "/actionEduBoardForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardForm(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap, 
            ModelMap model) {
        
        validateEduType(eduType);
        String eduTypeLower = eduType.trim().toLowerCase();
        
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        //교육 게시판 정보
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);
        
        Map<String, Object> result = service.selectEduBoardDetail(paramMap);
        
        // TODO: 작성자 권한 체크 및 비밀번호 체크 로직 필요시 EduController 참고하여 추가
        
        model.put("result", result);
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);

        return "edu/" + eduTypeLower + "/eduBoardForm";
    }

    /**
     * 교육 게시글 - 저장
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/saveEduBoardData")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduBoardData(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        // TODO: HttpServletRequest를 통한 세션 처리 필요시 추가
        resBodyMap.put("ordNo", service.saveEduBoardData(paramMap, null));
        
        return resBodyMap;
    }

    /**
     * 교육 게시글 - 비밀번호 확인
     * @param eduType 교육 타입 (PLOR, SAFETY)
     * @param paramMap
     * @return Map
     */
    @RequestMapping(value = "/checkEduBoardPassword")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> checkEduBoardPassword(
            @RequestParam("eduType") String eduType,
            @LpRequestParam Map<String, Object> paramMap) {
        
        validateEduType(eduType);
        
        Map<String, Object> resBodyMap = new HashMap<>();
        paramMap.put("EDU_TYPE", eduType.trim().toUpperCase());
        
        // TODO: 세션 처리 필요시 추가
        resBodyMap.put("ordNo", service.selectCheckEduBoardPassword(paramMap));
        
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
    public String actionEduCertForm(@RequestParam("eduType") String eduType,
                                    @LpRequestParam Map<String, Object> paramMap, ModelMap model) {

        model.put("result", eduApplyServiceFactory.getService(eduType).selectEduCertInfo(paramMap));
        String eduTypeLower = eduType.trim().toLowerCase();

        return "edu/" + eduTypeLower + "/eduCertForm";
    }
}