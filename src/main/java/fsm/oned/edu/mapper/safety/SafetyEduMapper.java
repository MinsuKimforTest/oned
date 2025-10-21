package fsm.oned.edu.mapper.safety;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SAFETY 교육 Mapper
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface SafetyEduMapper {

    /**
     * SAFETY 교육신청서 - 저장 (교육 종료일: 올해 12월 31일)
     * @param paramMap
     * @return int
     */
    int insertSafetyEduApply(Map<String,Object> paramMap);

    /**
     * 교육신청서 - 강의 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduClassList(Map<String,Object> paramMap);

    /**
     * 교육신청서 - 교육진도 등록
     * @param paramMap
     * @return int
     */
    int insertStudyStatus(Map<String,Object> paramMap);

    void saveEduCertInfo(Map<String, Object> applyMap);

    void insertEduSms(Map<String, Object> smsMap);

    int checkEduApplyInfo(Map<String, Object> paramMap);

    Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap);
}



