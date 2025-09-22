package fsm.oned.board.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 게시판 Mapper
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
public interface BoardMapper {

    /**
     * 게시판 정보
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectBoardInfo(Map<String,Object> paramMap);

    /**
     * 게시판 - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectBoardList(Map<String,Object> paramMap);

    /**
     * 게시판 - 답변형 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectAnswerBoardList(Map<String,Object> paramMap);

    /**
     * 게시글 - 상세 조회
     * @param paramMap
     * @return Map
     */
    Map<String,Object> selectBoardDetail(Map<String,Object> paramMap);

    /**
     * 게시글 - 조회수 count
     * @param paramMap
     */
    void increaseBoardLockupCnt(Map<String,Object> paramMap);

    /**
     * 게시글 - 등록
     * @param paramMap
     */
    void insertBoardData(Map<String,Object> paramMap);

    /**
     * 게시글 - 수정
     * @param paramMap
     */
    void updateBoardData(Map<String,Object> paramMap);

    /**
     * 게시글 - 삭제
     * @param paramMap
     */
    void deleteBoardData(Map<String,Object> paramMap);

    /**
     * 게시글 - 비밀번호 조회
     * @param paramMap
     * @return String
     */
    String selectCheckBoardPassword(Map<String,Object> paramMap);

    /**
     * 메인(공지사항) - 리스트 조회
     * @param paramMap
     * @return List
     */
    List<Map<String,Object>> selectBoardNoticeList(Map<String,Object> paramMap);

    int selectBoardInfoCheck(Map paramMap);
}