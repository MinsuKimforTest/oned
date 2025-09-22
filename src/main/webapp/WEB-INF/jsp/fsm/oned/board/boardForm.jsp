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
                <input type="hidden" id="REG_ID" value="<c:out value="${result.REG_ID}"/>" />

                <!-- RIGHT -->
                <div class="right_Wrap">
                    <!-- button -->
                    <div class="top_btn_wrap">
                        <button type="button" id="listBtn" class="sbtn_gray">취소</button>
                        <c:if test="${boardInfo.BOARD_AUTH eq 'Y'}">
                            <button type="button" id="saveBtn" class="sbtn_blue">저장</button>
                        </c:if>
                    </div>
                    <!-- //button -->

                    <h3><c:out value="${currentMemu.CURRENT_MENU_NM}"/> 글쓰기</h3>

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
                                <th>제목 <em>(필수)</em></th>
                                <td colspan="5">
                                    <input type="text" id="TITLE" style="width: 100%" value="<c:out value="${result.TITLE}"/>" caption="제목" required>
                                </td>
                            </tr>
                            <c:if test="${boardInfo.SECRET_AT eq 'Y'}">
                                <tr>
                                    <th>비밀번호</th>
                                    <td colspan="5">
                                        <input type="password" id="TEXT_PASSWORD" />
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <th>내용</th>
                                <td colspan="5">
                                    <textarea id="DES" class="des_text"><c:out value="${result.DES}" escapeXml="false"/></textarea>
                                </td>
                            </tr>
                            <tr>
                                <th>첨부파일</th>
                                <td colspan="5">
                                    <div id="mapFile" class="dropZone">
                                        <div class="btn-group fl" role="group">
                                            <button type="button" id="btnFileSearch" class="btn-file" onclick="document.getElementById('atchFile').click();">파일첨부</button>
                                            <input type="file" id="atchFile" style="display: none;" multiple="multiple" />
                                            <input type="hidden" id="ATTACH_FILE_ID" value="<c:out value="${result.ATTACH_FILE_ID}"/>"/>
                                        </div>
                                        <div class="fileDragDesc fr"> 여기로 파일을 끌어놓으세요. </div>
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

<%@include file="../comm/inc_javascript.jsp"%>
<script type="text/javascript" src="<c:url value="/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/ckeditor/editor.js"/>"></script>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        //첨부파일
        var fileZoneOption = { fileDivId:"mapFile", atchFileObjId:"atchFile", fileIdValue:"<c:out value="${result.ATTACH_FILE_ID}"/>"};
        lpCom.fileZone(fileZoneOption);

        //내용 에디터
        CKEDITOR.replace("DES");
    });

    /**
     * 콜백 함수
     */
    var calBackFunc = function(pSid, pData) {
        switch (pSid) {
            case "save": // 저장
                var postData = {};
                postData.TEXT_PASSWORD = $("#TEXT_PASSWORD").val();
                lpCom.hrefOrSubmit("/board/actionBoardDetail.do?MENU_ID=<c:out value="${boardInfo.MENU_ID}"/>&BOARD_ID=<c:out value="${boardInfo.BOARD_ID}"/>&ORD_NO="+pData.ordNo, postData);
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

            //저장
            $("#saveBtn").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;
                otherInit.formObj = $("#dataFrm");

                var param = {};
                param.rowStats = '${empty result.ORD_NO ? "I":"U"}';
                param.DES_CK= CKEDITOR.instances.DES.getData();
                param.REG_ID= '${result.REG_ID}';

                if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("save", "/board/saveBoardData.do", param, calBackFunc, otherInit);
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