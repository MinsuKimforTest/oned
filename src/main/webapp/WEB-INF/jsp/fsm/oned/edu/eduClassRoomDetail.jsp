<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduClassRoomDetail.jsp
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

            <!-- form -->
            <form id="dataFrm" onsubmit="return false">
                <input type="hidden" id="EDU_APLY_NO" value="<c:out value="${applyData.EDU_APLY_NO}"/>">
                <input type="hidden" id="EDU_ID" value="<c:out value="${applyData.EDU_ID}"/>">
                <input type="hidden" id="GBN" value="<c:out value="${applyData.GBN}"/>">

                <!-- RIGHT -->
                <div class="right_Wrap">

                    <!-- button -->
                    <div class="top_btn_wrap">
                        <button type="button" id="listBtn" class="sbtn_gray">목록</button>
                    </div>
                    <!-- //button -->

                    <!-- 강의안내 -->
                    <h3>강의안내</h3>
                    <div class="guide_Wrap">
                        <div class="guide_Wrap_L">
                            <table class="table_row">
                                <tbody>
                                <tr>
                                    <th scope="row">구분</th>
                                    <td><c:out value="${applyData.EDU_GUBUN_NM}"/></td>
                                </tr>
                                <tr>
                                    <th scope="row">교육명</th>
                                    <td><c:out value="${applyData.EDU_TITLE}"/></td>
                                </tr>
                                <tr>
                                    <th scope="row">교육시간</th>
                                    <td><c:out value="${applyData.EDU_TIME}"/>시간</td>
                                </tr>
                                <tr>
                                    <th scope="row">학습차시</th>
                                    <td><c:out value="${applyData.EDU_CNT}"/>차시</td>
                                </tr>
                                <tr>
                                    <th scope="row">교육신청일</th>
                                    <td><c:out value="${applyData.EDU_APLY_DT}"/></td>
                                </tr>
                                <tr>
                                    <th scope="row">교육기간</th>
                                    <td><c:out value="${applyData.EDU_APLY_DT} ~ ${applyData.EDU_END_DT}"/></td>
                                </tr>
                                <tr>
                                    <th scope="row">학습상태</th>
                                    <td><c:out value="${applyData.STUDY_STATUS_NM}"/></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="guide_Wrap_R">
                            <div class="bar_Wrap">
                                <div class="cont">
                                    <div class="bar">
                                        <div class="bar-gauge" style="width: ${applyData.STUDY_RATE_AVG}%;"></div>
                                        <div class="txt">진도율</div>
                                        <div class="num"><c:out value="${applyData.STUDY_RATE_AVG}"/>%</div>
                                    </div>
                                </div>
                            </div>
                            <div class="text">
                                <p class="stxt">남은기간</p>
                                <p class="b_num">
                                    <c:if test="${applyData.EDU_DT_DIFF > 0}"><span>기간 만료</span></c:if>
                                    <c:if test="${applyData.EDU_DT_DIFF <= 0}">
                                        D<span><c:out value="${applyData.EDU_DT_DIFF}"/></span>
                                    </c:if>
                                </p>
                            </div>
                            <ul class="edu_menu">
                                <li><a id="studyBtn">학습자료실</a></li>
                                <li><a id="qnaBtn">질의응답</a></li>
                                <li><a id="eduCertBnt">교육이수증</a></li>
                            </ul>
                        </div>
                    </div>
                    <!-- 강의안내 -->

                    <!-- list -->
                    <h3>강의목차</h3>
                    <div class="tb_Wrap responsive">
                        <table class="table_col">
                            <colgroup>
                                <col width="5%">
                                <col width="*">
                                <col width="12%">
                                <col width="12%">
                                <col width="15%">
                                <col width="15%">
                                <col width="12%">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>차시</th>
                                <th>학습목차</th>
                                <th>진도율</th>
                                <th>학습시간(분)</th>
                                <th>최초학습시간</th>
                                <th>학습종료시간</th>
                                <th>강의</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${classData}" varStatus="status">
                                    <tr>
                                        <td data-title="차시"><c:out value="${item.CLASS_ORD}"/></td>
                                        <td data-title="학습목차" class="txtL"><c:out value="${item.TITLE}"/></td>
                                        <td data-title="진도율"><c:out value="${item.STUDY_RATE}"/>%</td>
                                        <td data-title="학습시간(분)"><c:out value="${item.STUDY_TIME}"/></td>
                                        <td data-title="최초학습시간"><c:out value="${item.STUDY_START_DT}"/></td>
                                        <td data-title="학습종료시간"><c:out value="${item.STUDY_END_DT}"/></td>
                                        <td data-title="강의">
                                            <c:if test="${applyData.STUDY_STATUS != '4'}">
                                                <a class="sbtn_resi btn_play" style="padding: 0 15px 0 30px; min-width: 105px" onclick="fnMoveClass('<c:out value="${item.ORD_NO}"/>');">학습하기</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- list -->
                </div>
                <!-- //RIGHT -->

            </form>
            <!-- //form -->

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
        //목록
        $("#listBtn").on("click", function(e) {
            lpCom.href("/edu/actionEduClassRoomList.do");
        });
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
            $("#studyBtn").on("click", function(){
                fnMoveEduBoardList("학습자료실", "EDUBOARD001");
            });

            $("#qnaBtn").on("click", function(){
                fnMoveEduBoardList("질의응답", "EDUBOARD003");
            });

            $("#eduCertBnt").on("click", function(){
                var eduCertNo = "<c:out value="${applyData.EDU_CERT_NO}"/>";
                var gbn = "<c:out value="${applyData.GBN}"/>";
                if(eduCertNo == ""){
                    alert("교육 이수증이 발급되지 않았습니다.");
                    return;
                }

                var params = {};
                params.EDU_CERT_NO = eduCertNo;
                params.GBN = gbn;
                params.eduType = gbn;
                var opt = {};
                opt.width = "750";
                opt.height = "615";

                switch (gbn) {
                    case "PLOR":
                        url = "/edu/unified/actionEduCertForm.do";
                        break;
                    case "SAFETY":
                        url = "/edu/unified/actionEduCertForm.do";
                        break;
                    default:
                        url = "/edu/actionEduCertForm.do";
                        return;
                }
                lpCom.winOpen(url, "교육 이수증", params, opt);

            });
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    //교육 게시판 리스트
    var fnMoveEduBoardList = function(title, eduBoardId) {
        var params = {};
        params.EDU_BOARD_ID = eduBoardId;
        params.EDU_ID = $("#EDU_ID").val();
        var opt = {};
        opt.width = "780";
        opt.height = "745";
        opt.scrollbars = "yes";
        opt.resizable = "yes";
        lpCom.winOpen('/edu/actionEduBoardList.do', title, params, opt);
    }

    //강의 학습하기
    var fnMoveClass = function(ordNo){
        /*var params = {};
        params.EDU_APLY_NO = $("#EDU_APLY_NO").val();
        params.EDU_ID = $("#EDU_ID").val();
        params.ORD_NO = ordNo;
        var opt = {};
        opt.width = "900";
        opt.height = "608";
        lpCom.winOpen('/edu/actionEduClassDetail.do', "강의 학습하기", params, opt);*/
        var openNewWindow = window.open("about:blank");
        openNewWindow.location.href = lpCom.contextPath + "/edu/actionEduClassDetail.do?EDU_APLY_NO="+$("#EDU_APLY_NO").val()+"&EDU_ID="+$("#EDU_ID").val()+"&ORD_NO="+ordNo;
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>