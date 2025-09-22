/********************************************************
 파일명 : common.js
 설 명 : 공통 JavaScript
 수정일         수정자    Version       Function 명
 -------    -------- ---------- --------------
 2020.05.10    신현우    1.0           최초 생성
 *********************************************************/

/*

 !!! 수정하지말것
  추가하려는 함수는  commonFuncion.js에 추가해주세요

*/
//그리드 기본설정
var myGridID;
var theme = "default"; //그리드 테마
var columnLayout;
var lastRowVal;
var gridTmpList = ""; //신규 저장시 리로드를 하지않기 위해 기존 리스트를 임시로 저장
var auiGridProps = {
	rowIdField: "gridPk"	// 개별 행 아이템의 고유 값을 갖는 Key 필드에 해당되는 필드(pk)
	// 신규 데이터를 입력하는 그리드의 경우 DB 키값을 복사해서 gridPk로 사용해야 함
	// 조회성 그리드인 경우 gridPk 사용할 필요 없음

	, selectionMode: "singleRow"		// 선택모드[singleCell, singleRow, multipleCells, multipleRows, none]
	, noDataMessage: "출력할 데이터가 없습니다."
	, headerHeight: 30
	, rowHeight: 28
	, useGroupingPanel: false						// 그룹핑 패널 사용여부, default:false
	, groupingMessage: "여기에 칼럼을 드래그하면 그룹핑이 됩니다."

	, enableCellMerge: false							// 칼럼 셀 병합(cell merge) 가능 여부, default:false

	, editable: true								// 수정 가능 여부, default:false
	, editBeginMode: "click"						// 마우스로 편집, 수정 모드로 들어가는 정책을 지정, default:"doubleClick"
	, keepEditing: true								// 편집상태 지속여부, default:false

	, showRowNumColumn: false						// 행 줄번호(로우 넘버링) 칼럼의 출력 여부, default:true
	, showStateColumn: true							// 신규,수정,삭제 아이콘 표시, default:false
	, showRowCheckColumn: false						// 행 체크박스 출력여부, dafault:false

	, softRemoveRowMode: false						// 삭제된 행 표시 후 남겨둘지 여부, default:true
	, wrapText: true
	//페이징 설정 시작
	, usePaging: false			// 페이징 사용
	, showPageButtonCount: 10	// 한 화면 페이징 버턴 개수 5개로 지정
	// 페이지 행 개수 select UI 출력 여부 (기본값 : false)
	, showPageRowSelect: true
	, pageRowCount: 20			// 한 화면에 출력되는 행 개수 50개로 지정

};

var now = new Date();
var year = now.getFullYear();
var month = (now.getMonth() + 1) > 9 ? '' + (now.getMonth() + 1) : '0' + (now.getMonth() + 1);
var day = now.getDate() > 9 ? '' + now.getDate() : '0' + now.getDate();
var today = year + '-' + month + '-' + day;	// 오늘날짜
var firstDay = year + '-' + month + '-01'; // 해당 월의 첫날

/*********************************************************************
 함수명 :
 설 명 : 달력을 불러오기 위한 함수
 인 자 :
 사용법 : input 및 button에 data-datepicker를 추가하여 달력 스크립트와 매핑
 작성일 : 2020-05-10
 작성자 : 공통 김진성
 수정일          수정자     수정내용
 ------        ------    -------------------

 *********************************************************************/
