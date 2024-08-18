let postPage = function(e){
    let postId = document.getElementById("postId").value;
    let content = document.getElementById("content").value;
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

        for(let i = 0; i < replys.length; i++){
            replyDrawing(replys[i]);
        }
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
                    "Authorization" : "Bearer " + document.getElementById("token").value
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

    function replyDrawing(reply){
        let userNickname = document.getElementById("userNickName").value;
        let replyFunctionBtn = "";
        if(userNickname === reply.nickname.toString()){
            replyFunctionBtn = `<button class="reply-upt-btn">수정</button>
                                <button class="reply-del-btn" onclick="postPage.handleReplyDeleteBtnClick('${reply.id}')">삭제</button>`;
        }

        let replyHtml = `<div class="reply" id="reply_${reply.id}">
                                    <p>${reply.nickname}</p>
                                    <pre>${reply.comment}</pre>
                                    <div class="reply-btn-space">
                                        ${replyFunctionBtn}
                                    </div>
                                </div>`;
        $("#replyViewSpace").append(replyHtml);
    }

    function replyFilter(comment) {
        if(comment.trim() === ""){
            alert("댓글을 작성해주세요");
            return false;
        }
        return true;
    }

    function handleReplyInsertBtnClick(){
        let replyObject = {
            post_id : postId,
            comment : document.getElementById("insertReply").value,
            nickname : document.getElementById("userNickName").value
        }
        if(replyFilter(replyObject.comment)){
            $.ajax({
                url: ctx + "/ajax/post/reply",
                headers : {
                    "Authorization" : "Bearer " + document.getElementById("replyToken").value
                },
                data: replyObject,
                method: "POST",
                success: function(res) {
                    if(res !== "error"){
                        replyObject['id'] = res;
                        replyDrawing(replyObject);
                        document.getElementById("insertReply").value = "";
                    }else{
                        alert("댓글 등록이 실패했습니다.\n다시 시도해주세요.");
                    }
                }
            });
        }
    }

    function handleReplyDeleteBtnClick(id){
        let deleteConfirm = confirm("댓글을 삭제 하시겠습니까?");
        if(deleteConfirm){
            $.ajax({
                url: ctx + "/ajax/post/reply",
                headers : {
                    "Authorization" : "Bearer " + document.getElementById("replyToken").value
                },
                data: { "id" : id },
                method: "DELETE",
                success: function(res) {
                    if(res === "ok"){
                        document.getElementById("reply_" + id).remove();
                        alert("댓글 삭제가 완료 되었습니다.");
                    }else{
                        alert("댓글 삭제가 실패했습니다.");
                    }
                }
            });
        }
    }

    return {
        init : function() {
            pageInit()
        },
        handleUpdateBtnClick,
        handleDeleteBtnClick,
        handleReplyViewBtnClick,
        handleReplyInsertBtnClick,
        handleReplyDeleteBtnClick
    }
}();

$(function() {
    postPage.init();
})