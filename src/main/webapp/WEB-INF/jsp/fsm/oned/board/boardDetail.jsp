<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    /**
     * @Class Name : boardDetail.jsp
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
                <input type="hidden" id="MENU_ID" value="<c:out value="${boardInfo.MENU_ID}"/>" />
                <input type="hidden" id="BOARD_ID" value="<c:out value="${boardInfo.BOARD_ID}"/>" />
                <input type="hidden" id="ORD_NO" value="<c:out value="${result.ORD_NO}"/>" />
                <input type="hidden" id="PASSWORD_AT" value="<c:out value="${result.PASSWORD_AT}"/>" />
                <input type="hidden" id="REG_ID" value="<c:out value="${result.REG_ID}"/>" />

                <!-- RIGHT -->
                <div class="right_Wrap">
                    <!-- button -->
                    <div class="top_btn_wrap">
                        <button type="button" id="listBtn" class="sbtn_gray">목록</button>
                        <c:if test="${boardInfo.BOARD_AUTH eq 'Y' && result.UPPER_ORD_NO eq null && sessionScope.USER_INFO.USER_ID eq result.REG_ID}">
                            <button type="button" id="editBtn" class="sbtn_blue">수정</button>
                            <button type="button" id="delBtn" class="sbtn_blue">삭제</button>
                        </c:if>
                    </div>
                    <!-- //button -->
                    <h3><c:out value="${currentMemu.CURRENT_MENU_NM}"/> 상세</h3>
                    
                    <!-- detail -->
                    <div class="tb_Wrap">
                        <table class="table_col txtL responsive" style="border-top: 1px solid #e6e8ec">
                            <colgroup>
                                <col width="15%">
                                <col width="18%">
                                <col width="15%">
                                <col width="18%">
                                <col width="15%">
                                <col width="18%">
                            </colgroup>
                            <tr>
                                <th>제목</th>
                                <td colspan="5"><c:out value="${result.TITLE}" escapeXml="false"/></td>
                            </tr>
                            <tr>
                                <th>작성자</th>
                                <td><c:out value="${result.REG_NM}"/></td>
                                <th>작성일</th>
                                <td><c:out value="${result.REG_DT}"/></td>
                                <th>조회수</th>
                                <td><c:out value="${result.LOCKUP_CNT}"/></td>
                            </tr>
                            <tr>
                                <th>내용</th>
                                <td colspan="5">
                                    <div class="des_div"><c:out value="${result.DES}" escapeXml="false"/></div>
                                </td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="5">
                                    <div id="mapFile" class="dropZone">
                                        <div id="fileList" class="fileList fr"></div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <!-- //detail -->
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
                        <input type="hidden" id="ORD_NO_CK" name="ORD_NO" value="<c:out value="${result.ORD_NO}"/>" />
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
        //첨부파일
        var fileZoneOption = { readOnly:true, fileDivId:"mapFile", atchFileObjId:"atchFile", fileIdValue:"<c:out value="${result.ATTACH_FILE_ID}"/>"};
        lpCom.fileZone(fileZoneOption);
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "del": // 삭제
                lpCom.href("/board/actionBoardList.do?MENU_ID=<c:out value="${boardInfo.MENU_ID}"/>");
                break;
            case "password": // 비밀번호 확인
                fnMoveForm($("#ORD_NO").val(), "N");
                break;
        }

    }
    /*===================================================페이지 초기화,콜백함수 종료=============================*/

    /*===================================================오브젝트 이벤트 활당 시작(lpCom.js 에서 호출됨)=========*/
    var fnSetObjectEvent = function() {
        {//click 이벤트 활당
            //목록
            $("#listBtn").on("click", function(e) {
                lpCom.href("/board/actionBoardList.do?MENU_ID=<c:out value="${boardInfo.MENU_ID}"/>");
            });

            //수정
            $("#editBtn").on("click", function(e) {
//                 lpCom.href("/board/actionBoardForm.do?MENU_ID=<c:out value="${boardInfo.MENU_ID}"/>&BOARD_ID=<c:out value="${boardInfo.BOARD_ID}"/>&ORD_NO="+$("#ORD_NO").val()+"&REG_ID="+$("#REG_ID").val());
                fnMoveForm($("#ORD_NO").val(), $("#PASSWORD_AT").val());
            });

            //비밀번호 확인
            $("#confirmBtn").on("click", function(e) {
                if(!lpCom.isRequired($("#TEXT_PASSWORD")))return ;

                var otherInit = {};
                otherInit.isSuccesMsg = false;

                var param = {};
                param.BOARD_ID = $("#BOARD_ID").val();
                param.ORD_NO = $("#ORD_NO").val();

                param.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
                lpCom.Ajax("password",  "/board/checkBoardPassword.do", param, calBackFunc, otherInit);
            });

            //비밀번호 확인 취소
            $("#cancelBtn").on("click", function(e) {
                $("#TEXT_PASSWORD").val("");
                $("#pwConfirmLayer").fadeOut();
            });
            
            //삭제
            $("#delBtn").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;

                var param = {};
                param.BOARD_ID = "<c:out value="${boardInfo.BOARD_ID}"/>";
                param.ORD_NO = "<c:out value="${result.ORD_NO}"/>";
                param.REG_ID = "<c:out value="${result.REG_ID}"/>";
                param.rowStats = 'D';

                if(!lpCom.actionBeforeConfirm("D",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("del", "/board/saveBoardData.do", param, calBackFunc, otherInit);
            });
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    
    //수정폼
    var fnMoveForm = function(ordNo, passwordAt) {
        if(passwordAt == "Y"){
            $("#ORD_NO").val(ordNo);
            $("#pwConfirmLayer").fadeIn();
        }else{
            lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)

            var postData = {};
            postData.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
            lpCom.hrefOrSubmit("/board/actionBoardForm.do?MENU_ID=<c:out value="${boardInfo.MENU_ID}"/>&BOARD_ID=<c:out value="${boardInfo.BOARD_ID}"/>&ORD_NO="+$("#ORD_NO").val()+"&REG_ID="+$("#REG_ID").val(), postData);
        }
    }
    
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>