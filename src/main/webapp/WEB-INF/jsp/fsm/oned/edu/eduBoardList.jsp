<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    /**
     * @Class Name : eduBoardList.jsp
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
<!-- popup wrap --->
<div class="popup_wrap">
    <!-- container --->
    <div id="container">
        <div class="sub_content">

            <!-- form -->
            <form id="schFrm" onsubmit="return false">
                <input type="hidden" id="startPage">
                <input type="hidden" id="selectPage" value="1">
                <input type="hidden" id="pageSize" value="5">

                <input type="hidden" id="EDU_BOARD_ID" value="<c:out value="${paramMap.EDU_BOARD_ID}"/>">
                <input type="hidden" id="EDU_ID" value="<c:out value="${paramMap.EDU_ID}"/>">
                <input type="hidden" id="EDU_BOARD_TYPE" value="<c:out value="${boardInfo.EDU_BOARD_TYPE}"/>">

                <h3><c:out value="${boardInfo.EDU_BOARD_TITLE}"/></h3>

                <!-- info -->
                <div class="table_popup">
                    <table class="table_col responsive">
                        <colgroup>
                            <col width="20%">
                            <col width="80%">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>구분</th>
                            <td>
                                <c:out value="${eduData.EDU_GUBUN_NM}"/>
                                <input type="hidden" id="EDU_GUBUN" value="<c:out value="${eduData.EDU_GUBUN}"/>"/>
                            </td>
                        </tr>
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
                <!-- //info -->

                <!-- Search -->
                <div id="sysSearch" class="sch_Wrap" style="margin-top: 20px">
                    <ul class="form">
                        <li>
                            <label for="TITLE">제목</label>
                            <input type="text" id="TITLE" class="sch_form" style="width: 185px;" placeholder="글제목을 입력해주세요" />
                        </li>
                        <li>
                            <label for="DES">내용</label>
                            <input type="text" id="DES" class="sch_form" style="width: 185px;" placeholder="내용을 입력해주세요" />
                        </li>
                    </ul>
                    <a id="schBtn" class="btn_sch">조회</a>
                </div>
                <!-- //Search -->

                <!-- button -->
                <div class="top_btn_wrap" style="margin-top: 10px;">
                    <c:if test="${boardInfo.EDU_BOARD_TYPE eq '3'}">
                        <button type="button" id="regiBtn" class="sbtn_gray">글쓰기</button>
                    </c:if>
                </div>
                <!-- //button -->

                <!-- list -->
                <div class="tb_Wrap" style="min-height: 370px">
                    <table class="table_col responsive">
                        <colgroup>
                            <col width="8%">
                            <col width="42%">
                            <col width="15%">
                            <col width="15%">
                            <col width="10%">
                            <col width="10%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>첨부파일</th>
                            <th>조회수</th>
                        </tr>
                        </thead>
                        <tbody id="list">
                        </tbody>
                    </table>
                    <div id="pagination" class="pagination"></div>
                </div>
                <!-- list -->

                <!-- button -->
                <div class="md_btn" style="text-align: right">
                    <a href="#" id="closeBtn" class="left_btn md_close">닫기</a>
                </div>
                <!-- //button -->

            </form>
            <!-- //form -->
        </div>
    </div>
    <!-- //container -->
</div>
<!-- //popup wrap --->

<!-- layer_popup --->
<div id="pwConfirmLayer" class="modal">
    <div class="modal_content" style="width:400px;">
        <form id="passwordFrm" onsubmit="return false">
            <div class="md_head">
                <h1>비밀번호 확인</h1>
            </div>
            <div class="md_cont">
                <div class="md_input">
                    <div class="tit">비밀번호</div>
                    <div class="con">
                        <input type="password" id="TEXT_PASSWORD" name="TEXT_PASSWORD" caption="비밀번호" required>
                        <input type="hidden" id="ORD_NO" name="ORD_NO" value="<c:out value="${result.ORD_NO}"/>" />
                    </div>
                </div>
            </div>
            <div class="md_btn">
                <a href="#" id="cancelBtn" class="left_btn">취소</a>
                <a href="#" id="confirmBtn">확인</a>
            </div>
        </form>
    </div>
</div>
<!-- //layer_popup --->

<%@include file="../comm/inc_javascript.jsp"%>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
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
                fnSetList(pData);
                break;
            case "password": // 비밀번호 확인
                fnMoveDetail($("#ORD_NO").val(), "N");
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

            //글쓰기
            $("#regiBtn").on("click", function(e) {
                lpCom.href("/edu/actionEduBoardForm.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>");
            });

            //비밀번호 확인
            $("#confirmBtn").on("click", function(e) {
                if(!lpCom.isRequired($("#TEXT_PASSWORD")))return ;

                var otherInit = {};
                otherInit.isSuccesMsg = false;

                var param = {};
                param.EDU_BOARD_ID = $("#EDU_BOARD_ID").val();
                param.EDU_ID = $("#EDU_ID").val();
                param.ORD_NO = $("#ORD_NO").val();

                param.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
                lpCom.Ajax("password",  "/edu/checkEduBoardPassword.do", param, calBackFunc, otherInit);
            });

            //비밀번호 확인 취소
            $("#cancelBtn").on("click", function(e) {
                $("#TEXT_PASSWORD").val("");
                $("#pwConfirmLayer").fadeOut();
            });

            //닫기
            $("#closeBtn").on("click", function(e) {
                window.close();
            });
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    //조회
    var fnSearch = function(pSelectPage) {
        if (!$("#schFrm").valid())
            return false; //form 체크
        $("#selectPage").val(pSelectPage);

        var otherInit = {};
        otherInit.isSuccesMsg = false;

        var param = $("#schFrm").srializeJsonById();
        lpCom.Ajax("search", "/edu/selectEduBoardList.do", param, calBackFunc, otherInit);
    }

    //리스트
    var fnSetList = function(pData){
        var listHtml = [];
        console.log(pData);
        var menuId = $("#MENU_ID").val();

        for(i in pData.list){
            listHtml.push('<tr onclick=fnMoveDetail("' + pData.list[i].ORD_NO + '","' + pData.list[i].PASSWORD_AT + '")>')
            listHtml.push('<td data-title="번호">'+pData.list[i].RNUM+'</td>')

            listHtml.push('<td data-title="제목" class="txtL">')
            if(pData.list[i].TEXT_PASSWORD != "") {
                listHtml.push('<img src="<c:url value='/images/lock_icon.png'/>" alt="비밀글">&nbsp;')
            }
            if(pData.list[i].LV != "1") {
                listHtml.push('<img src="<c:url value='/images/arrow_icon.png'/>" alt="답글" style="margin-left:15px">&nbsp;')
            }
            listHtml.push(pData.list[i].TITLE+'</td>')

            listHtml.push('<td data-title="작성자">'+pData.list[i].REG_NM+'</td>')
            listHtml.push('<td data-title="작성일">'+pData.list[i].REG_DT+'</td>')

            listHtml.push('<td data-title="첨부파일">')
            if(pData.list[i].ATTACH_FILE_AT == "Y") {
                listHtml.push('<img src="<c:url value='/images/ic_file.png'/>" alt="파일 아이콘">')
            }
            listHtml.push('</td>')

            listHtml.push('<td data-title="조회수">'+pData.list[i].LOCKUP_CNT+'</td>')
            listHtml.push('</tr>')
        }

        $("#list").html(listHtml.join(""));

        pData.pageSize = $("#pageSize").val();
        lpCom.setPagination(pData);
    };

    //상세
    var fnMoveDetail = function(ordNo, passwordAt) {
        if(passwordAt == "Y"){
            $("#ORD_NO").val(ordNo);
            $("#pwConfirmLayer").fadeIn();
        }else{
            lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)

            var postData = {};
            postData.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
            lpCom.hrefOrSubmit("/edu/actionEduBoardDetail.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>&ORD_NO=" + ordNo, postData);
        }
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>