$(function () {
	$(document).on("focus", "input[data-datepicker]", function () {
		var opts = {
			showOtherMonths: true
			, selectOtherMonths: true
		};
		$(this).datepicker(opts);

	});

	$(document).on("click", "button[data-datepicker]", function () {
		var altFieldVal = $(this).attr("data-datepicker");
		$("#" + altFieldVal).focus();

	});

	/*
         <style>
              table.ui-datepicker-calendar { display:none; }
          </style>
    */
	$(document).on("focus", "input[data-datepicker-month]", function () {
		var opts = {
			showOtherMonths: true
			, selectOtherMonths: true
			, showButtonPanel: true
		};

		opts.closeText = "선택";
		opts.dateFormat = "yy-mm";
		opts.onClose = function (dateText, inst) {
			var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			$(this).datepicker("option", "defaultDate", new Date(year, month, 1));
			$(this).datepicker('setDate', new Date(year, month, 1));
		};

		opts.beforeShow = function () {
			var selectDate = $(this).val().split("-");
			var year = Number(selectDate[0]);
			var month = Number(selectDate[1]) - 1;
			$(this).datepicker("option", "defaultDate", new Date(year, month, 1));
		};

		$(this).datepicker(opts);

	});

	$(document).on("click", "button[data-datepicker-month]", function () {
		var altFieldVal = $(this).attr("data-datepicker-month");
		$("#" + altFieldVal).focus();

	});


});


var navBarSwc = true;
var navBarMouseStatus;
var this_m = "";
var this_s = "";
if (typeof this_menu !== "undefined") {
	this_menu.substr(4, 2);	// this_menu의 중분류
	this_menu.substr(6, 2); // this_menu의 소분류
}

$(document).ready(function (e) {

	// 좌측 메뉴 오픈
	if (!$("body").hasClass("main")) {
		navBarToggle(true);
	}

	// 좌측 GNB 현재 메뉴가 선택되도록
	initGnb();

	// 위치 및 사이즈 조정
	setPosition();

	// 커스텀 스크롤 적용
	$(".cScroll").niceScroll({styler: "fb", cursorcolor: "#285b9c", cursorborder: "none", horizrailenabled: false});


	$('#quick_button').click(function () {
		$('.quick_menu').toggle();
	});

	//탭 설정

	$("#content").find("[id^='tab']").hide(); // Hide all content
	$("#tabs li:first").attr("id", "current"); // Activate the first tab
	$("#content #tab1").fadeIn(); // Show first tab's content

	$('#tabs a').click(function (e) {
		e.preventDefault();
		if ($(this).closest("li").attr("id") == "current") { //detection for current tab
			return;
		} else {
			$("#content").find("[id^='tab']").hide(); // Hide all content
			$("#tabs li").attr("id", ""); //Reset id's
			$(this).parent().attr("id", "current"); // Activate this
			$('#' + $(this).attr('name')).fadeIn(); // Show content for the current tab
		}
		//탭 내부 컨텐츠 height 체크
		setPosition();
	});

	//탭 설정 끝

});


$(window).resize(function (e) {

	// 위치 및 사이즈 조정
	setPosition();

});


//위치 및 사이즈 조정
function setPosition() {

	if($("#systemContents").length > 0) {
		$("body.main .navToggleBt").hide();
		$("body.main #navBar").hide();
		$("body.main #navBar").css("margin-left","-200px");
		$("body.main #systemContents").css("margin-left","0px");

		$("#systemContents").width($("body").width()-$("#navBar").width()-Number($("#navBar").css("margin-left").replace("px", "")));
		$("#systemContents").height($("body").height()-(typeof $("#systemHeader").outerHeight() == "undefined" ? 0 : $("#systemHeader").outerHeight()));
		$(".scContainer").height($("body").height()-(typeof $("#systemHeader").outerHeight() == "undefined" ? 0 : $("#systemHeader").outerHeight())-$(".sysContentHeader").outerHeight()-40);

		$(".scContainer").css("margin-top",$(".sysContentHeader").outerHeight());
		$("#navBar").height($("body").height()-$("#systemHeader").outerHeight());

		$(".cScroll").niceScroll({styler:"fb",cursorcolor:"#285b9c",cursorborder:"none",horizrailenabled:false}).resize();

	}else if($("#systemPopupContents").length > 0) {

		$("#systemPopupContents").css("margin-left","0px");

		$("#systemPopupContents").height($("body").height()-$("#headerBtnContent").outerHeight()-$("#systemFooter").outerHeight()+30);
		$(".scContainer_popup").height($("body").height()-$("#systemFooter").outerHeight()-80);

		$(".cScroll").niceScroll({styler:"fb",cursorcolor:"#285b9c",cursorborder:"none",horizrailenabled:false}).resize();
	}
	if (typeof gridIDs !== "undefined") {
		fn_resize_all_grid(gridIDs);
	}

	// 크기가 변경되었을 때 AUIGrid.resize() 함수 호출
	if (typeof myGridID !== "undefined") {
		AUIGrid.resize(myGridID);
	}
}

