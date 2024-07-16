let loginPage = function () {
    function loginFilter(user) {
        if(user.id === ""){
            alert("아이디를 입력 해주세요.");
            return false;
        }
        if(user.password === ""){
            alert("비밀번호를 입력했는지 확인해주세요.");
            return false;
        }
        return true;
    }
    function handleLoginBtnClick() {
        let loginUser = {
            "id" : document.getElementById("id").value.trim(),
            "password" : document.getElementById("password").value.trim()
        }

        if(loginFilter(loginUser)){
            $.ajax({
                url: window.location.protocol + "//" + window.location.host + "/ajax/sign-in",
                data: {
                    "id" : loginUser.id,
                    "password" : loginUser.password
                },
                method: "POST",
                success: function(res) {
                    if(res === "success"){
                        window.location.href = window.location.protocol + "//" + window.location.host + "/post/list";
                    }else{
                        alert("로그인에 실패했습니다.");
                    }
                }
            });
        }
    }

    return {
        init : function () {

        },
        handleLoginBtnClick
    }
}();

$(function (){
   loginPage.init();
});