let accountCreate = function (e) {
    let passId = "";
    let passIdBoolean = false;

    let passName = "";
    let passNameBoolean = false;

    let pageInit = () => {
        // 아이디 입력 칸 테두리 색 변경 이벤트
        let idBox = document.getElementById("id-box");
        $(document).on("focus", ".nickname-box #id", function (e){
            idBox.style.borderColor = "#0d6efd";
        });
        $(document).on("blur", ".nickname-box #id", function (e){
            idBox.style.borderColor = "";
        });
        // 닉네임 입력 칸 테두리 색 변경 이벤트
        let nicknameBox = document.getElementById("nickname-box");
        $(document).on("focus", ".nickname-box #nickname", function (e){
            nicknameBox.style.borderColor = "#0d6efd";
        });
        $(document).on("blur", ".nickname-box #nickname", function (e){
            nicknameBox.style.borderColor = "";
        });

        // 아이디 변경 시 중복 체크 메시지 활성화
        $(document).on("change", "#id", function (e){
            if(passId === document.getElementById("id").value.trim()){
                $("#idAlertMessage").text("");
                passIdBoolean = true;
            }else{
                $("#idAlertMessage").text("아이디 중복 체크를 해주세요.");
                passIdBoolean = false;
            }
        });
        // 닉네임 변경 시 중복 체크 메시지 활성화
        $(document).on("change", "#nickname", function (e){
            if(passName === document.getElementById("nickname").value.trim()){
                $("#nicknameAlertMessage").text("");
                passNameBoolean = true;
            }else{
                $("#nicknameAlertMessage").text("닉네임 중복 체크를 해주세요.");
                passNameBoolean = false;
            }
        });
    }

    function accountFilter(account){
        let isValidName = /([^가-힣])/i.test(account.name);
        let checkPassword = document.getElementById("passwordCheck").value.trim();

        if(account.id === ""){
            alert("아이디를 입력 해주세요.");
            return false;
        }
        if(!passNameBoolean){
            alert("아이디 중복 체크를 해주세요.");
            return false;
        }
        if(account.password === "" || checkPassword === ""){
            alert("비밀번호를 입력했는지 확인해주세요.");
            return false;
        }
        if(account.password !== checkPassword){
            alert("비밀번호가 일치 하지 않습니다");
            return false;
        }
        if(account.name === ""){
            alert("이름을 입력해주세요.");
            return false;
        }
        if(isValidName){
            alert("이름에 잘못된 문자가 있는지 확인 해주세요.");
            return false;
        }

        if(!passNameBoolean){
            alert("닉네임 중복 체크를 해주세요.");
            return false;
        }

        return true;
    }

    function handleIdCheckBtnClick(){
        let id = document.getElementById("id").value.trim();
        let isValidId = /([^a-zA-Z0-9\x20])/i.test(id);

        if(id === "" || isValidId){
            if(id === ""){
                $("#idAlertMessage").text("아이디를 입력해주세요.");
                passIdBoolean = false;
                alert("아이디를 입력해주세요.");
            }
            if(isValidId){
                $("#idAlertMessage").text("사용할 수 없는 아이디 입니다.");
                passIdBoolean = false;
                alert("사용할 수 없는 아이디 입니다.");
            }
        }else{
            $.ajax({
                url: window.location.protocol + "//" + window.location.host + "/ajax/account/id/check",
                data: { "id" : id },
                method: "GET",
                success: function(res) {
                    if(res){
                        $("#idAlertMessage").text("");
                        passId = id;
                        passIdBoolean = true;
                        alert("사용할 수 있는 아이디 입니다.");
                    }else{
                        $("#idAlertMessage").text("이미 사용 중인 아이디 입니다.");
                        passIdBoolean = false;
                        alert("이미 사용 중인 아이디 입니다.");
                    }
                }
            });
        }
    }
    
    function handleNicknameCheckBtnClick(){
        let nickname = document.getElementById("nickname").value.trim();
        let isValidName = /([^가-힣a-zA-Z0-9\x20])/i.test(nickname);

        if(nickname === "" || isValidName){
            if(nickname === ""){
                $("#nicknameAlertMessage").text("닉네임을 입력해주세요.");
                passNameBoolean = false;
                alert("닉네임을 입력해주세요.");
            }
            if(isValidName){
                $("#nicknameAlertMessage").text("사용할 수 없는 닉네임 입니다.");
                passNameBoolean = false;
                alert("사용할 수 없는 닉네임 입니다.");
            }
        }else{
            $.ajax({
                url: window.location.protocol + "//" + window.location.host + "/ajax/account/nickname/check",
                data: { "nickname" : nickname },
                method: "GET",
                success: function(res) {
                    if(res){
                        $("#nicknameAlertMessage").text("");
                        passName = nickname;
                        passNameBoolean = true;
                        alert("사용할 수 있는 닉네임 입니다.");
                    }else{
                        $("#nicknameAlertMessage").text("이미 사용 중인 닉네임 입니다.");
                        passNameBoolean = false;
                        alert("이미 사용 중인 닉네임 입니다.");
                    }
                }
            });
        }
    }

    function handleAccountCreateBtnClick(){
        let account = {
            "id" : document.getElementById("id").value.trim(),
            "password" : document.getElementById("password").value.trim(),
            "nickname" : document.getElementById("nickname").value.trim(),
            "name" : document.getElementById("name").value.trim(),
            "gender" : $("input[name='gender']:checked").val()
        }

        if(accountFilter(account)){
            $.ajax({
                url: window.location.protocol + "//" + window.location.host + "/ajax/account",
                data: account,
                method: "POST",
                success: function(res) {
                    if(res === "success"){
                        alert("계정이 성공적으로 생성되었습니다.");
                        window.location.href = window.location.protocol + "//" + window.location.host + "/login";
                    }else{
                        alert("계정 생성 중 오류가 발생했습니다\n다시 한번 시도해주세요.");
                    }
                }
            });
        }
    }

    return {
        init : function() {
            pageInit()
        },
        handleIdCheckBtnClick,
        handleAccountCreateBtnClick,
        handleNicknameCheckBtnClick
    }
}();

$(function() {
    accountCreate.init();
})

