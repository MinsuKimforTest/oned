package fsm.oned.edu.service;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.Globals;
import fsm.oned.edu.mapper.UnifiedEduMapper;
import fsm.oned.organicCert.service.OrganicCertService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;

/**
 * 통합 교육 Service (PLOR, SAFETY 등)
 * 
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 * @see
 *
 *      <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2025.01.15    개발자    최초 생성
 *      </pre>
 */

@Slf4j
@Service
public class UnifiedEduService {
	@Autowired
	private UnifiedEduMapper mapper;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private OrganicCertService organicCertService;

	@Resource(name = "flicCommonFileService")
	private FlicCommonFileService flicCommonFileService;

	/**
	 * 유효한 교육 타입 조회 (동적 검증용)
	 * @param paramMap
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> selectValidEduTypes(Map<String, Object> paramMap) {
		return mapper.selectValidEduTypes(paramMap);
	}

	/**
	 * 교육신청 - 리스트 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return List
	 */
	public List<Map<String, Object>> selectEduPlanList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduPlanList(paramMap);
		return list;
	}

	/**
	 * 교육신청 - 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return Map
	 */
	public Map<String, Object> selectEduPlanDetail(Map<String, Object> paramMap) {
		//조회수 count
		mapper.increaseEduPlanLockupCnt(paramMap);

		Map<String, Object> retMap = new HashMap();
		if (paramMap.get("EDU_ID") != null) {
			retMap = mapper.selectEduPlanDetail(paramMap);
			String des = "";
			if (retMap.get("DES") != null) {
				des = retMap.get("DES").toString();
				retMap.put("DES", commonUtil.getEscape(des));
			}
		}
		return retMap;
	}

	/**
	 * 교육신청 - 목차 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return
	 */
	public List<Map<String, Object>> selectEduContentsList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduContentsList(paramMap);
		return list;
	}

	/**
	 * 교육신청서 - 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return Map
	 */
	public Map<String, Object> selectEduApplyInfo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap();
		retMap = mapper.selectEduApplyInfo(paramMap);
		return retMap;
	}

	/**
	 * 교육 신청자 담당업무 - 사용자 정의 코드
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return List
	 */
	public List<Map<String, Object>> selectEduApplyWorkOptions(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduApplyWorkOptions(paramMap);
		return list;
	}

	/**
	 * 교육이수증 - 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return Map
	 */
	public Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = mapper.selectEduCertInfo(paramMap);
		return retMap;
	}

	/**
	 * 교육 이수증 - 발급
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return String
	 */
	public String saveEduCertInfo(Map<String, Object> paramMap) {
		mapper.saveEduCertInfo(paramMap);
		return paramMap.get("EDU_CERT_NO").toString();
	}

	/**
	 * 교육 참여현황 - 리스트 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return List
	 */
	public List<Map<String, Object>> selectEduStatusList(Map<String, Object> paramMap) {
		//인증 관련 사용자 정보
		Map<String, Object> certUserMap = organicCertService.selectUserInfoForCert(paramMap);
		paramMap.put("certUserMap", certUserMap);

		List<Map<String, Object>> list = mapper.selectEduStatusList(paramMap);
		return list;
	}

	/**
	 * 교육 게시판 관리 - 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return Map
	 */
	public Map<String, Object> selectEduBoardInfo(Map<String, Object> paramMap) {
		Map<String, Object> map = new HashMap();
		if (paramMap.get("EDU_BOARD_ID") != null) {
			map = mapper.selectEduBoardInfo(paramMap);
		}

		return map;
	}

	/**
	 * 교육 게시판 - 리스트 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return List
	 */
	public List<Map<String, Object>> selectEduBoardList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduBoardList(paramMap);
		return list;
	}

	/**
	 * 게시글 - 상세 조회
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return Map
	 */
	public Map<String, Object> selectEduBoardDetail(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap();
		if (paramMap.get("EDU_BOARD_ID") != null && paramMap.get("EDU_ID") != null && paramMap.get("ORD_NO") != null) {
			retMap = mapper.selectEduBoardDetail(paramMap);
			String des = "";
			if (retMap.get("DES") != null) {
				des = retMap.get("DES").toString();
				retMap.put("DES", commonUtil.getEscape(des));
			}
		}
		return retMap;
	}

	/**
	 * 교육 게시글 - 저장
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return String
	 */
	public String saveEduBoardData(Map<String, Object> paramMap, HttpServletRequest request) {
		Logger logger = null;
		String rowStats = paramMap.get("rowStats").toString();

		switch (SaveEnum.valueOf(rowStats)) {
		case I:
		case U:
			if (request != null) {
				String[] fileNm = request.getParameterValues("mapFile_fileNm");
				if (fileNm != null) {
					if (paramMap.get("ATTACH_FILE_ID").toString().isEmpty()) {
						paramMap.put("ATTACH_FILE_ID", UUID.randomUUID().toString().replaceAll("-", ""));
					}

					String[] chgFileNm = request.getParameterValues("mapFile_chgFileNm");
					String[] fileSize = request.getParameterValues("mapFile_fileSize");
					String[] filePath = request.getParameterValues("mapFile_filePath");
					paramMap.put("fileId", paramMap.get("ATTACH_FILE_ID"));
					for (int i = 0; i < fileNm.length; i++) {
						paramMap.put("fileNm", fileNm[i]);
						paramMap.put("chgFileNm", chgFileNm[i]);
						paramMap.put("filePath", filePath[i]);
						paramMap.put("fileSize", fileSize[i]);
						try {
							flicCommonFileService.insertAtchFile(paramMap);
						} catch (NullPointerException e) {
							logger.error(e.getMessage());
							throw new UserException( e ,"파일 등록중 오류 발생 입니다.");
						} catch (IOException e) {
							logger.error(e.getMessage());
							throw new UserException( e ,"파일 등록중 오류 발생 입니다.");
						} catch (Exception e) {
							logger.error(e.getMessage());
							throw new UserException( e ,"파일 등록중 오류 발생 입니다.");
						}
					}
				}
			}
			break;
		case D:
			break;
		}

		switch (SaveEnum.valueOf(rowStats)) {

		case I:
			if (paramMap.get("TEXT_PASSWORD") != null && !paramMap.get("TEXT_PASSWORD").toString().isEmpty()) {
				//비밀번호 암호화
				paramMap.put("TEXT_PASSWORD", commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
			}
			mapper.insertEduBoardData(paramMap);
			break;
		case U:
			if (paramMap.get("TEXT_PASSWORD") != null && !paramMap.get("TEXT_PASSWORD").toString().isEmpty()) {
				//비밀번호 암호화
				paramMap.put("TEXT_PASSWORD", commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
			}
			mapper.updateEduBoardData(paramMap);
			break;
		case D:
			// TODO: 삭제 로직 필요시 추가
			break;
		}
		return paramMap.get("ORD_NO").toString();
	}

	/**
	 * 교육 게시글 - 비밀번호 확인
	 * 
	 * @param paramMap (EDU_TYPE 포함)
	 * @return String
	 */
	public String selectCheckEduBoardPassword(Map<String, Object> paramMap) {
		paramMap.put("TEXT_PASSWORD", commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
		if (mapper.selectCheckEduBoardPassword(paramMap) == null) {
			throw new UserException("게시글 비밀번호가 일치하지 않습니다.");
		}
		return paramMap.get("ORD_NO").toString();
	}
}
