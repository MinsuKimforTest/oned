package fsm.oned.comm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fsm.oned.comm.util.Globals;
import fsm.oned.comm.util.UserDetailsHelper;
import fsm.oned.comm.lpEnum.ComEnum;
import fsm.oned.comm.mapper.CommonMapper;
import fsm.oned.comm.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import fsm.oned.comm.exception.UserException;

@SuppressWarnings({"rawtypes","unchecked"})
@Service
@Transactional
public class CommonService {

    @Autowired
    private CommonMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 암호화서비스
     */
    @Resource(name = "egovEnvPasswordEncoderService")
    EgovPasswordEncoder egovPasswordEncoder;
    
    /**
     *  사용자 선택 메뉴 참조 URL 조회
     * @param hm
     * @return
     */
  /*  public Map<String, Object> selectLoginInfo(Map<String, Object> paramMap) {
        return mapper.selectLoginInfo(paramMap);
    }*/
    
/*    public void setSessionUserInfo(UserInfoVO userInfoVO) {
        String intgrMngrYn = mapper.selectIntgrMngrYn(userInfoVO);
        if(intgrMngrYn != null ) userInfoVO.setIntgrMngr(true);
    }*/
    
    /**
     * 로그인 > 로그인처리
     *
     * @param map
     * @return List
     */
    public Map<String, Object> selectLoginInfo(Map<String, Object> paramMap, HttpServletRequest request) {
    	Map<String, Object> hmRtn = new HashMap<String, Object>();
        String redirectUrl = ""; 
	    Map<String, Object> map = mapper.selectLoginInfoSso(paramMap);
        if (map != null) {
		      //공통 로그인 세션 처리
		       HashMap<String, Object> userInfo = new HashMap<>();
		       userInfo.put("USER_ID", map.get("USER_ID").toString());
		       userInfo.put("USER_NM", map.get("USER_NM").toString());
		       userInfo.put("USER_TYPE", map.get("USER_TYPE").toString());
		       userInfo.put("BIZ_REG_NO", map.get("BIZ_REG_NO").toString());
		       userInfo.put("COMP_NM_KOR", map.get("COMP_NM_KOR").toString());
		       userInfo.put("ORGAN_CD", map.get("ORGAN_CD").toString());
		       userInfo.put("INTGR_MN_GR", "false");
		       userInfo.put("SCE_SYS_CD", Globals.SCE_SYS_CD);
		       userInfo.put("USER_IP", commonUtil.getUserAccessIp(request));
		       //통합관리자 여부 설정
		       String intgrMngrYn = mapper.selectIntgrMngrYn(userInfo);
		       if (intgrMngrYn != null) userInfo.put("INTGR_MN_GR", "true");
		        
		       List<Map<String,String>> menuAllList; // 사용자 따라 메뉴 처리
		        if( userInfo.get("INTGR_MN_GR") == "true" ) {
		            menuAllList = mapper.selectAllMenuList(userInfo);
		        }else { 
		            menuAllList = mapper.selectUserMenuList(userInfo);
		        }
		       redirectUrl = "./main.do";
		       //메인 페이지가 있으면 메인페이지로 이동
		       //없으면 권한있는 첫번째 URL 처리
		       for(Map menuMap : menuAllList) {
		           if(!menuMap.get("URL").toString().isEmpty()) {
		               hmRtn.put("redirectUrl", menuMap.get("URL").toString());  
		               break;
		           }
		       }
		       UserDetailsHelper.setAuthenticatedUser(userInfo);
		    } else {
	          throw new UserException("로그인 정보를 확인해 주세요.");
	        }
	        return hmRtn;
    }
    
    
    /**
     * 로그인 > 로그인처리
     *
     * @param map
     * @return List
     */
    public Map<String, Object> selectLoginInfoSso(Map<String, Object> paramMap, HttpServletRequest request) {
	    Map<String, Object> map = mapper.selectLoginInfoSso(paramMap);
        HashMap<String, Object> userInfo = new HashMap<>();
        if (map != null  ) {
            userInfo.put("USER_ID", map.get("USER_ID").toString());
            userInfo.put("USER_NM", map.get("USER_NM").toString());
            userInfo.put("USER_TYPE", map.get("USER_TYPE").toString());
            userInfo.put("BIZ_REG_NO", map.get("BIZ_REG_NO").toString());
            userInfo.put("COMP_NM_KOR", map.get("COMP_NM_KOR").toString());
            userInfo.put("ORGAN_CD", map.get("ORGAN_CD").toString());
            userInfo.put("INTGR_MN_GR", "false");
            userInfo.put("SCE_SYS_CD", Globals.SCE_SYS_CD);
            userInfo.put("USER_IP", commonUtil.getUserAccessIp(request));

		    //통합관리자 여부 설정
		    String intgrMngrYn = mapper.selectIntgrMngrYn(userInfo);
		    if (intgrMngrYn != null) userInfo.put("INTGR_MN_GR", "true");
        } else {
            throw new UserException("로그인 정보를 확인해 주세요.");
        }
        return userInfo;
    }
    


