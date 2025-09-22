
// 파일 드롭 다운
function fileDropDownView(dropZoneId) {
    var dropZone = $("#" + dropZoneId);
    //업로드 가능 파일 수
    var maxFileCnt = maxFileCnt;
    // 등록 가능한 파일 사이즈 MB
    var maxUploadSize = maxUploadSize;
    //업로드 가능 파일 수
    var currentFileCnt = 0;
    
    
    this.bindFiles = function (files){
        for(var i = 0; i < files.length; i++){
            var fileItem = files[i];
            var resultBind = new bindResultFiles(dropZone, fileItem);
            resultBind.setFileDownLink();
        }

    }
    function bindResultFiles(dropZone, fileItem){

        var itemFileId = fileItem.FILE_ID;
        var itemFileSeq =   fileItem.FILE_SEQ;
        var itemFileNm = fileItem.FILE_NM;
        var itemChgFileNm = fileItem.CHG_FILE_NM;
        var itemFileSize =  fileItem.FILE_SIZE;
        var itemFileSizeStr = "";
        var fileSizeKb = itemFileSize / 1024; // 파일 사이즈(단위 :KB)
        var fileSizeMb = fileSizeKb / 1024; // 파일 사이즈(단위 :MB)
        if ((1024*1024) <= itemFileSize) {  // 파일 용량이 1메가 이상인 경우 
            itemFileSizeStr = "(" + fileSizeMb.toFixed(2) + " MB)";
        } else if ((1024) <= itemFileSize) {
            itemFileSizeStr = "(" + parseInt(fileSizeKb) + " KB)";
        } else {
            itemFileSizeStr = "(" + parseInt(itemFileSize) + " byte)";
        }
        
        this.statusbar = $("<div class='statusbar fr' >");
        this.filename = $("<div class='filename link'><img src='../images/ic_down.png' alt='다운로드 아이콘'> "+itemFileNm + " " + itemFileSizeStr+"</div>").appendTo(this.statusbar);
        this.fileId = $("<input type='hidden' id='fileId' value='"+itemFileId+"'/>").appendTo(this.statusbar);
        this.fileSeq = $("<input type='hidden' id='fileSeq' value='"+itemFileSeq+"'/>").appendTo(this.statusbar);
        this.fileNm = $("<input type='hidden' id='fileNm' value='"+itemFileNm+"'/>").appendTo(this.statusbar);
        this.chgFileNm = $("<input type='hidden' id='chgFileNm' value='"+itemChgFileNm+"'/>").appendTo(this.statusbar);
        
        dropZone.find('#fileList').append(this.statusbar);
        
        this.setFileDownLink = function(){
            var itmFileId = this.fileId;
            var itmFileSeq = this.fileSeq;
            var itmChgFileNm = this.chgFileNm;
            this.filename.click(function(){
                
                downloadFile(itmFileId.val(), itmFileSeq.val(), itmChgFileNm.val());
            });
        }

       dropZone.find(".fileDragDesc").hide(); 
    }
    
    
    function downloadFile(fileId, fileSeq, chgFileNm){
        
        var fileDownFrm = $("<form></form>");
        fileDownFrm.attr("name", "fileDownFrm");
        fileDownFrm.attr("method", "post");
        fileDownFrm.attr("action", "../comm/downloadAtchFile.do");
        
        fileDownFrm.append($("<input/>", {type:'hidden', name:'fileId', value:fileId}));
        fileDownFrm.append($("<input/>", {type:'hidden', name:'fileSeq', value:fileSeq}));
        fileDownFrm.append($("<input/>", {type:'hidden', name:'chgFileNm', value:chgFileNm}));
        
        fileDownFrm.appendTo("body");
        
        fileDownFrm.submit();
        
        fileDownFrm.remove();
        
    
    }
    
}


