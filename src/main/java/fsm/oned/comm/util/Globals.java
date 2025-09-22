package fsm.oned.comm.util;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 *
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see
 *
 */

public class Globals {

    //메인 페이지
    public static final String MAIN_PAGE = EgovProperties.getProperty("Globals.MainPage");

    //주소검색팝업 key(juso.go.kr)
    public static final String JUSO_POPUP_KEY = EgovProperties.getProperty("Globals.jusoPopupKey");

    //주소검색팝업 key 모바일(juso.go.kr)
    public static final String JUSO_MOBILE_POPUP_KEY = EgovProperties.getProperty("Globals.jusoMobilePopupKey");

    //주소검색 key 좌표검색(juso.go.kr)
    public static final String JUSO_COORD_SEARCH_KEY = EgovProperties.getProperty("Globals.jusoCoordSearchKey");

    //주소검색 key 검색(juso.go.kr)
    public static final String JUSO_SEARCH_KEY = EgovProperties.getProperty("Globals.jusoSearchKey");

    //주소검색 HOST 클라이언트
    public static final String JUSO_HOST_CLIENT = EgovProperties.getProperty("Globals.jusoHostClient");

    //주소검색 HOST 서버
    public static final String JUSO_HOST_SERVER = EgovProperties.getProperty("Globals.jusoHostServer");


    public static final String SCE_SYS_CD = "15";	// 시스템코드 (15:친환경시스템)

    public static final String REGEXP_USER_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*.?_])[A-Za-z\\d~!@#$%^&*.?_]{9,100}$";	// 비밀번호 정규표현식

    //친환경교육 문자 발신자
    public static final String EDU_CALLBACK_NUM = "051-400-5600";


}
