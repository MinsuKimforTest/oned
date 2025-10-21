$(document).ready(function () {
    /**
     * 오브젝트 name 값 자동으로 ID 값을 Name 으로 자동으로 설정
     */
    $("input, select, textarea").each(function () {
        if (lpCom.isEmpty(this.name) && !lpCom.isEmpty(this.id)) this.name = this.id;
    });


    lpCom.setValidator(); //폼값 체크 설정

    /**
     * ID로  Serialize  생성 return : 문자
     */
    $.fn.serializeById = function () {
        var returning = "";
        $("input, select, textarea", this).each(function () {
            if (this.type !== "button" && this.type !== "submit") // check this to avoid && in returning string
                if (this.type === "checkb") {
                    if ($(this).is(":checked")) {
                        returning += '&' + this.id + "=" + $(this).attr("chkvalue");
                    } else {
                        returning += '&' + this.id + "=" + $(this).attr("notchkvalue");
                    }
                } else if (this.type == "radio") {
                    returning += '&' + this.id + "=" + $(':radio[name=' + this.name + ']:checked').val();
                } else {
                    returning += '&' + this.id + "=" + this.value;
                }
        });
        return returning.substring(1);
    };

    /**
     * NAME 으로  Serialize  생성 return : 문자
     */
    $.fn.serializeByName = function () {
        var returning = "";
        $("input, select, textarea", this).each(function () {
            if (this.type != "button" && this.type != "submit") // check this to avoid && in returning string
                if (this.type == "checkbox") {
                    if ($(this).is(":checked")) {
                        returning += '&' + this.name + "=" + $(this).attr("chkvalue");
                    } else {
                        returning += '&' + this.name + "=" + $(this).attr("notchkvalue");
                    }
                } else if (this.type == "radio") {
                    returning += '&' + this.name + "=" + $(':radio[name=' + this.name + ']:checked').val();
                } else {
                    returning += '&' + this.name + "=" + this.value;
                }

        });
        return returning.substring(1);
    };

    /**
     * ID로  Serialize  생성 return : JSON 오브젝트
     */
    $.fn.srializeJsonById = function () {
        var returning = {};
        $("input, select, textarea", this).each(function () {
            if (this.type != "button" && this.type != "submit") { // check this to avoid && in returning string
                if (this.type == "checkbox") {
                    if ($(this).is(":checked")) {
                        returning[this.id] = $(this).attr("chkvalue");
                    } else {
                        returning[this.id] = $(this).attr("notchkvalue");
                    }
                } else if (this.type == "radio") {
                    returning[this.id] = $(':radio[name=' + this.name + ']:checked').val();
                } else {
                    returning[this.id] = this.value;
                }
            }
        });

        return returning;
    };

    /**
     * NAME 으로 Serialize  생성 return : JSON 오브젝트
     */
    $.fn.srializeJsonByName = function () {
        var returning = {};
        $("input, select, textarea", this).each(function () {
            if (this.type != "button" && this.type != "submit") // check this to avoid && in returning string
                if (this.type == "checkbox") {
                    if ($(this).is(":checked")) {
                        returning[this.name] = $(this).attr("chkvalue");
                    } else {
                        returning[this.name] = $(this).attr("notchkvalue");
                    }
                } else if (this.type == "radio") {
                    returning[this.name] = $(':radio[name=' + this.name + ']:checked').val();
                } else {
                    returning[this.name] = this.value;
                }
        });

        return returning;
    };

    //숫자만 입력 설정
    $.fn.inputFilter = function (inputFilter) {
        return this.on("input keydown keyup mousedown mouseup select contextmenu drop", function () {
            if (inputFilter(this.value)) {
                this.oldValue = this.value;
                this.oldSelectionStart = this.selectionStart;
                this.oldSelectionEnd = this.selectionEnd;
            } else if (this.hasOwnProperty("oldValue")) {
                this.value = this.oldValue;
                this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
            }
        });
    };


    //niceSelect 화면표시 변경
    $.fn.valNice = function (val) {
        $(this).val(val);
        $(this).niceSelect("update"); // niceSelect 사용시
        $(this).trigger("change");
    };

    //CheckBox default 값설정
    $.fn.valCheckBox = function () {
        if ($(this).is(":checked")) {
            return $(this).attr("chkvalue");
        } else {
            return $(this).attr("notchkvalue");
        }
    };

    //동적 테이블 설정위함 함수
    $.fn.getRowStats = function () {
        return $(this).attr("rowStats");
    };

    $.fn.setRowStats = function (pRowStats) {
        $(this).attr("rowStats", pRowStats);
        if (pRowStats == "D") {
            $(this).css("display", "none");
        } else {
            $(this).css("display", "");
        }
    };

    //오브젝트 readOnly 설
    $.fn.setReadOnly = function (pReadOnly) {
        lpCom.setSelfReadOnly($(this), pReadOnly);
    };

    //오브젝트 null 확인
    $.fn.isEmpty = function () {
        return lpCom.isEmpty($(this).val());
    };

    //오브젝트 null 확인
    $.fn.getMultiselectVal = function () {
        if ($(this).prop("tagName") === "SELECT" && !lpCom.isEmpty($(this).attr("multiple"))) {
            var list = $(this).val();
            if (lpCom.isEmpty(list)) return JSON.stringify([]);

            return JSON.stringify($(this).val());
        } else {
            return this.value;
        }

    };


    //오브젝트 이벤트 활당
    try {
        if (fnSetObjectEvent != undefined) {
            if (typeof (fnSetObjectEvent) == 'function') {
                fnSetObjectEvent();
            }
        }
    } catch (e) {
    }

    //닫기버튼은 공통처리
    $("#closeBtn").on('click', function () {
        window.close();
    });

    var ua = navigator.userAgent;
    var trident = ua.match(/Trident\/(\d.\d)/i);
    if (navigator.appName.indexOf("Microsoft") > -1 || trident != null) {
        lpCom.isIEbrowser = true;
    }

    // date 오브젝트 설정
    // $("[type=date]").attr("readonly", true);
    // $("[type=date]").css("width", "80px").css("text-align", "center").attr("type", "date1").datepicker(lpCom.datepickerDefault);
    //$("[type=date]").attr("readonly", true);
    // $("[type=date]").attr("readonly", true);
    // $("[type=date]").attr("readonly", true).attr("type", "date1").datepicker(lpCom.datepickerDefault);

    //오브젝트 attribute 자동 설정
    $("input, select, checkbox", this).each(function () {
        if ($(this).attr("type") == "date1" || $(this).attr("type") == "date") {
            //var calendarObj = $("<span class='btn_calendar' onclick='javascript:$(\"#" + this.id + "\").val(\"\"); $(\"#" + this.id + "\").datepicker(\"show\");'></span>"); // 매개변수에 해당하는 Element가 만들어진다.
            //$(this).after(calendarObj);
        } else if ($(this).prop("tagName") == "SELECT") {
            //if (!$(this).prop("multiple")) {
                //$(this).niceSelect();
            //}
            //셀렉트 박스 설정
            //$('.select_st1').niceSelect();
        } else if (this.type == "checkbox") {
            if ($(this).attr("chkvalue") == undefined) {
                //$(this).attr("value", "Y");
                $(this).attr("chkvalue", "Y");
                $(this).attr("notchkvalue", "N");
            }
        } else {
            //숫자만 입력하게 설정 정수만
            if ($(this).attr("digits") != undefined) {
                $(this).inputFilter(function (value) {
                    return /^\d*$/.test(value);
                });
            }
            if ($(this).attr("number") != undefined) {
                $(this).inputFilter(function (value) {
                    return /^-?\d*[.,]?\d*$/.test(value);
                });
            }

            {//GIS 방위 범위 설정
                //위도 : 0 ~ 90
                if ($(this).attr("latDegree") != undefined) {
                    $(this).inputFilter(function (value) {
                        if (/^\d*$/.test(value) && Number(value) >= 0 && Number(value) <= 90) {
                            return true;
                        } else {
                            return false;
                        }

                    });
                }

                //경도 : 0 ~ 60
                if ($(this).attr("lonDegree") != undefined) {
                    $(this).inputFilter(function (value) {
                        if (/^\d*$/.test(value) && Number(value) >= 0 && Number(value) <= 180) {
                            return true;
                        } else {
                            return false;
                        }

                    });
                }
                //분 : 0 ~ 180
                if ($(this).attr("latLonMinute") != undefined) {
                    $(this).inputFilter(function (value) {
                        if (/^-?\d*[.,]?\d*$/.test(value) && Number(value) >= 0 && Number(value) <= 60) {
                            return true;
                        } else {
                            return false;
                        }

                    });
                }
            }
        }
    });

    lpCom.setPlaceHolder(); //textbox 설명문구 표현

    /*
    * 같은 값이 있는 열을 병합함
    * 사용법 : $('#테이블 ID').rowspan(0);
    */
    $.fn.rowspan = function (colIdx, isStats) {
        return this.each(function () {
            var that;
            $('tr', this).each(function (row) {

                $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
                    if ($(this).html() == $(that).html()
                        && (!isStats
                            || isStats && $(this).prev().html() == $(that).prev().html()
                        )
                    ) {
                        rowspan = $(that).attr("rowspan") || 1;
                        rowspan = Number(rowspan) + 1;

                        $(that).attr("rowspan", rowspan);
                        $(this).hide();

                    } else {
                        that = this;
                    }
                    // set the that if not already set
                    that = (that == null) ? this : that;
                });
            });
        });
    };

    { //String prototype 추가 설정

        //null,공백확인
        String.prototype.isEmpty = function () {
            return lpCom.isEmpty(this);
        };
    }

    //a태그 버튼 클릭스 상단스크롤 움직임 막기
    $('a[href="#"]').click(function (e) {
        e.preventDefault();
        //event.preventDefault() -> 현재 이벤트의 기본 동작을 중단
        //event.stopPropagation() -> 현재 이벤트가 상위로 전파되지 않도록 중단
        //event.stopImmediatePropagation() -> 현재 이벤트가 상위뿐 아니라 현재 레벨에 걸린 다른 이벤트도 동작하지 않도록 중단
    });

    //left 메뉴 클릭시
    // $('#left_bx a[href], #top_bx a[href]').click(function (e) {
    //     //조건문 조기화
    //     localStorage.clear(); //클리어
    //
    //     //내부 링크 일때만
    //     if ($(this).attr("href").substring(0, 1) == "/") {
    //         var ajaxSetup = {
    //             url: lpCom.contextPath + "/manager/setMenuAccessStatAt.do"
    //             , data: {}
    //             , contentType: "application/x-www-form-urlencoded"
    //             , async: true
    //             , type: "POST"
    //             , dataType: "json"
    //             , success: function (data) {
    //
    //             }
    //             , beforeSend: function (jqXHR, settings) {
    //
    //             }
    //             , complete: function () {
    //
    //             }
    //             , error: function (data) {
    //
    //             }
    //         };
    //
    //         $.ajax(ajaxSetup);
    //     }
    //
    // });

    //lpCom.contextPath = "";

    // aui grid 보기변경
    $("#togglePagingBtn").on('click', function () {
        if ($(this).text() == "전체보기") {
            $(this).text("페이징보기");
            lpAuiGrid.setInitAuiGrid(StdGrid, {usePaging: false});
        } else {
            $(this).text("전체보기");
            lpAuiGrid.setInitAuiGrid(StdGrid, {usePaging: true});
        }
        fnSearch();
    });



});

