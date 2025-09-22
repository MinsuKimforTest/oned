package fsm.oned.organicCert.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 인증번호 Mapper
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
public interface OrganicCertMapper {

    /**
     * 친환경 인증번호 - 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectOrganicCertNo(Map<String,Object> paramMap);

    /**
     * 친환경 인증번호 - 저장
     */
    void saveOrganicCertNo(Map<String,Object> paramMap);

    /**
     * 친환경 인증번호(유기수산물인증) - 리스트
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectOrganicCertList(Map<String,Object> paramMap);

    /**
     * 친환경 인증번호 - 확인유무 저장
     */
    void saveCheckOrganicCertNo(Map<String,Object> paramMap);

    /**
     * 친환경 인증번호 - 유기수산물인증 정보
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectOrganicCertInfo(Map<String,Object> paramMap);

}