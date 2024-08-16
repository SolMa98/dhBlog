let postPage = function(e){
    let pageInit = () => {
        let postHtml = "";
        let searchTerm = "[이미지 출력]";
        let searchLength = searchTerm.length;
        for(let i = 0; i < images.length ; i++){
            let startIndex = content.indexOf(searchTerm, 0);
            let endIndex = startIndex + searchLength;

            postHtml += `<p>${content.substring(0, startIndex)}</p>`;
            postHtml += `<p>
                            <img src="${'/' + images[i]?.filePath.replace('src\\main\\resources\\static\\', '').replaceAll('\\', '/')}" alt="${images[i]?.originalFileName}"/>
                         </p>`;
            content = content.substring(endIndex);
        }
        postHtml += `<p>${content}</p>`;

        $("#postContent").html(postHtml);
    }

    function handleReplyViewBtnClick() {
        let replyStatus = document.getElementById("replyBtnImg");
        let replySpace = document.getElementById("replySpace");
        if(replyStatus.classList.contains("post-reply-unfold")){
            replyStatus.classList.remove("post-reply-unfold");
            replyStatus.classList.add("post-reply-fold");
            replySpace.classList.remove("d-none");
        }else{
            replyStatus.classList.add("post-reply-unfold");
            replyStatus.classList.remove("post-reply-fold");
            replySpace.classList.add("d-none");
        }
    }

    function handleUpdateBtnClick() {
        window.location.href = ctx + "/post/cu?id=" + postId;
    }

    function handleDeleteBtnClick(){
        let deleteConfirm = confirm("게시글을 삭제 하시겠습니까?");
        if(deleteConfirm){
            $.ajax({
                url: ctx + "/ajax/post",
                headers : {
                    "Authorization" : "Bearer " + token
                },
                data: { "id" : postId },
                method: "DELETE",
                success: function(res) {
                    if(res === "ok"){
                        alert("게시글 삭제가 완료 되었습니다.");
                        window.location.href = ctx + "/post/list";
                    }else{
                        alert("게시글 삭제가 실패했습니다.");
                    }
                }
            });
        }else{
            alert("게시글 삭제를 취소했습니다.");
        }
    }

    return {
        init : function() {
            pageInit()
        },
        handleUpdateBtnClick,
        handleDeleteBtnClick,
        handleReplyViewBtnClick
    }
}();

$(function() {
    postPage.init();
})