//상단 GNB 롤오버 및 Active
function initGnb() {


	$(".gnb > li > a").click(function (e) {

		if($(this).attr("href") !="#"){
			$(this).parent().addClass("active");
		}

		if($(this).parent().find(".gnbSub").is(':hidden')){
			$(this).parent().find(".gnbSub").show();
			$(this).find(".menuArrow .fa").addClass("fa-angle-down");
			$(this).find(".menuArrow .fa").removeClass("fa-angle-right");
		}else{
			$(this).parent().find(".gnbSub").hide();
			$(this).find(".menuArrow .fa").removeClass("fa-angle-down");
			$(this).find(".menuArrow .fa").addClass("fa-angle-right");
		}
		$(".cScroll").niceScroll().resize();
	});

	return;
	// 페이지에 맞게 GNB 메뉴 활성화
	setGnb(this_m, this_s);

	// GNB 마우스 이벤트
	$(".gnb > li > a").click(function (e) {
		if ($(this).parent().hasClass("active")) {

			$(".gnbSub").css("display", "none");
			$(this).find(".menuArrow .fa").removeClass("fa-angle-down");
			$(this).find(".menuArrow .fa").addClass("fa-angle-right");
			$(this).parent().removeClass("active");
			$(".cScroll").niceScroll().resize();

		} else {

			var thisId = $(this).parent().attr("id").split("_");
			thisId = thisId[1];
			if (thisId == this_m) {
				setGnb(thisId, this_s);
			} else {
				setGnb(thisId);
			}
			$(".cScroll").niceScroll().resize();

		}
	});

	// 현재 페이지 메뉴 자동 선택
	$("#navBar").mouseleave(function (e) {
		navBarMouseStatus = true;
		setTimeout(function () {
			//console.log("navBarMouseStatus out",navBarMouseStatus);
			if (navBarMouseStatus == true) {
				setGnb(this_m, this_s);
			}
		}, 1000);
	});
	$("#navBar").mouseover(function (e) {
		//console.log("navBarMouseStatus in",navBarMouseStatus);
		navBarMouseStatus = false;
	});

}

//GNB 메뉴 활성화
function setGnb(mm, ss) {
	//TODO
	return;

	$(".gnb > li").removeClass("active");

	$(".gnb > li").find(".menuArrow .fa").removeClass("fa-angle-down");
	$(".gnb > li").find(".menuArrow .fa").addClass("fa-angle-right");

	$(".gnbSub").css("display", "none");
	$(".gnbSub > li").removeClass("active");

	if (mm != "00") {
		$("#gnb_" + mm).addClass("active");
		$("#gnb_" + mm).find(".menuArrow .fa").removeClass("fa-angle-right");
		$("#gnb_" + mm).find(".menuArrow .fa").addClass("fa-angle-down");
		$("#gnb_" + mm).find(".gnbSub").css("display", "block");
		$("#gnb_" + mm).find(".gnbSub > li.gnb_s_" + this_menu).addClass("active");
	}
}


//좌측 메뉴 토글 버튼
function navBarToggle(swc) {

	if (swc) {
		navBarSwc = swc;
	} else {
		navBarSwc = !navBarSwc;
	}

	if (navBarSwc == true) {
		$("#navBar").css("margin-left", "0px");
		$("#topLogo").css("display", "block");
		$("#systemContents").css("margin-left", "200px");
	} else {
		$("#navBar").css("margin-left", "-200px");
		$("#topLogo").css("display", "none");
		$("#systemContents").css("margin-left", "0px");
	}
	setPosition();
}


