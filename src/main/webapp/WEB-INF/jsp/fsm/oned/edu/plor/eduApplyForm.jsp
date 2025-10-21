<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    /**
     * @Class Name : eduApplyForm.jsp
     * @Description : PLOR 교육신청서
     * @Modification Information
     *
     *  수정일               수정자      수정내용
     *  ----------   -----  ---------------------------
     *  2024.01.01           최초 생성
     *
     * author 라이온플러스
     * since 2024.01.01
     *
     */
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@include file="../../comm/inc_css.jsp"%>
</head>
<body>
<!-- popup wrap --->
<div class="popup_wrap">
    <!-- container --->
    <div id="container">
        <div class="sub_content">
            <form id="applyFrm" onsubmit="return false">
                <input type="hidden" id="EDU_ID" value="<c:out value="${eduData.EDU_ID}"/>"/><!--교육ID-->
                <input type="hidden" id="CERT_ID" value="<c:out value="${compData.CERT_ID}"/>"/><!--인증ID-->
                <input type="hidden" id="EDU_APLY_ID" value="<c:out value="${compData.USER_ID}"/>"/><!--신청자ID-->

                <h3>PLOR 교육신청서</h3>

                <div class="hd_info">
                    <ul>
                        <li><strong>신청(접수)번호 : </strong><input type="text" id="EDU_APLY_NO" style="width: 90px" value="<c:out value="${paramMap.STATE == 'I'? '' : applyData.EDU_APLY_NO}"/>" placeholder="자동생성"  readonly/></li>
                        <li><strong>신청(접수)일 : </strong><input type="text" id="EDU_APLY_DT" style="width: 80px" value="" readonly/></li>
                    </ul>
                </div>

                <!-- edu data -->
                <div class="table_popup">
                    <h4 class="stit">교육정보</h4>
                    <table class="table_col responsive mt5 mb20">
                        <colgroup>
                            <col width="20%">
                            <col width="80%">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>교육명</th>
                                <td><c:out value="${eduData.EDU_TITLE}"/></td>
                            </tr>
                            <tr>
                                <th>교육 시간</th>
                                <td><c:out value="${eduData.EDU_TIME}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //edu data -->

                <div class="table_popup">
                    <h4 class="stit">신청정보</h4>
                    <table class="table_col responsive mt5 mb20">
                        <colgroup>
                            <col width="20%">
                            <col width="30%">
                            <col width="20%">
                            <col width="30%">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>성명(신청자) <em>*</em></th>
                            <td><input type="text" id="EDU_APLY_NM" value="<c:out value="${applyData.EDU_APLY_NM != null? applyData.EDU_APLY_NM : compData.USER_NM}"/>" caption="성명(신청자)" required <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>></td>
                            <th>생년월일 <em>*</em></th>
                            <td>
                                <input type="text" id="EDU_APLY_BIRTH" style="width: 120px" caption="생년월일" required  value="<c:out value="${applyData.EDU_APLY_BIRTH != null? applyData.EDU_APLY_BIRTH : compData.BIRTHDAY}"/>" data-datepicker tmpType="date" readonly <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>><button class="btn btn-default" type="button" data-datepicker="EDU_APLY_BIRTH"><img src="<c:url value='/images/ic_calendar.png'/>" alt="달력" style="width: 14px"></button>
                            </td>
                        </tr>
                        <tr>
                            <th>담당지원 <em>*</em></th>
                            <td colspan="3"><select id="EDU_APLY_WORK" style="width: 37%" caption="담당지원" required <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>></select></td>
                        </tr>
                        <tr>
                            <th>전화번호<br>(문자수신가능)</th>
                            <td><input type="text" id="EDU_APLY_TEL" class="phone-number" value="<c:out value="${applyData.EDU_APLY_TEL != null? applyData.EDU_APLY_TEL : compData.MBL_NO}"/>" maxlength="13" <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>></td>
                            <th>전자우편(E-mail)</th>
                            <td><input type="text" id="EDU_APLY_EMAIL" value="<c:out value="${applyData.EDU_APLY_EMAIL != null? applyData.EDU_APLY_EMAIL : compData.EMAIL_ID}"/>" <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //apply data -->

                <!-- signature -->
                <div class="md_apply">
                    <div class="text"><span class="eduAplyYear"></span>년 PLOR 교육을 신청합니다.</div>
                    <div class="date"><span class="eduAplyDt"></span></div>
                    <div class="sign">신청인&nbsp;<input type="text" id="EDU_APLY_SIGN" style="width: 100px" value="<c:out value="${applyData.EDU_APLY_SIGN != null? applyData.EDU_APLY_SIGN : compData.USER_NM}"/>" <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>></div>
                    <div class="dear">국립수산물품질관리원장 귀하</div>
                </div>
                <!-- //signature -->

                <!-- privacy agree -->
                <div class="table_popup">
                    <table class="table_col responsive">
                        <tbody>
                        <tr>
                            <th style="font-size: 14px; font-weight: bold; padding: 5px">개인정보 활용동의서</th>
                        </tr>
                        <tr>
                            <td>
                                <p>■ 개인정보 수집·이용 동의 내역</p>
                                <table>
                                    <tbody>
                                    <tr>
                                        <th>수집·이용 목적</th>
                                        <td>PLOR 교육 이수관리</td>
                                    </tr>
                                    <tr>
                                        <th>수집·이용 항목</th>
                                        <td>성명, 생년월일, 전화번호, 주소</td>
                                    </tr>
                                    <tr>
                                        <th>보유 및 이용기간</th>
                                        <td>교육 이수일로부터 2년</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div class="md_end">
                                    <span>※ 위의 개인정보 수집·이용에 대한 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 「교육 이수증」이 발급되지 않습니다.</span>
                                    <span>- PLOR 교육 이수와 관련된 개인정보의 수집·이용에 동의합니다.</span>
                                    <div class="ckd_wrap">
                                        <fieldset class="ckdbox">
                                            <input type="checkbox" id="PRIVACY_AGREE_YN_Y" name="PRIVACY_AGREE_YN" value="Y" <c:if test="${applyData.PRIVACY_AGREE_YN == 'Y'}">checked</c:if> <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>>
                                            <label for="PRIVACY_AGREE_YN_Y">&nbsp;동의</label>
                                        </fieldset>&nbsp;&nbsp;
                                        <fieldset class="ckdbox">
                                            <input type="checkbox" id="PRIVACY_AGREE_YN_N" name="PRIVACY_AGREE_YN" value="N" <c:if test="${applyData.PRIVACY_AGREE_YN == 'N'}">checked</c:if> <c:if test="${paramMap.STATE == 'R'}">disabled</c:if>>
                                            <label for="PRIVACY_AGREE_YN_N">&nbsp;미동의</label>
                                        </fieldset>
                                    </div>
                                    <div class="sign">
                                        신청인&nbsp;<input type="text" id="PRIVACY_AGREE_NM" style="width: 100px" value="<c:out value="${applyData.PRIVACY_AGREE_NM != null? applyData.PRIVACY_AGREE_NM : compData.USER_NM}"/>">
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //privacy agree -->

                <!-- button -->
                <div class="md_btn">
                    <c:if test="${paramMap.STATE == 'I'}">
                        <a href="#" id="closeBtn" class="left_btn md_close">취소</a>
                        <a href="#" id="saveBtn">신청</a>
                    </c:if>
                    <c:if test="${paramMap.STATE == 'R'}">
                        <a href="#" id="closeBtn" class="left_btn md_close">닫기</a>
                        <a href="#" id="printBtn">출력</a>
                    </c:if>
                </div>
                <!-- //button -->
            </form>
        </div>
    </div>
    <!-- //container -->
