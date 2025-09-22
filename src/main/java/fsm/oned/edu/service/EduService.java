package fsm.oned.edu.service;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.Globals;
import fsm.oned.edu.mapper.EduMapper;
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
 * 교육 Service
 * 
 * @author 라이온 플러스 허은미
 * @since 2021.07.15
 * @version 1.0
 * @see
 *
 *      <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2021.07.15    허은미    최초 생성
 *      </pre>
 */

@Slf4j
@Service
public class EduService {
	@Autowired
	private EduMapper mapper;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private OrganicCertService organicCertService;

	@Resource(name = "flicCommonFileService")
	private FlicCommonFileService flicCommonFileService;

	/**
	 * 교육신청 - 리스트 조회
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduPlanList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduPlanList(paramMap);
		return list;
	}

	/**
	 * 교육신청 - 상세 조회
	 * 
	 * @param paramMap
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
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> selectEduContentsList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduContentsList(paramMap);
		return list;
	}

	/**
	 * 교육신청서 - 상세 조회
	 * 
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduApplyInfo(Map<String, Object> paramMap) {
		Map<String, Object> retMap = new HashMap();
		retMap = mapper.selectEduApplyInfo(paramMap);
		return retMap;
	}

	/**
	 * 교육신청서 - 저장
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String saveEduApply(Map<String, Object> paramMap) {

		//HACCP 등록번호 필수값 아니어서 공백일때 0000
		if (paramMap.get("GBN").equals("2")) {
			if (paramMap.get("CERT_NO").equals("")) {
				paramMap.put("CERT_NO", "0000");
			}
			//교육신청
			mapper.insertEduHaccpApply(paramMap);
		} else {
			//교육신청
			mapper.insertEduApply(paramMap);
		}

		//교육진도 생성
		List<Map<String, Object>> list = mapper.selectEduClassList(paramMap);
		for (Map map : list) {
			mapper.insertStudyStatus(map);
		}

		return paramMap.get("EDU_APLY_NO").toString();
	}

	/**
	 * 교육 신청자 담당업무 - 사용자 정의 코드
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduApplyWorkOptions(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduApplyWorkOptions(paramMap);
		return list;
	}

	/**
	 * 나의 강의실 - 학습상태 조회
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectStudyStatusList(Map<String, Object> paramMap) {
		//연도가 없는 경우 현재 연도
		if (paramMap.get("EDU_YEAR") == null || paramMap.get("EDU_YEAR").equals("")) {
			Calendar cal = Calendar.getInstance();
			paramMap.put("EDU_YEAR", cal.get(Calendar.YEAR));
		}
		List<Map<String, Object>> list = mapper.selectStudyStatusList(paramMap);
		return list;
	}

	/**
	 * 나의 강의실 - 리스트 조회
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduClassRoomList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduClassRoomList(paramMap);
		return list;
	}

	/**
	 * 나의 강의실 - 상세 조회
	 * 
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduClassRoomDetail(Map<String, Object> paramMap) {
		Map<String, Object> retMap = mapper.selectEduClassRoomDetail(paramMap);
		return retMap;
	}

	/**
	 * 강의 - 리스트 조회
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduClassStatusList(Map<String, Object> paramMap) {
		//권한체크
		int checkAuth = mapper.checkEduApplyInfo(paramMap);
		if (checkAuth <= 0) {
			throw new UserException("접근 권한이 없습니다.");
		}
		List<Map<String, Object>> list = mapper.selectEduClassStatusList(paramMap);
		return list;
	}

	/**
	 * 강의 - 상세 조회
	 * 
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduClassStatusData(Map<String, Object> paramMap) {
		Map<String, Object> retMap = mapper.selectEduClassStatusData(paramMap);
		return retMap;
	}

	/**
	 * 강의 학습하기 - 진도 체크
	 * 
	 * @param paramMap
	 */
	public void saveStudyTime(Map<String, Object> paramMap) {
		mapper.saveStudyTime(paramMap);
	}

