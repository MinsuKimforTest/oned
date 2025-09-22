<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    /**
     * @Class Name : main.jsp
     * @Description :
     * @Modification Information
     *
     *  수정일               수정자      수정내용
     *  ----------   -----  ---------------------------
     *  2021.07.15           최초 생성
     *
     * author 라이온플러스 허은미
     * since 2021.07.15
     *
     */
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="../comm/inc_css.jsp"%>
</head>
<body>
<%@include file="../comm/quickMenu.jsp"%>
<!-- wrap --->
<div id="wrap" class="main">
    <!-- container --->
    <div id="container">
        <%@include file="../comm/header.jsp"%>

        <div class="main_content">
            <!-- visual -->
            <div class="visual_Wrap">
            <ul>
                <li>
                    <div class="text">
                        <p class="btxt">친환경교육시스템</p>
                        <p class="stxt"><span>국립수산물품질관리원</span>에서는 안전하고 신선한 수산먹거리를 위해
                        친환경수산물 인증사업자 교육을 진행하고 있습니다. </p>
                        <a class="visual_btn" href="<c:url value='/edu/actionEduPlanList.do' />">교육신청 바로가기</a>
                    </div>
                </li>
                <li>
                    <div class="text">
                        <p class="btxt">HACCP 양식장</p>
                        <p class="stxt"><span>국립수산물품질관리원</span>에서는 안전하고 신선한 수산먹거리를 위해
                            HACCP 양식장 교육을 진행하고 있습니다. </p>
                        <a class="visual_btn" href="<c:url value='/edu/actionEduPlanHaccpList.do' />">교육신청 바로가기</a>
                    </div>
                </li>
            </ul>
            </div>
            <!-- //visual -->

            <!-- login-전 -->
            <c:if test="${empty sessionScope.USER_INFO}">
                <div class="login_Wrap">
                    <p class="btxt">로그인</p>
                    <form id="loginFrm" onsubmit="return false">
                        <div class="login">
                            <div class="login_input">
                                <input type="text" id="USER_ID" class="form" placeholder="아이디를 입력하세요" caption="아이디" required />
                                <input type="password" id="PASSWORD" class="form" placeholder="비밀번호를 입력하세요" caption="비밀번호" required />
                            </div>
                            <a href="#" id="loginBtn" class="btn_login">로그인</a>
                        </div>
                    </form>
                    <div class="log_save_Wrap">
                        <div class="log_save">
                            <fieldset class="ckdbox">
                                <input type="checkbox" id="idSaveCheck" name="idSaveCheck">
                                <label for="idSaveCheck"> 아이디저장</label>
                            </fieldset>
                        </div>
                        <ul class="login_find">
                            <li><a href="https://www.nfqs.go.kr/cvmg/actionFindIdForm.do" target="_blank">아이디 찾기</a></li>
                            <li><a href="https://www.nfqs.go.kr/cvmg/actionFindPasswordForm.do" target="_blank">비밀번호 찾기</a></li>
                        </ul>
                    </div>
                    <div class="join">
                        <p class="btxt">아직 회원이 아니신가요?</p>
                        <p class="stxt">회원가입하시면 친환경교육 강의수강 및 이수증을 발급하실 수 있습니다.<br>회원가입은 [국립수산물품질관리원 민원시스템]을 통해 진행됩니다. </p>
                        <a href="https://www.nfqs.go.kr/cvmg/actionRegistForm.do" class="sbtn_join" target="_blank">회원가입</a>
                    </div>
                </div>
            </c:if>
            <!-- //login-전 -->

            <!-- login-후 -->
            <c:if test="${!empty sessionScope.USER_INFO}">
                <div class="login_Wrap">
                    <p class="btxt">나의 강의실</p>
                    <ul class="detail">
                        <c:forEach var="item" items="${statusList}" varStatus="status">
                            <li>
                                <a href="javascript:fnMoveClassRoomList('${item.STUDY_STATUS}');">
                                    <p class="stxt"><c:out value="${item.STUDY_STATUS_NM}"/></p>
                                    <p class="num n_${status.count}"><c:out value="${item.STUDY_STATUS_CNT}"/></p>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                    <p class="btn_go_Wrap"><a class="btn_go" href="<c:url value='/edu/actionEduClassRoomList.do'/>">지금 강의 듣기 <img src="<c:url value='/images/ic_go.png'/>" alt="바로가기 아이콘"></a></p>
                </div>
            </c:if>
            <!-- //login-후 -->

            <!-- 공지사항 -->
            <div class="notice_Wrap">
                <div class="notice_tit">
                    <p class="title">새로운 소식</p>
                    <a class="sbtn_more" href="<c:url value='/board/actionBoardList.do?MENU_ID=M0000800' />">more</a>
                </div>
                <ul class="notice_txt">
                    <c:forEach var="item" items="${noticeList}" varStatus="status">
                        <fmt:parseDate var="regDt" value="${item.REG_DT}" pattern="yyyy-MM-dd" />
                        <li>
                            <a href="javascript:fnMoveNoticeDetail('${item.ORD_NO}');">
                                <div class="num_Wrap">
                                    <p class="num_B"><fmt:formatDate value="${regDt}" pattern="dd"/></p>
                                    <p class="num_s"><fmt:formatDate value="${regDt}" pattern="yy.MM"/></p>
                                </div>
                                <div class="con_Wrap">
                                    <p class="no_tit"><c:out value="${item.TITLE}"/></p>
                                    <div class="no_stxt"><c:out value="${item.DES}" escapeXml="false"/></div>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <!-- //공지사항 -->

            <!-- 배너 -->
            <div class="banner_Wrap">
                <ul>
                    <li>
                        <a  href="<c:url value='/edu/actionEduPlanList.do' />" class="bn_01">
                            친환경 교육신청
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                    <li>
                        <a  href="<c:url value='/edu/actionEduPlanHaccpList.do' />" class="bn_01">
                            Haccp 교육신청
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                    <li>
                        <a  href="<c:url value='/edu/actionEduClassRoomList.do' />" class="bn_02">
                            나의 강의실
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                    <li>
                        <a  href="<c:url value='/edu/actionEduStatusList.do' />" class="bn_03">
                            이수증 출력
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                    <li>
                        <a  href="<c:url value='/organicCert/actionOrganicCertNoForm.do' />" class="bn_04">
                            친환경 인증번호
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                    <li>
                        <a  href="https://www.nfqs.go.kr/cvmg/" class="bn_05" target="_blank">
                            친환경인증신청<span>(민원시스템)</span>
                            <p class="s_more">바로가기</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <%@include file="../comm/footer.jsp"%>
    </div>
    <!-- //container -->
