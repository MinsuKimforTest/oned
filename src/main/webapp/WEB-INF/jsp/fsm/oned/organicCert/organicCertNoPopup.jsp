<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- layer_popup --->
<form id="certFrm" onsubmit="return false">
    <div id="certDiv" class="modal">
        <div class="modal_content" style="width:640px;">
            <div class="md_head">
                <h1>친환경 인증번호</h1>
            </div>
            <div class="md_cont">
                <!-- step 1 -->
                <div class="step_wrap">
                    <div class="step_div">
                        <span class="step">STEP 1</span>
                        <span class="step_txt">사업자 구분</span>
                    </div>
                    <div class="sel_wrap">
                        <div class="sel">
                            <label for="CERT_GUBUN1">
                                <div class="i-radio">
                                    <input type="radio" id="CERT_GUBUN1" name="CERT_GUBUN" value="1" onclick="fnSelectCertGugun('1', '')">
                                    <em class="ico"></em>
                                </div>
                                <h2>최초사업자</h2>
                                <ul>
                                    <li>친환경 인증번호를 부여받지않은 사업자</li>
                                    <li>친환경 교육을 한번도 받지않은 사업자</li>
                                    <li>신규 인증을 위해 신규 교육이 필요한 사업자</li>
                                </ul>
                            </label>
                        </div>
                        <div class="sel">
                            <label for="CERT_GUBUN2">
                                <div class="i-radio">
                                    <input type="radio" id="CERT_GUBUN2" name="CERT_GUBUN" value="2" onclick="fnSelectCertGugun('2', '')">
                                    <em class="ico"></em>
                                </div>
                                <h2>기존사업자(갱신)</h2>
                                <ul>
                                    <li>친환경 인증번호를 부여받은 사업자</li>
                                    <li>친환경 교육을 받은 적이 있는 사업자</li>
                                    <li>갱신 인증을 위해 갱신 교육이 필요한 사업자</li>
                                </ul>
                            </label>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <!-- //step 1 -->
                <!-- step 2 -->
                <div class="step_wrap">
                    <div class="step_div">
                        <span class="step">STEP 2</span>
                        <span class="step_txt">인증번호 등록</span>
                    </div>
                    <div class="md_input">
                        <table class="table_col responsive" style="margin-top: 0">
                            <colgroup>
                                <col width="20%">
                                <col width="80%">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th style="height: 48px; font-weight: bold">인증번호</th>
                                    <td id="certNoDiv"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- //step 2 -->

                <!-- info -->
                <div class="info-txt">
                    <img src="<c:url value='/images/info.png'/>" alt="정보" style="width: 15px">
                    친환경 인증번호를 모르시면, <a href="https://www.nfqs.go.kr/hpmg/data/actionMarineOrganicCertificationForm.do?menuId=M0000229" target="_blank" style="font-weight: bold">여기</a>를 클릭하여 확인해주십시오.
                </div>
                <div class="info-txt">
                    <img src="<c:url value='/images/info.png'/>" alt="정보" style="width: 15px">
                    국립수산물품질관리원 홈페이지에 있는 인증번호와 동일하게 입력해주십시오.(-포함, 8자리)
                </div>
                <!-- //info -->
            </div>
            <div class="md_btn">
                <a href="#" class="left_btn md_close">취소</a>
                <a href="#" id="certSubmitBtn">저장</a>
            </div>
        </div>
    </div>
</form>
<!-- //layer_popup --->