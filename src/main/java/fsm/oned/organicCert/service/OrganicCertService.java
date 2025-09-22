package fsm.oned.organicCert.service;

import fsm.oned.organicCert.mapper.OrganicCertMapper;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 인증번호 Service
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

@Service
public class OrganicCertService {
    @Autowired
    private OrganicCertMapper mapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 친환경 인증번호 - 등록 확인
     * @param paramMap
     * @return Map
     */
    public Map<String,Object> selectOrganicCertNo(Map<String,Object> paramMap){
        Map<String,Object> retMap = new HashMap();

        //인증 관련 사용자 정보
        Map<String, Object> certUserMap = selectUserInfoForCert(paramMap);
        paramMap.put("certUserMap", certUserMap);

        retMap =  mapper.selectOrganicCertNo(paramMap);
        return retMap;
    }

    /**
     * 친환경 인증번호 - 저장
     * @param paramMap
     * @return Map
     */
    public Map<String,Object> saveOrganicCertNo(Map<String,Object> paramMap, HttpServletRequest request){
        //인증번호 검증
        /*if(paramMap.get("CERT_GUBUN").equals("2")){
            List<Map<String,Object>> organicCertList = mapper.selectOrganicCertList(paramMap);
            String[] certNoArr = paramMap.get("CERT_NO").toString().split(",");

            for(String certNo : certNoArr){
                boolean confirmCertNo = false;
                for(Map<String,Object> organicCert : organicCertList){
                    if(certNo.equals(organicCert.get("CERTNO"))){
                        confirmCertNo = true;
                        break;
                    }else{
                        confirmCertNo = false;
                    }
                }

                if(!confirmCertNo){
                    throw new UserException("[" + certNo + "]는 유효하지 않은 인증번호입니다. 인증번호를 다시 확인하여주십시오.");
                }
            }
        }*/

        //인증 관련 사용자 정보
        Map<String, Object> certUserMap = selectUserInfoForCert(paramMap);
        paramMap.put("certUserMap", certUserMap);

        //저장
        mapper.saveOrganicCertNo(paramMap);

        //친환경 인증번호 조회
        Map<String,Object> retMap = new HashMap();
        retMap =  mapper.selectOrganicCertNo(paramMap);

        return retMap;
    }

    /**
     * 친환경 인증번호 - 확인유무 저장
     */
    public void saveCheckOrganicCertNo(Map<String,Object> paramMap) {
        //인증 관련 사용자 정보
        Map<String, Object> certUserMap = selectUserInfoForCert(paramMap);
        paramMap.put("certUserMap", certUserMap);

        mapper.saveCheckOrganicCertNo(paramMap);
    }

    /**
     * 친환경 인증번호 - 유기수산물인증 정보
     * @param paramMap
     * @return Map
     */
    public Map<String,Object> selectOrganicCertInfo(Map<String,Object> paramMap){
        Map<String,Object> retMap = new HashMap();
        retMap =  mapper.selectOrganicCertInfo(paramMap);
        return retMap;
    }

    /**
     * 친환경 인증번호 - 사용자정보
     * @param paramMap
     * @return
     */
    public Map<String, Object> selectUserInfoForCert(Map<String, Object> paramMap){
        Map<String, Object> certUserMap = new HashMap<>();
        certUserMap = userMapper.selectUserInfo(paramMap);

        if("5".equals(certUserMap.get("USER_TYPE")) || "6".equals(certUserMap.get("USER_TYPE"))){
            certUserMap.put("USER_GUBUN", "2");                                     //업체
            certUserMap.put("CERT_ID", certUserMap.get("COMP_NO_BLNG").toString()); //업체번호
        }else{
            certUserMap.put("USER_GUBUN", "1");                                     //개인
            certUserMap.put("CERT_ID", certUserMap.get("USER_ID").toString());      //사용자ID
        }

        return certUserMap;
    }

}
