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
<body style="overflow: hidden">
    <!-- form -->
    <form id="dataFrm" onsubmit="return false">
        <input type="hidden" id="EDU_APLY_NO" value="<c:out value="${result.EDU_APLY_NO}"/>">
        <input type="hidden" id="EDU_ID" value="<c:out value="${result.EDU_ID}"/>">
        <input type="hidden" id="ORD_NO" value="<c:out value="${result.ORD_NO}"/>">
        <input type="hidden" id="ORD_CNT" value="<c:out value="${result.ORD_CNT}"/>">
        <input type="hidden" id="STUDY_TIME" value="<c:out value="${result.STUDY_TIME}"/>">
        <input type="hidden" id="GBN" value="<c:out value="${result.GBN}"/>">
        <input type="hidden" id="saveTime">

        <!-- vod_Wrap -->
        <div class="vod_Wrap">
            <div class="vod">
                <!-- vod -->
                <div class="vod_LT pushmenu-push">
                    <div class="vod_title">
                        <ul>
                            <li class="first"><c:out value="${result.EDU_TITLE}"/><span class="arrow-next"></span></li>
                            <li><strong><c:out value="${result.TITLE}"/></strong></li>
                        </ul>
                    </div>

                    <div class="vod_view">
                        <!--컨트롤-->
                        <div class="control_Wrap">
                            <div class="control_l">
                                <div class="basic_controls">
                                    <a id="play" class="sbtn_play">재생</a>
                                    <a id="playStop" class="sbtn_stop">처음으로</a>
                                    <a id="playBack" class="sbtn_pre">10초 뒤로</a>
                                    <a id="playGo" class="sbtn_next">10초 앞으로</a>
                                </div>

                                <!-- time bar -->
                                <div class="time_Wrap">
                                    <div class="bar">
                                        <input type="range" id="seekBar" class="bar-gauge" min="0" value="0" style="width: 100%">
                                    </div>
                                    <span class="timer">
                                        <span id="currentTime">00:00</span>
                                        <span> / </span>
                                        <span id="durationTime">00:00</span>
                                    </span>
                                </div>
                                <!-- //time bar -->
                            </div>

                            <div class="control_r">

                              <!-- sound -->
                                <div class="sound_Wrapper">
                                    <div id="mute" class="sound_icon"><span>음량</span></div>
                                    <div class="sound_Wrap">
                                        <input type="range" id="volumeBar" min="0" max="10" step="1" value="5">
                                    </div>
                                </div>
                                <!-- sound -->

                                <select name="select" id="playbackSpeed" class="select_form">
                                    <option value="0.25">x0.25</option>
                                    <option value="0.5">x0.5</option>
                                    <option value="0.75">x0.75</option>
                                    <option value="1" selected>x1(보통)</option>
<%--                                    <option value="1.25">x1.25</option>--%>
<%--                                    <option value="1.5">x1.5</option>--%>
<%--                                    <option value="1.75">x1.75</option>--%>
<%--                                    <option value="2">x2</option>--%>
                                </select>
                                <a id="fullScreen" class="sbtn_expand">확대</a>
                            </div>

                        </div>
                        <!--//컨트롤-->

                        <!-- mobile -->
                        <%--<div class="mobile_controls vod_blank">
                            <a class="sbtn_play pause"><span>재생</span></a>
                        </div>--%>

                        <!-- video -->
                        <div class="view">
                            <video id="myPlayer" class="video-js vjs-default-skin vjs-big-play-button vjs-big-play-centered vjs-fluid vjs-fullscreen"></video>
                            <div class="vod_blank">
                                <div class="vod_btn" style="opacity: 0.5;"></div>
                            </div>
                        </div>
                        <!-- //video -->
                    </div>

                    <div class="btm_control_btn">
                        <c:if test="${result.ORD_NO eq '1'}">
                            <a id="btnPreClass" class="arrow_prev"><span>첫번쨰수업</span></a>
                        </c:if>
                        <c:if test="${result.ORD_NO ne '1'}">
                            <a id="btnPreClass" class="arrow_prev on"><span>이전 수업</span></a>
                        </c:if>
                        <a class="learning"><span>학습중</span></a>
                        <c:if test="${result.ORD_NO eq result.ORD_CNT}">
                            <a id="btnNextClass" class="arrow_next"><span>마지막수업</span></a>
                        </c:if>
                        <c:if test="${result.ORD_NO ne result.ORD_CNT}">
                            <a id="btnNextClass" class="arrow_next on"><span>다음 수업</span></a>
                        </c:if>
                    </div>
                </div>
                <!-- //vod -->

                <!-- 강의목록 -->
                <div class="vod_RT pushmenu-right">
                    <a href="javascript:frameclose()" class="cont_close"><img src="../images/sbtn_close.png" alt="닫기"></a>
                    <div class="cont_head">
                        <h1>목차</h1>
                        <div class="tit"><c:out value="${result.EDU_TITLE}"/></div>
                        <div class="con">
                            <span><strong>교육기간</strong> : <c:out value="${result.STUDY_START_DT} ~ ${result.STUDY_END_DT}"/></span>
                            <span><strong>교육시간</strong> : <c:out value="${result.EDU_TIME}시간 / ${result.ORD_CNT}차시"/></span>
                        </div>
                    </div>
                    <div class="cont_list">
                        <h2>${result.EDU_TITLE}</h2>
                        <ul>
                            <c:forEach var="item" items="${classData}" varStatus="status">
                                <li <c:if test="${item.ORD_NO eq result.ORD_NO}">class="on"</c:if>>
                                    <a onclick="fnMoveClass('${item.ORD_NO}');" style="cursor: pointer;">
                                        <c:out value="${item.TITLE}"/>
                                        <span><c:out value="${item.CLASS_TIME==''? '00:00':item.CLASS_TIME}"/></span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- //강의목록 -->

                <div class="vod_Btn">
                    <a href="javascript:;" class="contlist push-btn"><span>목차</span></a>
                    <a id="btnClose" class="closed"><span>학습종료</span></a>
                </div>
            </div>
        </div>
        <!-- //vod_Wrap -->
    </form>
    <!-- //form -->

<%@include file="../comm/inc_javascript.jsp"%>
<!-- video js -->
<script src="<c:url value="/js/video/video.min.js"/>"></script>
<script src="<c:url value="/js/classViewPlayer.js"/>"></script>
<script>
    /*===================================================페이지 초기화,콜백함수 시작=============================*/
    $(document).ready(function() {
        var playerOption = { fileIdValue:"<c:out value="${result.ATTACH_FILE_ID}"/>", preload:"metadata", playsinline:true };
        classViewPlayer("myPlayer", playerOption, "videoControls");
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
            //닫기
            $("#closeBtn").on("click", function(e) {
                opener.location.reload();
                window.close();
            });
        }

        {//change 이벤트 활당
        }
    }
    /*===================================================오브젝트 이벤트 활당 종료(lpCom.js 에서 호출됨)=========*/

    /*===================================================사용자 함수 설정 시작 =================================*/
    var fnMoveClass = function(ordNo){
        lpCom.href("/edu/actionEduClassDetail.do?EDU_APLY_NO=<c:out value="${result.EDU_APLY_NO}"/>&EDU_ID=<c:out value="${result.EDU_ID}"/>&ORD_NO="+ordNo);
    }
    /*===================================================사용자 함수종료 시작 =================================*/
</script>
</body>
</html>