package fsm.oned.sample.controller;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.sample.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 샘플 Controller
 * @author 라이온 플러스 남상재
 * @since 2020.06.30
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2020.06.30    남상재    최초 생성
 *
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/sample")
public class SampleController {
    @Autowired
    private SampleService service;

    /**
     * java method명명 규칙
     * 유형             prefix
     * 내용검증         validate
     * 조건확인         check
     * 검색             search
     * 연계             contact
     * 기본화면         action
     * 파일관리-읽기    read
     * 파일관리-쓰기    write
     *
     * 등록             insert
     * 조회(단건)       select
     * 조회(멀티건)     select   postfix로 List사용
     * 수정             update
     * 삭제             delete
     * 등록/수정        merge
     * 등록/수정/삭제   multi    작업을 동시에 수행하는 경우
     */

    /**
     * JSP 명명 규칙
     * Postfix명 설명      적용 예
     * From      화면      EgovChangeRequestFrom
     * Search    조회      EgovChangeRequestSearch
     * Detail    상세      EgovChangeRequestDetail
     * Regist    등록      EgovChangeRequestRegist
     * Update    수정      EgovChangeRequestUpdate
     * Delete    삭제      EgovChangeRequestDelete
     * Popup     팝업      EgovChangeRequestPopup
     */

    /**
     * 샘플 - 화면이동 (파이텍제공)
     *
     * @param paramMap
     */
    @RequestMapping("/actionGridSample")
    public String actionGridSample(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "sample/gridSample";
    }

    /**
     * 샘플 - 화면이동
     *
     * @param paramMap
     */
    @RequestMapping("/actionSample")
    public String actionSample(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        return "sample/sampleSearch";
    }

    /**
     * 샘플 - 리스트 조회
     *
     * @param paramMap
     * @return Map
     */
    @RequestMapping("/selectSampleList")
    @ResponseBody
    public Map<String, Object> selectSampleList(@LpRequestParam Map<String, Object> paramMap) {
        Map<String, Object> resBodyMap = new HashMap<>(); // 리턴 결과값
        resBodyMap.put("list", service.selectSampleList(paramMap));
        return resBodyMap;
    }

    /**
     * 샘플 - 등록화면 이동
     *
     * @param paramMap
     * @param model
     */
    @RequestMapping("/actionSampleDetail")
    public String sampleForm(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        model.put("result", service.selectSampleDetail(paramMap));
        return "sample/sampleDetailPopup";
    }

    /**
     * 샘플 - 저장
     *
     * @param paramMap
     */
    @RequestMapping("/multiSample")
    @ResponseBody
    public void multiSample(@LpRequestParam Map<String, Object> paramMap) {
        service.multiSample(paramMap);
    }

}