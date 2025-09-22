package fsm.oned.main.controller;

import fsm.oned.board.service.BoardService;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.aop.LpRequestParam;
import fsm.oned.comm.service.CommonService;
import fsm.oned.comm.util.UserDetailsHelper;
import fsm.oned.edu.service.EduService;
import fsm.oned.main.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 메인 Controller
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
@RequestMapping("/main")
public class MainController {
    @Autowired
    private MainService service;

    @Autowired
    private EduService eduService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommonService commonService;

    /**
     * 메인 - 화면이동
     *
     * @param paramMap
     */
    @RequestMapping("/actionMain")
    @LpAuthCheck(isLoginChk=false,isAuthChk=false)
    public String actionMain(@LpRequestParam Map<String, Object> paramMap, ModelMap model) {
        //공지사항
        /*paramMap.put("BOARD_ID", "BOARD050");*/
        paramMap.put("startIdx", "1");
        paramMap.put("endIdx", "2");

        List<Map<String,Object>> noticeList = boardService.selectBoardNoticeList(paramMap);
        model.put("noticeList", noticeList);

        HashMap<String, Object> userInfo = (HashMap<String, Object>) UserDetailsHelper.getAuthenticatedUser();
        //학습상태
        if(userInfo != null){
            List<Map<String,Object>> statusList = eduService.selectStudyStatusList(paramMap);
            model.put("statusList", statusList);
        }

        //배너
        List<Map<String,Object>> bannerList = commonService.selectBannerList(paramMap);
        model.put("bannerList", bannerList);

        return "main/main";
    } 

}