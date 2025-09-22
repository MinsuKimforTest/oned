<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduClassRoomList.jsp
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
            <form id="schFrm" onsubmit="return false">
                <input type="hidden" id="startPage">
                <input type="hidden" id="selectPage" value="1">

                <!-- RIGHT -->
                <div class="right_Wrap">

                    <!-- 학습활동 -->
                    <div class="situat_Wrap" id="statusList">
                    </div>
                    <!-- 학습활동 -->

                    <!-- Search -->
                    <div class="sch_Wrap">
                        <ul class="form">
                            <li>
                                <label>연도</label>
                                <select id="EDU_YEAR" class="sch_form" style="width: 90px"></select>
                            </li>
                            <li>
                                <label for="EDU_GUBUN">구분</label>
                                <select id="EDU_GUBUN" class="sch_form" style="width: 90px"></select>
                            </li>
                            <li>
                                <label for="STUDY_STATUS">학습상태</label>
                                <select id="STUDY_STATUS" class="sch_form" style="width: 120px"></select>
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
                    <div id="list">
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
        lpCom.setCodeSelect(["EDU_GUBUN", "STUDY_STATUS"], {MSTR_CD:["FS_446", "FS_477"],first:"A"}); //교육구분
        lpCom.setFixCombo("EDU_YEAR", {DataGbn:"C01", first:"A", selectCd:year});
        lpCom.getSearchCond();
        lpCom.enterEventId("sysSearch", "fnSearch(1)");
        fnSearch($("#selectPage").val());

        if("${paramMap.STUDY_STATUS}" != ""){
            fnSetStudyStatus("<c:out value="${paramMap.STUDY_STATUS}"/>");
        }
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "search" : // 검색
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
        lpCom.Ajax("search", "/edu/selectEduClassRoomList.do", param, calBackFunc, otherInit);
    }

    //리스트
    var fnSetList = function(pData){
        var statusHtml = [];

        statusHtml.push('<h3>'+$("#EDU_YEAR").val()+' 학습활동</h3>');
        statusHtml.push('<ul>');
        for(i in pData.statusList){
            statusHtml.push('<li class="s_bn_0'+(Number(i)+1)+'" onclick="fnSetStudyStatus('+pData.statusList[i].STUDY_STATUS+')">');
            statusHtml.push('<div class="txt_Wrap"><p class="txt">'+pData.statusList[i].STUDY_STATUS_NM+'</p><p class="num">'+pData.statusList[i].STUDY_STATUS_CNT+'</p></div>');
            statusHtml.push('</li>');
        }
        statusHtml.push('</ul>');

        $("#statusList").html(statusHtml.join(""));

        var listHtml = [];

        if(pData.list.length > 0){
            for(i in pData.list){
                listHtml.push('<div class="detail_list">')
                listHtml.push('<p class="title">'+pData.list[i].EDU_TITLE+'</p>')
                listHtml.push('<ul>')
                listHtml.push('<li><span>구분</span>'+pData.list[i].EDU_GUBUN_NM+'</li>')
                listHtml.push('<li><span>교육 기간</span>'+pData.list[i].EDU_APLY_DT+' ~ '+pData.list[i].EDU_END_DT+'</li>')
                listHtml.push('<li><span>교육 시간</span>'+pData.list[i].EDU_TIME+'시간</li>')
                listHtml.push('<li><span>학습상태</span><span style="color: #ff6840;">'+pData.list[i].STUDY_STATUS_NM+'</span></li>')
                listHtml.push('<li><span>교육 신청일</span> '+pData.list[i].EDU_APLY_DT+'</li>')
                listHtml.push('<li class="gauge_bar"><span>나의 진도율</span>')
                listHtml.push('<div class="cont">')
                listHtml.push('<div class="bar">')
                listHtml.push('<div class="bar-gauge" style="width: '+pData.list[i].STUDY_RATE_AVG+'%;"></div>')
                listHtml.push('<div class="num">'+pData.list[i].STUDY_RATE_AVG+'%</div>')
                listHtml.push('</div>')
                listHtml.push('</div>')
                listHtml.push('</li>')
                listHtml.push('</ul>')
                listHtml.push('<div class="btn_Wrap">')
                listHtml.push('<a class="btn_blue" onclick=fnMoveClassDetail("' + pData.list[i].EDU_ID + '","' + pData.list[i].EDU_APLY_NO + '")>강의실입장</a>')
                listHtml.push('<a class="btn_gray" onclick=fnMoveEduDetail("' + pData.list[i].EDU_ID + '","' + pData.list[i].EDU_APLY_NO + '")>교육소개</a>')
                listHtml.push('</div>')
                listHtml.push('</div>')
            }
        }else{
            listHtml.push('<div class="detail_none">')
            listHtml.push('<p>조회된 강의 내역이 없습니다.</p>')
            listHtml.push('<p><a href="<c:url value='/edu/actionEduPlanList.do' />" class="sbtn_blue">교육신청 바로가기</a></p>')
            listHtml.push('</div>')
        }


        $("#list").html(listHtml.join(""));

        lpCom.setPagination(pData);
    };

    //학습활동
    var fnSetStudyStatus = function(studyStatus){
        $("#STUDY_STATUS").val(studyStatus);
        $("#STUDY_STATUS").trigger("change");
        fnSearch(1);
    }

    //강의실 상세
    var fnMoveClassDetail = function(eduId, eduAplyNo) {
        lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)
        lpCom.href("/edu/actionEduClassRoomDetail.do?EDU_ID=" + eduId + "&EDU_APLY_NO=" + eduAplyNo);
    }

    //교육 상세
    var fnMoveEduDetail = function(eduId, eduAplyNo) {
        lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)
        var postData = {};
        postData.returnUrl = "<c:out value="${currentMemu.CURRENT_MENU_URL}"/>";
        postData.eduAplyNo = eduAplyNo;
        lpCom.hrefOrSubmit("/edu/actionEduPlanDetail.do?EDU_ID=" + eduId ,postData);
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>