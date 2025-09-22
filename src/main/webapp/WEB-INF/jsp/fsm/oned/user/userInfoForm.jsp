<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : userInfoForm.jsp
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
                <!-- list -->
                <h3><c:out value="${currentMemu.CURRENT_MENU_NM}"/></h3>
                <div class="sub_tab">
                    <ul class="tabs">
                        <li class="active"><a href="#tab-1">회원정보</a></li>
                        <li><a href="#tab-2">회원비밀번호 수정</a></li>
                    </ul>
                </div>
                <div class="tb_Wrap">
                    <!-- tab-1 -->
                    <form id="dataFrm1" onsubmit="return false">
                        <input type="hidden" id="USER_ID" name="USER_ID" value="<c:out value="${result.USER_ID}"/>">

                        <div id="tab-1" class="tab-content">
                            <table class="table_col txtL responsive">
                                <colgroup>
                                    <col width="15%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th>이름</th>
                                    <td><c:out value="${result.USER_NM}"/></td>
                                </tr>
                                <tr>
                                    <th>아이디</th>
                                    <td><c:out value="${result.USER_ID}"/></td>
                                </tr>
                                <tr>
                                    <th>비밀번호 확인 <em>(필수)</em></th>
                                    <td><input type="password" id="PASSWORD" caption="비밀번호" required /></td>
                                </tr>
                                <tr>
                                    <th>휴대전화번호</th>
                                    <td><input type="text"id="MBL_NO" class="phone-number" value="<c:out value="${result.MBL_NO}"/>" maxlength="13" /></td>
                                </tr>
                                <tr>
                                    <th>이메일</th>
                                    <td><input type="email" id="EMAIL_ID" value="<c:out value="${result.EMAIL_ID}"/>" /></td>
                                </tr>
                                <tr>
                                    <th>주소</th>
                                    <td>
                                        <input type="text" id="ZIPCD_ST" style="width: 10%" placeholder="우편번호" value="<c:out value="${result.ZIPCD_ST}"/>" readonly />
                                        <a href="#" id="addrBtn" class="btn">주소찾기</a>
                                        <input type="text" id="ADDR1_ST" style="width: 40%" value="<c:out value="${result.ADDR1_ST}"/>" readonly />
                                        <input type="text" id="ADDR2_ST" value="<c:out value="${result.ADDR2_ST}"/>" placeholder="상세주소" />
                                        <input type="hidden" id="REGION_CD" value="<c:out value="${result.REGION_CD}"/>" />
                                        <input type="hidden" id="STREET_CD" value="<c:out value="${result.STREET_CD}"/>" />
                                        <input type="hidden" id="LAT" value="<c:out value="${result.LAT}"/>" />
                                        <input type="hidden" id="LON" value="<c:out value="${result.LON}"/>" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>전화번호</th>
                                    <td><input type="text" id="PHONE_NO" class="phone-number" value="<c:out value="${result.PHONE_NO}"/>" maxlength="13" /></td>
                                </tr>
                                <tr>
                                    <th>팩스번호</th>
                                    <td><input type="text" id="FAX_NO" class="phone-number" value="<c:out value="${result.FAX_NO}"/>" maxlength="13" /></td>
                                </tr>
                                <tr>
                                    <th>회원유형 <em>(필수)</em></th>
                                    <td>
                                        <select id="USER_TYPE" disabled>
                                            <c:choose>
                                                <c:when test="${'4' eq result.USER_TYPE}">
                                                    <option value="4" selected>개인</option>
                                                </c:when>
                                                <c:when test="${'5' eq result.USER_TYPE}">
                                                    <option value="5" selected>업체 및 대표자</option>
                                                </c:when>
                                                <c:when test="${'6' eq result.USER_TYPE}">
                                                    <option value="6" selected>업체직원</option>
                                                </c:when>
                                            </c:choose>
                                        </select>
                                    </td>
                                </tr>
                                <c:if test="${'5' eq result.USER_TYPE or '6' eq result.USER_TYPE}">
                                    <tr>
                                        <th>업체 <em>(필수)</em></th>
                                        <td>
                                            <c:if test="${empty result.COMP_NO and '5' eq result.USER_TYPE}">
                                                <input type="text" id="BIZ_REG_NO" class="company-number" placeholder="사업자등록번호" value="<c:out value="${result.BIZ_REG_NO}"/>" />
                                                <button type="button" id="btnSearchComp" class="btn">검색</button>
                                            </c:if>
                                            <c:if test="${not empty result.COMP_NO}">
                                                <input type="text" id="BIZ_REG_NO" class="company-number" placeholder="사업자등록번호" value="<c:out value="${result.BIZ_REG_NO}"/>" readonly />
                                            </c:if>
                                            <input type="text" id="COMP_NO_BLNG" placeholder="업체번호" value="<c:out value="${result.COMP_NO_BLNG}"/>" readonly />
                                            <input type="text" id="COMP_NM_BLNG" placeholder="업체명" value="<c:out value="${result.COMP_NM_BLNG}"/>" readonly />
                                            <input type="text" id="CEO_NM_KOR" placeholder="대표자명" value="<c:out value="${result.CEO_NM_KOR}"/>" readonly style="width:10%" />
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                            <div class="btn_wrap">
                                <a href="#" id="closeBtn1" name="close Btn" class="left_btn">취소</a>
                                <a href="#" id="saveBtn1">저장</a>
                            </div>
                        </div>
                    </form>
                    <!-- //tab-1 -->
                    <!-- tab-2 -->
                    <form id="dataFrm2" onsubmit="return false">
                        <div id="tab-2" class="tab-content">
                            <table class="table_col txtL responsive">
                                <colgroup>
                                    <col width="15%">
                                    <col>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th>이름</th>
                                    <td><c:out value="${result.USER_NM}"/></td>
                                </tr>
                                <tr>
                                    <th>아이디</th>
                                    <td><c:out value="${result.USER_ID}"/></td>
                                </tr>
                                <tr>
                                    <th>현재 비밀번호 <em>(필수)</em></th>
                                    <td><input type="password" id="OLD_PASSWORD" caption="현재 비밀번호" required /></td>
                                </tr>
                                <tr>
                                    <th>새로운 비밀번호 <em>(필수)</em></th>
                                    <td><input type="password" id="NEW_PASSWORD" caption="새로운 비밀번호" required /></td>
                                </tr>
                                <tr>
                                    <th>비밀번호 확인 <em>(필수)</em></th>
                                    <td><input type="password" id="CHK_PASSWORD" caption="비밀번호 확인" required equalTo="#NEW_PASSWORD" /></td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="btn_wrap">
                                <a href="#" id="closeBtn2" name="closeBtn" class="left_btn">취소</a>
                                <a href="#" id="saveBtn2">저장</a>
                            </div>
                        </div>
                    </form>
                    <!-- //tab-2 -->
                </div>
                <!-- list -->
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
        //tab
        $(".tab-content").hide();
        $("ul.tabs li:first").addClass("active").show();
        $(".tab-content:first").show();
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "juso": //주소
                $("#ZIPCD_ST").val(pData.zipNo); //우편번호
                $("#ADDR1_ST").val(pData.roadAddrPart1); // 기본주소
                $("#ADDR2_ST").val(pData.addrDetail); // 상세주소
                $("#REGION_CD").val(pData.admCd); // 법정동 지역코드
                $("#STREET_CD").val(pData.rnMgtSn); // 도로명코드
                var inputPintArray = [];
                inputPintArray.push(pData.entX);
                inputPintArray.push(pData.entY);
                var projetedPoint = ol.proj.transform(inputPintArray, "EPSG:5179", "EPSG:4326")
                $("#LON").val(projetedPoint[0]); // 경도
                $("#LAT").val(projetedPoint[1]); // 위도
                break;
            case "company": //업체정보
                if (pData.retData.length == 0) {
                    alert("등록된 업체정보가 없습니다. 담당자에게 확인바랍니다. TEL : 051-702-8856");
                } else if (pData.retData.length == 1) {
                    var companyInfo = pData.retData[0];
                    $("#COMP_NO_BLNG").val(companyInfo.COMP_NO);
                    $("#COMP_NM_BLNG").val(companyInfo.COMP_NM_KOR);
                    $("#CEO_NM_KOR").val(companyInfo.CEO_NM_KOR);

                } else if (pData.retData.length > 1) {
                    alert("담당자에게 확인바랍니다. TEL : 051-702-8856");
                }
                break;
            case "save": // 저장
                lpCom.href("/user/actionUserInfoForm.do");
                break;
        }
    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            $("ul.tabs li").on("click", function(e) {
                $("ul.tabs li").removeClass("active");
                $(this).addClass("active");
                $(".tab-content").hide();

                var activeTab = $(this).find("a").attr("href");
                $(activeTab).fadeIn();
                return false;
            });

            //주소 팝업
            $("#addrBtn").on("click", function(e){
                lpCom.goJusoPopup();
            });

            //취소
            $("[name='closeBtn']").on("click", function(e) {
                lpCom.href("/main/actionMain.do");
            });

            //업체정보 조회
            $("#btnSearchComp").on("click", function(e) {
                if(!lpCom.isRequired($("#BIZ_REG_NO"), "사업자등록번호를 입력해주십시오.")) return;

                var otherInit = {};
                otherInit.isSuccesMsg = false;

                var param = {};
                param.BIZ_REG_NO = $("#BIZ_REG_NO").val();

                lpCom.Ajax("company", "/user/selectCompanyList.do", param, calBackFunc, otherInit);
            });

            $("#BIZ_REG_NO").on("keydown", function(e) {
                if (e.keyCode === 13) {
                    $("#btnSearchComp").click();
                }
            });

            //회원정보 저장
            $("#saveBtn1").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;
                otherInit.formObj = $("#dataFrm1");

                var param = {};
                if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("save", "/user/saveUserInfo.do", param, calBackFunc, otherInit);
            });

            //비밀번호 저장
            $("#saveBtn2").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;
                otherInit.formObj = $("#dataFrm2");

                var param = {};
                param.USER_ID = $("#USER_ID").val();

                //비밀번호 패턴
                $("#dataFrm2").validate({
                    rules : { NEW_PASSWORD : { userPassword : true } }
                });
                if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("save", "/user/saveUserPassword.do", param, calBackFunc, otherInit);
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