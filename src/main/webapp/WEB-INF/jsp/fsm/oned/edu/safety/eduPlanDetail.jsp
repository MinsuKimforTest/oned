<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduPlanDetail.jsp
     * @Description : SAFETY 교육신청 상세
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
<%@include file="../../comm/quickMenu.jsp"%>
<!-- wrap --->
<div id="wrap" class="main">
    <!-- container --->
    <div id="container">
        <%@include file="../../comm/header.jsp"%>

        <div class="sub_visual">
            <h2><c:out value="${currentMemu.CURRENT_MENU_NM}"/></h2>
        </div>

        <!-- sub_content-->
        <div class="sub_content">
            <%@include file="../../comm/leftMenu.jsp"%>

            <form id="dataFrm" onsubmit="return false">
                <input type="hidden" id="EDU_ID" value="<c:out value="${result.EDU_ID}"/>"/>

                <!-- RIGHT -->
                <div class="right_Wrap">
                    <!-- button -->
                    <div class="top_btn_wrap">
                        <button type="button" id="listBtn" class="sbtn_gray">목록</button>
                        <%--<c:if test="${paramMap.eduAplyNo eq null}">
                            <button type="button" id="applyBtn" class="sbtn_blue">접수 신청</button>
                        </c:if>--%>
                    </div>
                    <!-- //button -->

                    <h3><c:out value="${currentMemu.CURRENT_MENU_NM}"/> 상세</h3>

                    <!-- detail -->
                    <div class="tb_Wrap">
                        <table class="table_col txtL responsive" style="border-top: 1px solid #e6e8ec; margin-bottom: 20px">
                            <colgroup>
                                <col width="15%">
                                <col width="85%">
                            </colgroup>
                            <tr>
                                <th>교육명</th>
                                <td><c:out value="${result.EDU_TITLE}"/></td>
                            </tr>
                            <tr>
                                <th>교육 시간</th>
                                <td><c:out value="${result.EDU_TIME}"/></td>
                            </tr>
                            <tr>
                                <th>교육 내용</th>
                                <td>
                                    <div class="des_div"><c:out value="${result.DES}" escapeXml="false"/></div>
                                </td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td>
                                    <div id="mapFile" class="dropZone">
                                        <div id="fileList" class="fileList fr"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>

                        <h3>강의목차</h3>
                        <table class="table_col">
                            <colgroup>
                                <col width="5%">
                                <col width="*">
                                <col width="15%">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>차시</th>
                                <th>학습목차</th>
                                <th>강의시간</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${classList}" varStatus="status">
                                    <tr>
                                        <td data-title="차시"><c:out value="${item.CLASS_ORD}"/></td>
                                        <td data-title="학습목차" class="txtL"><c:out value="${item.TITLE}"/></td>
                                        <td data-title="강의시간"><c:out value="${item.CLASS_TIME}"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- //detail -->
                </div>
                <!-- //RIGHT -->

            </form>

        </div>
        <!-- //sub_content-->

        <%@include file="../../comm/footer.jsp"%>
    </div>
    <!-- //container -->
</div>
<!-- wrap --->

<%@include file="../../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        //첨부파일
        var fileZoneOption = { readOnly:true, fileDivId:"mapFile", atchFileObjId:"atchFile", fileIdValue:"<c:out value="${result.ATTACH_FILE_ID}"/>"};
        lpCom.fileZone(fileZoneOption);
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
            //목록
            $("#listBtn").on("click", function(e) {
                lpCom.href("<c:out value="${paramMap.returnUrl}"/>");
            });

            //접수신청
            $("#applyBtn").on("click", function(e) {
                var params = {};
                params.EDU_ID = $("#EDU_ID").val();
                var opt = {};
                opt.width = "750";
                opt.height = "700";
                opt.scrollbars = "yes";
                opt.resizable = "yes";
                opt.postData = {STATE:"I"};
                lpCom.winOpen('/edu/safety/applyForm.do', "SAFETY 교육신청", params, opt);
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


