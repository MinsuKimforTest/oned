<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : organicCertNoForm.jsp
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

            <form id="dataFrm" onsubmit="return false">

                <!-- RIGHT -->
                <div class="right_Wrap">
                    <h3><c:out value="${currentMemu.CURRENT_MENU_NM}"/></h3>

                    <!-- step 1 -->
                    <div class="step_wrap" style="padding-top: 0">
                        <div class="step_div">
                            <span class="step">STEP 1</span>
                            <span class="step_txt">사업자 구분</span>
                        </div>
                        <div class="sel_wrap">
                            <div class="sel">
                                <label for="CERT_GUBUN1">
                                    <div class="i-radio">
                                        <input type="radio" id="CERT_GUBUN1" name="CERT_GUBUN" value="1" onclick="fnSelectCertGugun('1', '${result.CERT_NO}')" <c:if test="${result.CERT_GUBUN == '1'}">checked</c:if>>
                                        <em class="ico"></em>
                                    </div>
                                    <h2>최초사업자</h2>
                                    <ul>
                                        <li>친환경 인증번호를 부여받지않은 사업자</li>
                                        <li>친환경 교육을 한번도 받지않은 사업자</li>
                                        <li>신규 인증을 위해 신규 교육이 필요한 사업자</li>
                                    </ul>
                                </label>
                            </div>
                            <div class="sel">
                                <label for="CERT_GUBUN2">
                                    <div class="i-radio">
                                        <input type="radio" id="CERT_GUBUN2" name="CERT_GUBUN" value="2" onclick="fnSelectCertGugun('2', '${result.CERT_NO}')" <c:if test="${result.CERT_GUBUN == '2'}">checked</c:if>>
                                        <em class="ico"></em>
                                    </div>
                                    <h2>기존사업자(갱신)</h2>
                                    <ul>
                                        <li>친환경 인증번호를 부여받은 사업자</li>
                                        <li>친환경 교육을 받은 적이 있는 사업자</li>
                                        <li>갱신 인증을 위해 갱신 교육이 필요한 사업자</li>
                                    </ul>
                                </label>
                            </div>
                            <div class="clear"></div>
                        </div>
                    </div>
                    <!-- //step 1 -->
                    <!-- step 2 -->
                    <div class="step_wrap">
                        <div class="step_div">
                            <span class="step">STEP 2</span>
                            <span class="step_txt">인증번호 등록</span>
                        </div>
                        <table class="table_col txtL responsive" style="border-top: 1px solid #e6e8ec">
                            <colgroup>
                                <col width="20%">
                                <col width="80%">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th style="height: 48px; font-weight: bold">인증번호</th>
                                <td id="certNoDiv" style="line-height: 40px;">
                                    <c:if test="${result.CERT_GUBUN == '1'}">
                                        <span style="color: #ff6c32">최초 인증사업자는 제외<span>
                                    </c:if>
                                    <c:if test="${result.CERT_GUBUN == '2'}">
                                        <c:forTokens items="${result.CERT_NO}" delims="," var="item">
                                            <div class="certno_row">
                                                <input type="text" name="CERT_NO" style="width: 150px" maxlength="8" value="${item}" onkeyup="fnCheckCertNoInfo(this)">
                                            </div>
                                        </c:forTokens>
                                    </c:if>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- //step 2 -->

                    <!-- info -->
                    <div class="info-txt">
                        <img src="<c:url value='/images/info.png'/>" alt="정보" style="width: 15px">
                        친환경 인증번호를 모르시면, <a href="https://www.nfqs.go.kr/hpmg/data/actionMarineOrganicCertificationForm.do?menuId=M0000229" target="_blank" style="font-weight: bold">여기</a>를 클릭하여 확인해주십시오.
                    </div>
                    <div class="info-txt">
                        <img src="<c:url value='/images/info.png'/>" alt="정보" style="width: 15px">
                        국립수산물품질관리원 홈페이지에 있는 인증번호와 동일하게 입력해주십시오.(-포함, 8자리)
                    </div>
                    <!-- //info -->

                    <!-- button -->
                    <div class="btn_wrap">
                        <a href="#" id="closeBtn" name="closeBtn" class="left_btn">취소</a>
                        <a href="#" id="certSubmitBtn">저장</a>
                    </div>
                    <!-- //button -->

                </div>
                <!-- //RIGHT -->

            </form>

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
        fnDelCertNo();
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "certNo" : // 친환경 인증번호 등록
                lpCom.href("/organicCert/actionOrganicCertNoForm.do");
                break;
        }
    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            //취소
            $("#closeBtn").on("click", function(e) {
                lpCom.href("/main/actionMain.do");
            });
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