var lpCom = {};

lpCom.isIEbrowser = false;
lpCom.datepickerDefault = {
    dateFormat: 'yy-mm-dd',
    numberOfMonths: 1,
    showButtonPanel: true,
    dateFormat: 'yy-mm-dd',
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    showMonthAfterYear: true,
    yearSuffix: '년',
    closeText: '닫기',
    currentText: '오늘',
    changeMonth: true, //콤보 박스에 월 보이기
    changeYear: true // 콤보 박스에 년도 보이기
};

lpCom.setValidator = function () {

    $.validator.addMethod(
        'alpha', function (value, element) {
            var pattern = /[^a-zA-Z\s]/gi;
            var match = pattern.test(value);
            return match;
        }, '영문 만 입력가능 합니다.'
    );

    $.validator.addMethod(
        'alphaDigits', function (value, element) {
            var pattern = /[^a-zA-Z0-9\s]/gi;
            var match = pattern.test(value);
            return match;
        }, '영문, 숫자 만 입력가능 합니다.'
    );

    $.validator.addMethod(
        'alphaDigitsHyphen', function (value, element) {
            var pattern = /[^a-zA-Z0-9\-\s]/gi;
            var match = pattern.test(value);
            return match;
        }, '영문, 숫자, 하이픈 만 입력가능 합니다.'
    );

    $.validator.addMethod(
        'lengthEquals', function (value, element, option) {
            return option == value.length ? true : false;
        }, '문자의 길이는 {0}자만 가능 합니다 '
    );

    /*$.validator.addMethod(
        'password', function (value, element) {
            if (value == "") return true;
            var pattern = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/;
            var match = pattern.test(value);
            return match;
        }, '8~20자 영문,숫자,특수문자 포함를 해야 만 가능합니다.'
    );*/

    $.validator.addMethod(
        'userPassword', function (value, element) {
            if (value == "") return true;
            var pattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*.?_])[A-Za-z\d~!@#$%^&*.?_]{9,100}$/;
            var match = pattern.test(value);
            return match;
        }, '최소 하나의 영문자, 숫자, 특수문자(~!@#$%^&*.?_)를 포함하여 9~30자리로 입력해주십시오.'
    );

    //부모값 먼저 체크후 필수체크
    $.validator.addMethod(
        'preRequired', function (value, element, option) {
            var id = $(element).attr("id");
            if ($("#" + option).val() == "" || $("#" + id + " option").size() == 1) {
                return true;
            } else {
                return value != "" ? true : false;
            }
        }, '필수 항목입니다.'
    );


    $.extend(jQuery.validator.messages, {
        required: "필수 항목입니다."
        , remote: "항목을 수정하세요."
        , email: "유효하지 않은 E-Mail주소입니다."
        , url: "유효하지 않은 URL입니다."
        , date: "올바른 날짜를 입력하세요."
        , dateISO: "올바른 날짜(ISO)를 입력하세요."
        , number: "유효한 숫자가 아닙니다."
        , digits: "숫자만 입력 가능합니다."
        , creditcard: "신용카드 번호가 바르지 않습니다."
        , equalTo: "같은 값을 다시 입력하세요."
        , extension: "올바른 확장자가 아닙니다."
        , maxlength: jQuery.validator.format("{0}자를 넘을 수 없습니다. ")
        , minlength: jQuery.validator.format("{0}자 이상 입력하세요.")
        , rangelength: jQuery.validator.format("문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요.")
        , range: jQuery.validator.format("{0} 에서 {1} 사이의 값을 입력하세요.")
        , max: jQuery.validator.format("{0} 이하의 값을 입력하세요.")
        , min: jQuery.validator.format("{0} 이상의 값을 입력하세요.")
    });

    jQuery.validator.setDefaults({
        onkeyup: false,
        onclick: false,
        onfocusout: false,
        focusInvalid: true,
        showErrors: function (errorMap, errorList) {
            if (this.numberOfInvalids()) {
                var thisForm = this.currentForm.id;
                var thisId = $(errorList[0].element).attr("id");
                var msg = "";
                var label = $("#" + thisForm + " label[for=" + thisId + "]").html();
                var caption = $(errorList[0].element).attr("caption");
                if (caption != undefined) {
                    msg = caption + " 은(는) " + errorList[0].message;
                } else if (label != undefined) {
                    msg = label + " 은(는) " + errorList[0].message;
                } else {
                    msg = errorList[0].message;
                }
                //niceSelect 사용시만 적용 해야함 포커스는 못함
                //$("SELECT").css("display", "none");
                lpCom.setPlaceHolder();  //textbox 설명문구 표현
                alert(msg);
                $(errorList[0].element).focus();
            }
        }
    });
};


/**
 * 필수 입력체크
 */
lpCom.isRequired = function (pObj, pMsg) {
    if (lpCom.isEmpty(pObj.val())) {
        var label = $("label[for=" + pObj.attr("id") + "]").html();
        var caption = pObj.attr("caption");
        if (caption != undefined) {
            msg = caption + " 은(는) 필수 입력 값입니다.";
        } else if (label != undefined) {
            msg = label + " 은(는) 필수 입력 값입니다.";
        } else {
            msg = pMsg || "필수 입력 값입니다.";
        }
        alert(msg);
        $(pObj).focus();
        return false;
    } else {
        return true;
    }
};

/**
 * 트렌젝션 하기전 함수
 */
lpCom.actionBeforeConfirm = function (pMode, pFromObj) {
    switch (pMode) {
        case "S":
            msg = "저장 하시겠습니까?";
            break;
        case "I":
            msg = "등록 하시겠습니까?";
            break;
        case "U":
            msg = "수정 하시겠습니까?";
            break;
        case "D":
            msg = "삭제 하시겠습니까?";
            break;
        case "SD":
            msg = "선택한 항목을 삭제 하시겠습니까?";
            break;
        default:
            msg = pMode + " 하시 겠습니까?";
            break;
    }
    if (pFromObj != undefined) {
        //$("SELECT[required],SELECT[preRequired]").css("display", "block"); //niceSelect 사용시만 필요함 필수 입력값 체크
        if (!pFromObj.valid()) return false; //form 체크
    }
    var ret = confirm(msg);
    if (pFromObj != undefined) {
        //$("SELECT[required],SELECT[preRequired]").css("display", "none"); //niceSelect 사용시만 필요함 필수 입력값 체크
    }
    return ret;
};


/**
 * null, 공백 체크
 */
lpCom.isEmpty = function (pStr) {
    if (String(pStr) == "" || pStr == undefined || pStr == null || pStr == "undefined") {
        return true;
    } else {
        return false;
    }
};

/**
 * null, 공백 체크 일때 대체 값 리턴
 */
lpCom.isEmptyReplace = function (pStr, pRStr) {

    if (lpCom.isEmpty(pStr)) {
        return pRStr;
    } else {
        return pStr;
    }
};


/**
 * 사용자 정의 select 설정
 */
lpCom.setUserCodeSelect = function(pTargetId,pParam){
    pParam.pUrl = "/comm/selectUserCodeList.do";
    return lpCom.setComSelect(pTargetId,pParam)
}
/**
 * 공통코드 select 설정
 * 예제
 * 1.1개사용 => lpCom.setCodeSelect("BOARD_TYPE",{CODE_UPPER:"C03"],first:"A"});
 * 2.여러개 사용(코드값이 같을때) => lpCom.setCodeSelect(["BOARD_TYPE","BOARD_TYPE1"],{CODE_UPPER:"C03",first:"A"});
 * 3.여러개 사용(코드값이 다를때) => lpCom.setCodeSelect(["BOARD_TYPE","BOARD_TYPE1"],{CODE_UPPER:["C03","C04"],first:"A"});
 */

lpCom.setCodeSelect = function(pTargetId,pParam){
    pParam.pUrl = "/comm/selectCodeList.do";
    pParam.cond = {MSTR_CD:pParam.MSTR_CD};
    return lpCom.setComSelect(pTargetId,pParam)
}

/**
 * 콤보박스 아이템 설정
 *
 */