//URL 파라미터 파서
function URLString(obj_name) {

	var qStr = {};
	var query = window.location.search.substring(1);
	var vv = query.split("&");
	for (var i = 0; i < vv.length; i++) {
		var pair = vv[i].split("=");

		if (typeof qStr[pair[0]] === "undefined") {
			qStr[pair[0]] = pair[1];

		} else if (typeof qStr[pair[0]] === "string") {
			var arr = [qStr[pair[0]], pair[1]];
			qStr[pair[0]] = arr;

		} else {
			qStr[pair[0]].push(pair[1]);
		}
	}
	return qStr[obj_name];
};


//버튼링크
function goPage(url) {
	window.location.href = url;
	return false;
}

//Ajax 모달 팝업
function showAjaxModal(url) {

	var $modal = $('#ajax-modal');
	$('body').modalmanager('loading');

	setTimeout(function () {
		$modal.load(url, '', function () {
			$modal.modal();
		});
	}, 1000);
}

/*********************************************************************
 함수명 : fn_popup
 설 명 : 팝업 실행 함수
 인 자 : url(팝업 페이지를 띄울 액션명), target, width(가로값), height(세로값), dvs(1=popup, 2=modal)
 사용법 : 인자값을 입력하여 팝업 실행
 작성일 : 2020-05-20
 작성자 : 공통 김진성
 수정일          수정자     수정내용
 ------        ------    -------------------

 *********************************************************************/
function fn_popup(url, target, width, height, dvs) {
	var style;
	url = encodeURI(url);

	if (dvs == 2) { // 모달
		style = "dialogWidth:" + width + "px;dialogHeight:" + height + "px";
		window.showModalDialog(url, "", style);
	} else { // 팝업
		style = "width=" + width + "px, height=" + height + "px,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes";

		window.open(url, target, style);
	}
}

/*********************************************************************
 함수명 : goJusoPopup
 설 명 : JUSO.GO.KR에서 제공하는 주소 검색 팝업 호울
 인 자 :
 사용법 : 주소창을 호출하는 창에 jusoCallBack 함수 추가하여 원하는 입력필드에 세팅

 roadFullAddr   : 전체 도로명주소
 roadAddrPart1  : 도로명주소(참고항목 제외)
 roadAddrPart2  : 도로명주소 참고항목
 engAddr        : 도로명주소(영문)
 jibunAddr      : 지번주소
 zipNo          : 우편번호
 addrDetail     : 고객 입력 상세 주소
 admCd          : 행정구역코드
 rnMgtSn        : 도로명코드
 siNm           : 시도명
 sggNm          : 시군구명
 emdNm          : 읍면도명
 liNm           : 법정리명
 entX           : X좌표
 entY           : Y좌표
 ------        ------    -------------------

 *********************************************************************/
function goJusoPopup() {
	// 주소검색을 수행할 팝업 페이지를 호출합니다.

	var pop = fn_popup("/flic/comm/searchJusoPopup.do", "juso", "570", "420", "1");
	return pop;
}

/*********************************************************************
 함수명 : fnClearForm
 설 명 : 폼안에 있는 입력값을 초기화 한다.
 인 자 : 초기화하고자 하는 폼ID

 *********************************************************************/
function fnClearForm(oForm) {

	var frm_elements = oForm.elements;
	for (var i = 0; i < frm_elements.length; i++) {
		field_type = frm_elements[i].type.toLowerCase();
		switch (field_type) {
			case "text":
			case "password":
			case "textarea":
			case "hidden":
				frm_elements[i].value = "";
				break;

			case "radio":
			case "checkbox":
				if (frm_elements[i].checked) {
					frm_elements[i].checked = false;
				}
				break;

			case "select-one":
			case "select-multi":
				frm_elements[i].selectedIndex = 0;
				break;

			default:
				break;
		}
	}
}

