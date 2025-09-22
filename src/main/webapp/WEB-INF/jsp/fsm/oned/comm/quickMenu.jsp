<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty sessionScope.USER_INFO}">
<!-- quick menu --->
<div class="quick_menu">
    <h1 class="tit">QUICK MENU</h1>
    <ul class="menu">
        <li><a href="<c:url value='/edu/actionEduPlanList.do' />">친환경 교육신청</a></li>
        <li><a href="<c:url value='/edu/actionEduPlanHaccpList.do' />">HACCP 교육신청</a></li>
        <li><a href="<c:url value='/edu/actionEduClassRoomList.do' />">나의강의실</a></li>
        <li><a href="<c:url value='/edu/actionEduStatusList.do' />">이수증 출력</a></li>
        <li><a href="<c:url value='/organicCert/actionOrganicCertNoForm.do' />">친환경 인증번호</a></li>
        <li><a href="https://www.nfqs.go.kr/cvmg/" target="_blank">민원시스템</a></li>
    </ul>
</div>
<!-- //quick menu --->
</c:if>