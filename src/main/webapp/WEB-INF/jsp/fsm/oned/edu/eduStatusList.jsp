<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : eduStatusList.jsp
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
                    <!-- Search -->
                    <div id="sysSearch" class="sch_Wrap">
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
                    <div class="tb_Wrap responsive">
                        <table class="table_col">
                            <colgroup>
                                <col width="5%">
                                <col width="5%">
                                <col width="">
                                <col width="10%">
                                <col width="12%">
                                <col width="10%">
                                <col width="7%">
                                <col width="10%">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>번호</th>
                                <th>구분</th>
                                <th>교육명</th>
                                <th>교육 신청서</th>
                                <th>교육 신청일</th>
                                <th>학습상태</th>
                                <th>진도율</th>
                                <th>이수증</th>
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

        <%@include file="../comm/footer.jsp"%>
    </div>
    <!-- //container -->
</div>
<!-- wrap --->

<%@include file="../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        lpCom.setCodeSelect(["EDU_GUBUN","STUDY_STATUS"], {MSTR_CD:["FS_446","FS_477"],first:"A"}); //교육구분, 학습상태
        lpCom.setFixCombo("EDU_YEAR", {DataGbn:"C01", first:"A", selectCd:year});
        fnSearch($("#selectPage").val());


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
        lpCom.Ajax("search", "/edu/selectEduStatusList.do", param, calBackFunc, otherInit);
    }

    //리스트
    var fnSetList = function(pData){
        var listHtml = [];
        //console.log(pData);

        for(i in pData.list){
            listHtml.push('<tr>')
            listHtml.push('<td data-title="번호">'+pData.list[i].RNUM+'</td>')
            listHtml.push('<td data-title="구분">'+pData.list[i].EDU_GUBUN_NM+'</td>')
            listHtml.push('<td data-title="교육명" class="txtL">'+pData.list[i].EDU_TITLE+'</td>')
            listHtml.push('<td data-title="교육 신청서"><button type="button" class="sbtn_lgray" onclick=fnMoveEduApplyForm("' + pData.list[i].EDU_ID + '","' + pData.list[i].EDU_APLY_NO + '","' + pData.list[i].GBN + '") style="min-width: 100px">교육신청서</button></td>')
            listHtml.push('<td data-title="교육 신청일">'+pData.list[i].EDU_APLY_DT+'</td>')
            listHtml.push('<td data-title="학습상태">'+pData.list[i].STUDY_STATUS_NM+'</td>')
            listHtml.push('<td data-title="진도율">'+pData.list[i].STUDY_RATE_AVG+'%</td>')
            listHtml.push('<td data-title="이수증">')
            if(pData.list[i].EDU_CERT_NO != ""){
                listHtml.push('<button type="button" class="sbtn_resi" onclick=fnMoveEduCertNoForm("' + pData.list[i].EDU_CERT_NO + '","' + pData.list[i].GBN + '") style="min-width: 130px">'+pData.list[i].EDU_CERT_NO+'</button>')
            }
            listHtml.push('</td>')
            listHtml.push('</tr>')
        }

        $("#list").html(listHtml.join(""));

        lpCom.setPagination(pData);
    };

    //교육신청서
    var fnMoveEduApplyForm = function(eduId, eduAplyNo, gbn) {
        var params = {};
        params.EDU_ID = eduId;
        var opt = {};
        opt.width = "750";
        opt.height = "700";
        opt.scrollbars = "yes";
        opt.resizable = "yes";
        opt.postData = {EDU_APLY_NO:eduAplyNo, STATE:"R", GBN:gbn, eduType:gbn};

        switch (gbn) {
            case "PLOR":
                url = "/edu/unified/actionEduApplyForm.do";
                break;
            case "SAFETY":
                url = "/edu/unified/actionEduApplyForm.do";
                break;
            default:
                url = "/edu/actionEduCertForm.do";
                return;
        }

        lpCom.winOpen(url, "교육신청", params, opt);
    }

    //교육 이수증
    var fnMoveEduCertNoForm = function(eduCertNo, gbn){
        if(eduCertNo == ""){
            alert("교육 이수증이 발급되지 않았습니다.");
        }else{
            var params = {};
            params.EDU_CERT_NO = eduCertNo;
            params.GBN = gbn;
            params.eduType = gbn;
            var opt = {};
            opt.width = "750";
            opt.height = "640";

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
        }
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>