if (!String.prototype.trim) {
	String.prototype.trim = function () {
		return this.replace(/^[\s\uFEFF\u00A0]+|[\s\uFEFF\u00A0]+$/g, "");
	};
}

//Mozilla, Opera, Webkit
if (document.addEventListener) {
	document.addEventListener("DOMContentLoaded", function () {
		document.removeEventListener("DOMContentLoaded", arguments.callee, false);

		if (typeof documentReady === "function") {
			documentReady();
		}

	}, false);

// If IE event model is used
} else if (document.attachEvent) {
	// ensure firing before onload
	document.attachEvent("onreadystatechange", function () {
		if (document.readyState === "complete") {
			document.detachEvent("onreadystatechange", arguments.callee);
			if (typeof documentReady === "function") {
				documentReady();
			}
		}
	});
}


var results = "";

// 데이터 요청
function requestDataXml(url, xml) {

	var dataLength = 0;

	// ajax 요청 전 그리드에 로더 표시
//	AUIGrid.showAjaxLoader(myGridID);

	// ajax (XMLHttpRequest) 로 그리드 데이터 요청
	ajax({
		url: url,
		onSuccess: function (data) {
			// 그리드 데이터
			var gridData = data;

			dataLength = gridData.length;

			// 로더 제거
			//auiGrid.removeAjaxLoader();
//			AUIGrid.removeAjaxLoader(myGridID);

			if (xml) { // XML 응답인 경우
				if (gridData.nodeType == 9)
					dataLength = gridData.documentElement.childNodes.length;
				else
					dataLength = gridData.childNodes.length;

				// 그리드에 XML 데이터 세팅
				AUIGrid.setXmlGridData(myGridID, gridData);
			} else {
				// 그리드에 데이터 세팅
				AUIGrid.setGridData(myGridID, gridData);
			}
		},
		onError: function (status, e) {
			alert("데이터 요청에 실패하였습니다.\r status : " + status);
		}
	});
};

function requestDataJsonp(url) {
	$.ajax({
		url: url,
		dataType: 'jsonp',
		success: function (data) {

			// 그리드에 데이터 세팅
			AUIGrid.setGridData(myGridID, data);
		},
		error: function (xhr) {
			//console.log('실패 - ', xhr);
		}
	});
}


function requestData(url, formData) {
	$.ajax({
		url: url,
		type: 'POST',
		data: formData,
		async: false,
		success: function (data) {
			results = data;
		},
		error: function (xhr) {
			//console.log('실패 - ', xhr);
		}
	});
}

function requestDataProgress(url, formData, id, callback) {
	$.ajax({
		url: url,
		type: 'POST',
		data: formData,
		async: false,
		beforeSend: function () {
			AUIGrid.showAjaxLoader(id);
		},
		success: function (data) {
			setTimeout(function () {
				AUIGrid.removeAjaxLoader(id);
			}, 1500);

			if (typeof callback == 'function') {
				callback(data);
				return;
			}
			results = data;
		},
		error: function (xhr) {
			//console.log('실패 - ', xhr);
		}
	});
}

function requestDataForm(url, formData) {
	$.ajax({
		url: url,
		type: 'POST',
		//data : formData,
		data: JSON.stringify({
			a: 1,
			b: 2,
			c: [{a: 1, b: 2, c: 3}, {a: 4, b: 5, c: 6}]
		}),
		contentType: "application/json",
		async: false,
		success: function (data) {
			results = data;
			if (results.message != undefined) {
				alert(results.message);
			}
		},
		error: function (xhr) {
			//console.log('실패 - ', xhr);
		}
	});
}

function requestDataJson(url, formData) {
	$.ajax({
		url: url,
		type: "POST",
		dataType: 'json',
		data: formData,
		async: false,
		contentType: "application/json",
		success: function (data) {
			results = data;
			if (results.message != undefined) {
				alert(results.message);
			}

		},
		error: function (e) {
			//console.log('실패 - ', e);
		}
	});
}