    /**
     *  공통코드 조회
     * @param hm
     * @return
     */

    public List<Map<String, String>> getCodeList(Map<String, Object> paramMap) {
        return mapper.selectCodeList(paramMap);
    }

    /**
     *  사용자 정의 코드 조회
     * @param paramMap
     * @return
    */
    public List<Map<String, String>> getUserCodeList(Map<String, Object> paramMap) {
        String condCd = paramMap.get("COND_CD").toString();
        List<Map<String, String>> ls = new ArrayList();
        switch(condCd){
            case "UCD01": //사용자정의코드01 - 소속부서
                ls =  mapper.selectUserCode01List(paramMap);
            break;
            case "UCD02": //사용자정의코드02 - 소속지원
                ls =  mapper.selectUserCode02List(paramMap);
                break;
            case "UCD03": //사용자정의코드03 - 사용자유형
                ls =  mapper.selectUserCode03List(paramMap);
                break;
            case "UCD04": //사용자정의코드04
                ls =  mapper.selectUserCode04List(paramMap);
                break;
            case "UCD05": //사용자정의코드05
                ls =  mapper.selectUserCode05List(paramMap);
                break;
            case "UCD06": //사용자정의코드06
                ls =  mapper.selectUserCode06List(paramMap);
                break;
        }
        return ls;
    }

    /**
     *  사용자 메뉴  조회
     * @param hm
     * @return
     */
    public List<Map<String, String>> selectUserMenuList(HashMap<String, Object> userInfo) {

        if( userInfo.get("INTGR_MN_GR") == "true") {
            return mapper.selectAllMenuList(userInfo);
        }else {
            return mapper.selectUserMenuList(userInfo);
        }

    }

    /**
     *  사용자 선택 메뉴 참조 URL 조회
     * @param hm
     * @return
     */
    public List<Map<String, String>> selectUserMenuRefUrlList(Map<String, Object> paramMap) {
        return mapper.selectUserMenuRefUrlList(paramMap);
    }
    
    /**
     *  메뉴가 있는지 체크
     * @param hm
     * @return
     */

    public String selectCurrentMenuAt(Map<String, Object> paramMap) {
        return mapper.selectCurrentMenuAt(paramMap);
    }

    /**
     *  메뉴 접속 로그 등록
     * @param hm
     * @return
     */
    public void insertTcMenuAccessStat(Map<String, Object> paramMap) {
        if(null == paramMap.get("CURRENT_MENU_ID")) {
            paramMap.put("CURRENT_MENU_NM", "친환경교육 메인");
            paramMap.put("CURRENT_MENU_URL", "/main/actionMain.do");
        }
        paramMap.put(ComEnum.sessionUser.name(), UserDetailsHelper.getAuthenticatedUser());
        paramMap.put("SCE_SYS_CD", Globals.SCE_SYS_CD);
        mapper.insertTcMenuAccessStat(paramMap);
    }

    /**
     * 배너리스트
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> selectBannerList(Map<String, Object> paramMap) {
        return mapper.selectBannerList(paramMap);
    }
}