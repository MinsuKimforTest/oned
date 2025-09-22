package fsm.oned.user.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 사용자 Mapper
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

@Mapper
@SuppressWarnings("rawtypes")
public interface UserMapper {

    /**
     * 회원정보
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectUserInfo(Map<String,Object> paramMap);

    /**
     * 회원정보 - 수정
     * @param paramMap
     * @return String
     */
    void updateUserInfo(Map<String, Object> paramMap);

    /**
     * 회원정보 - 비밀번호 확인
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectUserPassword(Map<String,Object> paramMap);

    /**
     * 회원정보 - 비밀번호 수정
     * @param paramMap
     * @return String
     */
    void updateUserPassword(Map<String, Object> paramMap);

    /**
     * 회원정보 - 업체정보 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectCompanyList(Map<String,Object> paramMap);

    /**
     * 업체정보
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectUserCompanyInfo(Map<String,Object> paramMap);

}