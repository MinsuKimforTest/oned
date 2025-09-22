package fsm.oned.sample.service;

import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.sample.mapper.SampleMapper;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import java.util.*;
import java.io.*;
import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * 샘플 Service
 * @author 라이온 플러스 남상재
 * @since 2020.06.30
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2020.06.30    남상재    최초 생성
 *
 * </pre>
 */
@Service
public class SampleService {
    @Autowired
    private SampleMapper mapper;

    /**
     * 샘플 - 리스트 조회
     * @param paramMap
     * @return List
     */
    public List<Map<String,Object>> selectSampleList(Map<String,Object> paramMap) {
        List<Map<String,Object>> list = mapper.selectSampleList(paramMap);
        return list;
    }

    /**
     * 샘플 - 상세 조회
     * @param paramMap
     * @return Map
     */
    public Map<String, Object> selectSampleDetail(Map<String,Object> paramMap) {
        Map<String,Object> retMap = mapper.selectSampleDetail(paramMap);
        return retMap;
    }

    /**
     * 샘플 - 저장
     * @param paramMap
     */
    public void multiSample(Map<String,Object> paramMap) {
        List<Map<String,Object>> rowList = (List<Map<String,Object>>) paramMap.get("rowList");
        if(rowList == null) return;

        for (Map<String,Object> rowMap : rowList) {
            String rowStats = rowMap.get("rowStats").toString();
            switch(SaveEnum.valueOf(rowStats)){
                case I:
                    mapper.insertSample(rowMap);
                    break;
                case U:
                    mapper.updateSample(rowMap);
                    break;
                case D:
                    mapper.deleteSample(rowMap);
                    break;
            }
        }
    }
}
