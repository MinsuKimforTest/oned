package fsm.oned.edu.service;

import java.util.Map;

/**
 * 교육신청 저장을 위한 전략 인터페이스
 */
public interface EduApplyService {
	/**
	 * 이 전략이 담당하는 교육 타입을 대문자로 반환합니다. 예) "PLOR", "SAFETY"
	 */
	String getEduType();

	/**
	 * 교육신청 저장을 처리하고 신청번호를 반환합니다.
	 */
	String saveEduApply(Map<String, Object> paramMap);

	Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap);

	String saveEduCertInfo(Map<String, Object> applyMap);
}



