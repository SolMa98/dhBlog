let accountCreate = function (e) {
    let pageInit = () => {
        // 닉네임 입력 칸 테두리 색 변경 이벤트
        let nicknameBox = document.getElementById("nickname-box");
        $(document).on("focus", ".nickname-box #nickname", function (e){
            nicknameBox.style.borderColor = "#0d6efd";
        });
        $(document).on("blur", ".nickname-box #nickname", function (e){
            nicknameBox.style.borderColor = "";
        });
    }
    return {
        init : function() {
            pageInit()
        },
    }
}();

$(function() {
    accountCreate.init();
})

