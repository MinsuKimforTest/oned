var classViewPlayer = function(playerId, options, controlId) {
    var formData = new FormData();
    formData.append("fileId", options.fileIdValue);

    $.ajax({
        url: "../comm/selectAtchFile.do",
        type: 'POST',
        data : formData,
        contentType:false,
        processData: false,
        cache: false,
        async: false,
        success: function(data) {
            var fileList = data.fileList;
            if(data.fileList.length > 0){
                options.sources = [{src: "../comm/loadVideo.do?fileId="+fileList[0].FILE_ID+"&fileSeq="+fileList[0].FILE_SEQ+"&chgFileNm="+fileList[0].CHG_FILE_NM, type: "video/mp4"}];
            }else{
                alert("해당 강의가 없습니다. 관리자에게 문의하여 주십시오.");
            }
        },
        error: function(xhr) {
            console.log('실패 - ', xhr);
        }
    });

    //options.sources = [{src: "https://vjs.zencdn.net/v/oceans.mp4", type: "video/mp4"}];

    //플레이어 설정
    var player = videojs(playerId, options);


    var seekMax = 0;
    var timer;
    var studyTime = $("#STUDY_TIME").val();

    player.on("loadedmetadata", function(){
        //총 재생시간
        var durationMinutes = Math.floor(player.duration() / 60);
        var durationSeconds = Math.round(player.duration() - durationMinutes * 60);

        if(durationSeconds < 10){ durationSeconds = "0"+durationSeconds; }
        if(durationMinutes < 10){ durationMinutes = "0"+durationMinutes; }

        $("#durationTime").html(durationMinutes+":"+durationSeconds);

        seekMax = Math.floor(player.duration());
        $("#seekBar").attr("max", seekMax);

        //마지막 재생위치부터 재생
        if(studyTime > 0){
            if(studyTime >= player.duration()){
                player.currentTime(0);
            }else{  //재생이 완료된 건 처음부터 재생
                player.currentTime(studyTime);
            }
        }
    });

    player.on("timeupdate", function(){
        //현재 재생시간
        var currentMinutes = Math.floor(player.currentTime() / 60);
        var currentSeconds = Math.floor(player.currentTime() - currentMinutes * 60);

        if(currentSeconds < 10){ currentSeconds = "0"+currentSeconds; }
        if(currentMinutes < 10){ currentMinutes = "0"+currentMinutes; }

        $("#currentTime").html(currentMinutes+":"+currentSeconds);

        //seekbar
        var seek = player.currentTime() / player.duration() * seekMax;
        $("#seekBar").val(seek);
    });

    player.on("ended", function(){
        $("#currentTime").html($("#durationTime").html());
        fnSaveStudyTime();
        opener.location.reload();
        alert("학습이 완료되었습니다.");
    });

    $(".vod_blank").on("click", function(e){
        if (player.paused()) {
            $(".vod_btn").css("background","url(../images/vod_pause.png) no-repeat");
            $("#play").addClass("pause");
            player.play();
            fnSaveStudyTime();
            timer = setInterval(function(){
                fnSaveStudyTime();
            }, 30000);

        } else {
            $("#play").removeClass("pause");
            $(".vod_btn").css("background","url(../images/vod_play.png) no-repeat");
            player.pause();
            clearInterval(timer);
        }
    });

    $(".vod_blank").on("mouseover", function(e){
        $(".vod_btn").css("opacity", "0.5");
    });

    $(".vod_blank").on("mouseout", function(e){
        if(!player.paused()){
            $(".vod_btn").css("opacity", "0");
        }
    });

   $("#seekBar").on("change", function(e){
        var seekto = player.duration() * $(this).val() / $(this).attr("max");
        if(studyTime < seekto){
            alert("학습 되지 않은 위치로 이동이 불가능합니다.");
            return false;
        }else{
            player.currentTime(seekto);
        }
    });

    $("#play").on("click", function(e){
        if (player.paused()) {
            $(".vod_btn").css("background","url(../images/vod_pause.png) no-repeat");
            $("#play").addClass("pause");
            player.play();
            fnSaveStudyTime();
            timer = setInterval(function(){
                fnSaveStudyTime();
            }, 30000);

        } else {
            $("#play").removeClass("pause");
            $(".vod_btn").css("background","url(../images/vod_play.png) no-repeat");
            player.pause();
            clearInterval(timer);
        }

    });

    $("#playStop").on("click", function(e){
        player.currentTime(0);
        $("#play").removeClass("pause");
        player.pause();
        clearInterval(timer)
    });

    $("#playGo").on("click", function(e){
        if(studyTime < player.currentTime()+10){
            alert("학습 되지 않은 위치로 이동이 불가능합니다.");
            return false;
        }else{
            player.currentTime(player.currentTime()+10);
        }
    });

    $("#playBack").on("click", function(e){
        player.currentTime(player.currentTime()-10);
    });

    $("#mute").on("click", function(e){
        if(player.volume() == "0"){
            player.volume(0.5);
            $("#volumeBar").val(5);
        }else{
            player.volume(0);
            $("#volumeBar").val(0);
        }
    });

    $("#volumeBar").on("change", function(e){
        player.volume($(this).val()/10);
    });

    $("#playbackSpeed").on("change", function(e){
        player.playbackRate($(this).val());
    });

    $("#fullScreen").on("click", function(e){
        player.requestFullscreen();
    });

    $("#btnClose").on("click", function(e){
        if(confirm("학습창을 종료하시겠습니까?")){
            fnSaveStudyTime();
            opener.location.reload();
            window.close();
        }
    });

    $("#btnPreClass").on("click", function (e) {
        let ordNo = $("#ORD_NO").val();
        if (Number(ordNo)-1 > 0) {
            fnMoveClass(Number(ordNo)-1);
        } else {
            alert("이전 수업이 없습니다.");
        }
    });

    $("#btnNextClass").on("click", function (e) {
        let ordNo = $("#ORD_NO").val();
        let ordCnt = $("#ORD_CNT").val();
        console.log(ordCnt);
        console.log(Number(ordNo)+1);
        if (Number(ordNo)+1 > ordCnt) {
            alert("다음 수업이 없습니다.");
        } else {
            fnMoveClass(Number(ordNo)+1);
        }
    });

    var fnSaveStudyTime = function(){
        var params = {};
        params.EDU_APLY_NO = $("#EDU_APLY_NO").val();
        params.EDU_ID = $("#EDU_ID").val();
        params.ORD_NO = $("#ORD_NO").val();
        params.GBN = $("#GBN").val();

        //학습시간(재생시간)
        params.STUDY_TIME = player.currentTime();

        //진도율
        // if(player.currentTime() > 0){
        //     var studyRate = Math.floor(player.currentTime() / player.duration() * 100);
        //     params.STUDY_RATE = studyRate;
        // }

        $.ajax({
            url: "../edu/saveStudyTime.do",
            type: 'POST',
            data : params,
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            cache: false,
            async: false,
            success: function(data) {
                /*if(data.eduCertNo != null){
                    alert(data.eduCertNo);
                }*/
            },
            error: function(xhr) {
                console.log('실패 - ', xhr);
            }
        });
    }
}

//nav sub menu drop
if (matchMedia("screen and (min-width: 1000px)").matches) {
    $(document).ready(function () {
        $menuRight = $('.pushmenu-right');
        $nav_list = $('.push-btn');

        $nav_list.click(function () {
            $(this).toggleClass('active');
            $('.pushmenu-push').toggleClass('pushmenu-push-toright');
            $menuRight.toggleClass('pushmenu-open');
        });

        $(".vod_RT .cont_close").click(function () {
            $(this).toggleClass('active');
            $('.pushmenu-push').toggleClass('pushmenu-push-toright');
            $menuRight.toggleClass('pushmenu-open');
        });

        $nav_list.click();
    });


} else {
    $(function () {
        $(".push-btn").click(function () {
            $(".vod_RT").fadeIn();
        });

        $(".vod_RT .cont_close").click(function () {
            $(".vod_RT").fadeOut();
        });
    });
}
