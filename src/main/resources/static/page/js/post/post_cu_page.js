let postCUPage = function (e) {
    let imgList = {};
    let imgDeleteList = [];

    let pageInit = () => {
        for(let postImage of postImages){
            dbImgDrawing(postImage?.id, postImage?.filePath)
        }
    }

    let postEvent = () => {
        // 제목 추가 or 수정
        let title = document.getElementById("title");
        title.addEventListener("input", function (e) {
            postFilter("title");
        });

        // 내용 추가 or 수정
        let content = document.getElementById("content");
        content.addEventListener("input", function (e) {
           postFilter("content");
        });

        // 이미지 추가 버튼 클릭
        let imgUploadBtn = document.getElementById("imgUploadBtn");
        let imgUploadInput = document.getElementById("imgUpload");
        imgUploadBtn.addEventListener("click", function (e){
            imgUploadInput.click();
        });
    }

    // 유효성 필터
    function postFilter( type ){
        let title = document.getElementById("title");
        let content = document.getElementById("content");
        let submitBtn = document.getElementById("createBtn");
        let updateBtn = document.getElementById("updateBtn");

        // 제목 유효성 검사
        if( typeof type === "undefined" || type === "title"){
            let titleValidation = title.value.trim() === "" ? "is-invalid" : "is-valid";
            validationText("title", titleValidation);
        }

        // 내용 유효성 검사
        if( typeof type === "undefined" || type === "content"){
            let contentValidation = (content.value.trim() === "")
                || (countOccurrences(content.value, "[이미지 출력]") !== Object.keys(imgList).length)
                ? "is-invalid" : "is-valid";
            validationText("content", contentValidation);
        }

        // 유효성 검사 결과에 따라서 submit 버튼 활성화/비활성화
        if(title.classList.contains("is-invalid") || content.classList.contains("is-invalid")){
            submitBtn.classList.add("disabled");
            updateBtn.classList.add("disabled");
        }else{
            submitBtn.classList.remove("disabled");
            updateBtn.classList.remove("disabled");
        }
    }

    // validation 결과 Class target에 추가
    function validationText(type, text){
        validationReset(type);
        let validationTarget = document.getElementById(type);
        validationTarget.classList.add(text);
    }

    // validation 관련 클래스 제거
    function validationReset(type) {
        let validationClassList = ["is-fixed", "is-invalid", "is-valid"];
        let validationTarget = document.getElementById(type);

        for(let validationClass of validationClassList){
            if (validationTarget.classList.contains(validationClass)) {
                validationTarget.classList.remove(validationClass);
            }
        }
    }

    // 특정 문구 찾기
    function countOccurrences(text, searchString) {
        let escapedSearchString = searchString.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
        let regex = new RegExp(escapedSearchString, 'g');
        let matches = text.match(regex);
        return matches ? matches.length : 0;
    }

    // 이미지 등록 버튼 클릭 후 이미지 등록 시 이벤트
    function handleImgInputBtnClick() {
        $(document).on("change", "#imgUpload", function (e){
            let randomId = Math.random().toString(36).substr(2,11);
            imgList[randomId] = e.target.files[0];
            imgDrawing(randomId);
            postFilter("content");
            // 이미지 삽입 input tag 초기화
            e.target.value = "";
        });
    }

    function dbImgDrawing(id, src){
        let imgPreviewHtml = `<div id="img_${id}">
                                        <img id="previewImg${id}" src="${'/' + src.replace('src\\main\\resources\\static\\', '').replaceAll('\\', '/')}" data-key="${id}" />
                                        <a class="img-delete" onclick="postCUPage.dbImgDelete('${id}')"/>
                                     </div>`;
        let imgViewer = $("#imgViewer");

        if(imgViewer.children("div").length === 0){
            imgViewer.html(imgPreviewHtml);
        }else{
            imgViewer.append(imgPreviewHtml);
        }
    }

    // 이미지 Html에 삽입
    function imgDrawing(randomId) {
        let imgPreviewHtml = `<div id="img_${randomId}">
                                        <img id="previewImg${randomId}" data-key="${randomId}" />
                                        <a class="img-delete" onclick="postCUPage.imgDelete('${randomId}')"/>
                                     </div>`;
        let imgViewer = $("#imgViewer");

        if(imgViewer.children("div").length === 0){
            imgViewer.html(imgPreviewHtml);
        }else{
            imgViewer.append(imgPreviewHtml);
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('previewImg' + randomId).src = e.target.result;
        }

        reader.readAsDataURL(imgList[randomId]);
    }

    // 이미지 삭제
    function imgDelete(randomId){
        $("#img_" + randomId).remove();
        delete imgList[randomId];

        if($("#imgViewer").children("div").length === 0){
            let noneImgMessage = `<label>등록된 이미지가 없습니다.</label>`;
            $("#imgViewer").html(noneImgMessage);
        }
    }

    // DB 저장 이미지 삭제
    function dbImgDelete(id) {
        $("#img_" + id).remove();
        imgDeleteList.push(id);

        if($("#imgViewer").children("div").length === 0){
            let noneImgMessage = `<label>등록된 이미지가 없습니다.</label>`;
            $("#imgViewer").html(noneImgMessage);
        }
    }

    // 게시글 등록
    function handleCreateBtnClick(){
        let imgFormData = new FormData();
        imgFormData.append('title', document.getElementById("title").value.trim());
        imgFormData.append('content', document.getElementById("content").value.trim());
        imgFormData.append('writer', document.getElementById("name").value.trim());

        Object.keys(imgList).forEach(function(key) {
            imgFormData.append('images', imgList[key]);
        });

        $.ajax({
            url: ctx + "/ajax/post/create",
            headers : {
              "Authorization" : "Bearer " + token
            },
            data: imgFormData,
            method: "POST",
            processData: false,
            contentType: false,
            success: function(res) {
                if(res === "success"){
                    window.location.href = ctx + "/post/list";
                }else{
                    alert("게시글 등록이 실패했습니다.");
                }
            }
        });
    }

    function handleUpdateBtnClick(){
        let updateFormData = new FormData();
        let postId = document.getElementById("postId").value.trim()
        updateFormData.append('id', postId);
        updateFormData.append('title', document.getElementById("title").value.trim());
        updateFormData.append('content', document.getElementById("content").value.trim());
        updateFormData.append('writer', document.getElementById("name").value.trim());

        Object.keys(imgList).forEach(function(key) {
            updateFormData.append('images', imgList[key]);
        });

        imgDeleteList.forEach(function(id) {
            updateFormData.append('deleteImages', id);
        });

        $.ajax({
            url: ctx + "/ajax/post/update",
            headers : {
                "Authorization" : "Bearer " + token
            },
            data: updateFormData,
            method: "PUT",
            processData: false,
            contentType: false,
            success: function(res) {
                if(res === "success"){
                    window.location.href = ctx + "/post/page?id=" + postId;
                }else{
                    alert("게시글 수정에 실패했습니다.");
                }
            }
        });
    }

    return {
        init : function() {
            pageInit()
            postEvent()
            handleImgInputBtnClick()
        },
        handleCreateBtnClick,
        handleUpdateBtnClick,
        imgDelete,
        dbImgDelete
    }
}();

$(function() {
    postCUPage.init();
})