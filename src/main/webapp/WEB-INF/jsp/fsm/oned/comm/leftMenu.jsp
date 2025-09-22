<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            <!-- Location -->
            <div class="loca">
                <ul>
                    <li><a href="<c:url value='/main/actionMain.do' />" class="home">홈으로</a></li>
                    <c:forEach var="item" items="${menuList}">
                        <c:if test="${item.MENU_ID eq currentMemu.CURRENT_UPPER_MENU_ID}">
                            <li><a href="<c:url value='${item.URL}' />">${item.MENU_NM}</a></li>
                        </c:if>
                        <c:if test="${item.MENU_ID eq currentMemu.CURRENT_MENU_ID}">
                            <li><a href="<c:url value='${item.URL}' />">${item.MENU_NM}</a></li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
            <!-- //Location -->

            <!--LEFT -->
            <div class="left_menu">
                <ul>
                    <c:forEach var="item" items="${menuList}">
                        <c:if test="${(item.LV eq  '2') && (item.UPPER_MENU_ID eq currentMemu.CURRENT_UPPER_MENU_ID)}">
                            <li><a href="<c:url value='${item.URL}' />" <c:if test="${item.MENU_ID eq currentMemu.CURRENT_MENU_ID}">class="on"</c:if>>${item.MENU_NM}</a></li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
            <!--//LEFT -->
