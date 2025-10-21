package fsm.oned.edu.service;

import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.util.Globals;
import fsm.oned.edu.mapper.plor.PlorEduMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * PLOR 교육 Service
 * 
 * @author 라이온 플러스
 * @since 2025.01.15
 * @version 1.0
 */
@Slf4j
@Service
public class PlorEduService {
	
	@Autowired
	private PlorEduMapper plorMapper;

	/**
	 * PLOR 교육신청서 - 저장 (교육 종료일: 2개월 후)
	 * 
	 * @param paramMap
	 * @return String
	 */
	public String savePlorEduApply(Map<String, Object> paramMap) {
		int result = 0;

		try {
			log.debug("PLOR 교육신청 시작 - paramMap: {}", paramMap);
			
			// PLOR 교육신청 등록
			result = plorMapper.insertPlorEduApply(paramMap);
			log.debug("PLOR 교육신청 등록 완료: EDU_APLY_NO={}, result={}", paramMap.get("EDU_APLY_NO"), result);
			
			if (result <= 0) {
				throw new UserException("PLOR 교육신청 등록에 실패했습니다.");
			}

			// 교육진도 생성
			List<Map<String, Object>> classList = plorMapper.selectEduClassList(paramMap);
			log.debug("PLOR 강의 목록 조회 결과: {} 건", classList != null ? classList.size() : 0);
			
			if (classList != null && !classList.isEmpty()) {
				for (Map<String, Object> classMap : classList) {
					log.debug("PLOR 교육진도 등록 - ORD_NO: {}", classMap.get("ORD_NO"));
					plorMapper.insertStudyStatus(classMap);
				}
			}

			return paramMap.get("EDU_APLY_NO").toString();
			
		} catch (UserException e) {
			throw e;
		} catch (Exception e) {
			log.error("PLOR 교육신청 중 예외 발생", e);
			throw new UserException("PLOR 교육신청 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

    public String saveEduCertInfo(Map<String, Object> applyMap) {
		plorMapper.saveEduCertInfo(applyMap);
		return applyMap.get("EDU_CERT_NO").toString();
    }


	//TODO MSG 알맞는거 넣기
	public void sendEduCompleteSms(Map<String, Object> paramMap) {
		//전화번호 있는 경우만 발송
		if (!paramMap.get("EDU_APLY_TEL").equals("") && paramMap.get("EDU_APLY_TEL") != null) {
			String msg = "";

			msg = "교육이수가 완료되어 이수증 출력이 가능합니다.";
			Map<String, Object> smsMap = new HashMap<>();

			smsMap.put("CALLBACK", Globals.EDU_CALLBACK_NUM);
			smsMap.put("CALLNUM", paramMap.get("EDU_APLY_TEL"));
			smsMap.put("TITLE", "교육 이수 완료");
			smsMap.put("MSG", msg);

			//문자발송
			plorMapper.insertEduSms(smsMap);
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
		int checkAuth = plorMapper.checkEduApplyInfo(paramMap);
		if (checkAuth <= 0) {
			throw new UserException("접근 권한이 없습니다.");
		}
		Map<String, Object> retMap;
		retMap = plorMapper.selectEduCertInfo(paramMap);

		return retMap;
	}
}