lpCom.setComSelect = function(pTargetId,pParam){

    if(!pParam.hasOwnProperty("first")) pParam.first = "A"; // A:전체,S:선택, A S 가 아니면 그대로 출력
    if(!pParam.hasOwnProperty("cond")) pParam.cond = {};
    if(!pParam.hasOwnProperty("notValue")) pParam.notValue = [];
    if(!pParam.hasOwnProperty("selectCd")) pParam.selectCd = "";
    if(!pParam.hasOwnProperty("labelNm")) pParam.labelNm = "KOR"; //표시명 디폴터 국문 ==> [KOR,ENG,JPN,CHN,ETC]
    if(!pParam.hasOwnProperty("multiple")) pParam.multiple = ""; //멀티설렉트 여부
    if(!pParam.hasOwnProperty("dMapCd1")) pParam.dMapCd1 = ""; //상세맵핑코드1
    if(!pParam.hasOwnProperty("dMapCd2")) pParam.dMapCd2 = ""; //상세맵핑코드1

    var comboData = [];
    var targetArr = [];

    if(!$.isArray(pTargetId)){
        targetArr.push(pTargetId);
    }else{
        targetArr = pTargetId;
    }

    //공통코드일때만
    if(pParam.cond.hasOwnProperty("MSTR_CD")){
        var condMstrCdArr = [];
        if(!$.isArray(pParam.cond.MSTR_CD)){
            //대상오브젝트 갯수별 설정
            for(var i=0; i<targetArr.length; i++){
                condMstrCdArr.push(pParam.cond.MSTR_CD);
            }
        }else{
            condMstrCdArr = pParam.cond.MSTR_CD;
        }

        if(targetArr.length != condMstrCdArr.length){
            alert("공통코드 대상오브젝트 배열 갯수와 조건 코드 배열 갯수가 같아야 합니다.");
            return;
        }

        pParam.cond.MSTR_CD = JSON.stringify(condMstrCdArr);
        pParam.cond.labelNm = pParam.labelNm;
        pParam.cond.dMapCd1 = pParam.dMapCd1;
        pParam.cond.dMapCd2 = pParam.dMapCd2;
    }

    var chkNotValue = {};
    for(var i=0; i<pParam.notValue.length; i++){
        chkNotValue[pParam.notValue[i]] = true;
    }

    $.ajax({
        url : lpCom.contextPath+pParam.pUrl,
        data : pParam.cond,
        async : false,
        type : "POST",
        success : function(data){
            comboData = data.list;
        },
        error:function(request,status,error){
            alert(error);
        }
    });


    switch(pParam.first){
        case "S":
            comboData.unshift({CD:"",CD_NM:"---선택---"});
            break;
        case "A":
            comboData.unshift({CD:"",CD_NM:"---전체---"});
             break;
        case "X":
            break;
        default:
            comboData.unshift({CD:"",CD_NM:pParam.first});
            break;
    }

     if(pTargetId != "" ){
         for(var j=0; j<targetArr.length; j++){
             var tagetId = targetArr[j];
             $("#"+tagetId+" > option").remove();
                 for(var i=0; i<comboData.length; i++){
                     //공통코드일때만
                     if(pParam.cond.hasOwnProperty("MSTR_CD")){
                         if(comboData[i].CD !="" && comboData[i].MSTR_CD != condMstrCdArr[j]) continue;
                     }
                     //제거코드값
                     if(chkNotValue.hasOwnProperty(comboData[i].CD)) continue;
                     $("#"+tagetId).append("<option value='"+comboData[i].CD+"'>"+comboData[i].CD_NM+"</option>");

                 }

             if(!lpCom.isEmpty($("#"+tagetId).attr("value"))){
                 pParam.selectCd = $("#"+tagetId).attr("value"); // 초기값 셋팅시 오브젝트에 value 값이 우선적용됨
                 $("#"+tagetId).removeAttr("value");
             }

             if(pParam.selectCd != ""){
                 $("#"+tagetId).val(pParam.selectCd);
             }else{
                 if(pParam.multiple != "Y") {
                     $("#" + tagetId + " option:eq(0)").attr('selected', 'selected'); // 첫번째 option selected
                 }
             }
             $("#"+tagetId).trigger("change");
             pParam.selectCd = "";
         }

    }else{

         //제거 코드값
         comboData = comboData.filter(function(value) {
            return !chkNotValue.hasOwnProperty(value.CD)
         });

        //아이디 없을때 콤보 데이터 리턴
        return comboData;
    }

    if(pParam.multiple == "Y"){
        $('.multiSelect').multiselect({
            includeSelectAllOption: true,
            selectAllText: '---전체---',
            nonSelectedText: '---전체---',
            maxHeight: 500
        });
    }
};

/**
 * Ajax 통신
 */

 lpCom.Ajax = function (pSid, pUrl, pParam, pCallBackFunc, pOtherInit) {

    if (pOtherInit == undefined) pOtherInit = {};
    pOtherInit.isSuccesMsg = lpCom.isEmpty(pOtherInit.isSuccesMsg) ? true : pOtherInit.isSuccesMsg;
    pOtherInit.isErrCallBackFunc = lpCom.isEmpty(pOtherInit.isErrCallBackFunc) ? false : pOtherInit.isErrCallBackFunc;
    pOtherInit.isAsync = lpCom.isEmpty(pOtherInit.isAsync) ? true : pOtherInit.isAsync;
    pOtherInit.resultMsg = lpCom.isEmpty(pOtherInit.resultMsg) ? "처리 완료 하였습니다." : pOtherInit.resultMsg;
    pOtherInit.isShowLoding = lpCom.isEmpty(pOtherInit.isShowLoding) ? false : pOtherInit.isShowLoding;

    var ajaxSetup = {
        url: lpCom.contextPath + pUrl
        , data: pParam
        , contentType: "application/x-www-form-urlencoded"
        , async: pOtherInit.isAsync
        , type: "POST"
        , dataType: "json"
        , success: function (data) {
            data.result = "Success";
            data.resultMsg = pOtherInit.resultMsg;
            if (pOtherInit.isSuccesMsg) alert(pOtherInit.resultMsg);
            if (pCallBackFunc != undefined) {
                if (typeof (pCallBackFunc) == 'function') {
                    pCallBackFunc(pSid, data);
                } else {
                    alert("콜백 함수가 없습니다.");
                }
            }
        }
        , beforeSend: function (jqXHR, settings) {
            if (pOtherInit.isShowLoding) {
                //this.lodingDiv = lpCom.loadingDivCreate();
                // document.body.appendChild(this.lodingDiv);
                window.top.document.body.appendChild(this.lodingDiv); //전체 화면 표시 할때
            }

            if (settings.dataType === 'binary') {
                settings.xhr().responseType = 'arraybuffer';
                settings.processData = false;
            }

        }
        , complete: function () {
            //  document.body.removeChild(this.lodingDiv);
            if (pOtherInit.isShowLoding) {
                window.top.document.body.removeChild(this.lodingDiv); //전체 화면 표시 할때
            }
        }
        , error: function (data) {
            if (data.status == 400) {
                alert(data.responseJSON.resultMsg);
                if (pOtherInit.isErrCallBackFunc) {
                    if (pCallBackFunc != undefined) {
                        if (typeof (pCallBackFunc) == 'function') {
                            pCallBackFunc(pSid, data.responseJSON);
                        } else {
                            alert("콜백 함수가 없습니다.");
                        }
                    }
                }
            } else if (data.status == "9999") {
                alert(data.responseText); // 오류메세지
                //로그인 페이지로 이동
                top.location.href = lpCom.contextPath + "/main/main.do";
            } else {
                //페이지 에러 페이지로 이동할지
                alert("관리자에게 문의 바랍니다.");
            }
        }
    };

    if (pOtherInit.hasOwnProperty("formObj")) {
        if (pOtherInit.formObj.length == 0) {
            alert("해당 form 이 정의 되지 않았습니다.");
            return;
        }
        pOtherInit.formObj.ajaxSubmit(ajaxSetup);    //form 전송(파일)
    } else {
        $.ajax(ajaxSetup);
    }
};

/**
 * var formData = new FormData();  생성후 전송 Ajax
 *
 */
lpCom.formDataAjax = function (pSid, pUrl, pFormData, pCallBackFunc, pOtherInit) {
    if (pOtherInit == undefined) pOtherInit = {};
    pOtherInit.isSuccesMsg = lpCom.isEmpty(pOtherInit.isSuccesMsg) ? true : pOtherInit.isSuccesMsg;
    pOtherInit.isErrCallBackFunc = lpCom.isEmpty(pOtherInit.isErrCallBackFunc) ? false : pOtherInit.isErrCallBackFunc;
    pOtherInit.isAsync = lpCom.isEmpty(pOtherInit.isAsync) ? true : pOtherInit.isAsync;
    pOtherInit.resultMsg = lpCom.isEmpty(pOtherInit.resultMsg) ? "처리 완료 하였습니다." : pOtherInit.resultMsg;

    $.ajax({
        url: lpCom.contextPath + pUrl
        , type: "post"
        , data: pFormData
        , contentType: false
        , processData: false
        , cache: false
        , xhr: function () {
            myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', function (event) {
                    var percent = parseInt((event.loaded / event.total) * 100) + "%";
                    $("#fileUploadProgressStat").css("width", percent);
                    $("#fileUploadProgressStat").html(percent + " ");

                    if (percent == "100%") {
                        $("#fileUploadProgress1").remove();
                        $("#fileUploadProgress2").remove();
                    }
                }, false);
            }
            return myXhr;
        }
        , beforeSend: function () {
            this.lodingDiv = lpCom.loadingDivCreate();
            window.top.document.body.appendChild(this.lodingDiv); //전체 화면 표시 할때
            $(this.lodingDiv).css("display", "table");

            var progressDiv = " <div style='display: table-cell; vertical-align: middle;text-align: center; '> " +
                "        <div id='fileUploadProgress1' style='display: inline-block;border:2px solid burlywood;width:600px;text-align:center;background-color:burlywood;color:blanchedalmond'>파일 업로드중 ... </div><br>" +
                "        <div id='fileUploadProgress2' style='display: inline-block;border:2px solid #ffe690;margin-top:3px;margin-bottom:3px;width:600px;background-color: #fffa90;'>" +
                "            <div id='fileUploadProgressStat' style='height:30px;line-height:30px;background-color:rgba(254, 255, 0, 0.81);width:0%;margin-top: 1px;margin-bottom: 1px;text-align:right;font-size:22px;color:ff3e01'></div>" +
                "        </div>" +
                "    </div> ";

            $(this.lodingDiv).html(progressDiv);
        }
        , complete: function () {
            window.top.document.body.removeChild(this.lodingDiv); //전체 화면 표시 할때
        }
        , success: function (data) {
            data.result = "Success";
            data.resultMsg = pOtherInit.resultMsg;
            if (pOtherInit.isSuccesMsg) alert(pOtherInit.resultMsg);
            if (pCallBackFunc != undefined) {
                if (typeof (pCallBackFunc) == 'function') {
                    pCallBackFunc(pSid, data);
                } else {
                    alert("콜백 함수가 없습니다.");
                }
            }
        }
        , error: function (data) {
            if (data.status == 400) {
                alert(data.responseJSON.resultMsg);
                if (pOtherInit.isErrCallBackFunc) {
                    if (pCallBackFunc != undefined) {
                        if (typeof (pCallBackFunc) == 'function') {
                            pCallBackFunc(pSid, data.responseJSON);
                        } else {
                            alert("콜백 함수가 없습니다.");
                        }
                    }
                }
            } else {
                //페이지 에러 페이지로 이동할지
                alert("관리자에게 문의 바랍니다.");
            }
        }
    });
};


