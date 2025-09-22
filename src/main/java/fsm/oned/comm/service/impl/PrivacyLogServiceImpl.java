package fsm.oned.comm.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import fsm.oned.comm.dao.PrivacyLogDAO;
import fsm.oned.comm.service.PrivacyLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("privacyLogServiceImpl")
public class PrivacyLogServiceImpl extends EgovAbstractServiceImpl implements PrivacyLogService {

    @Resource(name = "privacyLogDAO")
    private PrivacyLogDAO privacyLogDAO;


    @Override
    public void insertPrivacyLog(Map<String, Object> map)  {
        privacyLogDAO.insertPrivacyLog(map);
    }


}
