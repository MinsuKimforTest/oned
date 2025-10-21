package fsm.oned.edu.service.safety;

import fsm.oned.edu.service.EduApplyService;
import fsm.oned.edu.service.SafetyEduService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SafetyEduApplyServiceImpl implements EduApplyService {

	private final SafetyEduService safetyEduService;

	public SafetyEduApplyServiceImpl(SafetyEduService safetyEduService) {
		this.safetyEduService = safetyEduService;
	}

	@Override
	public String getEduType() {
		return "SAFETY";
	}

	@Override
	public String saveEduApply(Map<String, Object> paramMap) {
		return safetyEduService.saveSafetyEduApply(paramMap);
	}

	@Override
	public Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap) {
		return safetyEduService.selectEduCertInfo(paramMap);
	}

	@Override
	public String saveEduCertInfo(Map<String, Object> applyMap) {
		safetyEduService.saveEduCertInfo(applyMap);
		return applyMap.get("EDU_CERT_NO").toString();
	}
}



