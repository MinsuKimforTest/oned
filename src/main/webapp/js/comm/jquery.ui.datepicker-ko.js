/********************************************************
파일명 : jquery.ui.datepicker-ko.js
설 명 : 달력 공통 JavaScript
수정일         수정자    Version       Function 명
-------    -------- ---------- --------------
2020.05.10    김진성    1.0           최초 생성

*********************************************************/
/* Korean initialisation for the jQuery calendar extension. */
/* Written by DaeKwon Kang (ncrash.dk@gmail.com), Edited by Genie. */
jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearRange: 'c-100:c+60',
		minDate: new Date(1920, 1 - 1, 1),
		maxDate: '+3Y',
		yearSuffix: '년',
		//showOtherMonths: true,
		//selectOtherMonths: true,
		changeYear: true,
        changeMonth: true
    };
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});