<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

        <!-- header --->
        <header id="m_header">
            <div class="headerin">
                <h1 class="logo"><a href="<c:url value='/main/actionMain.do' />">국립수산물품질관리원 온라인교육</a></h1>
                <p class="open_menu"><a>메뉴열기</a></p>

                <!-- mobile -->
                <div class="bg_bk"></div>

                <div class="side">
                    <div class="side_in">
                        <nav class="m_gnb">
                            <ul>
                                <c:forEach var="item" items="${menuList}">
                                    <c:if test="${item.LV eq  '1'}">
                                        <li class="d1">
                                            <c:if test="${not fn:contains(item.MENU_NM, '나의정보')}">
                                                <a href="<c:url value='${item.URL}' />" class="m"><c:out value="${item.MENU_NM}"/><span class="gnb-border"></span></a>
                                                <div class="sub">
                                                    <ul>
                                                        <c:forEach var="item2" items="${menuList}">
                                                            <c:if test="${(item.MENU_ID eq item2.UPPER_MENU_ID) && (item2.LV eq '2')}">
                                                                <li><a href="<c:url value='${item2.URL}' />"><c:out value="${item2.MENU_NM}"/></a></li>
                                                            </c:if>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </c:if>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </nav>
                    </div>
                    <div class="lang">
                        <ul>
                            <c:if test="${empty sessionScope.USER_INFO}">
                                <li><a href="https://www.nfqs.go.kr/cvmg/actionRegistForm.do" target="_blank">회원가입</a></li>
                                <li><a href="#" class="login_btn">로그인</a></li>
                            </c:if>
                            <c:if test="${!empty sessionScope.USER_INFO}">
                                <li><a href="<c:url value="/user/actionUserInfoForm.do"/>">마이페이지</a></li>
                                <li><a href="<c:url value="/organicCert/actionOrganicCertNoForm.do"/>">인증번호관리</a></li>
                                <li><a href="<c:url value="/login/actionLogout.do"/>">로그아웃</a></li>
                            </c:if>
                        </ul>
                    </div>
                    <p class="close_menu"><a href="javascript:;">메뉴닫기</a></p>
                </div>
                <!-- //mobile -->

                <!-- pc -->
                <nav class="gnb_pc">
                    <ul>
                        <c:forEach var="item" items="${menuList}">
                            <c:if test="${item.LV eq  '1'}">
                                <c:if test="${not fn:contains(item.MENU_NM, '나의정보')}">
                                    <li class="d1">
                                        <a href="<c:url value='${item.URL}' />" class="m"><c:out value="${item.MENU_NM}"/><span class="gnb-border"></span></a>
                                        <div class="sub">
                                            <ul>
                                                <c:forEach var="item2" items="${menuList}">
                                                    <c:if test="${(item.MENU_ID eq item2.UPPER_MENU_ID) && (item2.LV eq '2')}">
                                                        <li><a href="<c:url value='${item2.URL}' />"><c:out value="${item2.MENU_NM}"/></a></li>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </li>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </ul>

                    <!-- utill_pc -->
                    <ul class="utill">
                        <c:if test="${empty sessionScope.USER_INFO}">
                            <li><a href="https://www.nfqs.go.kr/cvmg/actionRegistForm.do" target="_blank">회원가입</a></li>
                            <li><a href="#" class="login_btn">로그인</a></li>
                        </c:if>
                        <c:if test="${!empty sessionScope.USER_INFO}">
                            <li><a href="<c:url value="/user/actionUserInfoForm.do"/>">마이페이지</a></li>
                            <li><a href="<c:url value="/login/actionLogout.do"/>">로그아웃</a></li>
                        </c:if>
                    </ul>
                    <!--//utill_pc -->
                </nav>
            </div>

            <div class="subBg"></div>
        </header>
        <!-- //header --->

        <!-- mobile login layer_popup --->
        <c:if test="${empty sessionScope.USER_INFO}">
            <div class="modal" id="mobileLoginLayer">
                <div class="modal_content" style="width:450px;">
                    <a href="#;" class="close_btn" id="loginCloseBtn">
                        <img src="<c:url value='/images/btn_close_b.png'/>" alt="닫기">
                    </a>
                    <div class="md_head">
                        <h1>로그인</h1>
                    </div>
                    <form id="mLoginFrm" onsubmit="return false">
                        <div class="md_cont">
                            <div class="login_Wrap" style="height: 300px">
                                <div class="login">
                                    <div class="login_input">
                                        <input type="text" id="M_USER_ID" class="form" placeholder="아이디를 입력하세요" caption="아이디" required />
                                        <input type="password" id="M_PASSWORD" class="form" placeholder="비밀번호를 입력하세요" caption="비밀번호" required />
                                    </div>
                                    <a href="#" id="mLoginBtn" class="btn_login">로그인</a>
                                </div>
                                <div class="log_save_Wrap">
                                    <div class="log_save">
                                        <fieldset class="ckdbox">
                                            <input type="checkbox" name="" id="ckd">
                                            <label for="ckd"></label>
                                        </fieldset>
                                        아이디저장
                                    </div>
                                    <ul class="login_find">
                                        <li><a href="javascript:;">아이디 찾기</a></li>
                                        <li><a href="javascript:;">비밀번호 찾기</a></li>
                                    </ul>
                                </div>

                                <div class="join">
                                    <p class="btxt">아직 회원이 아니신가요?</p>
                                    <p class="stxt">회원가입하시면 온라인교육 강의수강 및 <br>수료증을 발급하실 수 있습니다. </p>
                                    <a href="" class="sbtn_join">회원가입</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>
        <!-- //mobile login layer_popup --->

