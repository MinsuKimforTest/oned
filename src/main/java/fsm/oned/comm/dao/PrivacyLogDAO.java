package fsm.oned.comm.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("privacyLogDAO")
public class PrivacyLogDAO extends CommonAbstractDAO {

    public void insertPrivacyLog(Map<String, Object> map)  {
        insert("privacyLogDAO.insertPrivacyLog", map);
    }

}
