package fsm.oned.comm.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import fsm.oned.comm.dao.FlicCommonFileDAO;
import fsm.oned.comm.service.FlicCommonFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("flicCommonFileService")
public class FlicCommonFileServiceImpl extends EgovAbstractServiceImpl implements FlicCommonFileService {

 @Resource(name = "flicCommonFileDAO")
 private FlicCommonFileDAO flicCommonFileDAO;


 @Override
    public void insertAtchFile(Map<String, Object> map) throws Exception {
     map.put("fileNm", map.get("fileNm").toString().replace("<", "&lt;").replace(">", "&gt;"));
   flicCommonFileDAO.insertAtchFile(map);
 }

 @Override
    public int deleteAtchFile(Map<String, Object> map) throws Exception {
     return flicCommonFileDAO.deleteAtchFile(map);
 }

 @Override
    public Map<String, Object> selectAtchFileForDownload(Map<String, Object> map) throws Exception {
  return flicCommonFileDAO.selectAtchFileForDownload(map);
 }

 @Override
    public List<Map<String, Object>> selectAtchFile(Map<String, Object> map) throws Exception {
  return flicCommonFileDAO.selectAtchFile(map);
 }



}
