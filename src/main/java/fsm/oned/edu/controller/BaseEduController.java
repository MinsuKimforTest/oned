package fsm.oned.edu.controller;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.service.CommonService;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.edu.service.EduService;
import fsm.oned.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 교육 Controller 공통 기능
 * @author 라이온 플러스
 * @since 2025.09.18
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseEduController {
    
    // 교육 타입 상수
    protected static final String EDU_TYPE_PLOR = "PLOR";
    protected static final String EDU_TYPE_SAFETY = "SAFETY";
//    protected static final String EDU_TYPE_GENERAL = "GENERAL";
//    protected static final String EDU_TYPE_HACCP = "HACCP";
    
    // JSP 경로 상수
    protected static final String JSP_PREFIX = "edu/";
    protected static final String JSP_SUFFIX = "/";
    
    // 공통 코드
    protected static final String[] CERT_CODE = {"FS_451"};
    protected static final String EDU_BOARD_ID = "EDUBOARD002";
    
    protected EduService service;
    protected UserService userService;
    protected CommonService commonService;
    protected CommonUtil commonUtil;
    protected FlicCommonFileService flicCommonFileService;
    
    /**
     * 각 컨트롤러에서 구현해야 하는 교육 타입 반환
     */
    protected abstract String getEduType();
    
    /**
     * 각 컨트롤러에서 구현해야 하는 JSP 경로 반환
     */
    protected abstract String getJspPath();
    
    /**
     * 교육 타입을 파라미터에 설정
     */
    protected void setEduType(Map<String, Object> paramMap) {
        paramMap.put("EDU_TYPE", getEduType());
    }
    
    /**
     * 공통 응답 맵 생성
     */
    protected Map<String, Object> createResponseMap() {
        return new HashMap<>();
    }
    
    /**
     * 교육 계획 리스트 조회 공통 로직
     */
    protected Map<String, Object> selectEduPlanListCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("list", service.selectEduPlanList(paramMap));
        return responseMap;
    }
    
    /**
     * 교육 계획 상세 조회 공통 로직
     */
    protected void setEduPlanDetailCommon(Map<String, Object> paramMap, ModelMap model) {
        setEduType(paramMap);
        model.put("paramMap", paramMap);
        
        // 교육정보
        Map<String, Object> result = service.selectEduPlanDetail(paramMap);
        model.put("result", result);
        
        // 강의 목차
        List<Map<String, Object>> classList = service.selectEduContentsList(paramMap);
        model.put("classList", classList);
    }
    
    /**
     * 교육 신청서 폼 공통 로직
     */
    protected void setEduApplyFormCommon(Map<String, Object> paramMap, ModelMap model) {
        setEduType(paramMap);
        model.put("paramMap", paramMap);
        
        // 교육정보
        model.put("eduData", service.selectEduPlanDetail(paramMap));
        // 업체정보
        model.put("compData", userService.selectUserCompanyInfo(paramMap));
        // 교육 신청서 정보
        model.put("applyData", service.selectEduApplyInfo(paramMap));
        // 공통코드(인증종류)
        paramMap.put("MSTR_CD", CERT_CODE);
        model.put("certCd", commonService.getCodeList(paramMap));
    }
    
    /**
     * 중복 체크 공통 로직
     */
    protected boolean duplicateCheckCommon(Map<String, Object> paramMap) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        paramMap.put("year", year);
        setEduType(paramMap);
        int count = service.duplicateCheck(paramMap);
        return count <= 0;
    }
    
    /**
     * 교육 신청 저장 공통 로직
     */
    protected Map<String, Object> saveEduApplyCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("eduApplyNo", service.saveEduApply(paramMap));
        return responseMap;
    }
    
    /**
     * 나의 강의실 리스트 조회 공통 로직
     */
    protected Map<String, Object> selectEduClassRoomListCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        // 학습상태
        responseMap.put("statusList", service.selectStudyStatusList(paramMap));
        responseMap.put("list", service.selectEduClassRoomList(paramMap));
        return responseMap;
    }
    
    /**
     * 나의 강의실 상세 조회 공통 로직
     */
    protected void setEduClassRoomDetailCommon(Map<String, Object> paramMap, ModelMap model) {
        setEduType(paramMap);
        // 강의 기본정보
        model.put("applyData", service.selectEduClassRoomDetail(paramMap));
        // 강의 리스트
        model.put("classData", service.selectEduClassStatusList(paramMap));
    }
    
    /**
     * 강의 학습하기 공통 로직
     */
    protected void setEduClassDetailCommon(Map<String, Object> paramMap, ModelMap model) {
        paramMap.put("EDU_BOARD_ID", EDU_BOARD_ID);
        setEduType(paramMap);
        Map<String, Object> result = service.selectEduClassStatusData(paramMap);
        model.addAttribute("result", result);
        
        try {
            Map<String, Object> fileParamMap = new HashMap<>();
            fileParamMap.put("fileId", result.get("ATTACH_FILE_ID"));
            List<Map<String, Object>> fileList = flicCommonFileService.selectAtchFile(fileParamMap);
            model.addAttribute("fileList", fileList);
        } catch(NullPointerException e) {
            throw new UserException(e, "관리자에게 문의 바랍니다.");
        } catch (Exception e) {
            throw new UserException(e, "관리자에게 문의 바랍니다.");
        }
        
        // 강의 리스트
        model.put("classData", service.selectEduClassStatusList(paramMap));
        model.put("paramMap", paramMap);
    }
    
    /**
     * 학습 시간 저장 공통 로직
     */
    protected Map<String, Object> saveStudyTimeCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        
        // 30초 이상인지 검사
        boolean checksStudyTime = service.checkStudyTime(paramMap);
        paramMap.put("STUDY_CHECKTIME", new Date().getTime());
        
        double studyRate = service.checkStudyRate(paramMap);
        paramMap.put("STUDY_RATE", studyRate);
        
        if (checksStudyTime) {
            service.saveStudyTime(paramMap);
        }
        
        // 진도율 100%
        Map<String, Object> classMap = service.selectEduClassRoomDetail(paramMap);
        
        if (((BigDecimal) classMap.get("STUDY_RATE_AVG")).intValue() >= 100 && 
            classMap.get("EDU_CERT_NO").equals("")) {
            
            // 이수증 발급
            Map<String, Object> applyMap = service.selectEduApplyInfo(paramMap);
            responseMap.put("eduCertNo", service.saveEduCertInfo(applyMap));
            
            // 교육이수 완료 문자발송
            service.sendEduCompleteSms(applyMap);
        }
        
        return responseMap;
    }
    
    /**
     * 교육이수증 폼 공통 로직
     */
    protected void setEduCertFormCommon(Map<String, Object> paramMap, ModelMap model) {
        setEduType(paramMap);
        model.put("result", service.selectEduCertInfo(paramMap));
    }
    
    /**
     * 교육 참여현황 리스트 조회 공통 로직
     */
    protected Map<String, Object> selectEduStatusListCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("list", service.selectEduStatusList(paramMap));
        return responseMap;
    }
    
    /**
     * 교육 게시판 공통 로직
     */
    protected void setEduBoardCommon(Map<String, Object> paramMap, ModelMap model) {
        setEduType(paramMap);
        model.addAttribute("paramMap", paramMap);
        model.addAttribute("boardInfo", service.selectEduBoardInfo(paramMap));
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
    }
    
    /**
     * 교육 게시판 리스트 조회 공통 로직
     */
    protected Map<String, Object> selectEduBoardListCommon(Map<String, Object> paramMap) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("list", service.selectEduBoardList(paramMap));
        return responseMap;
    }
    
    /**
     * 교육 게시판 상세 조회 공통 로직
     */
    protected void setEduBoardDetailCommon(Map<String, Object> paramMap, ModelMap model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        
        setEduType(paramMap);
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);
        
        Map<String, Object> result = service.selectEduBoardDetail(paramMap);
        model.put("result", result);
        
        // 비밀번호 체크
        if (result.get("TEXT_PASSWORD") != null && !result.get("TEXT_PASSWORD").toString().isEmpty()) {
            if (!result.get("ORD_NO").toString().equals(session.getAttribute("ORD_NO")) || 
                session.getAttribute("ORD_NO") == null) {
                throw new UserException("비정상적인 접근입니다.");
            }
            
            if (paramMap.get("TEXT_PASSWORD") == null || 
                !result.get("TEXT_PASSWORD").toString().equals(commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()))) {
                throw new UserException("비밀번호가 일치하지 않습니다.");
            }
            session.removeAttribute("ORD_NO");
        }
        
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);
    }
    
    /**
     * 교육 게시판 폼 공통 로직
     */
    protected void setEduBoardFormCommon(Map<String, Object> paramMap, ModelMap model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        HashMap<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("USER_INFO");
        
        setEduType(paramMap);
        Map<String, Object> boardInfo = service.selectEduBoardInfo(paramMap);
        model.addAttribute("boardInfo", boardInfo);
        
        Map<String, Object> result = service.selectEduBoardDetail(paramMap);
        
        // 작성자 ID 체크
        if (paramMap.get("ORD_NO") != null) {
            if (!result.get("REG_ID").equals(userInfo.get("USER_ID"))) {
                throw new UserException("권한이 없습니다.");
            }
        }
        
        // 비밀번호 체크
        if (result.get("TEXT_PASSWORD") != null && !result.get("TEXT_PASSWORD").toString().isEmpty()) {
            if (!result.get("ORD_NO").toString().equals(session.getAttribute("ORD_NO"))) {
                throw new UserException("비정상적인 접근입니다.");
            }
            session.removeAttribute("ORD_NO");
        }
        
        model.put("result", result);
        model.addAttribute("eduData", service.selectEduPlanDetail(paramMap));
        model.put("paramMap", paramMap);
    }
    
    /**
     * 교육 게시글 저장 공통 로직
     */
    protected Map<String, Object> saveEduBoardDataCommon(Map<String, Object> paramMap, HttpServletRequest request) {
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("ordNo", service.saveEduBoardData(paramMap, request));
        HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        return responseMap;
    }
    
    /**
     * 교육 게시글 비밀번호 확인 공통 로직
     */
    protected Map<String, Object> checkEduBoardPasswordCommon(Map<String, Object> paramMap) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("ORD_NO", paramMap.get("ORD_NO"));
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("ordNo", service.selectCheckEduBoardPassword(paramMap));
        return responseMap;
    }
}