</div>
<!-- //popup wrap --->

<!-- 교육신청안내 -->
<%@include file="../../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        if("I" == '${paramMap.STATE}'){
            $(".modal").fadeIn();
        }
        lpCom.setCodeSelect(["EDU_APLY_WORK"], {MSTR_CD:["MQM54"],first:"S",selectCd:"<c:out value="${applyData.EDU_APLY_WORK}"/>"}); //지원정보


        if('${paramMap.STATE}' == 'I'){         //신청서 작성
            //날짜 Setting
            $("#EDU_APLY_DT").val(today);
            $(".eduAplyYear").html(year);
            $(".eduAplyDt").html(year+'년 '+month+'월 '+day+'일' );
        }else if('${paramMap.STATE}' == 'R'){   //신청서 상세
            //날짜 Setting
            $("#EDU_APLY_DT").val('<c:out value="${applyData.EDU_APLY_DT}"/>');
            $(".eduAplyYear").html('<fmt:parseDate var="eduAplyDt" value="${applyData.EDU_APLY_DT}" pattern="yyyy-MM-dd" /><fmt:formatDate value="${eduAplyDt}" pattern="yyyy"/>');
            $(".eduAplyDt").html('<fmt:formatDate value="${eduAplyDt}" pattern="yyyy"/>년 <fmt:formatDate value="${eduAplyDt}" pattern="MM"/>월 <fmt:formatDate value="${eduAplyDt}" pattern="dd"/>일');
        }
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "save" : // PLOR 교육신청 저장
                window.close();
                opener.lpCom.href("/edu/actionEduClassRoomList.do");
                break;
        }

    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            //개인정보 활용 동의
            $('input[name="PRIVACY_AGREE_YN"]').on("click", function(e) {
                if ($(this).prop('checked')) {
                    $('input[type="checkbox"][name="PRIVACY_AGREE_YN"]').prop('checked', false);
                    $(this).prop('checked', true);
                }
            });

            //취소
            $("#closeBtn").on("click", function(e) {
                window.close();
            });

            //신청
            $("#saveBtn").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;
                otherInit.formObj = $("#applyFrm");

                var param = {};

                if(!lpCom.isRequired($("input[name='PRIVACY_AGREE_YN']:checked"), "개인정보 활용 동의서는 필수 입력 값입니다.")) return;
                if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("save", "/edu/unified/saveEduApply.do?eduType=PLOR", param, calBackFunc, otherInit);
            });

            //출력
            $("#printBtn").on("click", function(e) {
                let objReportParam = {};
                objReportParam["connection.reportname"] = "nfpis_report/FSM/ONED/OZ_ONED_01.ozr";   // 폼 디자인명 (경로+파일명)
                objReportParam["odi.odinames"] = "OZ_ONED_01";   // 폼 쿼리명 (다중 쿼리시 ","로 구분)
                objReportParam["odi.OZ_ONED_01.pcount"] = "1";   // 쿼리1 파라미터 개수
                objReportParam["odi.OZ_ONED_01.args1"] = "eduAplyNo=" + $("#EDU_APLY_NO").val();  // 쿼리1 파라미터1 OR3082020000001
                objReportParam["odi.OZ_ONED_02.args2"] = "eduAplyId=" + $("#EDU_APLY_ID").val();  // 쿼리2 파라미터2 userid
                lpCom.openReportViewer(objReportParam);
            });

            //레이어팝업 닫기
            $(".modal_content .md_close").on("click", function(e) {
                $(".modal").fadeOut();
            });

            //주소 팝업
            $("#addrBtn").on("click", function(e){
                lpCom.goJusoPopup();
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