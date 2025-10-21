package fsm.oned.edu.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 교육신청 전략을 조회하는 팩토리
 */
@Component
public class EduApplyServiceFactory {

	private final Map<String, EduApplyService> serviceMap;

	public EduApplyServiceFactory(List<EduApplyService> services) {
		this.serviceMap = services.stream()
				.collect(Collectors.toMap(
					s -> s.getEduType().trim().toUpperCase(),
					Function.identity()
				));
	}

	public EduApplyService getService(String eduTypeUpper) {
		EduApplyService service = serviceMap.get(eduTypeUpper);
		if (service == null) {
			throw new IllegalArgumentException("지원하지 않는 교육 타입: " + eduTypeUpper);
		}
		return service;
	}

}



