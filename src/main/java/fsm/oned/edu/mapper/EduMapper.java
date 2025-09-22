package fsm.oned.edu.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 교육 Mapper
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
public interface EduMapper {

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
     * @return
     */
    List<Map<String, Object>> selectEduContentsList(Map<String, Object> paramMap);

    /**
     * 교육신청서 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduApplyInfo(Map<String,Object> paramMap);

    /**
     * 교육신청서 - 권한확인
     * @return
     */
    int checkEduApplyInfo(Map<String, Object> paramMap);

    /**
     * 교육신청서 - 등록
     * @param paramMap
     */
    void insertEduApply(Map<String,Object> paramMap);

    /**
     * 교육신청서 - 강의 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduClassList(Map<String,Object> paramMap);
    
    /**
     * 교육신청서 - 교육진도 등록
     * @param paramMap
     */
    void insertStudyStatus(Map<String,Object> paramMap);

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduApplyWorkOptions(Map<String,Object> paramMap);

    /**
     * 나의 강의실 - 학습상태 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectStudyStatusList(Map<String,Object> paramMap);

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
     * 강의 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduClassStatusList(Map<String,Object> paramMap);

    /**
     * 강의 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduClassStatusData(Map<String,Object> paramMap);

    /**
     * 강의 학습하기 - 진도 체크
     * @param paramMap
     */
    void saveStudyTime(Map<String,Object> paramMap);

    /**
     * 교육이수증 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduCertInfo(Map<String,Object> paramMap);

    /**
     * 교육 이수증 - 발급
     * @param paramMap
     */
    void saveEduCertInfo(Map<String,Object> paramMap);

    /**
     * 교육 참여현황 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduStatusList(Map<String,Object> paramMap);

    /**
     * 교육 게시판 관리  - 상세 조회
     *
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduBoardInfo(Map<String,Object> paramMap);

    /**
     * 교육 게시판 - 리스트 조회
     *
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduBoardList(Map<String,Object> paramMap);

    /**
     * 교육 게시판(답변형) - 리스트 조회
     *
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduBoardAnswerList(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectEduBoardDetail(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 조회수 count
     * @param paramMap
     */
    void increaseEduBoardLockupCnt(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 등록
     * @param paramMap
     */
    void insertEduBoardData(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 수정
     * @param paramMap
     */
    void updateEduBoardData(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 삭제
     * @param paramMap
     */
    void deleteEduBoardData(Map<String,Object> paramMap);

    /**
     * 교육 게시글 - 비밀번호 조회
     * @param paramMap
     * @return String
     */
    String selectCheckEduBoardPassword(Map<String,Object> paramMap);

    /**
     * 친환경교육 - 문자 발송
     * @param paramMap
     */
    void insertEduSms(Map<String,Object> paramMap);

    Map<String, Object> checkStudyTime(Map<String, Object> paramMap);

    double checkStudyRate(Map<String, Object> paramMap);

    Map checkStudyRate2(Map<String, Object> paramMap);

    /**
     * 교육 신청자 담당업무 - 사용자 정의 코드
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectEduApplyHaccpWorkOptions(Map<String,Object> paramMap);

    List<Map<String, Object>> selectEduPlanHaccpList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectEduStatusHaccpList(Map<String, Object> paramMap);

    Map<String, Object> selectEduCertHaccpInfo(Map<String, Object> paramMap);

    void insertEduHaccpApply(Map<String, Object> paramMap);

    void saveEduCertHaccpInfo(Map<String, Object> paramMap);

    int duplicateCheck(Map<String, Object> paramMap);
}