package fsm.oned.edu.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 통합 교육 Mapper (PLOR, SAFETY 등)
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2025.01.15    개발자    최초 생성
 *
 * </pre>
 */

@Mapper
@SuppressWarnings("rawtypes")
public interface UnifiedEduMapper {

    /**
     * 유효한 교육 타입 조회 (동적 검증용)
     * @param paramMap
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectValidEduTypes(Map<String, Object> paramMap);

    /**
     * 교육신청 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduPlanList(Map<String,Object> paramMap);

    /**
     * 교육신청 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduPlanDetail(Map<String,Object> paramMap);

    /**
     * 교육신청 - 조회수 count
     * @param paramMap
     */
    void increaseEduPlanLockupCnt(Map<String,Object> paramMap);

    /**
     * 교육신청 - 목차 상세 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduContentsList(Map<String,Object> paramMap);

    /**
     * 교육신청서 - 교육 정보 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduApplyInfo(Map<String,Object> paramMap);

    double checkStudyRate(Map<String, Object> paramMap);

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduApplyWorkOptions(Map<String,Object> paramMap);

    /**
     * 나의 강의실 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduClassRoomList(Map<String,Object> paramMap);

    /**
     * 나의 강의실 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduClassRoomDetail(Map<String,Object> paramMap);

    /**
     * 강의 학습하기 - 강의 리스트
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduClassStatusList(Map<String,Object> paramMap);

    /**
     * 강의 학습하기 - 강의 정보
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduClassStatusData(Map<String,Object> paramMap);

    /**
     * 강의 학습하기 - 진도 체크
     * @param paramMap
     * @return int
     */
    int saveStudyTime(Map<String,Object> paramMap);

    /**
     * 교육이수증 - 이수증 정보 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduCertInfo(Map<String,Object> paramMap);

    /**
     * 교육이수증 - 이수증 발급
     * @param paramMap
     * @return int
     */
    int saveEduCertInfo(Map<String,Object> paramMap);

    /**
     * 교육 참여현황 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduStatusList(Map<String,Object> paramMap);

    /**
     * 교육 게시판 정보 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduBoardInfo(Map<String,Object> paramMap);

    /**
     * 교육 게시판 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduBoardList(Map<String,Object> paramMap);

    /**
     * 교육 게시판 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduBoardDetail(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 저장
     * @param paramMap
     * @return int
     */
    int insertEduBoardData(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 수정
     * @param paramMap
     * @return int
     */
    int updateEduBoardData(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 비밀번호 확인
     * @param paramMap
     * @return String
     */
    String selectCheckEduBoardPassword(Map<String,Object> paramMap);

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
}
