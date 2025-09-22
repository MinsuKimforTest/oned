package fsm.oned.comm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSSFilterRequestWrapper extends HttpServletRequestWrapper{

	public XSSFilterRequestWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XSSFilterRequestWrapper.class);
	
	public String[] getParameterValues(String parameter) {
	String[] values = super.getParameterValues(parameter);
	if(values == null)
		return null;
	for (int i = 0; i < values.length; i++) {
		if(values[i] != null) {
			StringBuffer strBuff = new StringBuffer();
			for(int j = 0; j < values[i].length(); j++) {
				char c = values[i].charAt(j);
				switch (c) {
				case '<':
					strBuff.append("&lt;");
					break;				
				case '>':
					strBuff.append("&gt;");
					break;
				case '&':
					strBuff.append("&amp;");
					break;
				case '"':
					strBuff.append("&quot;");
					break;
				case '\'':
					strBuff.append("&apos;");
					break;
				default:
					strBuff.append(c);
					break;
				}
			}
			try {
				values[i] = CommonUtil.xssFilter(strBuff.toString());
			}catch (NullPointerException e) {
				LOGGER.error("xssFilter처리 후 리턴 실패", e);
				values[i] = strBuff.toString();
			}catch (Exception e) {
				LOGGER.error("xssFilter처리 후 리턴 실패", e);
				values[i] = strBuff.toString();
			}
		} else {
			values[i] = null;
		}
	}
	return values;
	}
	
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null)
			return null;
		try {
			return CommonUtil.xssFilter(value);
		}catch (NullPointerException e) {
			LOGGER.error("xssFilter처리 후 리턴 실패", e);
			return value;
		}catch (Exception e) {
			LOGGER.error("xssFilter처리 후 리턴 실패", e);
			return value;
		}
	}

}
