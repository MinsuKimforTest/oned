package fsm.oned.comm.controller;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import fsm.oned.comm.aop.LpAuthCheck;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.EgovProperties;
import fsm.oned.comm.util.EgovWebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class FlicCommonFileController {

    /**
     * 로그설정
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FlicCommonFileController.class);

    @Resource(name = "egovFileIdGnrService")
    private EgovIdGnrService idgenService;

    @Resource(name = "flicCommonFileService")
    private FlicCommonFileService flicCommonFileService;

    /**
     * 파일 업로드
     *
     * @param
     * @return "jsonView"
     * @throws Exception
     */
    @RequestMapping("comm/fileUpload.do")
    @LpAuthCheck(isAuthChk = false)
    public ModelAndView fileUpload(final MultipartHttpServletRequest multiRequest) throws Exception {
        ModelAndView mav = new ModelAndView();

        Iterator<String> itr = multiRequest.getFileNames();

        //globals.properties 파일에 정의된 파일 업로드 Path
        String filePath = EgovProperties.getProperty("commonFilePath");

        //System.out.println(filePath);

        File fpDir = new File(filePath);

        if (!fpDir.exists())
            fpDir.mkdirs();


        while (itr.hasNext()) { //받은 파일들을 모두 돌린다.
            try {

                MultipartFile mpf = multiRequest.getFile(itr.next());
                // 보안취약점 적용
                //String originFileNm = mpf.getOriginalFilename(); //파일명
                String originFileNm = EgovWebUtil.filePathBlackList(mpf.getOriginalFilename()); //파일명
                String fileExt = originFileNm.substring(originFileNm.lastIndexOf(".") + 1);
                String chgFileNm = "FLIC_" + System.currentTimeMillis() + "." + fileExt;
                long fileSize = mpf.getSize();

                //확장자 체크
                String[] validExt = {"hwp", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "png", "pdf", "jpg", "jpeg", "gif", "zip" };
                boolean checkExt = Arrays.asList(validExt).contains(fileExt);

                if(checkExt){
                    String saveFilePath = filePath + chgFileNm; //파일 전체 경로
                    mpf.transferTo(new File(saveFilePath)); //파일저장 실제로는 service에서 처리
                    mav.addObject("originFileNm", originFileNm);
                    mav.addObject("chgFileNm", chgFileNm);
                    mav.addObject("filePath", filePath);
                    mav.addObject("fileSize", fileSize);
                }else{
                    mav.addObject("error", "Y");
                    throw new IOException("등록 할 수 없는 파일입니다.");
                }

            } catch (IllegalStateException ie) {
                LOGGER.error("[IllegalStateException] Try/Catch...mpf.transferTo : " + ie.getMessage());
            } catch (IOException e) {
                LOGGER.error("[IOException] Try/Catch...mpf.transferTo : " + e.getMessage());
            }

        }


        mav.setViewName("jsonView");
        return mav;
    }

    /**
     * 파일 삭제
     *
     * @param
     * @return "jsonView"
     * @throws Exception
     */
    @RequestMapping("comm/deleteAtchFile.do")
    @LpAuthCheck(isAuthChk = false)
    public ModelAndView deleteAtchFile(ModelMap model, @RequestParam Map<String, Object> map) throws Exception {
        ModelAndView mav = new ModelAndView();

        int result = flicCommonFileService.deleteAtchFile(map);

        mav.addObject("result", result);
        mav.setViewName("jsonView");
        return mav;
    }


    /**
     * 파일 다운로드
     *
     * @param map, request, response
     * @return
     * @throws Exception
     */
    @RequestMapping("comm/downloadAtchFile.do")
    @LpAuthCheck(isAuthChk = false)
    public void downloadAtchFile(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView();
        int result = 1;
        //System.out.println("fileId : " + map.get("fileId"));
        //System.out.println("fileSeq : " + map.get("fileSeq"));
        //System.out.println("chgFileNm : " + map.get("chgFileNm"));

        Map<String, Object> fileMap = flicCommonFileService.selectAtchFileForDownload(map);

        if (fileMap != null) {
            //System.out.println("filePath : " + fileMap.get("FILE_PATH").toString() + fileMap.get("CHG_FILE_NM").toString());
            File uFile = new File(fileMap.get("FILE_PATH").toString(), fileMap.get("CHG_FILE_NM").toString());
            long fSize = uFile.length();
            //System.out.println("fSize : " + fSize);
            if (fSize > 0) {
                String mimetype = "application/x-msdownload";

                response.setContentType(mimetype);
                setDisposition(fileMap.get("FILE_NM").toString(), request, response);

                BufferedInputStream in = null;
                BufferedOutputStream out = null;

                try {
                    in = new BufferedInputStream(new FileInputStream(uFile));
                    out = new BufferedOutputStream(response.getOutputStream());

                    FileCopyUtils.copy(in, out);
                    out.flush();
                } catch (FileNotFoundException fe) {
                    result = -1;
                    LOGGER.debug("IGNORED: {}", fe.getMessage());
                } catch (IOException ie) {
                    result = -1;
                    LOGGER.debug("IGNORED: {}", ie.getMessage());
                } catch (Exception e) {
                    result = -1;
                    LOGGER.debug("IGNORED: {}", e.getMessage());
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (NullPointerException ignore) {
                            LOGGER.debug("IGNORED: {}", ignore.getMessage());
                        } catch (Exception ignore) {
                            LOGGER.debug("IGNORED: {}", ignore.getMessage());
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (NullPointerException ignore) {
                            LOGGER.debug("IGNORED: {}", ignore.getMessage());
                        } catch (Exception ignore) {
                            LOGGER.debug("IGNORED: {}", ignore.getMessage());
                        }
                    }
                }

            } else {
                result = -1;
            }
        }

        //System.out.println("result : " + result);

        mav.addObject("result", result);
        mav.setViewName("jsonView");
        //return mav;

    }

    /**
     * Disposition 지정하기.
     *
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String browser = getBrowser(request);

        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Trident")) { // IE11 문자열 깨짐 방지
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            //throw new RuntimeException("Not supported browser");
            throw new IOException("Not supported browser");
        }

        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

        if ("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }

    /**
     * 브라우저 구분 얻기.
     *
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
            return "Trident";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }


    /**
     * 파일 조회
     *
     * @param
     * @return "jsonView"
     * @throws Exception
     */
    @RequestMapping("comm/selectAtchFile.do")
    @LpAuthCheck(isLoginChk=false,isAuthChk = false)
    public ModelAndView selectAtchFile(ModelMap model, @RequestParam Map<String, Object> map) throws Exception {
        ModelAndView mav = new ModelAndView();
        List<Map<String, Object>> fileList = flicCommonFileService.selectAtchFile(map);
        mav.addObject("fileList", fileList);
        mav.setViewName("jsonView");
        return mav;
    }

    /**
     * 둉영상 로드
     *
     * @param
     * @return "jsonView"
     * @throws Exception
     */
    @RequestMapping("comm/loadVideo.do")
    @LpAuthCheck(isLoginChk=true,isAuthChk=false)
    public ModelAndView loadVideo(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
        ModelAndView mav = new ModelAndView();

        Map<String, Object> fileMap = flicCommonFileService.selectAtchFileForDownload(map);

        if (fileMap != null) {

            String filePath = fileMap.get("FILE_PATH").toString();
            filePath = filePath.replaceAll("\\\\", "/");
            filePath = filePath.replaceAll("//", "/");

            String fullPath = "";

            String fileNm = fileMap.get("CHG_FILE_NM").toString();
            //업로드 경로 구분자 중복 제거
            if(!StringUtils.isEmpty(fileNm)) {
                fileNm = fileNm.replaceAll("\\\\", "/");
                fileNm = fileNm.replaceAll("//", "/");
            }

            if (fileNm.indexOf(filePath) > -1) {   // 이전에 등록된 파일들은 경로가 포함되어 있음.
                fullPath = fileNm;
            } else {
                fullPath = filePath + fileNm;
            }

            String fileName = StringUtils.getFilename(fullPath);

            if(StringUtils.isEmpty(fileName)) { // 엑박 대신 임시 이미지(그냥 흰색)
                fullPath = "";
                fileName = StringUtils.getFilename(fullPath);
            }

            if(!StringUtils.isEmpty(fileName)) {
                model.addAttribute("movieFile", fullPath);
                mav.setViewName("streamView");   // StreamView 실행
            }

        }

        return mav;
    }
}
