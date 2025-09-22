//친환경 인증번호 확인 > 저장
$("#certSubmitBtn").on("click", function(e) {
    if(!lpCom.isRequired($("input[name='CERT_GUBUN']:checked"), "사업자 구분은 필수 입력 값입니다.")) return;

    var cnt = $("input[name='CERT_NO']").length;
    var certNoArr = new Array();
    if($("input[name='CERT_GUBUN']:checked").val() =="2") {  //기존사업자
        for (var i = 0; i < cnt; i++) {
            if(!lpCom.isRequired($("input[name='CERT_NO']").eq(i), "인증번호는 필수 입력 값입니다.")) return;
            certNoArr[i] = $("input[name='CERT_NO']").eq(i).val();
        }
    }

    var otherInit = {};
    otherInit.isSuccesMsg = true;

    var param = {};
    param.CERT_GUBUN = $("input[name='CERT_GUBUN']:checked").val();
    param.CERT_NO = certNoArr.join();

    if(!lpCom.actionBeforeConfirm("S",otherInit.formObj)) return;// 메세지 처리 및 폼체크
    lpCom.Ajax("certNo","/organicCert/saveOrganicCertNo.do", param, calBackFunc, otherInit);
});

//친환경 인증번호 등록 확인
var fncheckOrganicCertNo = function() {
    var otherInit = {};
    otherInit.isSuccesMsg = false;

    var param = {};
    lpCom.Ajax("certNo", "/organicCert/checkOrganicCertNo.do", param, calBackFunc, otherInit);
}

//친환경 인증번호 확인 > 사업자 구분 선택
var fnSelectCertGugun = function(eduGubun, certNo) {
    var certNoHtml = "";
    if(eduGubun == "2"){
        if(lpCom.isEmpty(certNo)){
            certNoHtml = '<div class="certno_row"><input type="text" name="CERT_NO" style="width: 150px" maxlength="8" onkeyup="fnCheckCertNoInfo(this)"></div>';
        }else{
            var certNoSplit = certNo.split(',');
            for(var i in certNoSplit) {
                certNoHtml += '<div class="certno_row"><input type="text" name="CERT_NO" style="width: 150px" maxlength="8" onkeyup="fnCheckCertNoInfo(this)" value="'+certNoSplit[i]+'"></div>';
            }
        }
    }else{
        certNoHtml = '<span style="color: #ff6c32">최초 인증사업자는 제외<span>'
    }
    $("#certNoDiv").html(certNoHtml);
    fnDelCertNo();
}

//친환경 인증번호 row 추가
var fnAddCertNo = function() {
    $(".cetNoAddBtn").remove();

    var certNoHtml = "";
    certNoHtml += '<div class="certno_row">';
    certNoHtml += '<input type="text" name="CERT_NO" style="width: 150px" maxlength="8" onkeyup="fnCheckCertNoInfo(this)">';
    certNoHtml += '<a href="#" class="btn cetNoAddBtn" onclick="fnAddCertNo();">추가</a>';
    certNoHtml += '<a href="#" class="btn cetNoDelBtn" onclick="fnDelCertNo(this);">삭제</a>';
    certNoHtml += '</div>';
    $("#certNoDiv").append(certNoHtml);
    fnDelCertNo();
}

//친환경 인증번호 row 삭제
var fnDelCertNo = function(e) {
    $(e).parent().remove();
    $(".cetNoAddBtn").remove();
    $(".cetNoDelBtn").remove();

    var cnt = $(".certno_row").length;
    if(cnt == 1){
        console.log()
        $(".certno_row:first").append('<a href="#" class="btn cetNoAddBtn" onclick="fnAddCertNo();">추가</a>');
        $(".certno_row:first .certno_info").insertAfter(".certno_row:first .cetNoAddBtn");

    }else{
        for(var i=1; i<=cnt; i++){
            if(i == 1){
                $(".certno_row:first").append('<a href="#" class="btn cetNoDelBtn" onclick="fnDelCertNo(this);">삭제</a>');
                $(".certno_row:first .certno_info").insertAfter(".certno_row:first .cetNoDelBtn");
            }else if(i == cnt){
                $(".certno_row:last").append('<a href="#" class="btn cetNoAddBtn" onclick="fnAddCertNo();">추가</a>');
                $(".certno_row:last").append('<a href="#" class="btn cetNoDelBtn" onclick="fnDelCertNo(this);">삭제</a>');
                $(".certno_row:last .certno_info").insertAfter(".certno_row:last .cetNoDelBtn");
            }else{
                $(".certno_row:nth-child("+i+")").append('<a href="#" class="btn cetNoDelBtn" onclick="fnDelCertNo(this);">삭제</a>');
                $(".certno_row:nth-child("+i+") .certno_info").insertAfter(".certno_row:nth-child("+i+") .cetNoDelBtn");
            }
        }
    }
}

var fnCheckCertNoInfo = function(e){
    const certNo = $(e).val();
    $(e).parent().children('.certno_info').remove();

    if(certNo.length > 7){
        var param = {};
        param.CERT_NO = certNo;

        $.ajax({
            url: "../organicCert/selectOrganicCertInfo.do",
            type: 'POST',
            data : param,
            success: function(data) {
                const result = data.retData;
                var msg = '';
                $(e).parent().children('.certno_info').remove();

                if(result != null){
                    msg = result.COMP_NM_KOR + '(' + result.CEO_NM_KOR + ')';
                }else {
                    msg = "유효하지 않은 번호입니다.";
                }
                $(e).parent().append('<span class="certno_info">' + msg + '</span>');
            },
            error: function(xhr) {
                console.log('실패 - ', xhr);
            }
        });
    }
}