function fn_quick_close() {
	$('.quick_menu').hide();
}

function fn_quick_open() {
	$('.quick_menu').show();
}

/*********************************************************************
 함수명 : replaceAll
 설 명 : 자바스크립트에서 자바의 replaceAll을 사용할수 있는 함수
 인 자 : 치환대상, 치환값
 사용법 : url.replaceAll("/","")
 작성일 : 2020-05-10
 작성자 : 공통 김진성
 수정일          수정자     수정내용
 ------        ------    -------------------

 *********************************************************************/
String.prototype.replaceAll = function () {
	var a = arguments, length = a.length;

	if (length == 0) {
		return this;
	}

	var regExp = new RegExp(a[0], "g");

	if (length == 1) {

		return this.replace(regExp, "");
	} else {
		return this.replace(regExp, a[1]);
	}
	return this;
};

/*********************************************************************
 함수명 : numberWithCommas
 설 명 : 숫자 3자리마다 콤마 찍기
 인 자 : 치환값
 사용법 : numberWithCommas(1000)
 작성일 : 2020-05-10
 작성자 : 공통 김진성
 수정일          수정자     수정내용
 ------        ------    -------------------

 *********************************************************************/
function numberWithCommas(x) {

	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

}

function GetColumnLayout() {
	var key = ["dataField", "headerText", "editable", "style", "width"];
	var obj = {};

	var i = 0;
	for (var k in key) {
		obj[key[k]] = arguments[i];
		i += 1;
	}
	if (typeof arguments[i] == 'object') {
		var objArg = arguments[i];
		for (var k in objArg) {
			obj[k] = objArg[k];
		}
	}

	return obj;
}

function GetEditRenderer(list) {
	var obj = {};

	obj["type"] = "DropDownListRenderer";
	obj["list"] = list;
	obj["keyField"] = "code";
	obj["valueField"] = "value";

	return obj;
}

function GetIconRenderer(onClickFnc) {
	var obj = {};

	obj["type"] = "IconRenderer";
	obj["iconWidth"] = 16;
	obj["iconHeight"] = 16;
	obj["iconPosition"] = "aisleRight";
	obj["iconTableRef"] = {"default": "/flic/images/comm/search_over.png"};
	obj["onclick"] = function (rowIndex, columnIndex, value, item) {
		onClickFnc(rowIndex);
	};

	return obj;
}

function setLabelFunction(value, list) {
	var retVal = "";
	for (var i = 0, len = list.length; i < len; i++) {
		if (list[i]["code"] == value) {
			retVal = list[i]["value"];
			break;
		}
	}
	return retVal;
}

function ymdFormatLabel(rowIndex, columnIndex, value, headerText, item) {
	return getyyyymmdd(value);
}

function getDateFormat(day, y, m, d) {
	if (typeof (day) == 'undefined') return '';
	return day.yyyymmdd(y, m, d);
}


function getyyyymmdd(day, y, m, d) {
	if (!day) return '';
//	if(typeof(day) == 'undefined') return '';
//	if(day == null) return '';
	return day.yyyymmdd(y, m, d);
}

String.prototype.yyyymmdd = function (y, m, d) {

	y = typeof y !== 'undefined' ? y : 0;
	m = typeof m !== 'undefined' ? m : 0;
	d = typeof d !== 'undefined' ? d : 0;

	if (!this.valueOf()) return " ";

	var value = this.valueOf();
	value = value.replace(/[^0-9]/gi, '');
	if (value.length == 8) {
		var year = value.substr(0, 4);
		var month = value.substr(4, 2);
		var day = value.substr(6, 2);

		var year = Number(year) + Number(y);
		var month = Number(month) + Number(m) - 1;
		var day = Number(day) + Number(d);
		var tmpDay = new Date(year, month, day);
		var strDay = tmpDay.format("yyyy-MM-dd")
		return strDay;
	} else {
		return value;
	}
}

