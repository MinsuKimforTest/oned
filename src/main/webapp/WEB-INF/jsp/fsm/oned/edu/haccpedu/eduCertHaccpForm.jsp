<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    /**
     * @Class Name : eduApplyForm.jsp
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
    <%@include file="../../comm/inc_css.jsp"%>
</head>
<body>
<!-- popup wrap --->
<div class="popup_wrap">
    <!-- container --->
    <div id="container">
        <div class="sub_content">
            <form id="applyFrm" onsubmit="return false">
                <input type="hidden" id="EDU_APLY_ID" value="<c:out value="${result.EDU_APLY_ID}"/>"/><!--신청자ID-->
                <h3>교육 이수증</h3>

                <div class="hd_info">
                    <ul>
                        <li><strong>이수번호 : </strong><input type="text" id="EDU_CERT_NO" style="width: 90px" value="<c:out value="${result.EDU_CERT_NO}"/>"  readonly/></li>
                        <li>
                            <strong>교육 이수일 : </strong><input type="text" id="EDU_CERT_DT" style="width: 80px" value="<c:out value="${result.EDU_CERT_DT}"/>" readonly/>
                            <a id="copyBtn" class="sbtn_resi">교육 이수번호 복사</a>
                        </li>
                    </ul>
                </div>

                <!-- edu data -->
                <div class="table_popup">
                    <table class="table_col responsive">
                        <colgroup>
                            <col width="23%">
                            <col width="27%">
                            <col width="23%">
                            <col width="27%">
                        </colgroup>
                        <tbody>
                            <tr style="height: 40px">
                                <th>등록번호<br>(미등록시설은 제외)</th>
                                <td colspan="3"><c:out value="${result.CERT_NO}"/></td>
                            </tr>
                            <tr style="height: 40px">
                                <th>업체명(대표자)</th>
                                <td><c:out value="${result.COMP_NM_KOR} (${result.CEO_NM_KOR})"/></td>
                                <th>사업자등록번호</th>
                                <td><c:out value="${result.BIZ_REG_NO}"/></td>
                            </tr>
                            <tr style="height: 40px">
                                <th>시설(양식장)소재지</th>
                                <td colspan="3"><c:out value="${result.COMP_ZIPCD_ST} ${result.COMP_ADDR1_ST} ${result.COMP_ADDR2_ST}"/></td>
                            </tr>
                            <tr style="height: 40px">
                                <th>성명</th>
                                <td><c:out value="${result.EDU_APLY_NM}"/></td>
                                <th>생년월일</th>
                                <td><c:out value="${result.EDU_APLY_BIRTH}"/></td>
                            </tr>
                            <tr style="height: 40px">
                                <th>직책 및 담당업무</th>
                                <td colspan="3"><c:out value="${result.EDU_APLY_WORK_NM}"/></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //edu data -->

                <!-- signature -->
                <div class="md_apply">
                    <div class="text">생산·출하 전 단계 수산물의 안전관리인증기준(HACCP) 제6조에 따른 교육을 수료하였음을 증명합니다.</div>
                    <div class="text txtR">
                        <fmt:parseDate var="eduCertDt" value="${result.EDU_CERT_DT}" pattern="yyyy-MM-dd" />
                        <fmt:formatDate value="${eduCertDt}" pattern="yyyy"/>년
                        <fmt:formatDate value="${eduCertDt}" pattern="MM"/>월
                        <fmt:formatDate value="${eduCertDt}" pattern="dd"/>일
                    </div>
                    <div class="dear">
                        국립수산물품질관리원장
                    </div>
                </div>
                <!-- //signature -->

                <!-- button -->
                <div class="md_btn">
                    <a href="#" id="closeBtn" class="left_btn md_close">닫기</a>
                    <a href="#" id="printBtn">출력</a>
                </div>
                <!-- //button -->
            </form>
        </div>
    </div>
    <!-- //container -->
</div>
<!-- //popup wrap --->

<%@include file="../../comm/inc_javascript.jsp"%>
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
            //교육 이수번호 복사
            $("#copyBtn").on("click", function(e) {
                $('#EDU_CERT_NO').select() //복사할 텍스트를 선택
                document.execCommand("copy") //클립보드 복사 실행
                alert("교육 이수번호가 복사되었습니다.");
            });

            //닫기
            $("#closeBtn").on("click", function(e) {
                window.close();
            });

            //출력
            $("#printBtn").on("click", function(e) {
                let objReportParam = {};
                objReportParam["connection.reportname"] = "nfpis_report/FSM/ONED/OZ_ONED_02.ozr";   // 폼 디자인명 (경로+파일명)
                objReportParam["odi.odinames"] = "OZ_ONED_02";   // 폼 쿼리명 (다중 쿼리시 ","로 구분)
                objReportParam["odi.OZ_ONED_02.pcount"] = "2";   // 쿼리1 파라미터 개수
                objReportParam["odi.OZ_ONED_02.args1"] = "eduCertNo=" + $("#EDU_CERT_NO").val();  // 쿼리1 파라미터1 OR3082020000001
                objReportParam["odi.OZ_ONED_02.args2"] = "eduAplyId=" + $("#EDU_APLY_ID").val();  // 쿼리2 파라미터2 userid
                lpCom.openReportViewer(objReportParam);
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