// 파일 드롭 다운
function fileDropDown(dropZoneId, maxFileCnt, maxUploadSize) {
    var dropZone = $("#" + dropZoneId);
    //업로드 가능 파일 수
    var maxFileCnt = maxFileCnt;
    // 등록 가능한 파일 사이즈 MB
    var maxUploadSize = maxUploadSize;
    //업로드 가능 파일 수
    var currentFileCnt = 0;

    //Drag기능 
    dropZone.on('dragenter', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $(this).css('background-color', '#E3F2FC');
        $(this).css('border', '2px solid #5272A0');
    });
    dropZone.on('dragleave', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $(this).css('background-color', '#FFFFFF');
        $(this).css('border', '2px dotted #8296C2');
    });
    dropZone.on('dragover', function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $(this).css('background-color', '#E3F2FC');
    });
    dropZone.on('drop', function(e) {
        e.preventDefault();
        // 드롭다운 영역 css
        $(this).css('background-color', '#FFFFFF');
        $(this).css('border', '2px dotted #8296C2');

        var files = e.originalEvent.dataTransfer.files;
        if (files != null) {
            if (files.length < 1) {
                /* alert("폴더 업로드 불가"); */
                console.log("폴더 업로드 불가");
                return;
            } else {
                handleFileUpload(dropZone, files)
            }
        } else {
            alert("ERROR");
        }
    });
    
    this.handleFileUploadSelect = function ( fileObject,validFileExt){

        handleFileUpload(dropZone, fileObject,validFileExt);
    }
    function handleFileUpload(dropZone, fileObject, validFileExt) {
        var files = fileObject;

        if(validFileExt == undefined) validFileExt = "default";
        // 다중파일 등록
        if (files != null) {
            
            if (files != null && files.length > 0) {
                dropZone.find(".fileDragDesc").hide(); 
                dropZone.find(".fileListTable").show();
            } else {
                dropZone.find(".fileDragDesc").show(); 
                dropZone.find(".fileListTable").hide();
            }
            
            for (var i = 0; i < files.length; i++) {
                // 파일 이름
                var fileName = files[i].name;
                var fileNameArr = fileName.split("\.");
                // 확장자
                var ext = fileNameArr[fileNameArr.length - 1];
                
                var fileSize = files[i].size; // 파일 사이즈(단위 :byte)
                console.log("fileSize="+fileSize);
                if (fileSize <= 0) {
                    console.log("0kb file return");
                    return;
                }
                
                var fileSizeKb = fileSize / 1024; // 파일 사이즈(단위 :KB)
                var fileSizeMb = fileSizeKb / 1024; // 파일 사이즈(단위 :MB)
                
                var fileSizeStr = "";
                if ((1024*1024) <= fileSize) {  // 파일 용량이 1메가 이상인 경우 
                    fileSizeStr = "(" + fileSizeMb.toFixed(2) + " MB)";
                } else if ((1024) <= fileSize) {
                    fileSizeStr = "(" + parseInt(fileSizeKb) + " KB)";
                } else {
                    fileSizeStr = "(" + parseInt(fileSize) + " byte)";
                }

                /*if(validFileExt == "default"){
                    if (validFileExtCheck(ext)) {
                        alert("등록이 불가능한 파일 입니다.("+fileName+")");
                        break;
                    }
                }else if(validFileExt == "doc"){
                     if (validFileExtDocCheck(ext)) {
                        alert("문서파일만 등록 가능합니다.("+fileName+")");
                        break;
                    }
                }else if(validFileExt == "img"){
                     if (validFileExtImgCheck(ext)) {
                        alert("이미지만 등록 가능입니다.("+fileName+")");
                        break;
                    }
                }else if(validFileExt == "video"){
                    if (validFileExtVideoCheck(ext)) {
                        alert("동영상만 등록 가능입니다.("+fileName+")");
                        break;
                    }
                }*/

                
                if (fileSizeMb > maxUploadSize) {
                    // 파일 사이즈 체크
                    alert("업로드 가능 용량("+ maxUploadSize +" MB)을 초과하였습니다.");
                    break;
                } 
                    
                //현재 파일 갯수 증가
                currentFileCnt ++;
                if( currentFileCnt <= maxFileCnt){
                    
                    var fd = new FormData();
                    fd.append('file', files[i]);
             
                    var status = new createStatusbar(dropZone); //Using this we can set progress.
                    status.setFileNameSize(fileName, fileSizeStr);
                    sendFileToServer(fd, status);
                    
                }else{
                    alert("최대 " + maxFileCnt + "개 까지 등록 가능합니다.");
                    //현재 파일 갯수 감소
                    currentFileCnt --;
                    break;
                }
                
            }
        } else {
            alert("ERROR");
        }
        
    }
    

    function createStatusbar(dropZone){
        var zoneId = dropZone[0].id;
        
        this.statusbar = $("<div class='statusbar fr' >");
        this.progressBar = $("<div class='progressBar'><div></div></div>").appendTo(this.statusbar);
        this.abort = $("<div class='abort'>중지</div>").appendTo(this.statusbar);
        this.filename = $("<div class='filename'></div>").appendTo(this.statusbar);
        this.del = $("<div class='del'><button type='button' class='btn-del'>삭제</button></div>").appendTo(this.statusbar);
        this.fileNm = $("<input type='hidden' id='" +zoneId+ "_fileNm' name='" +zoneId+ "_fileNm' value=''/>").appendTo(this.statusbar);
        this.chgFileNm = $("<input type='hidden' id='" +zoneId+ "_chgFileNm' name='" +zoneId+ "_chgFileNm' value=''/>").appendTo(this.statusbar);
        this.fileSize = $("<input type='hidden' id='" +zoneId+ "_fileSize' name='" +zoneId+ "_fileSize' value=''/>").appendTo(this.statusbar);
        this.filePath = $("<input type='hidden' id='" +zoneId+ "_filePath' name='" +zoneId+ "_filePath' value=''/>").appendTo(this.statusbar);
        dropZone.find('#fileList').append(this.statusbar);
        
     
        this.setFileNameSize = function(name, sizeStr){
            this.filename.html(name + " " + sizeStr);
        }
        
        this.setProgress = function(progress){       
            var progressBarWidth =progress*this.progressBar.width()/ 100;  
            this.progressBar.find('div').animate({ width: progressBarWidth }, 10).html(progress + "% ");
            if(parseInt(progress) >= 100)
            {
                this.abort.hide();
            }
        }
        
        this.setAbort = function(jqxhr){
            var sb = this.statusbar;
            this.abort.click(function()
            {
                jqxhr.abort();
                removeFile(dropZone, sb);
                //sb.hide();
            });
        }
        
        this.setDel = function(){
            var sb = this.statusbar;
            this.del.click(function()
            {
                removeFile(dropZone, sb);
            });
        }
        
        //파일 업로드 완료 후 저장 정보 세팅
        this.setFileNm = function(name){
            this.fileNm.val(name);
        }
        this.setChgFileNm = function(chgName){
            this.chgFileNm.val(chgName);
        }
        this.setFileSize = function(size){
            this.fileSize.val(size);
        }
        this.setFilePath = function(path){
            this.filePath.val(path);
        }
    }
    
    function sendFileToServer(formData, status){
        var uploadURL = "../comm/fileUpload.do"; //Upload URL
        var extraData ={}; //Extra Data.
        var jqXHR=$.ajax({
                xhr: function() {
                var xhrobj = $.ajaxSettings.xhr();
                if (xhrobj.upload) {
                        xhrobj.upload.addEventListener('progress', function(event) {
                            var percent = 0;
                            var position = event.loaded || event.position;
                            var total = event.total;
                            if (event.lengthComputable) {
                                percent = Math.ceil(position / total * 100);
                            }
                            //Set progress
                            status.setProgress(percent);
                        }, false);
                    }
                return xhrobj;
            },
            url: uploadURL,
            type: "POST",
            enctype : 'multipart/form-data',
            contentType:false,
            processData: false,
            cache: false,
            data: formData,
            success: function(data){
                console.log(data);
                if(data.error == 'Y'){
                    removeFile(dropZone, status.statusbar);
                    alert("등록할 수 없는 파일입니다.");
                }else{
                    status.setProgress(100);
                    status.setDel();
                    status.setFileNm(data.originFileNm);
                    status.setChgFileNm(data.chgFileNm);
                    status.setFileSize(data.fileSize);
                    status.setFilePath(data.filePath);
                }
            },
            error: function(data){
                console.log(data);
            }
        }); 
     
        status.setAbort(jqXHR);
    }
    
    function validFileExtCheck(ext){
        var validExt = [ 'hwp', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'png', 'pdf', 'jpg', 'jpeg', 'gif', 'zip' ];
        return ($.inArray(ext.toLowerCase(), validExt) < 0);
    }
    
    //문서
    function validFileExtDocCheck(ext){
        var validExt = [ 'hwp', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'zip' ];
        return ($.inArray(ext.toLowerCase(), validExt) < 0);
    }
    
    //이미지
    function validFileExtImgCheck(ext){
        var validExt = ['jpg', 'jpeg', 'gif', 'png' ];
        return ($.inArray(ext.toLowerCase(), validExt) < 0);
    }

    //동영상
    function validFileExtVideoCheck(ext){
        var validExt = ['mp4', 'avi', 'flv' ];
        return ($.inArray(ext.toLowerCase(), validExt) < 0);
    }


    function removeFile(dropZone, sb) {
        
        //현재 파일 갯수 감소
        currentFileCnt --;

        // 업로드 파일 테이블 목록에서 삭제
        sb.remove();
        

        if (currentFileCnt > 0) {
            dropZone.find(".fileDragDesc").hide(); 
        } else {
            dropZone.find(".fileDragDesc").show(); 
        }
    }
    this.bindFiles = function (files){
        for(var i = 0; i < files.length; i++){
            var fileItem = files[i];
            var resultBind = new bindResultFiles(dropZone, fileItem);
            resultBind.setDel();
            resultBind.setFileDownLink();
        }

    }
    function bindResultFiles(dropZone, fileItem){

        var itemFileId = fileItem.FILE_ID;
        var itemFileSeq =   fileItem.FILE_SEQ;
        var itemFileNm = fileItem.FILE_NM;
        var itemChgFileNm = fileItem.CHG_FILE_NM;
        var itemFileSize =  fileItem.FILE_SIZE;
        var itemFileSizeStr = "";
        var fileSizeKb = itemFileSize / 1024; // 파일 사이즈(단위 :KB)
        var fileSizeMb = fileSizeKb / 1024; // 파일 사이즈(단위 :MB)
        if ((1024*1024) <= itemFileSize) {  // 파일 용량이 1메가 이상인 경우 
            itemFileSizeStr = "(" + fileSizeMb.toFixed(2) + " MB)";
        } else if ((1024) <= itemFileSize) {
            itemFileSizeStr = "(" + parseInt(fileSizeKb) + " KB)";
        } else {
            itemFileSizeStr = "(" + parseInt(itemFileSize) + " byte)";
        }
        
        this.statusbar = $("<div class='statusbar fr' >");
        this.filename = $("<div class='filename link'><img src='../images/ic_down.png' alt='다운로드 아이콘'> "+itemFileNm + " " + itemFileSizeStr+"</div>").appendTo(this.statusbar);
        this.del = $("<div class='del'><button type='button' class='btn-del'>삭제</button></div>").appendTo(this.statusbar);
        this.fileId = $("<input type='hidden' id='fileId' value='"+itemFileId+"'/>").appendTo(this.statusbar);
        this.fileSeq = $("<input type='hidden' id='fileSeq' value='"+itemFileSeq+"'/>").appendTo(this.statusbar);
        this.fileNm = $("<input type='hidden' id='fileNm' value='"+itemFileNm+"'/>").appendTo(this.statusbar);
        this.chgFileNm = $("<input type='hidden' id='chgFileNm' value='"+itemChgFileNm+"'/>").appendTo(this.statusbar);
        
        dropZone.find('#fileList').append(this.statusbar);
        
        this.setDel = function(){
            var sb = this.statusbar;
            var itmFileId = this.fileId;
            var itmFileSeq = this.fileSeq;
            var itmFileNm = this.fileNm;
            var itmChgFileNm = this.chgFileNm;
            this.del.click(function(){
                var fd = new FormData();
                fd.append('fileId', itmFileId.val());
                fd.append('fileSeq', itmFileSeq.val());
                fd.append('fileNm', itmFileNm.val());
                fd.append('chgFileNm', itmChgFileNm.val());
                deleteServerFile(dropZone, sb, fd, itmFileNm.val());
            });
        }
        
        this.setFileDownLink = function(){
            var itmFileId = this.fileId;
            var itmFileSeq = this.fileSeq;
            var itmChgFileNm = this.chgFileNm;
            this.filename.click(function(){
                
                downloadFile(itmFileId.val(), itmFileSeq.val(), itmChgFileNm.val());
            });
        }

        
        currentFileCnt ++;
        
        if (currentFileCnt > 0) {
            dropZone.find(".fileDragDesc").hide(); 
        } else {
            dropZone.find(".fileDragDesc").show(); 
        }
                
    }
    
    function deleteServerFile(dropZone, sb, formData, fileNm){
        if(confirm("선택한 '" + fileNm + "' 파일을 삭제하시겠습니까?")){
            var url = "../comm/deleteAtchFile.do";

            $.ajax({
                url: url,
                type: 'POST',
                data : formData,
                contentType:false,
                processData: false,
                cache: false,
                success: function(data) {
                    if(data.result > 0){
                        alert("정상적으로 삭제되었습니다.");
                        removeFile(dropZone, sb);
                    }else{
                        alert("파일 삭제를 실패하였습니다.\n관리자에게 문의 바랍니다.");
                    }
                },
                error: function(xhr) {
                    alert("파일 삭제를 실패하였습니다.\n관리자에게 문의 바랍니다.");
                  console.log('실패 - ', xhr);
                }
            });
        }
    }
    
    function downloadFile(fileId, fileSeq, chgFileNm){
        
        var fileDownFrm = $("<form></form>");
        fileDownFrm.attr("name", "fileDownFrm");
        fileDownFrm.attr("method", "post");
        fileDownFrm.attr("action", "../comm/downloadAtchFile.do");
        
        fileDownFrm.append($("<input/>", {type:'hidden', name:'fileId', value:fileId}));
        fileDownFrm.append($("<input/>", {type:'hidden', name:'fileSeq', value:fileSeq}));
        fileDownFrm.append($("<input/>", {type:'hidden', name:'chgFileNm', value:chgFileNm}));
        
        fileDownFrm.appendTo("body");
        
        fileDownFrm.submit();
        
        fileDownFrm.remove();
        
    
    }
    
}



