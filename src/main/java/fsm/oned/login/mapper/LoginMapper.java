package fsm.oned.login.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 로그인 Mapper
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
public interface LoginMapper {

    /**
     * 로그인 - 처리
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectLoginInfo(Map<String, Object> paramMap);

    Map<String, Object> selectcheckLoginInfoUser(Map<String, Object> paramMap);

    Map<String, Object> selectcheckUserInfo(Map<String, Object> paramMap);

    void insertcheckUserInfo(Map<String, Object> paramMap);

    void updatecheckFailtime(Map<String, Object> paramMap);

    void updatecheckUserInfo(Map<String, Object> paramMap);

    void insertcheckSuccessInfo(Map<String, Object> paramMap);

    void updatecheckCntUserInfo(Map<String, Object> paramMap);
}