lpCom.formDataAjax1 = function (pSid, pUrl, pFormData, pCallBackFunc, pOtherInit, pFileObjNm) {
    if (pOtherInit == undefined) pOtherInit = {};
    pOtherInit.isSuccesMsg = lpCom.isEmpty(pOtherInit.isSuccesMsg) ? true : pOtherInit.isSuccesMsg;
    pOtherInit.isErrCallBackFunc = lpCom.isEmpty(pOtherInit.isErrCallBackFunc) ? false : pOtherInit.isErrCallBackFunc;
    pOtherInit.isAsync = lpCom.isEmpty(pOtherInit.isAsync) ? true : pOtherInit.isAsync;
    pOtherInit.resultMsg = lpCom.isEmpty(pOtherInit.resultMsg) ? "처리 완료 하였습니다." : pOtherInit.resultMsg;

    $.ajax({
        url: lpCom.contextPath + pUrl
        , type: "post"
        , data: pFormData
        , contentType: false
        , processData: false
        , cache: false
        , xhr: function () {
            myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', function (event) {
                    var percent = parseInt((event.loaded / event.total) * 100) + "%";
                    $("#" + pFileObjNm).css("width", percent).html(percent + " ");

                }, false);
            }
            return myXhr;
        }
        , beforeSend: function () {
            //  this.lodingDiv = lpCom.loadingDivCreate();
            //window.top.document.body.appendChild(this.lodingDiv); //전체 화면 표시 할때
        }
        , complete: function () {
            // window.top.document.body.removeChild(this.lodingDiv); //전체 화면 표시 할때
        }
        , success: function (data) {
            data.result = "Success";
            data.resultMsg = pOtherInit.resultMsg;
            if (pOtherInit.isSuccesMsg) alert(pOtherInit.resultMsg);
            if (pCallBackFunc != undefined) {
                if (typeof (pCallBackFunc) == 'function') {
                    pCallBackFunc(pSid, data);
                } else {
                    alert("콜백 함수가 없습니다.");
                }
            }
        }
        , error: function (data) {
            if (data.status == 400) {
                alert(data.responseJSON.resultMsg);
                if (pOtherInit.isErrCallBackFunc) {
                    if (pCallBackFunc != undefined) {
                        if (typeof (pCallBackFunc) == 'function') {
                            pCallBackFunc(pSid, data.responseJSON);
                        } else {
                            alert("콜백 함수가 없습니다.");
                        }
                    }
                }
            } else {
                //페이지 에러 페이지로 이동할지
                alert("관리자에게 문의 바랍니다.");
            }
        }
    });
};

/**
 * 공통 팝업
 * */
lpCom.winOpen = function (url, title, params, opt) {

    if (url.substring(0, 1) == "/") { //절대 경로 일때는
        url = lpCom.contextPath + url;
    }

    // opt : left , top , width , height , toolbar , menubar , scrollbars , status , resizable
    // toolbar , menubar , scrollbars , status , resizable은 Default값을 가짐
    if (opt == undefined) opt = {};
    if (params == undefined) params = {};

    var optStr = "";
    opt.width = opt.width || "600";
    opt.height = opt.height || "400";

    if (opt.width != null && opt.height != null) {
        var left = screen.width / 2 - (Number(opt.width) / 2);
        var top = screen.height / 2 - (Number(opt.height) / 2);
        optStr += "left=" + left + ",top=" + top;
    }
    for (var o in opt) {
        optStr += "," + o.toString() + "=" + opt[o].toString();
    }

    var paramStr = "";
    var cnt = 0;
    for (var p in params) {
        if (cnt > 0) {
            paramStr += "&";
        } else {
            paramStr += "?";
        }
        paramStr += p.toString() + "=" + params[p].toString();
        cnt++;
    }

    cnt = 0;
    if (optStr.toLowerCase().indexOf("toolbar") == "-1") {
        optStr += ",toolbar=no";
    }
    if (optStr.toLowerCase().indexOf("menubar") == "-1") {
        optStr += ",menubar=no";
    }
    if (optStr.toLowerCase().indexOf("status") == "-1") {
        optStr += ",status=no";
    }
    if (optStr.toLowerCase().indexOf("scrollbars") == "-1") {
        optStr += ",scrollbars=no";
    }
    if (optStr.toLowerCase().indexOf("resizable") == "-1") {
        optStr += ",resizable=no";
    }

    if(!opt.hasOwnProperty("postData")){
         window.open(url + encodeURI(paramStr), title, optStr);
    }else{
         //submit 전송
        window.open("about:blank", title, optStr);
        var $form = $('<form ></form>');
        $form.attr("target",title);
        $form.attr("method","post");
        $form.appendTo('body');
        $form.attr("action",url + encodeURI(paramStr));
        for(var inputId in opt.postData){
            $form.append("<input type='hidden' value='"+opt.postData[inputId]+"' name='"+inputId+"'>");
        }

        $form.submit();
        $form.remove();
    }


};


/**
 * 현재 날짜 , 이전, 이후 날짜 가지고 오기
 */
lpCom.getNowPreNextDay = function (pDayCnt, pDate) {

    var newDate = new Date(pDate);
    if (pDate == undefined) newDate = new Date();
    if (pDayCnt == undefined) var pDayCnt = 0;

    newDate.setDate(newDate.getDate() + pDayCnt);
    var year = newDate.getFullYear();
    var month = newDate.getMonth() + 1;
    var date = newDate.getDate();

    var retval;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    var delimiter = "-";
    return year + delimiter + month + delimiter + date;
};

/**
 * 현재 날짜 , 이전(월), 이후(월) 날짜 가지고 오기
 */
lpCom.getNowPreNextMonth = function (pMonthCnt, pDate) {

    var newDate = new Date(pDate);
    if (pDate == undefined) newDate = new Date();
    if (pMonthCnt == undefined) var pMonthCnt = 0;

    newDate.setDate(newDate.getDate());
    newDate.setMonth(newDate.getMonth() + pMonthCnt);

    var year = newDate.getFullYear();
    var month = newDate.getMonth()+1;
    var date = newDate.getDate();

    var retval;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    var delimiter = "-";
    return year + delimiter + month + delimiter + date;
};


lpCom.getJsonPreNextDay = function (pDate,pDayCnt,pDelimiter) {

    if(pDate.length == 8) {
         pDate =  pDate.substr(0,4)+"-"+pDate.substr(4,2)+"-"+pDate.substr(6,2)
    }

    var newDate = new Date(pDate);
    newDate.setDate(newDate.getDate() + pDayCnt);

    var year = newDate.getFullYear();
    var month = newDate.getMonth()+1;
    var day = newDate.getDate();

    var retval;
    if (month < 10) month = "0" + month;
    if (day < 10) day = "0" + day;
    if (pDelimiter == undefined) var pDelimiter = "-";
    var date  =year+pDelimiter+month+pDelimiter+day;

    return {year:year,month:month,day:day,date:date};
};

lpCom.getJsonNowPreNextMonth = function (pMonthCnt, pDate) {

    var newDate = new Date(pDate);
    if (pDate == undefined) newDate = new Date();
    if (pMonthCnt == undefined) var pMonthCnt = 0;

    newDate.setDate(newDate.getDate());
    newDate.setMonth(newDate.getMonth() + pMonthCnt);

    var year = newDate.getFullYear();
    var month = newDate.getMonth()+1;
    var date = newDate.getDate();

    var retval;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    var delimiter = "-";
    return {year:year,month:month,date:date};
};

/**
 * FORM action submit
 */
lpCom.submit = function (pUrl, pFormNm) {
    var form = document.getElementsByTagName("form");

    var targetForm = null;
    if (pFormNm == undefined) var pFormNm = "frm";
    for (var i = 0; i < form.length; i++) {
        if (form[i].name == pFormNm) {
            targetForm = form[i];
            form[i].method = "post";
            break;
        }
    }

    if (targetForm == null) {
        alert("해당 폼 정보가 없습니다.(default:frm)");
        return;
    }

    if (pUrl.substring(0, 1) == "/") { //절대 경로 일때는
        pUrl = lpCom.contextPath + pUrl;
    }

    targetForm.action = pUrl;
    targetForm.submit();
};

