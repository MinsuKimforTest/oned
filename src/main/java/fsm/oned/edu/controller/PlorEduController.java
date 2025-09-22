package fsm.oned.edu.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * PLOR 교육 Controller
 * @author 라이온 플러스
 * @since 2025.09.18
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2025.09.18    라이온 플러스    최초 생성 및 리팩토링
 *
 * </pre>
 */

@Slf4j
@Controller
@RequestMapping("/edu/plor")
public class PlorEduController extends BaseEduController {
    
    @Override
    protected String getEduType() {
        return EDU_TYPE_PLOR;
    }
    
    @Override
    protected String getJspPath() {
        return JSP_PREFIX + "plor" + JSP_SUFFIX;
    }

    /**
     * PLOR 교육신청 - 리스트 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/planList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return getJspPath() + "eduPlanList";
    }

    /**
     * PLOR 교육신청 - 리스트 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/selectPlanList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduPlanList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        return selectEduPlanListCommon(paramMap);
    }

    /**
     * PLOR 교육신청 - 상세 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/planDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduPlanDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduPlanDetailCommon(paramMap, model);
        return getJspPath() + "eduPlanDetail";
    }

    /**
     * PLOR 교육신청서 - 폼 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/applyForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduApplyForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        if (!duplicateCheckCommon(paramMap)) {
            throw new UserException("P:중복된 강의가 있습니다.");
        }
        
        setEduApplyFormCommon(paramMap, model);
        return getJspPath() + "eduApplyForm";
    }

    /**
     * PLOR 교육신청서 - 저장
     * @param paramMap 파라미터 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/saveApply")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduApply(@LpRequestParam Map<String, Object> paramMap){
        return saveEduApplyCommon(paramMap);
    }

    /**
     * PLOR 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap 파라미터 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value="/selectApplyWorkOptions")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduApplyWorkOptions(@LpRequestParam Map<String, Object> paramMap){
        setEduType(paramMap);
        Map<String, Object> responseMap = createResponseMap();
        responseMap.put("list", service.selectEduApplyWorkOptions(paramMap));
        return responseMap;
    }

    /**
     * PLOR 나의 강의실 - 리스트 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/classRoomList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassRoomList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        model.put("paramMap", paramMap);
        return getJspPath() + "eduClassRoomList";
    }

    /**
     * PLOR 나의 강의실 - 리스트 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/selectClassRoomList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduClassRoomList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        return selectEduClassRoomListCommon(paramMap);
    }

    /**
     * PLOR 나의 강의실 - 상세 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/classRoomDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassRoomDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduClassRoomDetailCommon(paramMap, model);
        return getJspPath() + "eduClassRoomDetail";
    }

    /**
     * PLOR 강의 학습하기 - 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping("/classDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduClassDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduClassDetailCommon(paramMap, model);
        return getJspPath() + "eduClassDetail";
    }

    /**
     * PLOR 강의 학습하기 - 진도 체크
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/saveStudyTime")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveStudyTime(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        return saveStudyTimeCommon(paramMap);
    }

    /**
     * PLOR 교육이수증 - 폼 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/certForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduCertForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduCertFormCommon(paramMap, model);
        return getJspPath() + "eduCertForm";
    }

    /**
     * PLOR 교육 참여현황 - 리스트 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/statusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduStatusList(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return getJspPath() + "eduStatusList";
    }

    /**
     * PLOR 교육 참여현황 - 리스트 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/selectStatusList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduStatusList(@LpRequestParam Map<String, Object> paramMap, ModelMap model){
        return selectEduStatusListCommon(paramMap);
    }

    /**
     * PLOR 교육 게시판 - 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping("/boardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduRoom(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduBoardCommon(paramMap, model);
        return getJspPath() + "eduBoardList";
    }

    /**
     * PLOR 교육 게시판 - 리스트 조회
     * @param paramMap 파라미터 맵
     * @return Map 응답 데이터
     */
    @RequestMapping("/selectBoardList")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> selectEduBoardList(@LpRequestParam Map<String, Object> paramMap) {
        return selectEduBoardListCommon(paramMap);
    }

    /**
     * PLOR 교육 게시판 - 상세 조회
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/boardDetail")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardDetail(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduBoardDetailCommon(paramMap, model);
        return getJspPath() + "eduBoardDetail";
    }

    /**
     * PLOR 교육 게시판 - 폼 화면이동
     * @param paramMap 파라미터 맵
     * @param model 모델 맵
     * @return String JSP 경로
     */
    @RequestMapping(value = "/boardForm")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public String actionEduBoardForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        setEduBoardFormCommon(paramMap, model);
        return getJspPath() + "eduBoardForm";
    }

    /**
     * PLOR 교육 게시글 - 저장
     * @param paramMap 파라미터 맵
     * @param request HTTP 요청
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/saveBoardData")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> saveEduBoardData(@LpRequestParam Map<String, Object> paramMap, HttpServletRequest request){
        return saveEduBoardDataCommon(paramMap, request);
    }

    /**
     * PLOR 교육 게시글 - 비밀번호 확인
     * @param paramMap 파라미터 맵
     * @return Map 응답 데이터
     */
    @RequestMapping(value = "/checkBoardPassword")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    @ResponseBody
    public Map<String, Object> checkEduBoardPassword(@LpRequestParam Map<String, Object> paramMap){
        return checkEduBoardPasswordCommon(paramMap);
    }
}