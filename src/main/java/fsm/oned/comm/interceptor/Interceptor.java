package fsm.oned.comm.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.ComEnum;
import fsm.oned.comm.mapper.CommonMapper;
import fsm.oned.comm.service.CommonService;
import fsm.oned.comm.util.Globals;
import fsm.oned.comm.util.UserDetailsHelper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

@Slf4j
@SuppressWarnings({"unchecked","rawtypes"})
public class Interceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommonService commonService ;

    @Autowired
    ServletContext servletContext;

    @Value("#{environment.getActiveProfiles()[0]}")
    private String activeProfiles;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String checkurl = request.getRequestURL().toString();
//        if(checkurl.startsWith("http://") && checkurl.indexOf("localhost") <0 && checkurl.indexOf("202.158.150.92:35080") <0){
//            checkurl = checkurl.replace("http://", "https://");
//            response.sendRedirect(checkurl);
//        }

        //CustomAspect.java 에서 @LpRequestParam 설정시 멀티파트 form 데이터를 세팅하기 위해서 변환
        if (request instanceof MultipartHttpServletRequest) {
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        }

        HandlerMethod method = (HandlerMethod)handler;
        LpAuthCheck auth = method.getMethodAnnotation(LpAuthCheck.class);

        if(auth != null && auth.isLoginChk()){
            //세션가져오기
            Map<String, Object> USER_INFO = UserDetailsHelper.getAuthenticatedUser();

            //로그인 페이지 이동
            if(USER_INFO == null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                try { if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && request.getHeader("X-Requested-With") != null ) {       // Ajax 처리
	                      response.setStatus(9999);
	                      response.setContentType("text/html; charset=UTF-8");
	                      out.println("로그인 정보가 없습니다");
	                  }else{
	                      out.println("<script language=\"javascript\">");
	                      out.println("alert('로그인 정보가 없습니다');");
	                      out.println("top.location.href='"+request.getContextPath()+"/main/actionMain.do'");
	                      out.println("</script>");
	                  }
	                  out.close();
	                  return false;
                } catch (NullPointerException e) {
                	throw new UserException(e, "관리자에게 문의 바랍니다.");
                }
            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {       // Ajax 처리
            HandlerMethod method = (HandlerMethod)handler;
            //리턴 타입 void 일때는 빈 JSON 오브젝트 리턴 처리
            if("void".equals(method.getMethod().getReturnType().getSimpleName())){
                response.setContentType("application/json;charset=UTF-8");
                JSONObject voidRes = new JSONObject();
                response.getWriter().print(voidRes);
            }
        }

        if(mav != null){

            //전체메뉴
            HashMap<String, Object> map = new HashMap<>();
            map.put("INTGR_MN_GR", "true");
            map.put("SCE_SYS_CD", Globals.SCE_SYS_CD);
            List<Map<String, String>> menuList = commonService.selectUserMenuList(map);
            mav.addObject("menuList", menuList);

            //현재 선택 메뉴
            String url = request.getRequestURI().replaceFirst(request.getContextPath(), "");
            String menuId = request.getParameter("MENU_ID");
            Map<String, Object> currentMemu = new HashMap<>();

            for(Map menuData : menuList) {
                if(menuId != null){
                    if(menuData.get("MENU_ID").equals(menuId)){
                        currentMemu.put("CURRENT_MENU_ID", menuData.get("MENU_ID"));
                        currentMemu.put("CURRENT_MENU_NM", menuData.get("MENU_NM"));
                        currentMemu.put("CURRENT_MENU_URL", menuData.get("URL"));
                        currentMemu.put("CURRENT_UPPER_MENU_ID", menuData.get("UPPER_MENU_ID"));
                    }
                }else{
                    if(menuData.get("URL").equals(url)){
                        currentMemu.put("CURRENT_MENU_ID", menuData.get("MENU_ID"));
                        currentMemu.put("CURRENT_MENU_NM", menuData.get("MENU_NM"));
                        currentMemu.put("CURRENT_MENU_URL", menuData.get("URL"));
                        currentMemu.put("CURRENT_UPPER_MENU_ID", menuData.get("UPPER_MENU_ID"));
                    }else{
                        //참조 url
                        List<Map> refUrlList = commonService.selectUserMenuRefUrlList(menuData);

                        for(Map refData : refUrlList) {
                            if(refData.get("REF_URL").equals(url)){
                                currentMemu.put("CURRENT_MENU_ID", menuData.get("MENU_ID"));
                                currentMemu.put("CURRENT_MENU_NM", menuData.get("MENU_NM"));
                                currentMemu.put("CURRENT_MENU_URL", menuData.get("URL"));
                                currentMemu.put("CURRENT_UPPER_MENU_ID", menuData.get("UPPER_MENU_ID"));
                            }
                        }
                    }
                }
            }

            mav.addObject("currentMemu", currentMemu);

            //메뉴 접속 로그 등록
            commonService.insertTcMenuAccessStat(currentMemu);
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}

