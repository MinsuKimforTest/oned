<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    /**
     * @Class Name : eduBoardForm.jsp
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
                <input type="hidden" id="EDU_BOARD_TYPE" value="<c:out value="${boardInfo.EDU_BOARD_TYPE}"/>">

                <!-- button -->
                <div class="top_btn_wrap">
                    <button type="button" id="listBtn" class="sbtn_gray">취소</button>
                    <c:if test="${boardInfo.EDU_BOARD_TYPE eq '3'}">
                        <button type="button" id="saveBtn" class="sbtn_blue">저장</button>
                    </c:if>
                </div>
                <!-- //button -->

                <h3><c:out value="${boardInfo.EDU_BOARD_TITLE}"/> 글쓰기</h3>

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
                            <th>제목 <em>(필수)</em></th>
                            <td colspan="5">
                                <input type="text" id="TITLE" style="width: 100%" value="<c:out value="${result.TITLE}" escapeXml="false"/>" caption="제목" required>
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
                lpCom.hrefOrSubmit("/edu/actionEduBoardDetail.do?EDU_BOARD_ID=<c:out value="${paramMap.EDU_BOARD_ID}"/>&EDU_ID=<c:out value="${paramMap.EDU_ID}"/>&ORD_NO="+pData.ordNo, postData);
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

            //저장
            $("#saveBtn").on("click", function(e) {
                var otherInit = {};
                otherInit.isSuccesMsg = true;
                otherInit.formObj = $("#dataFrm");

                var param = {};
                param.rowStats = '${empty result.ORD_NO ? "I":"U"}';
                param.DES_CK= CKEDITOR.instances.DES.getData();
                
                console.log("param.rowStats => ", param.rowStats)
                console.log("param.DES_CK => ", param.DES_CK)

                if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
                lpCom.Ajax("save", "/edu/saveEduBoardData.do", param, calBackFunc, otherInit);
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
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>