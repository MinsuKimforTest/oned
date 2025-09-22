/*모바일 메뉴열기 js*/
$('.open_menu > a').click(function () {
    $('.bg_bk').fadeIn(200);
    $('.side').animate({right: '0'}, 200);

});

/*모바일 메뉴닫기 js*/
$('.close_menu').click(function () {
    $('.bg_bk').fadeOut(200);
    $('.side').animate({right: '-100%'}, 200);

});


$(".m_gnb .d1 .m").click(function () {
    var tg = $(this).siblings(".sub");
    var dis = tg.css("display");

    if (dis == "none") {

        $(".m_gnb .d1 .m").removeClass("active");
        $(".m_gnb .d1 .sub").slideUp(500);

        $(this).addClass("active")
        tg.slideDown(500);

    } else {

        //메뉴가 열여진 상태일때는 닫아랏.
        $(".m_gnb .d1 .m").removeClass("active");
        $(".m_gnb .d1 .sub").slideUp(500);


        $(this).removeClass("active")
        tg.slideUp(500);


    }

    return false; //a태그의 기본기능인 링크 무효화

});


$('.bg_bk').click(function () {
    //.close_menu > a 가 클릭이 된것과 같은 효과가 일어난다 --------*************************
    $('.close_menu').click();
    /*	$(this).fadeOut(400);
    $('.side').animate({right:'-70%'},300);*/
});


/*lang js*/
$('.lang').mouseover(function () {
    $(this).find(".list").stop().slideDown(200);

}).mouseout(function () {
    $(this).find(".list").stop().slideUp(200);
});


/***** MENU - PC******/
$(".gnb_pc").mouseover(function () {
    $(this).stop().animate({height: "350px"}, 300);
    $(".subBg").stop().slideDown(300);


}).mouseout(function () {
    $(".gnb_pc").stop().animate({height: "113px"}, 300);
    $(".subBg").stop().slideUp(300);

});


$(function () {
    $(".location li").hover(function () {
        $('ul:first', this).show();
    }, function () {
        $('ul:first', this).hide();
    });
    $(".location>li:has(ul)>a").each(function () {
        $(this).html($(this).html() + ' ');
    });
    $(".location ul li:has(ul)")
        .find("a:first")
        .append("<p style='float:right;margin:-3px'>&#9656;</p>");
});

//login modal
$(document).ready(function() {
    lpCom.enterEventId("M_PASSWORD", "$('#mLoginBtn').click()");
});

var loginCalBackFunc = function(pSid, pData) {
    switch (pSid) {
        case "login": // 로그인 처리
            location.reload();
            break;
    }
}

$(function(){
    $(".login_btn").click(function(){
        $("#mobileLoginLayer").fadeIn();
    });

    $("#loginCloseBtn").click(function(){
        $("#mobileLoginLayer").fadeOut();
    });

    //로그인
    $("#mLoginBtn").on("click", function(e) {
        if(!$("#mLoginFrm").valid()) return false; //form 체크

        var otherInit = {};
        otherInit.isSuccesMsg = false;
        otherInit.isErrCallBackFunc = true;

        var param = {};
        param.USER_ID = $("#M_USER_ID").val();
        param.PASSWORD = $("#M_PASSWORD").val();

        lpCom.Ajax("login", "/login/actionLogin.do", param, loginCalBackFunc, otherInit);
    });

    //퀵메뉴
    if (matchMedia("screen and (min-width: 1479px)").matches) {
        $(document).ready(function(){
            var currentPosition = parseInt($(".quick_menu").css("top"));
            $(window).scroll(function() {
                var position = $(window).scrollTop();
                $(".quic_kmenu").stop().animate({"top":position+currentPosition+"px"},1000);
            });
        });// 이상일때
    } else {
        $(".quick_menu .tit").on("click", function() {
            $("ul.menu").toggle();
        });
    }
});

