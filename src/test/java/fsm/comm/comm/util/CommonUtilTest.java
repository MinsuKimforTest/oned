package fsm.comm.comm.util;

import fsm.oned.comm.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@ContextConfiguration(locations = {
        "classpath*:egovframework/spring/context-*.xml",
        "file:src/main/webapp/WEB-INF/config/egovframework/springmvc/dispatcher-servlet.xml"
})
public class CommonUtilTest {
    @Autowired
    CommonUtil commonUtil;

    @Test
    public void encrypt() {
        log.debug("encrypt {}" , commonUtil.encrypt("test"));
    }

    @Test
    public void decrypt() {
        String encrypt = commonUtil.encrypt("test");
        log.debug("encrypt {}" , encrypt);
        log.debug("decrypt {}" , commonUtil.decrypt(encrypt));
    }

    @Test
    public void encryptPassword() {
        log.debug("encryptPassword {}" , commonUtil.encryptPassword("test"));
    }
}
