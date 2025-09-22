<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduPurpose.jsp
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

        <div class="sub_visual">
            <h2><c:out value="${currentMemu.CURRENT_MENU_NM}"/></h2>
        </div>

        <!-- sub_content-->
        <div class="sub_content">
            <%@include file="../comm/leftMenu.jsp"%>

            <!-- RIGHT -->
            <div class="right_Wrap">

                <!-- 교육 목표 -->
                <section>
                    <h3>교육목표</h3>
                    <div class="edu_content">
                        <article>
                            <h4 class="title">교육목표</h4>
                            <div class="text">
                                생산·출하 전 수산물의 안전관리인증기준(HACCP) 이행시설 등록 희망자 또는 등록시설 관계자 등을 대상으로 안전관리인증 기준
                                교육을 실시함으로써 안전한 수산물을 생산·공급하여 소비자를 보호하고 소비를 촉진시켜 어업인의 이익을 도모하고자 합니다.
                            </div>
                        </article>
                        <article>
                            <h4 class="title">교육대상</h4>
                            <div class="text">
                                「생산·출하 전 수산물의 안전관리인증기준」 제6조에 따른 안전관리인증기준(HACCP) 이행시설로
                                등록하고자 하거나 등록한 시설의 관리·운영자, HACCP 팀장·팀원 등 관계자
                            </div>
                        </article>
                    </div>
                </section>
                <!-- //교육 목표 -->

                <!-- 교육과정 -->
                <section>
                    <h3>교육과정</h3>
                    <div class="process_content">
                        <article>
                            <h4 class="title">교육절차</h4>
                            <div class="process_Wrap">
                                <div class="process">
                                    <ul>
                                        <li><div class="tit">01. 교육신청</div><span>교육신청서 작성 후 신청</span></li>
                                        <li><div class="tit">02. 학습시작</div><span>3차시 과정 학습</span></li>
                                        <li><div class="tit">03. 학습기간</div><span>해당 연도 내</span></li>
                                        <li><div class="tit">04. 교육수료증 발급</div><span>수강 완료 시,<br>교육 이수증 자동 발급</span></li>
                                    </ul>
                                </div>
                                <span class="notice_txt">* * 교육이수자는 교육수료증을 출력하여 보관</span>
                            </div>
                        </article>
                    </div>
                </section>
                <!-- //교육과정 -->

            </div>
            <!-- //RIGHT -->

        </div>
        <!-- //sub_content-->

        <%@include file="../comm/footer.jsp"%>
    </div>
    <!-- //container -->
</div>
<!-- wrap --->

<%@include file="../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {

    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {

    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>