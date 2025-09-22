package fsm.oned.comm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("flicCommonFileDAO")
public class FlicCommonFileDAO extends CommonAbstractDAO {

 public void insertAtchFile(Map<String, Object> map) throws Exception {
     insert("flicCommonFileDAO.insertAtchFile", map);
 }

 public int deleteAtchFile(Map<String, Object> map) throws Exception {
     return delete("flicCommonFileDAO.deleteAtchFile", map);
 }

 @SuppressWarnings("unchecked")
 public Map<String, Object> selectAtchFileForDownload(Map<String, Object> map) throws Exception {
  return (Map<String, Object>)select("flicCommonFileDAO.selectAtchFileForDownload", map);
 }

 @SuppressWarnings("unchecked")
 public List<Map<String, Object>> selectAtchFile(Map<String, Object> map) throws Exception {
  return (List<Map<String, Object>>)list("flicCommonFileDAO.selectAtchFile", map);
 }


}
