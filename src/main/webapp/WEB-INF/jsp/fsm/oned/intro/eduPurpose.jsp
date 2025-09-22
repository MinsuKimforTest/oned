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
                                국민이 신뢰할 수 있는 친환경 인증품의 생산ㆍ가공ㆍ유통을 위하여 친환경인증 사업자를 대상으로 한
                                인증기준 이행교육 실시로 친환경수산물 생산에 제한 이해를 제공하여 품질 좋은 인증품을 생산·관리하고자 합니다.
                            </div>
                        </article>
                        <article>
                            <h4 class="title">교육대상</h4>
                            <div class="text">
                                「친환경농어업 육성 및 유기식품 등의 관리·지원에 관한 법률」제19조 및
                                제34조에 따른 친환경수산물 등 신규 인증이나 인증 갱신을 희망하는 사업자 등
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
                            <h4 class="title">교육과정</h4>
                            <div class="course">
                                <ul>
                                    <li><span class="num">1</span>신규 친환경수산물 인증사업자 교육(3시간 과정)</li>
                                    <li><span class="num">2</span>갱신 친환경수산물 인증사업자 교육(2시간 과정)</li>
                                </ul>
                            </div>
                            <span class="notice_txt">* 한 번 인증 받은 업체의 유효기간 만료, 인증취소 후 신규 신청의 경우 갱신사업자에 해당됩니다. </span>
                        </article>
                        <article>
                            <h4 class="title">교육절차</h4>
                            <div class="process_Wrap">
                                <h5 class="stit">신규교육 : 3시간과정</h5>
                                <div class="process">
                                    <ul>
                                        <li><div class="tit">01. 친환경 인증번호 확인</div><span>최초사업자 선택</span></li>
                                        <li><div class="tit">02. 신규 교육 신청</div><span>교육신청서 작성 후 신청</span></li>
                                        <li><div class="tit">03. 학습 시작</div><span>3시간 과정 학습</span></li>
                                        <li><div class="tit">04. 학습 종료</div><span>기간 내 학습 종료</span></li>
                                        <li><div class="tit">05. 교육 이수증 발급</div><span>수강 완료 시,<br>교육 이수증 자동 발급</span></li>
                                    </ul>
                                </div>
                                <span class="notice_txt">* 교육 기간 : 교육 신청일로부터 2달 이내</span>
                            </div>
                            <div class="process_Wrap">
                                <h5 class="stit">갱신 교육 : 2시간 과정</h5>
                                <div class="process">
                                    <ul>
                                        <li><div class="tit">01. 친환경 인증번호 확인</div><span>기존사업자 선택,<br>인증번호 등록(최초 1회)</span></li>
                                        <li><div class="tit">02. 갱신 교육 신청</div><span>교육신청서 작성 후 신청</span></li>
                                        <li><div class="tit">03. 학습 시작</div><span>2시간 과정 학습</span></li>
                                        <li><div class="tit">04. 학습 종료</div><span>기간 내 학습 종료</span></li>
                                        <li><div class="tit">05. 교육 이수증 발급</div><span>수강 완료 시,<br>교육 이수증 자동 발급</span></li>
                                    </ul>
                                </div>
                                <span class="notice_txt">* 교육 기간 : 교육 신청일로부터 3달 이내</span>
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