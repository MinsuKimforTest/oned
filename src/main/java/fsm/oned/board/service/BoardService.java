package fsm.oned.board.service;

import fsm.oned.board.mapper.BoardMapper;
import fsm.oned.comm.exception.UserException;
import fsm.oned.comm.lpEnum.SaveEnum;
import fsm.oned.comm.service.FlicCommonFileService;
import fsm.oned.comm.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 게시판 Service
 * @author 라이온 플러스 허은미
 * @since 2021.07.15
 * @version 1.0
 * @see
 *
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 *  수정일    수정자    수정내용
 *  -------   --------  ---------------------------
 *  2021.07.15    허은미    최초 생성
 *
 * </pre>
 */

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardMapper mapper;

    @Autowired
    private CommonUtil commonUtil;

    @Resource(name = "flicCommonFileService")
    private FlicCommonFileService flicCommonFileService;

    /**
     * 게시판 정보
     * @param paramMap
     * @return Map
     */
    public Map<String, Object> selectBoardInfo(Map<String,Object> paramMap) {
        Map<String,Object> retMap = mapper.selectBoardInfo(paramMap);
        return retMap;
    }

    /**
     * 게시판 - 리스트 조회
     * @param paramMap
     * @return List
     */
    public List<Map<String,Object>> selectBoardList(Map<String,Object> paramMap) {
        List<Map<String,Object>> list;

        if(paramMap.get("BOARD_TYPE").equals("3")) { //답변형 게시판
            list = mapper.selectAnswerBoardList(paramMap);
        }else {
            list = mapper.selectBoardList(paramMap);
        }
        
        return list;
    }

    /**
     * 게시글 - 상세 조회
     * @param paramMap
     * @return Map
     */
    public Map<String,Object> selectBoardDetail(Map<String,Object> paramMap) {
        //조회수 count
        mapper.increaseBoardLockupCnt(paramMap);

        Map<String,Object> retMap = new HashMap();
        if(paramMap.get("BOARD_ID") != null && paramMap.get("ORD_NO") != null) {
            retMap =  mapper.selectBoardDetail(paramMap);
            String des = "";
            if(retMap.get("DES") != null){
                des = retMap.get("DES").toString();
                retMap.put("DES", commonUtil.getEscape(des));
            }
        }
        return retMap;
    }

    /**
     * 게시글 - 저장
     * @param paramMap
     * @return String
     */
    public String saveBoardData(Map<String,Object> paramMap, HttpServletRequest request){
        if(!"BOARD052".equals(paramMap.get("BOARD_ID"))) throw new UserException("권한이 없습니다.");
        Logger logger = null;
        String rowStats = paramMap.get("rowStats").toString();

        switch(SaveEnum.valueOf(rowStats)){
            case I:
            case U:
                String[] fileNm = request.getParameterValues("mapFile_fileNm");
                if(fileNm != null) {
                    if(paramMap.get("ATTACH_FILE_ID").toString().isEmpty()) {
                        paramMap.put("ATTACH_FILE_ID", UUID.randomUUID().toString().replaceAll("-",""));
                    }

                    String[] chgFileNm = request.getParameterValues("mapFile_chgFileNm");
                    String[] fileSize = request.getParameterValues("mapFile_fileSize");
                    String[] filePath = request.getParameterValues("mapFile_filePath");
                    paramMap.put("fileId", paramMap.get("ATTACH_FILE_ID"));
                    for(int i=0; i<fileNm.length; i++) {
                        paramMap.put("fileNm", fileNm[i]);
                        paramMap.put("chgFileNm", chgFileNm[i]);
                        paramMap.put("filePath", filePath[i]);
                        paramMap.put("fileSize", fileSize[i]);
                        try {
                            flicCommonFileService.insertAtchFile(paramMap);
                        }catch(NullPointerException e) {
                            logger.error(e.getMessage());
                            throw new UserException("파일 등록중 오류 발생 입니다.");
                        }catch(IOException e) {
                            logger.error(e.getMessage());
                            throw new UserException("파일 등록중 오류 발생 입니다.");
                        }catch(Exception e) {
                            logger.error(e.getMessage());
                            throw new UserException("파일 등록중 오류 발생 입니다.");
                        }
                    }
                }
                break;
            case D:
                break;
        }


        switch(SaveEnum.valueOf(rowStats)){
            case I:
                if(paramMap.get("TEXT_PASSWORD") !=null && !paramMap.get("TEXT_PASSWORD").toString().isEmpty()) {
                    //비밀번호 암호화
                    paramMap.put("TEXT_PASSWORD",commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
                }
                mapper.insertBoardData(paramMap);
                break;
            case U:
                if(paramMap.get("TEXT_PASSWORD") !=null && !paramMap.get("TEXT_PASSWORD").toString().isEmpty()) {
                    //비밀번호 암호화
                    paramMap.put("TEXT_PASSWORD",commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
                }
                if(checkauth(paramMap)){
                    mapper.updateBoardData(paramMap);
                }else{
                    throw new UserException("권한이 없습니다.");
                }
                break;
            case D:
                if(checkauth(paramMap)){
                    mapper.deleteBoardData(paramMap);
                }else{
                    throw new UserException("권한이 없습니다.");
                }

                break;
        }
        return paramMap.get("ORD_NO").toString();
    }

    public boolean checkauth(Map paramMap){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        int checkcnt = mapper.selectBoardInfoCheck(paramMap);
        HashMap<String, Object> userInfo = (HashMap<String, Object>) session.getAttribute("USER_INFO");
        if(paramMap.get("REG_ID").equals(userInfo.get("USER_ID"))){
            if(checkcnt>0){
                return true;
            }
        }
        return false;
    }

    /**
     * 게시글 - 비밀번호 확인
     * @param paramMap
     * @return String
     */
    public String selectCheckBoardPassword(Map<String,Object> paramMap){
        paramMap.put("TEXT_PASSWORD", commonUtil.encryptPassword(paramMap.get("TEXT_PASSWORD").toString()));
        if(mapper.selectCheckBoardPassword(paramMap) == null) {
            throw new UserException("게시글 비밀번호가 일치하지 않습니다.");
        }
        return paramMap.get("ORD_NO").toString();
    }

    /**
     * 메인(공지사항) - 리스트 조회
     * @param paramMap
     * @return List
     */
    public List<Map<String,Object>> selectBoardNoticeList(Map<String,Object> paramMap) {
        List<Map<String,Object>> list =  mapper.selectBoardNoticeList(paramMap);
        for(int i=0; i<list.size(); i++) {
            String des = "";
            if(list.get(i).get("DES") != null){
                des = list.get(i).get("DES").toString();
                list.get(i).put("DES", commonUtil.getEscape(des));
            }
        }
        return list;
    }


}
