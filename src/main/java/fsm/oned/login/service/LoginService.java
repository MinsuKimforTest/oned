package fsm.oned.login.service;

import fsm.oned.board.mapper.BoardMapper;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.Globals;
import fsm.oned.comm.util.UserDetailsHelper;
import fsm.oned.login.mapper.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 로그인 Service
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
@Service
public class LoginService {
    @Autowired
    private LoginMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 로그인 - 처리
     * @param paramMap
     * @return Map
     */
    public Map<String, Object> selectLoginInfo(Map<String, Object> paramMap) {
        Map returnmap = new HashMap<>();

        log.info("selectLoginOnfo paramMpa : " + paramMap);

        Map<String,Object> retMap = new HashMap<>();
        Map<String, Object> map = mapper.selectLoginInfo(paramMap);
        Map<String, Object> check = mapper.selectcheckLoginInfoUser(paramMap);
        Map<String, Object> check2 = mapper.selectcheckUserInfo(paramMap);

        if (map != null) {

            if(check2 == null){
                mapper.insertcheckSuccessInfo(paramMap);
            }else {
                if((new Date().getTime() - Long.parseLong(String.valueOf(check2.get("FAILTIME")))) < 30000){
                    returnmap.put("failtime",null);
                    return returnmap;
                }
                mapper.updatecheckCntUserInfo(paramMap);
            }

            //공통 로그인 세션 처리
            HashMap<String, Object> userInfo = new HashMap<>();
            userInfo.put("USER_ID", map.get("USER_ID").toString());             //사용자ID
            userInfo.put("USER_NM", map.get("USER_NM").toString());             //이름
            userInfo.put("USER_TYPE", map.get("USER_TYPE").toString());         //사용자구분(4 개인사업자, 5 업체대표자, 6 업체직원)
            userInfo.put("COMP_NO_BLNG", map.get("COMP_NO_BLNG").toString());   //업체번호
            userInfo.put("BIZ_REG_NO", map.get("BIZ_REG_NO").toString());       //사업자번호
            userInfo.put("COMP_NM_KOR", map.get("COMP_NM_KOR").toString());     //업체명
            userInfo.put("SCE_SYS_CD", Globals.SCE_SYS_CD);                     //시스템 구분

            /*if("5".equals(map.get("USER_TYPE")) || "6".equals(map.get("USER_TYPE"))){
                userInfo.put("USER_GUBUN", "2");                                //업체
                userInfo.put("CERT_ID", map.get("COMP_NO_BLNG").toString());    //업체번호
            }else{
                userInfo.put("USER_GUBUN", "1");                                //개인
                userInfo.put("CERT_ID", map.get("USER_ID").toString());         //사용자ID
            }*/

            UserDetailsHelper.setAuthenticatedUser(userInfo);
        } else {
            if(check!= null && check2 == null){
                mapper.insertcheckUserInfo(paramMap);
            }else if(check != null && check2 != null){
                if((new Date().getTime() - Long.parseLong(String.valueOf(check2.get("FAILTIME")))) < 30000){
                    returnmap.put("failtime",null);
                    return returnmap;
                }
                if(Integer.parseInt(String.valueOf(check2.get("CHECKCNT"))) >= 4){
                    paramMap.put("FAILTIME", new Date().getTime());
                    mapper.updatecheckFailtime(paramMap);
                }else{
                    mapper.updatecheckUserInfo(paramMap);
                }
            }
            if(check2==null){
                returnmap.put("fail",null);
            }else{
                returnmap.put("fail",check2.get("CHECKCNT"));
            }
            return returnmap;

        }
        return retMap;
    }

}