	/**
	 * 교육이수증 - 상세 조회
	 * 
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap) {
		//권한체크
		int checkAuth = mapper.checkEduApplyInfo(paramMap);
		if (checkAuth <= 0) {
			throw new UserException("접근 권한이 없습니다.");
		}
		Map<String, Object> retMap;
		if (paramMap.get("GBN").equals("1")) {
			retMap = mapper.selectEduCertInfo(paramMap);
		} else {
			retMap = mapper.selectEduCertHaccpInfo(paramMap);
		}

		return retMap;
	}

	/**
	 * 교육 이수증 - 발급
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String saveEduCertInfo(Map<String, Object> paramMap) {
		mapper.saveEduCertInfo(paramMap);
		return paramMap.get("EDU_CERT_NO").toString();
	}

	/**
	 * 교육 이수 완료 - 자동 문자 발송
	 */
	public void sendEduCompleteSms(Map<String, Object> paramMap) {

		//전화번호 있는 경우만 발송
		if (!paramMap.get("EDU_APLY_TEL").equals("") && paramMap.get("EDU_APLY_TEL") != null) {
			String msg = "";
			if (paramMap.get("GBN").equals("1")) {
				msg = "친환경수산물 인증사업자 교육이수가 완료되어 이수증 출력이 가능합니다.";
			} else {
				msg = "HACCP 교육이수가 완료되어 이수증 출력이 가능합니다.";
			}
			Map<String, Object> smsMap = new HashMap<>();

			smsMap.put("CALLBACK", Globals.EDU_CALLBACK_NUM);
			smsMap.put("CALLNUM", paramMap.get("EDU_APLY_TEL"));
			smsMap.put("TITLE", "교육 이수 완료");
			smsMap.put("MSG", msg);

			//문자발송
			mapper.insertEduSms(smsMap);
		}
	}

	/**
	 * 교육 참여현황 - 리스트 조회
	 * 
	 * @param paramMap
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
	 * 교육 참여현황 - 리스트 조회
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduStatusHaccpList(Map<String, Object> paramMap) {
		//인증 관련 사용자 정보

		List<Map<String, Object>> list = mapper.selectEduStatusHaccpList(paramMap);
		return list;
	}

	/**
	 * 교육 게시판 관리 - 상세 조회
	 * 
	 * @param paramMap
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
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduBoardList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list;
		if ("3".equals(paramMap.get("EDU_BOARD_TYPE"))) { //답변형게시판
			list = mapper.selectEduBoardAnswerList(paramMap);
		} else {
			list = mapper.selectEduBoardList(paramMap);
		}
		return list;
	}

	/**
	 * 게시글 - 상세 조회
	 * 
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduBoardDetail(Map<String, Object> paramMap) {
		//조회수 count
		mapper.increaseEduBoardLockupCnt(paramMap);

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
	 * @param paramMap
	 * @return String
	 */
	public String saveEduBoardData(Map<String, Object> paramMap, HttpServletRequest request) {
		Logger logger = null;
		String rowStats = paramMap.get("rowStats").toString();

		switch (SaveEnum.valueOf(rowStats)) {
		case I:
		case U:
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
			mapper.deleteEduBoardData(paramMap);
			break;
		}
		return paramMap.get("ORD_NO").toString();
	}

	/**
	 * 교육 게시글 - 비밀번호 확인
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String selectCheckEduBoardPassword(Map<String, Object> paramMap) {
		paramMap.put("TEXT_PASSWORD", commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
		if (mapper.selectCheckEduBoardPassword(paramMap) == null) {
			throw new UserException("게시글 비밀번호가 일치하지 않습니다.");
		}
		return paramMap.get("ORD_NO").toString();
	}
	
	/**
	 * 강의 학습하기 - 학습시간 30초 체크
	 * 
	 * @param paramMap
	 * @return Boolean
	 */
	public boolean checkStudyTime(Map<String, Object> paramMap) {
		Map<String, Object> retMap = mapper.checkStudyTime(paramMap);

		if ((Double.parseDouble(String.valueOf(paramMap.get("STUDY_TIME"))) - Double.parseDouble(String.valueOf(retMap.get("STUDY_TIME")))) > 32) {
			return false;
		}
		if (retMap.get("STUDY_CHECKTIME") != "" || retMap.get("STUDY_CHECKTIME") != null) {
			if ((new Date().getTime() - Long.parseLong(String.valueOf(retMap.get("STUDY_CHECKTIME")))) < 29000) {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * 강의 학습하기 - 진도율 체크
	 * 
	 * @param paramMap
	 * @return Boolean
	 */
	public double checkStudyRate(Map<String, Object> paramMap) {
		double checkRate = mapper.checkStudyRate(paramMap);
		return checkRate;
	}

	/**
	 * HACCP 교육 신청자 담당업무 - 사용자 정의 코드
	 * 
	 * @param paramMap
	 * @return List
	 */
	public List<Map<String, Object>> selectEduApplyHaccpWorkOptions(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduApplyHaccpWorkOptions(paramMap);
		return list;
	}

	public List<Map<String, Object>> selectEduPlanHaccpList(Map<String, Object> paramMap) {
		List<Map<String, Object>> list = mapper.selectEduPlanHaccpList(paramMap);
		return list;
	}

	public String saveEduCertHaccpInfo(Map<String, Object> paramMap) {
		mapper.saveEduCertHaccpInfo(paramMap);
		return paramMap.get("EDU_CERT_NO").toString();
	}

	public int duplicateCheck(Map<String, Object> paramMap) {
		return mapper.duplicateCheck(paramMap);
	}
}
