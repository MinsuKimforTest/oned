package fsm.oned.comm.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"rawtypes","unused","unchecked"})
@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    private Map xHm = new HashMap(); // 리턴 결과값
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOGGER.error(ex.getMessage(),ex);
        return null;
    }

    /**
     * 사용자 정의 Exception
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws IOException
     */
    @ExceptionHandler(UserException.class)
    @ResponseBody
    //Todo 보안취약점 처리했음 원래 public이었음
    private Object handleUserException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {       // Ajax 처리
            xHm.put("result", "Fail");
            xHm.put("resultMsg", ex.getMessage());

            return new ResponseEntity<>(xHm, HttpStatus.BAD_REQUEST);
        }else{
            request.setAttribute("resultMsg", ex.getMessage());
            String resultMsg1 = request.getAttribute("resultMsg").toString();
            resultMsg1=resultMsg1.replaceAll("<","");
            resultMsg1=resultMsg1.replaceAll(">","");
            HashMap tmpMap = new HashMap();
            tmpMap.put("resultMsg", resultMsg1);
            HashMap msgMap = (HashMap)tmpMap.clone();
            String resultMsg =tmpMap.get("resultMsg").toString();

            boolean isPop = false;
            if("P:".equals(resultMsg.substring(0, 2))) {
                //throw new UserException("P:비밀번호가 올바르지 않습니다."); 팝업으로 처리 창을 닫고 싶을때
                isPop = true;
                resultMsg = resultMsg.substring(2);
            }

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script language=\"javascript\">");
            out.println("alert('"+resultMsg+"');");
            if(isPop) {
                out.println("window.close();");
            }else {
                out.println("history.back();");
            }
            out.println("</script>");
            out.close();
            return false;
        }
    }
}