/**
 * location.href
 */
lpCom.href = function (pUrl) {
    if (pUrl.substring(0, 1) == "/") { //절대 경로 일때는
        pUrl = lpCom.contextPath + pUrl;
    }

    location.href = pUrl;
};

/**
 * 동적 submit
 */
lpCom.hrefOrSubmit = function (pUrl,pPostData) {

     if(Object.keys(pPostData).length != 0){
        if (pUrl.substring(0, 1) == "/") { //절대 경로 일때는
            pUrl = lpCom.contextPath + pUrl;
        }
        var $form = $('<form ></form>');
        $form.attr("method","post");
        $form.appendTo('body');
        $form.attr("action",pUrl);
        for(var inputId in pPostData){
            $form.append("<input type='hidden' value='"+pPostData[inputId]+"' name='"+inputId+"'>");
        }

        $form.submit();
        $form.remove();
     }else{
         lpCom.href(pUrl);
     }
};


/**
 * enter 처리
 * */
lpCom.enterEventId = function (id_name, fn_name) {
    $("#" + id_name).keyup(function (event) {
        if (event.keyCode == 13) {
            eval(fn_name);
            return;
        }
    });
};

/**
 * ROW 카운트 콤보박스 설정
 *
 * */
lpCom.setComboRowCnt = function (pTargetId) {
    var comboData = [];
    comboData.push({CD: 10, CD_NM: "10개씩 보기"});
    comboData.push({CD: 50, CD_NM: "50개씩 보기"});
    comboData.push({CD: 100, CD_NM: "100개씩 보기"});
    comboData.push({CD: 200, CD_NM: "200개씩 보기"});
    comboData.push({CD: 99999999, CD_NM: "전체 보기"});

    $("#" + pTargetId).css("border", "1px solid #ddd");
    $("#" + pTargetId).css("font-family", "Arial,Helvetica,sans-serif");
    $("#" + pTargetId + " > option").remove();
    comboData.forEach(function (rowData) {
        $("#" + pTargetId).append("<option value='" + rowData.CD + "'>" + rowData.CD_NM + "</option>");
    });
};

/**
 * 고정 콤보 박스 설정
 *
 * */
lpCom.setFixCombo = function (pTargetId, pParam) {
    if(!pParam.hasOwnProperty("first")) pParam.first = "A"; // A:전체,S:선택, A S 가 아니면 그대로 출력
    if(!pParam.hasOwnProperty("selectCd")) pParam.selectCd = "";

    var comboData = [];

    switch (pParam.DataGbn) {
        case "C01" : // 2020년 ~ 현재연도
            var newDate = new Date();
            var year = newDate.getFullYear();
            for (var i = year; i >= 2020; i--) {
                comboData.push({CD: i, CD_NM: i + "년"});
            }
            break;
        case "C02" : // 월
            for (var i = 1; i <= 12; i++) {
                var mon = lpCom.lpad(i + "", 2, "0");
                comboData.push({CD: mon, CD_NM: mon + "월"});
            }
            break;
        case "C10" : // Email
            comboData.push({CD: "", CD_NM: "직접입력"});
            comboData.push({CD: "fira.or.kr", CD_NM: "fira.or.kr"});
            comboData.push({CD: "hotmail.com", CD_NM: "hotmail.com"});
            comboData.push({CD: "gmail.com", CD_NM: "gmail.com"});
            comboData.push({CD: "naver.com", CD_NM: "naver.com"});
            comboData.push({CD: "nate.com", CD_NM: "nate.com"});
            comboData.push({CD: "korea.kr", CD_NM: "korea.kr"});
            break;
    }

    switch (pParam.first) {
        case "S":
            comboData.unshift({CD:"",CD_NM:"---선택---"});
            break;
        case "A":
            comboData.unshift({CD:"",CD_NM:"---전체---"});
            break;
    }


    if (pTargetId != "") {
        $("#" + pTargetId + " > option").remove();
        comboData.forEach(function (rowData) {
            $("#" + pTargetId).append("<option value='" + rowData.CD + "'>" + rowData.CD_NM + "</option>");
        });

        if(pParam.selectCd != ""){
            $("#"+pTargetId).val(pParam.selectCd);
            $("#"+pTargetId).trigger("change");
        }
        pParam.selectCd = "";

        //$("#" + pTargetId).niceSelect("update"); // niceSelect 사용시
    } else {
        //아이디 없을때 콤보 데이터 리턴
        return comboData;
    }

};

/**
 * 로딩 DIV 생성
 */
lpCom.loadingDivCreate = function () {
    //로딩중 DIV 이미지 생성
    var now = new Date(); //현재 날짜 가져오기
    var lodingDiv = document.createElement("div");
    lodingDiv.setAttribute("id", "loading_div" + now.getMilliseconds());
    lodingDiv.setAttribute("style", " width:99%; height:99%;" +
        " background-color:rgba(181, 181, 181, 0.02);" +
        " background-image: url('" + lpCom.contextPath + "/common/images/viewLoading.gif'); " +
        " background-repeat:no-repeat; background-position:center center; background-size:60px 60px; " +
        " position:absolute; z-index:9999;" +
        " top:0px;");

    return lodingDiv;
};


/**
 * TEXTAREA.INPUT,SELECT 오브젝트 readOnly 동적 설정 함수
 * 예)lpCom.setObjReadOnly(true); //targetObj 없으면 전체 있으면 하위 모든 오브젝트 기준으로 실행됨
 * 오브젝트 readOnly 동적 변경이 필요 없을시 오브젝트에 notReadOnlyTag 추가 <input type="text" notReadOnlyTag>
 */
lpCom.setObjReadOnly = function (pReadOnly, targetObj) {
    if (targetObj == undefined) {
        var targetObj = ":input, #"+ targetObj + ":Button";
    } else {
        targetObj = "#" + targetObj + " :input , #"+ targetObj + ":Button";
    }
    $(targetObj).each(function () { //TEXTAREA.INPUT,SELECT
        if ($(this).attr("notReadOnlyTag") == undefined) {
            lpCom.setSelfReadOnly($(this), pReadOnly);
        }
    });
};

/**
 * TEXTAREA.INPUT,SELECT 오브젝트 readOnly 동적 설정 함수
 * 예)lpCom.setSelfReadOnly(pTagetObj,true);
 *
 */
lpCom.setSelfReadOnly = function (pTagetObj, pReadOnly) {
    var objTagName = pTagetObj.get(0).tagName;
    console.log(objTagName)
    switch (objTagName) {
        case "INPUT" :
            var objType = pTagetObj.attr("type"); //text date radio checkbox
            if (objType == "text" ) {

                pTagetObj.attr("readonly", pReadOnly);
                if (pReadOnly) {
                    if (pTagetObj.attr("data-datepicker") != undefined) {
                        pTagetObj.datepicker('destroy');
                        pTagetObj.removeAttr('data-datepicker')
                        $("[data-datepicker="+pTagetObj.attr("id")+"]").hide();
                    }
                    if (objType == "text") {
                        if (pTagetObj.attr("digits")) {
                            //  pTagetObj.get(0).value = lpCom.formatNumber(pTagetObj.get(0).value);
                        }
                    }
                } else {
                    if (objType.attr("tmpType") == "date") {
                        pTagetObj.datepicker('destroy');
                        pTagetObj.datepicker({ // 삭제후 재생성
                            dateFormat: 'yy-mm-dd',
                            numberOfMonths: 1,
                            showButtonPanel: true
                        });
                    }
                }
            } else {
                $("[name=" + pTagetObj.attr("name") + "]:not(:checked)").attr('disabled', pReadOnly ? 'disabled' : '');
            }
            break;
        case "TEXTAREA" :
            pTagetObj.attr("readonly", pReadOnly);
            break;
        case "SELECT" :
           // pTagetObj.attr("readonly", pReadOnly);

             $(pTagetObj).prop('disabled', pReadOnly);

            //console.log($(pTagetObj))
         //   $(pTagetObj).prop('disabled', true);
            /*
               //pTagetObj.children("option").not(":selected").attr("style", pReadOnly ? 'display:none': 'display:' );
              pTagetObj.children("option").not(":selected").remove();
               if(pReadOnly){
                   pTagetObj.addClass("input-std-readonly");
               }else{
                   pTagetObj.removeClass("input-std-readonly");
               }

                var _lable_Obj = "_label_"+pTagetObj.attr("id");
                $('#'+_lable_Obj).remove();
                if(pReadOnly){

                    var tmpObj =  $("<input type='text' id='"+_lable_Obj+"'>");
                    tmpObj.attr("value",(lpCom.isEmpty(pTagetObj.find(' option:selected').val()) ? "" :pTagetObj.find(' option:selected').text()));
                    tmpObj.attr("readonly",pReadOnly);
                    tmpObj.css("width",pTagetObj.css("width"));
                    tmpObj.css("height",pTagetObj.css("height"));
                    tmpObj.addClass("input-std-readonly");
                    pTagetObj.after(tmpObj);
                    pTagetObj.css("display","none");
                }else{
                    pTagetObj.css("display","");
                }
                */
            break;
           case "BUTTON" :
               if(pReadOnly)pTagetObj.hide();
               else pTagetObj.show();
               break;
    }
};

//lpad
lpCom.lpad = function (s, padLength, padString) {

    while (s.length < padLength)
        s = padString + s;
    return s;
};

//rpad
lpCom.rpad = function (s, padLength, padString) {
    while (s.length < padLength)
        s += padString;
    return s;
};

//현제일자
lpCom.getToday = function (format) {

    var date = new Date();
    var dt = date.getFullYear() + format + lpCom.lpad((date.getMonth() + 1) + "", 2, "0") + format + lpCom.lpad(date.getDate() + "", 2, "0");
    return dt;
};


