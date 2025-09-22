<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    /**
     * @Class Name : eduBoardDetail.jsp
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
            <form id="dataFrm" onsubmit="return false">
                <input type="hidden" id="EDU_BOARD_ID" value="<c:out value="${paramMap.EDU_BOARD_ID}"/>">
                <input type="hidden" id="EDU_ID" value="<c:out value="${paramMap.EDU_ID}"/>">
                <input type="hidden" id="ORD_NO" value="<c:out value="${paramMap.ORD_NO}"/>">
                <input type="hidden" id="REG_ID" value="<c:out value="${result.REG_ID}"/>" />
                <input type="hidden" id="PASSWORD_AT" value="<c:out value="${result.PASSWORD_AT}"/>" />
                <input type="hidden" id="EDU_BOARD_TYPE" value="<c:out value="${boardInfo.EDU_BOARD_TYPE}"/>">

                <!-- button -->
                <div class="top_btn_wrap">
                    <button type="button" id="listBtn" class="sbtn_gray">목록</button>
                    <c:if test="${boardInfo.EDU_BOARD_TYPE eq '3' && result.UPPER_ORD_NO eq null && sessionScope.USER_INFO.USER_ID eq result.REG_ID}">
                        <button type="button" id="editBtn" class="sbtn_blue">수정</button>
                        <button type="button" id="delBtn" class="sbtn_blue">삭제</button>
                    </c:if>
                </div>
                
                <!-- //button -->
                <h3><c:out value="${boardInfo.EDU_BOARD_TITLE}"/> 상세</h3>
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

                <!-- detail -->
                <div class="table_popup">
                    <table class="table_col responsive" style="border-top: 1px solid #e6e8ec">
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
                lpCom.href("/edu/actionEduBoardList.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>");
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
                lpCom.href("/edu/actionEduBoardList.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>");
            });

            //수정
            $("#editBtn").on("click", function(e) {
//                 lpCom.href("/edu/actionEduBoardForm.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>&ORD_NO="+$("#ORD_NO").val()+"&REG_ID="+$("#REG_ID").val());
                fnMoveForm($("#ORD_NO").val(), $("#PASSWORD_AT").val());
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

            //삭제
            $("#delBtn").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;

                var param = {};
                param.EDU_BOARD_ID = "<c:out value="${result.EDU_BOARD_ID}"/>";
                param.EDU_ID = "<c:out value="${result.EDU_ID}"/>";
                param.ORD_NO = "<c:out value="${result.ORD_NO}"/>";
                param.rowStats = 'D';

                if(!lpCom.actionBeforeConfirm("D",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("del", "/edu/saveEduBoardData.do", param, calBackFunc, otherInit);
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
    
    //수정폼
    var fnMoveForm = function(ordNo, passwordAt) {
        if(passwordAt == "Y"){
            $("#ORD_NO").val(ordNo);
            $("#pwConfirmLayer").fadeIn();
        }else{
            lpCom.setSearchCond(); //페이지 이동 검색조건 파라메터 저장(폼 ID는 schFrm 해야 작동함)

            var postData = {};
            postData.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
            lpCom.hrefOrSubmit("/edu/actionEduBoardForm.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>&ORD_NO=" +$("#ORD_NO").val()+"&REG_ID="+$("#REG_ID").val(), postData);
        }
    }
    
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>