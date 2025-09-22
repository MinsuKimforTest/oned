package fsm.oned.comm.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import fsm.oned.comm.exception.UserException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class CommonUtil {
    @Autowired
    EgovEnvCryptoService cryptoService;

    @Autowired
    EgovPasswordEncoder egovPasswordEncoder;


    /**
     * SHA256 암호화 리턴
     *
     * @param planText
     * @return
     */
    public String getSha256(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes());
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 사용자 IP
     *
     * @param request
     * @return
     */
    public String getUserAccessIp(HttpServletRequest request) {

        String ip = request.getHeader("Proxy-Client-IP");
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip == null) {
                ip = request.getHeader("X-Forwarded-For");  //클라이언트 ip 주소 가져오기
                if (ip == null) {
                    ip = request.getRemoteAddr();
                }
            }
        }

        return ip;
    }

    /**
     * 암호화
     *
     * @param encrypt
     */
    public String encrypt(String encrypt) {
        return cryptoService.encryptNone(encrypt); // Does not handle URLEncoding.
    }

    /**
     * 복호화
     *
     * @param decrypt
     */
    public String decrypt(String decrypt){
        return cryptoService.decryptNone(decrypt); // Does not handle URLDecoding.

    }

    /**
     * 패스워드 단반향 암호화
     * @param password
     * @return
     */
    public String encryptPassword(String password) {
        return egovPasswordEncoder.encryptPassword(password);
    }

    /**
     * escape 처리
     */
    public String getEscape(String str){
     str = str.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&").replace("&quot;", "\"");
        return str;
    }

     /**
     * getXssCheck
     */
    public static String getXssCheck(String value){

        try {
            value = value.replace("onerror", "");
            value = value.replace("prompt", "");
            value =  value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
            value =  value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
            value =  value.replaceAll("'", "&#39;");
            value =  value.replaceAll("eval\\((.*)\\)", "");
            value =  value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value =  value.replaceAll("script", "");
            value =  value.replaceAll("SCRIPT", "");
        } catch (Exception e) {
            System.out.println(e);
        }

        return value;
    }
    
     /**
     * xssFilter
     */
     public static String xssFilter(String value) {
		if (value.equals("")) {
			return "";
		}

		String filstr = "<script>,javascript,%3Cscript,JaVaScRiPt,ScRiPt%20%0a%0d,ja%0Av%0Aa%0As%0Ac%0Aript,script,vbscript,"
				+ "binding,allowscriptaccess,expression,applet,meta,xml,blink,link,style,embed,object,iframe,frame,frameset,"
				+ "background,layer,ilayer,bgsound,title,base,eval,innerHTML,charset,refresh,string,void,create,append,%3Ealert,alert,"
				+ "msgbox,document,cookie,href,onabort,@import,+ADw,+AD4,aim:,%0da=eval,xmlns:html,http-equiv=refresh,"
				+ "xmlns:html=,list-style-image,x-scriptlet,echo(,0%0d%0a%00,moz-binding,res://,"
				+ "#exec,%u0,&#x,fromcharcode,firefoxurl,<br size=,wvs-xss,acunetix_wvs,"
				+ "lowsrc,dynsrc,behavior,activexobject,microsoft.xmlhttp,clsid:cafeefac-dec7-0000-0000-abcdeffedcba,"
				+ "application/npruntime-scriptable-plugin,deploymenttoolkit,onactive,onafterprint,onafterupdate,"
				+ "onbefore,onbeforeactivate,onbeforecopy,onbeforecut,onbeforedeactivate,onbeforeeditfocus,onbeforepaste,"
				+ "onbeforeprint,onbeforeunload,onbeforeupdate,onblur,onbounce,oncellchange,onchange,onclick,oncontextmenu,"
				+ "oncontrolselect,oncopy,oncut,ondataavailable,ondatasetchanged,ondatasetcomplete,ondblclick,ondeactivate,"
				+ "ondrag,ondragend,ondragenter,ondragleave,ondragover,ondragstart,ondrop,onerror,onerrorupdate,onfilterchange,"
				+ "onfinish,onfocus,onfocusin,onfocusout,onhelp,onkeydown,onkeypress,onkeyup,onlayoutcomplete,"
				+ "onload,onlosecapture,onmousedown,onmouseenter,onmouseleave,onmousemove,onmouseout,onmouseover," 
				+ "onmouseup,onmousewheel,onmove,onmoveend,onmovestart,onpaste,onpropertychange,onreadystatechange," 
				+ "onreset,onresize,onresizeend,onresizestart,onrowenter,onrowexit,onrowsdelete,onrowsinserted,onscroll,"
				+ "onselect,onselectionchange,onselectstart,onstart,onstop,onsubmit,onunload,onMessage,onRowDelete,;//,"
				+ "onOffline,onRowInserted,FSCommand,onOnline,onSeek,onAbort,onOutOfSync,onStorage,onActivate,onPause,"
				+ "onSyncRestored,onBegin,onPopState,onTimeError,onDragDrop,onProgress,onTrackChange,onEnd,onRedo,onUndo,"
				+ "onHashChange,onRepeat,onURLFlip,onInput,onResume,seekSegmentTime,onMediaComplete,onReverse,"
				+ "onRowsEnter,onMediaError,onwheel,onpageshow,oncanplaythrough,onloadedmetadata,"
				+ "onplay,onseeking,ontimeupdate,onemptied,audio,onsearch,oninvalid,oncuechange,onloadstart,onplaying,"
				+ "onstalled,onvolumechange,ontoggle,details,onpagehide,oncanplay,ondurationchange,onseeked,onratechange," 
				+ "onsuspend,onwaiting,img,video,prompt";

		filstr = filstr.replaceAll(" ", "");
		if (!value.equals("")) {
			String [] st = filstr.split(",");
			for( int x = 0; x < st.length; x++ ) {
				while(value.toLowerCase().indexOf(st[x].toLowerCase()) > -1) {
					int xssIdx = value.toLowerCase().indexOf(st[x].toLowerCase());
					//value = value.replaceAll(st[x], "");
					value = value.substring(0, xssIdx) + value.substring(xssIdx + st[x].length());
				}
			}
		}
		return clearXss(value);
	}

    /**
     * clearXss
     */
	public static String clearXss(String str) {
		String avatag = "p,br";
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str =  str.replaceAll("'", "&#39;");
		str =  str.replaceAll("\\(", "&#40;");
		str =  str.replaceAll("\\)", "&#41;");
		str = str.replaceAll("\0", " ");
		if (!avatag.equals("")) {
			avatag.replaceAll(" ", "");
			String st[] = avatag.split(",");
			for (int i = 0; i < st.length; i++) {
				str = str.replaceAll("&lt;" + st[i] + " ", "<" + st[i] + " ");
				str = str.replaceAll("&lt;" + st[i] + "&gt;", "<" + st[i] + ">");
				str = str.replaceAll(" " + st[i] + "&gt;", " " + st[i] + ">");
				str = str.replaceAll(st[i] + "/&gt;", st[i] + "/>");
				str = str.replaceAll("&lt;/" + st[i], "</" + st[i]);
			}
		}
		return str;
	}
	
	/**
     * clearXSSMap
     */
	public static Map<String,Object> clearXSSMap(Map<String,Object> map) {
    	Map<String,Object> rtn = new HashMap<>();
    	map.forEach((key, value) -> {
    		if(value instanceof String[]) {
    			String[] clearedValue = new String[((String[])value).length];
    			for (int i = 0; i < ((String[])value).length; i++) {
					clearedValue[i] = xssFilter(((String[])value)[i]);
				}
	    		rtn.put(key, clearedValue);
    		} else if(value instanceof String) {
    			rtn.put(key, xssFilter((String) value));
    		} else if(value instanceof java.util.ArrayList) {
    			if(((java.util.ArrayList) value).get(0) instanceof String) {
    				ArrayList<String> strArr = new ArrayList<String>();
    				for (int i = 0; i < ((java.util.ArrayList) value).size(); i++) {
						strArr.add(xssFilter((String) ((java.util.ArrayList) value).get(i)));
					}
    				rtn.put(key, strArr);
    			}
    		} else {
    			rtn.put(key, value);
    		}
    	});
    	
    	return rtn;
    }
}