//첨부파일 다운로드
lpCom.fnFileDown = function (fileName, orgFileName) {

    var params = "fileName=" + encodeURIComponent(fileName);  //저장된 파일명
    if (!lpCom.isEmpty(orgFileName)) {
        params += "&orgFileName=" + encodeURIComponent(orgFileName);    //실제 파일명
    }

    location.href = lpCom.contextPath + "/BT/com/fileDown.jsp?" + params;
};

/**
 * 데이터 값으로 폼값 자동 설정
 * */
lpCom.setFormValByData = function (pData) {
    for (var objId in pData) {
        var obj = $("#" + objId);
        if (obj.attr("type") == "file") continue;
        obj.val(pData[objId]);
    }
};


/**
 * 해당 오브젝트 문서 파일 체크 타입 change 이벤트 설정
 */
lpCom.setEventDocFileType = function (pObj) {
    lpCom.setEventFileType(pObj, "hwp|xls|pdf|xlsx|txt");
};

/**
 * 해당 오브젝트 이미지 파일 체크 타입 change 이벤트 설정
 */
lpCom.setEventImgFileType = function (pObj) {
    lpCom.setEventFileType(pObj, "bmp|gif|jpg|jpeg|png");
};

/**
 * 해당 오브젝트 사용자 설정 파일 체크 change 이벤트 설정
 */
lpCom.setEventFileType = function (pObj, pComfirmNm) {
    pObj.bind("change", function (event, param) {
        var fileFormat = "\.(" + pComfirmNm + ")$";
        if ((new RegExp(fileFormat, "i")).test($(this).val())) return true;

        alert($(this).val() + "는 등록 할수 없는 파일 입니다.");
        if (lpCom.isIEbrowser) {
            $(this).replaceWith($(this).clone(true));
        } else {
            $(this).val("");
        }
    });
};

/**
 * textbox 설명문구 표현
 */
lpCom.setPlaceHolder = function () {

    $(".placeHolder").each(function () {

        var textElement = $(this);
        var displayText = textElement.attr("rel"); // 표시할 문구

        if (textElement.val() === "") {
            textElement.val(displayText).css("color", "#888");
        }

        textElement.bind("focus.placeHolder", function () {  //텍스트필드가 포커스를 가질때
            if (textElement.val() === displayText) {
                textElement.val("").css("color", "");
            }
        });

        textElement.bind("blur.placeHolder", function () { //포커스 OUT
            if (textElement.val() === "") {
                textElement.val(displayText).css("color", "#888");
            }
        });
    });
};

/**
 * placeHolder 설명 제거
 * save 일때.. 미입력 설명문구는 제거한다.
 */
lpCom.placeHolderReset = function () {

    $(".placeHolder").each(function () {

        var textElement = $(this);
        var displayText = textElement.attr("rel");

        if (textElement.val() === displayText) {
            textElement.val("").css("color", "");
        }
    });
};

/**
 * 해당페이지 오보젝트 값으로 파라메터 설정
 */
lpCom.setPageParamByObj = function (pParamJson, pKey) {
    if (pKey == undefined) {
        var href = location.href;
        var hrefArr = href.split("/");
        var pKey = hrefArr[hrefArr.length - 1].split(".")[0];
    }
    sessionStorage.setItem(pKey, JSON.stringify(pParamJson));
};

/**
 * 해당 페이지 파라메서 값으로 오브젝트 값 설정
 */
lpCom.setPageObjByParam = function (pKey) {
    if (pKey == undefined) {
        var href = location.href;
        var hrefArr = href.split("/");
        var pKey = hrefArr[hrefArr.length - 1].split(".")[0];
    }

    var paramDataStr = sessionStorage.getItem(pKey);
    if (paramDataStr != undefined) {
        var paramData = JSON.parse(paramDataStr);
        for (var objId in paramData) {
            if ($("#" + objId).attr("type") == "checkbox") {
                if (paramData[objId] == $("#" + objId).attr("chkvalue")) {
                    $("#" + objId).attr("checked", true);
                }
            } else if ($("#" + objId).attr("type") == "radio") {
                $("[name=" + objId + "]:input:radio[value='" + paramData[objId] + "']").attr("checked", true);
            } else {
                $("#" + objId).val(paramData[objId]);
            }
        }
        sessionStorage.removeItem(pKey);
        return paramData;
    }

    return {};
};

//다이얼로그 오픈
lpCom.dialogOpen = function (opt) {

    if (opt == undefined) opt = {};

    if (lpCom.isEmpty(opt.dialogNm)) opt.dialogNm = "dialog";

    var dialog = $("#" + opt.dialogNm);

    $(dialog).html(opt.contents);

    var top = screen.height / 2 - (Number(200) / 2);

    opt.width = opt.width || "250";
    opt.height = opt.height || "200";

    $(dialog).dialog({
        modal: true,
        resizable: false,
        height: opt.height,
        width: opt.width,
        position: {
            my: "center",
            at: "top+" + top,
            of: window
        },
        title: opt.title,
        buttons: opt.buttons
    });
    if (lpCom.isEmpty(opt.title)) {
        $(dialog).siblings('div.ui-dialog-titlebar').remove();
    }

};

// 스페이스, 엔터키 제거
lpCom.fn_removeSpaceEnter = function (str) {

    str = str.trim();
    str = str.replace(/\n/g, "");
    return str;
};


/**
 * input box 입력제어
 * 예)lpCom.valdInputCheck("frm");
 */
lpCom.valdInputCheck = function (obj) {

    if (obj == undefined) return;

    $(obj + " input, textarea").bind("keyup", function (event) {
        //숫자
        if ($(this).attr("digits")) {
            this.value = this.value.replace(/[^0-9]/g, '');
        }
        //영문
        if ($(this).attr("alpha")) {
            this.value = this.value.replace(/[^a-zA-Z\s]/g, '');
        }
    });
};

/**
 * 팝업여부
 * true : 팝업
 * */
lpCom.isPopup = function () {
    if (opener && !opener.closed) {
        return true;
    } else {
        return false;
    }
};


/**
 * 천자리 콤바 포멧
 */

lpCom.formatNumber = function (str) {
    str = String(str);
    var rstr = "", sign = "";
    if (str.substr(0, 1) == "-") {
        sign = "-";
        str = str.substr(1);
    }
    var arr = str.split(".");
    for (var ii = 0; ii < arr[0].length; ii++) {
        if (ii % 3 == 0 && ii > 0) {
            rstr = arr[0].substring(arr[0].length - ii, arr[0].length - ii - 1) + "," + rstr;
        } else {
            rstr = arr[0].substring(arr[0].length - ii, arr[0].length - ii - 1) + rstr;
        }
    }
    rstr = sign + rstr;
    return arr.length > 1 ? rstr + "." + arr[1] : rstr;

};

/**
 * 엑셀 다운로드
 *
 * */
lpCom.excelDown = function (pFileNm, pTableRows) {

    var e = {};
    e.settings = {filename: pFileNm, sheetName: "sheet"};

    var utf8Heading = "<meta http-equiv=\"content-type\" content=\"application/vnd.ms-excel; charset=UTF-8\">";
    var style = [];
    style.push("<style>");
    style.push(".excel_head{mso-style-parent:style0;font-size:10.0pt; background:#D9D9D9;mso-pattern:black none; white-space:normal;}");
    style.push(".excel_base{mso-style-parent:style0;font-size:10.0pt; mso-number-format:'\@'; white-space:normal;}");
    style.push("</style>");

    e.template = {
        head: "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\">" + utf8Heading + "<head>" + style.join() + "<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets>",
        sheet: {
            head: "<x:ExcelWorksheet><x:Name>",
            tail: "</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>"
        },
        mid: "</x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>",
        table: {
            head: "<table>",
            tail: "</table>"
        },
        foot: "</body></html>"
    };

    e.tableRows = [];

    e.tableRows.push(pTableRows);

    var fullTemplate = "", i, link, a;

    e.format = function (s, c) {
        return s.replace(/{(\w+)}/g, function (m, p) {
            return c[p];
        });
    };

    e.ctx = {
        worksheet: name || "Worksheet",
        table: e.tableRows,
        sheetName: e.settings.filename
    };

    fullTemplate = e.template.head;

    fullTemplate += e.template.sheet.head + e.settings.filename + e.template.sheet.tail;

    fullTemplate += e.template.mid;

    if ($.isArray(e.tableRows)) {
        for (i in e.tableRows) {
            fullTemplate += e.template.table.head + "{table" + i + "}" + e.template.table.tail;
        }
    }

    fullTemplate += e.template.foot;

    for (i in e.tableRows) {
        e.ctx["table" + i] = e.tableRows[i];
    }
    delete e.ctx.table;

    var isIE = /*@cc_on!@*/false || !!document.documentMode; // this works with IE10 and IE11 both :)
    //if (typeof msie !== "undefined" && msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // this works ONLY with IE 11!!!
    if (isIE) {
        if (typeof Blob !== "undefined") {
            //use blobs if we can
            fullTemplate = e.format(fullTemplate, e.ctx); // with this, works with IE
            fullTemplate = [fullTemplate];
            //convert to array
            var blob1 = new Blob(fullTemplate, {type: "text/html"});
            window.navigator.msSaveBlob(blob1, e.settings.filename + ".xls");
        } else {
            //otherwise use the iframe and save
            //requires a blank iframe on page called txtArea1
            txtArea1.document.open("text/html", "replace");
            txtArea1.document.write(e.format(fullTemplate, e.ctx));
            txtArea1.document.close();
            txtArea1.focus();
            sa = txtArea1.document.execCommand("SaveAs", true, e.settings.filename + ".xls");
        }

    } else {
        var blob = new Blob([e.format(fullTemplate, e.ctx)], {type: "application/vnd.ms-excel"});
        window.URL = window.URL || window.webkitURL;
        link = window.URL.createObjectURL(blob);
        a = document.createElement("a");
        a.download = e.settings.filename;
        a.href = link;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    }
};


