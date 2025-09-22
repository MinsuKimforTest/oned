package fsm.oned.comm.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface CommonMapper {

	
	 /**
     * 사용자 조회
     * @param paramMap
     * @return List
     */
    Map<String,Object> selectLoginInfo(Map<String, Object> paramMap);
	
	 /**
     * 사용자 조회
     * @param mav
     * @return List
     */
     Map<String,Object> selectLoginInfoSso(Map<String, Object> paramMap);
	
    /**
     * 공통코드 조회
     * @param paramMap
     * @return
     */
    List<Map<String, String>> selectCodeList(Map<String, Object> paramMap);


    /*-----------------------사용자 정의 코드 시작------------------------------*/
    /**
     * 사용자정의코드01 - 소속부서
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode01List(Map<String, Object> paramMap);

    /**
     * 사용자정의코드02
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode02List(Map<String, Object> paramMap);
    /**
     * 사용자정의코드03 - 사용자유형
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode03List(Map<String, Object> paramMap);
    /**
     * 사용자정의코드04
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode04List(Map<String, Object> paramMap);
    /**
     * 사용자정의코드05
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode05List(Map<String, Object> paramMap);

    /**
     * 사용자정의코드06
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserCode06List(Map<String, Object> paramMap);

    /*-----------------------사용자 정의 코드 종료------------------------------*/


 
    
    /**
     * 전체 메뉴 조회
     * @param hm
     * @return
     */
    List<Map<String, String>> selectAllMenuList(HashMap<String, Object> userInfo);

    /**
     * 사용자 메뉴 조회
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserMenuList(HashMap<String, Object> userInfo);

    /**
     * 사용자 선택 메뉴 참조 URL 조회
     * @param hm
     * @return
     */
    List<Map<String, String>> selectUserMenuRefUrlList(Map<String, Object> paramMap);

    /**
     * 메뉴 접근 가능 여부 체크
     * @param paramMap
     * @return
     */
    String selectAccessMenuAt(Map<String, Object> paramMap);
    
    /**
     * 메뉴가 있는지 체크
     * @param paramMap
     * @return
     */
    String selectCurrentMenuAt(Map<String, Object> paramMap);

    /**
     * 통합관리자 그룹 여부
     * @param paramMap
     * @return
     */
    String selectIntgrMngrYn(HashMap<String, Object> userInfo);

    /**
     * 메뉴 접속 로그 등록
     * @param paramMap
     */
    void insertTcMenuAccessStat(Map<String, Object> paramMap);

    /**
     * 배너리스트
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectBannerList(Map<String, Object> paramMap);

}