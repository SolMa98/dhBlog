let layout = function (){
    function handleLogoutBtnClick(){
        $.ajax({
            url: window.location.protocol + "//" + window.location.host + "/ajax/logout",
            method: "POST",
            success: function(res) {
                if(res === "success"){
                    window.location.reload();
                }else{
                    alert("로그아웃에 실패했습니다\n다시 시도해주세요");
                }
            }
        });
    }

    return {
        init : function (){

        },
        handleLogoutBtnClick
    }
}();

$(function (){
   layout.init();
});