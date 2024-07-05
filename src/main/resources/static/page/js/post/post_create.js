let postCreate = function (e) {
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

        // 제목 유효성 검사
        if( typeof type === "undefined" || type === "title"){
            let titleValidation = title.value.trim() === "" ? "is-invalid" : "is-valid";
            validationText("title", titleValidation);
        }

        // 내용 유효성 검사
        if( typeof type === "undefined" || type === "content"){
            let contentValidation = content.value.trim() === "" ? "is-invalid" : "is-valid";
            validationText("content", contentValidation);
        }

        // 유효성 검사 결과에 따라서 submit 버튼 활성화/비활성화
        if(title.classList.contains("is-invalid") || content.classList.contains("is-invalid")){
            submitBtn.classList.add("disabled");
        }else{
            submitBtn.classList.remove("disabled");
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

    // 작성 버튼 클릭
    function handleCreateBtnClick(){
        console.log("작성 버튼 클릭");
        let post = {
            title : document.getElementById("title").value.trim(),
            writer : "테스터",
            content : document.getElementById("content").value.trim()
        }

        $.ajax({
            url: ctx + "/ajax/post/create",
            data: post,
            method: "POST",
            success: function(res) {
                console.log(res);
            },
            error: function(xhr, status, error) {
                console.error("AJAX 오류 발생:", error);
            }
        });
    }

    return {
        init : function() {
            postEvent()
        },
        handleCreateBtnClick
    }
}();

$(function() {
    postCreate.init();
})