</div>
<!-- wrap --->

<%@include file="../comm/popup.jsp"%>

<%@include file="../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        lpCom.enterEventId("USER_ID", "$('#loginBtn').click()");
        lpCom.enterEventId("PASSWORD", "$('#loginBtn').click()");

        var userInputId = lpCom.getCookie("userInputId");//저장된 쿠기값 가져오기
        if(!(userInputId == "undefined" || userInputId == null)){
            $("#USER_ID").val(userInputId);
        }
        if($("#USER_ID").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
            $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
        }

        //업체정보 확인
        const userType = "${sessionScope.USER_INFO.USER_TYPE}";
        const compNo = "${sessionScope.USER_INFO.COMP_NO_BLNG}";
        if("5" == userType || "6" == userType){
            if("" == compNo){
                if(confirm("업체정보가 없습니다. 업체 정보를 등록해주시기 바랍니다.")){
                    lpCom.href("/user/actionUserInfoForm.do");
                }
            }
        }

    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "login": // 로그인 처리
                lpCom.href("/main/actionMain.do");
                break;
        }
    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            //로그인
            $("#loginBtn").on("click", function(e) {
                if(!$("#loginFrm").valid()) return false; //form 체크

                var otherInit = {};
                otherInit.isSuccesMsg = false;
                otherInit.isErrCallBackFunc = true;
                var param = $("#loginFrm").srializeJsonById();
                lpCom.Ajax("login", "/login/actionLogin.do", param, calBackFunc, otherInit);
            });
        }

        {//change 이벤트 활당
            $("#idSaveCheck").on("click", function(e) {
                if($("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
                    var userInputId = $("#USER_ID").val();
                    lpCom.setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
                }else{ // ID 저장하기 체크 해제 시,
                    lpCom.deleteCookie("userInputId");
                }
            });
        }

        {
            $("#USER_ID").on("keyup", function(e) {// ID 입력 칸에 ID를 입력할 때,
                if($("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
                    var userInputId = $("#USER_ID").val();
                    lpCom.setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
                }
            });
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    //공지사항 상세
    var fnMoveNoticeDetail = function(ordNo) {
        lpCom.href("/board/actionBoardDetail.do?MENU_ID=M0000800&BOARD_ID=BOARD050&ORD_NO=" + ordNo);
    }

    //나의 강의실
    var fnMoveClassRoomList = function (studyStatus) {
        var postData = {};
        postData.STUDY_STATUS = studyStatus;
        lpCom.hrefOrSubmit("/edu/actionEduClassRoomList.do", postData);
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>