/**
 * 검색조건 입력유무
 * return 값 있으면.. false
 */

lpCom.searchInputCheck = function (obj) {

    if (obj == undefined) return;

    var ret = true;
    $("#" + obj).each(function () {
        $("input ,select", this).each(function () {
            if (this.type == "select-one" || this.type == "text") {
                if (!lpCom.isEmpty(this.value)) {
                    ret = false;
                }
            }
        });
    });
    return ret;
};


/**
 * UUID
 */
lpCom.getUuid = function () {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    }

    return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4();
}

/**
 * 목록에서 페이지 이동시 파라메터 저장
 */

lpCom.setSearchCond = function () {
    var param = $("#schFrm").srializeJsonById();
    localStorage.setItem('searchCond', JSON.stringify(param));
}

/**
 * 목록페이지 돌아올때 검색조건 파라메터 자동 설정
 */
lpCom.getSearchCond = function () {
    var searchCond = localStorage.getItem('searchCond');
    if (searchCond != null) {
        searchCond = JSON.parse(searchCond);
        for (var objId in searchCond) {
            if (!objId.isEmpty()) {
                var searchObj = $("#schFrm #" + objId);
                $(searchObj).val(searchCond[objId]);
                if ($(searchObj).prop("tagName") == "SELECT") {
                    //$(searchObj).niceSelect("update"); // niceSelect 사용시
                }
            }
        }
    }

    localStorage.removeItem('searchCond'); //클리어
    //수동설정을 위한 오브젝트 리턴
    return searchCond;
};

/**
 * 엑셀다운로드
 * AuiGrid colModel 정보가 필요함
 */
lpCom.AjaxExcel = function (pFileNm, pUrl, pParam, pColModel) {

    var excelInnerCallBack = function (pSid, pData) {
        var excelData = [];
        var excelColId = [];
        for (var i = 0; i < pColModel.length; i++) {
            var rowData = pColModel[i];
            if (rowData.hidden == "false" || lpCom.isEmpty(rowData.hidden)) {
                excelColId.push({col: rowData.name, lable: rowData.label});
            }
        }

        //컬럼명 설정
        var tr = "<tr>";
        for (var i = 0; i < excelColId.length; i++) {
            tr += "<td>" + [excelColId[i].lable] + "</td>";
        }
        tr += "</tr>";
        excelData.push(tr);

        var rowDatas = pData.list;
        for (var j = 0; j < rowDatas.length; j++) {
            var tr = "<tr>";
            for (var i = 0; i < excelColId.length; i++) {
                tr += "<td>" + rowDatas[j][excelColId[i].col] + "</td>";
            }
            tr += "</tr>";
            excelData.push(tr);
        }

        var excelTable = "<table border='1'>" + excelData.join("") + "</table>";

        lpCom.excelDown(pFileNm, excelTable);

    }

    pParam.excelDownAt = "Y";
    var otherInit = {};
    otherInit.isSuccesMsg = false;
    lpCom.Ajax(pFileNm, pUrl, pParam, excelInnerCallBack, otherInit);
}

/**
 * 레이어 팝업 오픈
 */
lpCom.popupDivOpen = function (url, param, option) {
    if (option == undefined) option = {};
    if (option.width == undefined) option.width = "1200";
    if (option.height == undefined) option.height = "720";
    if (option.follow == undefined) option.follow = true;
    $("body div[id=element_to_pop_up]").remove();
    var popDivObj = "<div id='element_to_pop_up' style='display:none;'>"
        + "<span class='button b-close' style='border-radius:7px 7px 7px 7px;box-shadow:none;font:bold 131% sans-serif;padding:0 6px 2px;position:absolute;right:-20px;top:-7px; background-color:#2b91af; color:#fff; cursor: pointer; display: inline-block; text-align: center;'><span>X</span></span>"
        + "<div class='content' style='width:" + option.width + "px;height:" + option.height + "px'></div>";
    "</div>";

    $("body").append(popDivObj);

    $divPopopParam = param;

    $("#element_to_pop_up").bPopup({
        content: 'iframe' //'ajax', 'iframe' or 'image'
        , iframeAttr: 'scrolling="auto" frameborder="0" width="100%" height="100%" '
        , contentContainer: ".content"
        , follow: [option.follow, option.follow]
        , loadUrl: lpCom.contextPath + url //Uses jQuery.load()
    });
}


/**
 * 레이어 팝업 닫기
 */
lpCom.popupDivClose = function (pRetParam) {
    if (window.parent.calBackPopupFunc != undefined) {
        window.parent.calBackPopupFunc(pRetParam);
        window.parent.calBackPopupFunc = undefined;
    }

    window.parent.$('#element_to_pop_up').bPopup().close();
}

/**
 * 레이어 팝업 파라메턴 리턴
 */
lpCom.getParamPopupDiv = function () {

    if (window.parent.$divPopopParam != undefined) {
        return window.parent.$divPopopParam;
    } else {
        return {};
    }
}

/**
 *  인공어초 -공통코드
 *
 */
lpCom.setReefCodeSelect = function (pTargetId, pParam) {

    pParam.pUrl = "/stat/getReefCodeList.do";
    return lpCom.setComSelect(pTargetId, pParam)
}

/**
 *  파일 존재 확인여부
 *
 */
lpCom.fnFileExists = function (fileUrl) {

    if (fileUrl) {
        var req = new XMLHttpRequest();
        req.open('GET', fileUrl, false);
        req.send();
        return req.status == 200;
    } else {
        return false;
    }
}

/**
 * 방류종자 품종 코드
 *
 */
lpCom.setFishseedCodeSelect = function (pTargetId, pParam) {

    pParam.pUrl = "/fishseed/getfishseedList.do";
    return lpCom.setComSelect(pTargetId, pParam)
}

/**
 * 방류종자 지자체기관 코드
 *
 */
lpCom.setMANAGEMENTDIVISIONCodeSelect = function (pTargetId, pParam) {

    pParam.pUrl = "/fishseed/getMANAGEMENTDIVISIONList.do";
    return lpCom.setComSelect(pTargetId, pParam)
}


/**
 * 페이지 readOnly 설정 뷰페이로 보기용
 */
lpCom.setPageReadOnly = function () {
    var $inputObj = $('div[id=contents_bx] :input').filter('[notReadOnlyTag!=""]');
    $inputObj.each(function (index, item) { //TEXTAREA.INPUT,SELECT
        var objTagName = $(item).get(0).tagName;
        switch (objTagName) {
            case "INPUT" :
                var objType = $(item).attr("type"); //text date radio checkbox
                if (objType == "text" || objType == "date" || objType == "date1" || objType == "textbox") {
                    $(item).attr("readonly", true);
                    $(item).addClass("input-std-readonly");

                    if ((objType == "date" || objType == "date1" || objType == "date2")) {

                        $(item).attr("type", "text");
                        $(item).datepicker('destroy');
                        $(item).next().remove();// 달력버튼 삭제
                    }

                } else {
                    $(item).attr('disabled', true);
                }
                break;
            case "TEXTAREA" :
                $(item).attr("readonly", true);
                $(item).addClass("input-std-readonly");
                break;
            case "SELECT" :
                $(item).prop('disabled', true).niceSelect('update');
                $(item).next().addClass('without-after-element');
                break;
        }
    });

    //버튼설정 목록버튼은 기본적으로 보임처리
    var $btnObj = $('a[class^=btn_]').filter('[notReadOnlyTag!=""]').filter('[id!=listBtn]');
    $btnObj.hide();
};


/**
 * 탭 설정
 * pTabId : 탭id
 * pContentsId : 대상 컨텐츠 영역 ID
 */
lpCom.setTabInit = function (pTabId, pContentsId, pParam) {

    //탭 컨텐츠 생성
    $("#" + pTabId + " ul li a").each(function (idx, item) {
        var val = $(item).attr("value");
        $("#" + pContentsId).append("<div id='" + pContentsId + val + "' style='display:none;'></div>");
    });


    //탭 컨트롤
    $("#" + pTabId + " ul li a").click(function () {

        $("#" + pTabId + " ul li a").removeClass("on");
        $(this).addClass("on");

        var val = $(this).attr("value");

        $("#" + pContentsId).children("div").hide();
        $("#" + pContentsId + val).show();

        loadContents(this);
    });

    //선택 컨텐츠 내용 load
    var loadContents = function (selObj) {

        var val = $(selObj).attr("value");
        var src = $(selObj).attr("src");

        //중복 조회 안함
        if (!lpCom.isEmpty($("#" + pContentsId + val).text())) return;

        $.ajax({
            url: src,
            async: false,
            data: pParam,
            success: function (data) {
                var script = $(data).filter("script");
                var contents = $(data).find("#viewContents");

                $("#" + pContentsId + val).html(script);
                $("#" + pContentsId + val).append(contents);

                lpCom.setPageReadOnly();
            }
        });
    }
}


/**
 * 엑셀 업로드 정보 세팅
 */
lpCom.setGridExcelParam = function (pFormNm) {

    if (pFormNm == undefined) var pFormNm = "frm";

    var params = lpCom.getParamPopupDiv();
    var pColModel = params.colModel;
    var label = "";
    var cols = "";

    for (var i = 0; i < pColModel.length; i++) {
        var rowData = pColModel[i];
        if (rowData.hidden == "false" || lpCom.isEmpty(rowData.hidden)) {
            label += "," + rowData.label;
            cols += "," + rowData.name;
        }
    }

    $("#" + pFormNm).append('<input type="hidden" id="label" name="label" value=""/>');
    $("#" + pFormNm).append('<input type="hidden" id="cols" name="cols" value=""/>');
    $("#label").val("번호" + label);
    $("#cols").val("NO" + cols);

}


