package fsm.oned.edu.service.plor;

import fsm.oned.edu.service.EduApplyService;
import fsm.oned.edu.service.PlorEduService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlorEduApplyServiceImpl implements EduApplyService {

	private final PlorEduService plorEduService;

	public PlorEduApplyServiceImpl(PlorEduService plorEduService) {
		this.plorEduService = plorEduService;
	}

	@Override
	public String getEduType() {
		return "PLOR";
	}

	@Override
	public String saveEduApply(Map<String, Object> paramMap) {
		return plorEduService.savePlorEduApply(paramMap);
	}

	@Override
	public Map<String, Object> selectEduCertInfo(Map<String, Object> paramMap) {
		return plorEduService.selectEduCertInfo(paramMap);
	}

	@Override
	public String saveEduCertInfo(Map<String, Object> applyMap) {
		return plorEduService.saveEduCertInfo(applyMap);
	}
}



