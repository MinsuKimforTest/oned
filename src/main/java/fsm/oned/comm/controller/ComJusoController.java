package fsm.oned.comm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.fdl.property.EgovPropertyService;

@Controller
public class ComJusoController {
	
	/** 로그설정 */	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComJusoController.class);
	
	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 주소 팝업 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("comm/getAddrApi.do")
	public ModelAndView getAddrApi(ModelMap model, @RequestParam Map<String,Object> map) throws Exception {		
		ModelAndView mav = new ModelAndView();
		try {
			String xmlStr = executeJusoApi(map);
			mav.addObject("returnXml", xmlStr);
		}catch(NullPointerException e){
			LOGGER.error(e.getMessage(),e);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	public String executeJusoApi(Map<String, Object> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		try{
			String targetAddr 	= URLEncoder.encode((String) map.get("keyword"),"UTF-8") ;
			
			String confmKey 	= (String) map.get("confmKey"); // 주소 api 키 넣기;
			String currentPage 	= (String) map.get("currentPage"); // 필수라서 넣음
			String countPerPage = (String) map.get("countPerPage"); // 필수라서 넣음
			
			String apiUrl  = (String) map.get("domain") +"/addrlink/addrLinkApi.do?currentPage="+currentPage
					+"&countPerPage="+countPerPage+"&keyword="+targetAddr+"&confmKey="+confmKey+"&resultType=xml";
			
			URL 			url 	= new URL(apiUrl);
			HttpURLConnection conn  = (HttpURLConnection)url.openConnection();
	    	BufferedReader 	br 		= new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	    	
	    	String line;
	    	while((line = br.readLine()) != null){
	    		sb.append(line.replace("<![CDATA[", "").replace("]]>", ""));
	    	} //-- while end
	    	br.close();
	    	
		}catch(IOException e){
			LOGGER.error("관리자에게 문의바랍니다.", e);
		}catch(Exception e){
			LOGGER.error("관리자에게 문의바랍니다.", e);
		}
		return sb.toString();
	}
	
	/**
	 * 좌표제공 API
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("comm/getAddrCoordApi.do")
	public ModelAndView getAddrCoordApi(ModelMap model, @RequestParam Map<String,Object> map) throws Exception {		
		ModelAndView mav = new ModelAndView();
		try {
			JSONObject xmlStr = executeCoordApi(map);
			mav.addObject("results", xmlStr);
		}catch(NullPointerException e){
			LOGGER.error(e.getMessage(),e);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		
		mav.setViewName("jsonView");
		return mav;
	}
	
	public JSONObject executeCoordApi(Map<String, Object> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		JSONParser jsonParser 		= new JSONParser();
		JSONObject jsonObj = null;
		try{
			// 요청변수 설정
	    	String admCd = (String) map.get("admCd");
			String rnMgtSn = (String) map.get("rnMgtSn");
			String udrtYn = (String) map.get("udrtYn");
			String buldMnnm = (String) map.get("buldMnnm");
			String buldSlno = (String) map.get("buldSlno");
			String confmKey 	= (String) map.get("confmKey");
			
			String apiUrl  = (String) map.get("domain") +"/addrlink/addrCoordApi.do?admCd="+admCd+"&rnMgtSn="+rnMgtSn+"&udrtYn="+udrtYn+"&buldMnnm="+buldMnnm+"&buldSlno="+buldSlno+"&confmKey="+confmKey+"&resultType=json";
			URL 			url 	= new URL(apiUrl);
			HttpURLConnection conn  = (HttpURLConnection)url.openConnection();
	    	BufferedReader 	br 		= new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	    	
	    	String line;
	    	while((line = br.readLine()) != null){
	    		sb.append(line);
	    		jsonObj = (JSONObject) jsonParser.parse(sb.toString());
	    	} //-- while end
	    	br.close();
		}catch(IOException e){
			LOGGER.error(e.getMessage(),e);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
		return jsonObj;
	}
}
