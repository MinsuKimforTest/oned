<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduPlanList.jsp
     * @Description : PLOR 교육신청 리스트
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

            <!-- form -->
            <form id="schFrm" onsubmit="return false">
                <input type="hidden" id="startPage">
                <input type="hidden" id="selectPage" value="1">
                <input type="hidden" id="checkYn">

                <!-- RIGHT -->
                <div class="right_Wrap">

                    <!-- Search -->
                    <div id="sysSearch" class="sch_Wrap">
                        <ul class="form">
                            <li>
                                <label for="EDU_STATUS">상태</label>
                                <select id="EDU_STATUS" class="sch_form" style="width: 90px"></select>
                            </li>
                            <li>
                                <label for="EDU_TITLE">교육명</label>
                                <input type="text" id="EDU_TITLE" class="sch_form" style="width: 185px;" placeholder="교육명을 입력해주세요" />
                            </li>
                        </ul>
                        <a id="schBtn" class="btn_sch">조회</a>
                    </div>
                    <!-- //Search -->

                    <!-- list -->
                    <div class="tb_Wrap responsive">
                        <table class="table_col">
                            <colgroup>
                                <col width="5%">
                                <col width="45%">
                                <col width="10%">
                                <col width="10%">
                                <col width="15%">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>번호</th>
                                <th>교육명</th>
                                <th>상태</th>
                                <th>교육 시간</th>
                                <th>교육 신청</th>
                            </tr>
                            </thead>
                            <tbody id="list">
                            </tbody>
                        </table>
                        <div id="pagination" class="pagination"></div>
                    </div>
                    <!-- list -->
                </div>
                <!-- //RIGHT -->

            </form>
            <!-- //form -->

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
    // 교육 타입 설정
    var EDU_TYPE = "PLOR";
    
    $(document).ready(function() {
        lpCom.setCodeSelect(["EDU_STATUS"], {MSTR_CD:["FS_447"],first:"A"}); //교육구분, 교육상태
        lpCom.getSearchCond();
        lpCom.enterEventId("sysSearch", "fnSearch(1)");
        fnSearch($("#selectPage").val());
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "search" : // 검색
                console.log("pSid11 : ",pSid);
                console.log("pData11 : ",pData);
                fnSetList(pData);
                break;
        }
    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            //검색
            $("#schBtn").on("click", function(e) {
                fnSearch(1);
            });

            //레이어팝업 닫기
            $(".modal_content .md_close").on("click", function(e) {
                $(".modal").fadeOut();
            });
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    //조회
    var fnSearch = function(pSelectPage) {
        if (!$("#schFrm").valid()) return false; //form 체크
        $("#selectPage").val(pSelectPage);

        var otherInit = {};
        otherInit.isSuccesMsg = false;

        var param = $("#schFrm").srializeJsonById();
        lpCom.Ajax("search", "/edu/unified/selectPlanList.do?eduType=" + EDU_TYPE, param, calBackFunc, otherInit);
    }

    //리스트
    var fnSetList = function(pData){

        console.log("pData : ",pData);
        var listHtml = [];
        //console.log(pData);

        for(i in pData.list){
            listHtml.push('<tr onclick=fnMoveDetail("' + pData.list[i].EDU_ID + '")>');
            listHtml.push('<td data-title="번호">'+pData.list[i].RNUM+'</td>');
            listHtml.push('<td data-title="교육명" class="txtL">'+pData.list[i].EDU_TITLE+'</td>');
            listHtml.push('<td data-title="상태"><span style="color: #00a2ff;">'+pData.list[i].EDU_STATUS_NM+'</span></td>');
            listHtml.push('<td data-title="교육 시간">'+pData.list[i].EDU_TIME+'</td>');
            if(pData.list[i].EDU_STATUS == "2"){
                listHtml.push('<td data-title="PLOR 교육 신청" onclick="event.cancelBubble=true"><button type="button" class="sbtn_resi re_btn" onclick=fnMoveEduApplyForm("' + pData.list[i].EDU_ID + '")>교육신청</button></td>');
            }else{
                listHtml.push('');
            }
            listHtml.push('</tr>');
        }

        $("#list").html(listHtml.join(""));

        lpCom.setPagination(pData);
    };

    //상세
    var fnMoveDetail = function(eduId) {
        lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)
        var postData = {};
        postData.returnUrl = "${currentMemu.CURRENT_MENU_URL}";
        var menuParam = "${param.MENU_ID}" ? "&MENU_ID=${param.MENU_ID}" : "";
        lpCom.hrefOrSubmit("/edu/unified/actionEduPlanDetail.do?eduType=" + EDU_TYPE + "&EDU_ID=" + eduId + menuParam, postData);
    }

    //PLOR 교육 신청
    var fnMoveEduApplyForm = function(eduId) {
        var params = {};
        params.eduType = EDU_TYPE;
        params.EDU_ID = eduId;
        params.STATE = "I";
        var opt = {};
        opt.width = "750";
        opt.height = "700";
        opt.scrollbars = "yes";
        opt.resizable = "yes";
        lpCom.winOpen('/edu/unified/actionEduApplyForm.do', "PLOR 교육신청", params, opt);

    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>