//입력한 문자열 전달
lpCom.inputNumberFormat = function (obj) {
    obj.value = lpCom.comma(lpCom.uncomma(obj.value));
}

//콤마찍기
lpCom.comma = function (str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

//콤마풀기
lpCom.uncomma = function (str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

//숫자만 리턴(저장할때)
lpCom.cf_getNumberOnly = function (str) {
    var len = str.length;
    var sReturn = "";

    for (var i = 0; i < len; i++) {
        if ((str.charAt(i) >= "0") && (str.charAt(i) <= "9")) {
            sReturn += str.charAt(i);
        }
    }
    return sReturn;
}

//멀티설렉트 json 처리
lpCom.multiSelectJson = function (pTargetId) {
    var arrList = $("#" + pTargetId).val()
    if (arrList == null) arrList = [];
    for (var i = 0; i < arrList.length; i++) {
        arrList[i] = {list: arrList[i]};
    }
    return JSON.stringify(arrList);
}

//tagsinput json 처리
lpCom.tagsinputJson = function (str) {
    var arrList = str.split(",");
    if(str == ""){
        arrList= [];
    }
    for (var i = 0; i < arrList.length; i++) {
        arrList[i] = {list: arrList[i]};
    }
    return JSON.stringify(arrList);
}

/**
 * 주소 팝업
 * 부모창 fn_jusoCallBack(pData) 호출함
 * 결과 예시
 * {roadFullAddr: "부산광역시 연제구 거제시장로 13, --- (거제동)", roadAddrPart1: "부산광역시 연제구 거제시장로 13", roadAddrPart2: "(거제동)", engAddr: "13, Geojesijang-ro, Yeonje-gu, Busan", ji
 */
lpCom.goJusoPopup = function(pSid){
    var params = {};
    params.sid = "juso";
    if(pSid !=undefined)  params.sid = pSid;
    var opt = {};
    opt.width = "554";
    opt.height = "400";
    opt.scrollbars = "yes";
    opt.resizable = "yes";

    lpCom.winOpen('/comm/actionJusoPop.do', "goJusoPopup", params, opt);
};

lpCom.goJusoMobilePopup = function(pSid){
    var params = {};
    params.sid = "juso";
    if(pSid !=undefined)  params.sid = pSid;
    var opt = {};
    opt.width = "554";
    opt.height = "400";
    opt.scrollbars = "yes";
    opt.resizable = "yes";

    lpCom.winOpen('/comm/SearchJusoMobilePopup.do', "goJusoPopup", params, opt);
};

/**
 * 첨부파일 설정
 *   lpCom.fileZone({readOnly:true,fileDivId:"mapFile",atchFileObjId:"atchFile",fileIdValue:"222"});
 */
lpCom.fileZone = function(pOption){

        if(!pOption.hasOwnProperty("readOnly")) pOption.readOnly = false;
        if(!pOption.hasOwnProperty("validFileExt")) pOption.validFileExt = "default";
        if(!pOption.hasOwnProperty("maxFileCnt")) pOption.maxFileCnt = 100;
        if(!pOption.hasOwnProperty("maxUploadSize")) pOption.maxUploadSize = 20;

        var fileZone;
        if(pOption.readOnly){
            fileZone = new fileDropDownView(pOption.fileDivId);
        }else{
            fileZone = new fileDropDown(pOption.fileDivId,pOption.maxFileCnt, pOption.maxUploadSize);
             $("#"+pOption.atchFileObjId).bind('change', function() {
                 fileZone.handleFileUploadSelect(this.files,pOption.validFileExt);
            });

        }

        function selectFile(){
             var formData = new FormData();
             formData.append("fileId",pOption.fileIdValue);
             $.ajax({
                 url: lpCom.contextPath + "/comm/selectAtchFile.do",
                 type: 'POST',
                 data : formData,
                 contentType:false,
                 processData: false,
                 cache: false,
                 success: function(data) {
                     var fileList = data.fileList;
                     fileZone.bindFiles(fileList);
                 },
                 error: function(xhr) {
                   console.log('실패 - ', xhr);
                 }
             });
        }

        if(!lpCom.isEmpty(pOption.fileIdValue)){
            selectFile();
        }
    }

// TODO
// 파이텍 common.js Migration
$(document).ready(function () {

});

lpCom.dateValidation = function (sdate, edate) {
    var scConsDateStart = $("#"+sdate).val();
    var scConsDateEnd = $("#"+edate).val();

    var mScConsDateStart = moment(scConsDateStart);

    if (!mScConsDateStart.isValid()) {
        alert("시작일이 올바르지 않습니다.");
        return false;
    } else {
        $("#"+sdate).val(mScConsDateStart.format('YYYY-MM-DD'));
    }

    var mScConsDateEnd = moment(scConsDateEnd);
    if (!mScConsDateEnd.isValid()) {
        alert("종료일이 올바르지 않습니다.");
        return false;
    } else {
        $("#"+edate).val(mScConsDateEnd.format('YYYY-MM-DD'));
    }

    var duration = moment.duration(mScConsDateEnd.diff(mScConsDateStart)).asDays();

    if (duration < 0) {
        alert("종료일은 시작일 보다 커야합니다.");
        return false;
    }
    return true;
}

lpCom.setCookie = function(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

lpCom.deleteCookie = function(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

lpCom.getCookie = function(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}





//전화번호 하이픈
$(".phone-number").on("keyup keydown", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3").replace("--", "-"));
});
$("input.phone-number").each(function () {
    $(this).val($(this).val().replace(/[^0-9]/g, "").replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/, "$1-$2-$3").replace("--", "-"));
});

//사업자번호 하이픈
$(".company-number").on("keyup keydown", function () {
    $(this).val($(this).val().replace(/[^0-9]/g, "").substring(0, 10).replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3').replace("--", "-"));
});
$("input.company-number").each(function () {
    $(this).val($(this).val().replace(/[^0-9]/g, "").substring(0, 10).replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3').replace("--", "-"));
});        

//페이징
lpCom.setPagination = function (pData) {
    var pAllCnt = pData.list.length == 0 ? 0 : pData.list[0].ALL_CNT;
    var pageSize = pData.pageSize == null ? 10 : pData.pageSize;
    var pageObj = [];
    var startPage = 1;

    if (pAllCnt > 0) {
        var allCnt = pAllCnt;
        var totalPage = Number(Math.ceil(allCnt / pageSize));
        var selectPage = Number($("#selectPage").val()) || 1;
        startPage = Number($("#startPage").val()) || 1;
        if ((startPage - 1) == selectPage) {
            startPage = selectPage;
        }

        if ((Number(startPage) + (5)) == selectPage) {
            startPage = (Number(startPage) + 1);
        }

        if (selectPage == totalPage) {
            startPage = Number(totalPage) - (5 - 1);
            if (startPage < 0)
                startPage = 1;
        }
        if (selectPage == 1) {
            startPage = 1;
        }

        var pageNo = startPage;

        if (startPage - 1 > 0) {
            pageObj.push('<a href="#" onclick="fnSearch(1); return false;" class="btn_first" title="첫 페이지로 이동"><span>처음</span></a>');
            pageObj.push('<a href="#" onclick="fnSearch(' + (Number(selectPage) - 1) + '); return false;" class="btn_prev" title="이전 5개 목록 페이지로 이동"><span>이전</span></a>');
        }

        pageObj.push('<ul class="paging">');
        var cnt = 0;
        for (pageNo; pageNo <= totalPage; pageNo++) {
            if (cnt++ == 5) break;

            if (selectPage == pageNo) {
                pageObj.push('<li><a href="#" class="on" title="현재페이지로 이동">' + pageNo + '</a></li>');
            } else {
                pageObj.push('<li><a href="#" onclick="fnSearch(' + pageNo + '); return false;" title="현재페이지로 이동">' + pageNo + '</a></li>');
            }
        }
        pageObj.push('</ul>');

        if (pageNo <= totalPage) {
            pageObj.push('<a href="#" onclick="fnSearch(' + (Number(selectPage) + 1) + '); return false;" class="btn_next" title="다음 5개 목록 페이지로 이동"><span>다음</span></a>');
            pageObj.push('<a href="#" onclick="fnSearch(' + totalPage + '); return false;" class="btn_end" title="마지막 페이지로 이동"><span>끝</span></a>');
        }
    }

    $("#pagination").html(pageObj.join(""));
    $("#startPage").val(startPage);
};

//리포트뷰어_외부
lpCom.openReportViewer = function(objReportParam, pWinNm, pWidth, pHeight){
    console.log("openReportViewer param **** ", objReportParam);

    let viewerUrl = "https://www.nfqs.go.kr/nfpis_report/ReportViewer.jsp";
    let target = "popReportViewer" + (pWinNm ? pWinNm : "");

    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", viewerUrl);
    form.setAttribute("target", target);

    Object.keys(objReportParam).forEach(function(key, idx) {
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = key;
        input.value = objReportParam[key];
        form.appendChild(input);
    });

    //console.log("form data **** ", $(form).serializeObject());

    document.body.appendChild(form);

    var width = pWidth ? pWidth : 680;
    var height = pHeight ? pHeight: 960;

    var topPos = ((screen.width)/2)-(width/2);
    var leftPos =((screen.height)/2)-(height/2);

    window.open(viewerUrl, target, "top=" + topPos + ",left=" + leftPos + ",width=" + width + ",height=" + height + ",resizable=yes,toolbar=no,menubar=no,location=no");

    // if ( /Chrome/.test(navigator.userAgent) && /Google Inc/.test(navigator.vendor) ) {
    //    window.open(viewerUrl, target);
    // } else {
    //    window.open(viewerUrl, target, "top=" + topPos + ",left=" + leftPos + ",width=" + width + ",height=" + height + ",resizable=yes,toolbar=no,menubar=no,location=no");
    // }

    form.submit();

    document.body.removeChild(form);
}