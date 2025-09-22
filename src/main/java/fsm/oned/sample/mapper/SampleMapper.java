package fsm.oned.sample.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
/**
 * 샘플 Mapper
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
@Mapper
@SuppressWarnings("rawtypes")
public interface SampleMapper {
    /**
     * 샘플 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectSampleList(Map<String,Object> paramMap);

    /**
     * 샘플 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectSampleDetail(Map<String,Object> paramMap);

    /**
     * 샘플  - 등록
     * @param paramMap
     */
    void insertSample(Map<String,Object> paramMap);

    /**
     * 샘플 - 수정
     * @param paramMap
     */
    void updateSample(Map<String,Object> paramMap);

    /**
     * 샘플 - 삭제
     * @param paramMap
     */
    void deleteSample(Map<String,Object> paramMap);

}