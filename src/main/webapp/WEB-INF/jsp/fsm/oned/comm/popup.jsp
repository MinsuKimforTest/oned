<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- layer_popup --->
<c:forEach var="item" items="${bannerList}" varStatus="status">
    <div class="modal" id="modalPopup${status.count}" style="display:block;">
        <div class="modal_content" style="width:840px;">
            <c:if test="${not empty item.BANNER_IMG_DATA}">
                <c:if test="${not empty item.URL}">
                    <a href="${item.URL}" target="_blank">
                </c:if>
                <img src="${item.BANNER_IMG_DATA}" width="100%" alt="${item.BANNER_TITLE}">
                <c:if test="${not empty item.URL}">
                    </a>
                </c:if>
            </c:if>
            <div class="md_btn">
                <a href="#" class="left_btn" onclick="javascript:$('#modalPopup${status.count}').fadeOut();">닫기</a>
            </div>
        </div>
    </div>
</c:forEach>
<!-- //layer_popup --->