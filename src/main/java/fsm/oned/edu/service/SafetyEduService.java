package fsm.oned.edu.service;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.util.Globals;
import fsm.oned.edu.mapper.safety.SafetyEduMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * SAFETY 교육 Service
 * 
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 */
@Slf4j
@Service
public class SafetyEduService {
	
	@Autowired
	private SafetyEduMapper safetyMapper;

	/**
	 * SAFETY 교육신청서 - 저장 (교육 종료일: 올해 12월 31일)
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String saveSafetyEduApply(Map<String, Object> paramMap) {
		int result = 0;

		try {
			log.debug("SAFETY 교육신청 시작 - paramMap: {}", paramMap);
			
			// SAFETY 교육신청 등록
			result = safetyMapper.insertSafetyEduApply(paramMap);
			log.debug("SAFETY 교육신청 등록 완료: EDU_APLY_NO={}, result={}", paramMap.get("EDU_APLY_NO"), result);
			
			if (result <= 0) {
				throw new UserException("SAFETY 교육신청 등록에 실패했습니다.");
			}

			// 교육진도 생성
			List<Map<String, Object>> classList = safetyMapper.selectEduClassList(paramMap);
			log.debug("SAFETY 강의 목록 조회 결과: {} 건", classList != null ? classList.size() : 0);
			
			if (classList != null && !classList.isEmpty()) {
				for (Map<String, Object> classMap : classList) {
					log.debug("SAFETY 교육진도 등록 - ORD_NO: {}", classMap.get("ORD_NO"));
					safetyMapper.insertStudyStatus(classMap);
				}
			}

			return paramMap.get("EDU_APLY_NO").toString();
			
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			log.error("SAFETY 교육신청 중 예외 발생", e);
			throw new UserException("SAFETY 교육신청 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

    public String saveEduCertInfo(Map<String, Object> applyMap) {
		safetyMapper.saveEduCertInfo(applyMap);
		return applyMap.get("EDU_CERT_NO").toString();
    }

	//TODO MSG 알맞는거 넣기
	public void sendEduCompleteSms(Map<String, Object> paramMap) {
		//전화번호 있는 경우만 발송
		if (!paramMap.get("EDU_APLY_TEL").equals("") && paramMap.get("EDU_APLY_TEL") != null) {
			String msg = "";

			msg = "이수증 출력이 가능합니다.";
			Map<String, Object> smsMap = new HashMap<>();

			smsMap.put("CALLBACK", Globals.EDU_CALLBACK_NUM);
			smsMap.put("CALLNUM", paramMap.get("EDU_APLY_TEL"));
			smsMap.put("TITLE", "교육 이수 완료");
			smsMap.put("MSG", msg);

			//문자발송
			safetyMapper.insertEduSms(smsMap);
		}
	}

	/**
	 * 교육이수증 - 상세 조회
	 *
	 * @param paramMap
	 * @return Map
	 */
	public Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap) {
		//권한체크
		int checkAuth = safetyMapper.checkEduApplyInfo(paramMap);
		if (checkAuth <= 0) {
			throw new UserException("접근 권한이 없습니다.");
		}
		Map<String, Object> retMap;
		retMap = safetyMapper.selectEduCertInfo(paramMap);

		return retMap;
	}
}



