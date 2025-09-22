package fsm.oned.comm.service;

import java.util.List;
import java.util.Map;

public interface FlicCommonFileService {

 void insertAtchFile(Map<String, Object> map) throws Exception;

 int deleteAtchFile(Map<String, Object> map) throws Exception;

 Map<String, Object> selectAtchFileForDownload(Map<String, Object> map) throws Exception;

 List<Map<String, Object>> selectAtchFile(Map<String, Object> map) throws Exception;

}