Date.prototype.format = function (f) {
	if (!this.valueOf()) return " ";

	var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	var d = this;

	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function ($1) {
		switch ($1) {
			case "yyyy":
				return d.getFullYear();
			case "yy":
				return (d.getFullYear() % 1000).zf(2);
			case "MM":
				return (d.getMonth() + 1).zf(2);
			case "dd":
				return d.getDate().zf(2);
			case "E":
				return weekName[d.getDay()];
			case "HH":
				return d.getHours().zf(2);
			case "hh":
				return ((h = d.getHours() % 12) ? h : 12).zf(2);
			case "mm":
				return d.getMinutes().zf(2);
			case "ss":
				return d.getSeconds().zf(2);
			case "a/p":
				return d.getHours() < 12 ? "오전" : "오후";
			default:
				return $1;
		}
	});
};

String.prototype.string = function (len) {
	var s = '', i = 0;
	while (i++ < len) {
		s += this;
	}
	return s;
};
String.prototype.zf = function (len) {
	return "0".string(len - this.length) + this;
};
Number.prototype.zf = function (len) {
	return this.toString().zf(len);
};

function getStr(val) {
	if (!val) val = "";
	return val;
}

function trim(val) {
	if (!val) val = "";
	return val.replace(/(^\s*)|(\s*$)/gi, "");
}

function gridEditComplete(gridIDs) {
	for (var i = 0; i < gridIDs.length; i++) {
		gridID = gridIDs[i].gridID;
		fn_grid_edit_complete(gridID);
	}
}


//그리드에서 신규, 수정, 삭제된 데이터 정보를 받아온다.
function fn_get_modfied_data(gridID) {
	var addedRowItems = AUIGrid.getAddedRowItems(gridID); // 추가된 행 아이템들(배열)
	var editedRowItems = AUIGrid.getEditedRowItems(gridID);  // 수정된 행 아이템들(배열) (수정되지 않은 칼럼들의 값도 가지고 있음)
	var removedRowItems = AUIGrid.getRemovedItems(gridID); // 삭제된 행 아이템들(배열)

	var data = {};

	if (addedRowItems.length > 0) {
		data.add = addedRowItems;
	}

	if (editedRowItems.length > 0) {
		data.update = editedRowItems;
	}

	if (removedRowItems.length > 0) {
		data.remove = removedRowItems;
	}
	return data;
}

//전체 그리드 사이즈 조절
function fn_resize_all_grid(gridIDs) {
	for (var i = 0; i < gridIDs.length; i++) {
		gridID = gridIDs[i].gridID;
		var displayOfGrid = $(gridID).css("display");
		if (displayOfGrid != "none") {
			AUIGrid.resize(gridID);
		}
	}
}

//그리드에 선택된 행이 있는지 여부를 체크
function fn_check_select_grid(gridId) {
	var selectedItems = AUIGrid.getSelectedItems(gridId);
	if (selectedItems == "") {
		alert("선택된 정보가 없습니다.");
		return false;
	} else {
		return true;
	}
}

function fn_export(gridId, fileName, type) {
	// 그리드가 작성한 엑셀, CSV 등의 데이터를 다운로드 처리할 서버 URL을 지시합니다.
	// 서버 사이드 스크립트가 JSP 이라면 export.jsp 로 변환해 주십시오.
	// 스프링 또는 MVC 프레임워크로 프로젝트가 구축된 경우 해당 폴더의 export.jsp 파일을 참고하여 작성하십시오.
	AUIGrid.setProperty(gridId, "exportURL", "/flic/common/export.jsp");
	fileName = encodeURI(fileName);
	// 내보내기 실행
	switch (type) {
		case "xlsx":
			AUIGrid.exportAsXlsx(gridId, true, {fileName: fileName});
			break;
		case "csv":
			AUIGrid.exportAsCsv(gridId, true, {fileName: fileName});
			break;
	}
}

