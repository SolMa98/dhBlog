let template = function (e) {
    let temPlateInit = () => {
        console.log("테스트");
    }

    let test = () => {

    }

    return {
        init : function() {
            temPlateInit()
        },
        test
    }
}

$(function() {
    template().init();
})