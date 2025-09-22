package fsm.oned.edu.service;

import fsm.oned.edu.mapper.EduMapperExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 교육 Service (확장/백업용)
 * - 기존 EduService는 그대로 두고, GBN/EDU_BOARD_ID 파라미터 버전 제공
 */
@Service
public class EduServiceExt {

    @Autowired
    private EduMapperExt mapperExt;

    public List<Map<String, Object>> selectEduPlanListByGbn(Map<String, Object> paramMap) {
        return mapperExt.selectEduPlanListByGbn(paramMap);
    }

    public Map<String, Object> selectEduPlanDetail(Map<String, Object> paramMap) {
        return mapperExt.selectEduPlanDetail(paramMap);
    }

    public List<Map<String, Object>> selectEduContentsListByBoard(Map<String, Object> paramMap) {
        return mapperExt.selectEduContentsListByBoard(paramMap);
    }

    public Map<String, Object> selectEduClassStatusDataByBoard(Map<String, Object> paramMap) {
        return mapperExt.selectEduClassStatusDataByBoard(paramMap);
    }
}


