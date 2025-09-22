package fsm.oned.comm.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import fsm.oned.comm.service.PrivacyLogService;
import fsm.oned.comm.util.CommonUtil;
import fsm.oned.comm.util.Globals;
import fsm.oned.comm.util.UserDetailsHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
public class PrivacyLogAspect {
    private static final Logger log = LoggerFactory.getLogger(PrivacyLogAspect.class);

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    PrivacyLogService privacyLogService;

    @Pointcut(value = "execution(* *..*Controller.*(..))")
    public void isContoller() {
    }

    @Pointcut(value = "@annotation(fsm.oned.comm.aop.PrivacyLog)")
    public void isExistPrivacyLogAnnotation() {
    }

    /**
     * 개인정보가 존재하는 요청에 대해 LOG 기록
     */
    @Before(value = "isContoller() && isExistPrivacyLogAnnotation()")
    public void insertPrivacyLog(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        PrivacyLog privacyLog = methodSignature.getMethod().getAnnotation(PrivacyLog.class);

        Map<String, Object> privacyLogParam = new HashMap<>();
        privacyLogParam.put("URL", request.getRequestURI());
        privacyLogParam.put("USER_ID", UserDetailsHelper.getAuthenticatedUser().getOrDefault("USER_ID", ""));
        privacyLogParam.put("IP", commonUtil.getUserAccessIp(request));

        Map<String, String> tempDatas = Arrays.stream(privacyLog.parameterNames())
                .filter(s -> !StringUtils.isEmpty(s))
                .collect(Collectors.toMap(String::toString, s -> request.getParameter(s) == null ? "" : request.getParameter(s)));
        privacyLogParam.put("PARAM", new ObjectMapper().writeValueAsString(tempDatas)); // parameters json string 변환

        //DATA보유 시스템 공통코드 FS_305 활용
        privacyLogParam.put("SCE_SYS_CD", Globals.SCE_SYS_CD);

        privacyLogService.insertPrivacyLog(privacyLogParam);
    }
}
