package fsm.oned.edu.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 교육 Mapper (확장/백업용)
 * - 기존 쿼리를 건드리지 않고, GBN/EDU_BOARD_ID 등을 파라미터화한 메서드를 추가 제공
 */
@Mapper
@SuppressWarnings({"rawtypes", "unchecked"})
public interface EduMapperExt {

    /**
     * 교육신청 - 리스트 조회 (GBN 파라미터)
     */
    List<Map<String, Object>> selectEduPlanListByGbn(Map<String, Object> paramMap);

    /**
     * 교육신청 - 상세 조회 (기존과 동일, 필요 시 재사용)
     */
    Map<String, Object> selectEduPlanDetail(Map<String, Object> paramMap);

    /**
     * 교육신청 - 목차 상세 조회 (EDU_BOARD_ID 파라미터)
     */
    List<Map<String, Object>> selectEduContentsListByBoard(Map<String, Object> paramMap);

    /**
     * 강의 - 상세 조회 (EDU_BOARD_ID 파라미터)
     */
    Map<String, Object> selectEduClassStatusDataByBoard(Map<String, Object> paramMap);
}


