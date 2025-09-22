package fsm.oned.comm.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import fsm.oned.comm.lpEnum.ComEnum;
import fsm.oned.comm.util.UserDetailsHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class CustomAspect {
    @Value("#{environment.getActiveProfiles()[0]}")
    private String activeProfiles;

    /**
     * 파라메터가 @LpRequestParam 일때만 호출 설정
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* *(.., @fsm.oned.comm.aop.LpRequestParam (java.util.Map), ..))")
    public Object LpRequestParam(ProceedingJoinPoint joinPoint) throws Throwable {

        Map paramMap = new HashMap<String, Object>();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        paramMap.put(ComEnum.sessionUser.name(), UserDetailsHelper.getAuthenticatedUser());
        //paramMap.put("USER_INFO", UserDetailsHelper.getAuthenticatedUser());

        Enumeration penum = request.getParameterNames();
        String key = null;
        String value = null;
        while (penum.hasMoreElements()) {
            key = (String) penum.nextElement();
            value = (new String(request.getParameter(key)) == null) ? "" : new String(request.getParameter(key));
            if (!"".equals(value)) {
                try {
                    //array 데이터를 list 로 변환후 session ROW 마다 담기
                    List list = new JSONArray(value.replace("&quot;", "\"")).toList();

                    if (list.size() > 0 && ("Map".equals(list.get(0).getClass().getSimpleName()) || "HashMap".equals(list.get(0).getClass().getSimpleName()))) {
                        for (int i = 0; i < list.size(); i++) {
                            ((Map) list.get(i)).put(ComEnum.sessionUser.name(), paramMap.get(ComEnum.sessionUser.name()));
                        }
                    }

                    paramMap.put(key, list);

                } catch (JSONException e) {
                    paramMap.put(key, value);
                }
            } else {
                paramMap.put(key, value);
            }
        }

        if (paramMap.containsKey("selectPage")) {
            int selectPage = Integer.parseInt(paramMap.get("selectPage").toString());
            int pageSize = paramMap.get("pageSize") == null ? 10 : Integer.parseInt(paramMap.get("pageSize").toString());
            paramMap.put("startIdx", ((selectPage - 1) * pageSize) + 1);
            paramMap.put("endIdx", ((selectPage - 1) * pageSize) + pageSize);
        }


        if (request instanceof MultipartHttpServletRequest) {
            //Interceptor.java 에서 변환작업이 먼저 실행 되어야함
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            paramMap.put("multipartRequest", multipartRequest);
//            service.setFileUpload(paramMap, multipartRequest);// 파일 등록후 아이디 설정
        }

        //엑셀다운로드 action 이면 페이징 무시
//        if (paramMap.containsKey("excelDownAt")) {
//            if ("Y".equals(paramMap.get("excelDownAt"))) {
//                paramMap.put("startIdx", 1);
//                paramMap.put("endIdx", 9999999);
//            }
//        }

        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        for (int i = 0; i < method.getParameterAnnotations().length; i++) {
            Annotation[] params = method.getParameterAnnotations()[i];
            for (Annotation annotation : params) {
                if (annotation instanceof LpRequestParam) {
                    args[i] = paramMap;
                }
            }
        }

        if ("local".equals(activeProfiles)) {
            MapUtils.debugPrint(System.out, "paramMap", paramMap);
        }

        return joinPoint.proceed(args);
    }
}
