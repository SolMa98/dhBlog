let postListPage = function(e){
    let pageInit = () => {
        // 글 작성 시간 포맷에 맞춰서 변경
        let modifiedTimes = document.getElementsByClassName("post-modified-time");
        for(let modifiedTime of modifiedTimes){
            modifiedTime.textContent = formattedDate(modifiedTime.textContent);
        }

        // 페이지 이동 이벤트
        $(document).on("click", ".pagination", function(e){
            window.location.href = ctx + "/post/list?page=" + e.target.dataset.page;
        });
    }

    function formattedDate(dateString) {
        let date = new Date(dateString);
        let options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' };
        return date.toLocaleDateString('en-GB', options).replace(/(\d+)\/(\d+)\/(\d+),\s(\d+):(\d+)/, '$3-$2-$1 $4:$5');
    }

    return {
        init : function() {
            pageInit()
        },
        formattedDate
    }
}();

$(function() {
    postListPage.init();
})