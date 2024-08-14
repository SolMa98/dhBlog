let postListPage = function(e){
    let prevLink = window.location.href;
    let pageInit = () => {
        // 글 작성 시간 포맷에 맞춰서 변경
        let modifiedTimes = document.getElementsByClassName("post-modified-time");
        for(let modifiedTime of modifiedTimes){
            modifiedTime.textContent = formattedDate(modifiedTime.textContent);
        }

        // 페이지 이동 이벤트
        $(document).on("click", ".pagination", function(e){
            searchPost(e.target.dataset.page);
        });
    }

    function handleSearchBtnClick(){
        searchPost(0);
    }

    function handleSearchEnterKeyClick(e) {
        if(e.keyCode == 13) {
            searchPost(0);
        }
    }

    function searchPost(page){
        let searchType = document.getElementById('searchType').value;
        let searchData = document.getElementById('searchData').value;
        let searchObject = {
            page : page
        }
        if(searchData !== '') {
            switch (searchType) {
                case "title":
                    searchObject['title'] = searchData;
                    break;
                case "content":
                    searchObject['content'] = searchData;
                    break;
                case "writer":
                    searchObject['writer'] = searchData;
                    break;
                case "tc":
                    searchObject['title'] = searchData;
                    searchObject['content'] = searchData;
                    break;
            }
        }

        let postLink = ctx + '/post/list?';
        for (let key of Object.keys(searchObject)) {
            postLink += (key + '=' + searchObject[key] + '&');
        }
        postLink = postLink.slice(0, -1);
        if (postLink !== prevLink) {
            window.location.href = postLink;
        }
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
        handleSearchBtnClick,
        handleSearchEnterKeyClick
    }
}();

$(function() {
    postListPage.init();
})