let postPage = function(e){
    let pageInit = () => {
        let postHtml = "";
        let postContent = postData.content;
        let searchTerm = "[이미지 출력]";
        let searchLength = searchTerm.length;
        for(let i = 0; i < postData?.postImages.length ; i++){
            let startIndex = postContent.indexOf(searchTerm, 0);
            let endIndex = startIndex + searchLength;

            postHtml += `<p>${postContent.substring(0, startIndex)}</p>`;
            postHtml += `<p>
                            <img src="${'/' + postData?.postImages[i]?.filePath.replace('src\\main\\resources\\static\\', '').replaceAll('\\', '/')}" alt="${postData?.postImages[i]?.originalFileName}"/>
                         </p>`;
            postContent = postContent.substring(endIndex);
        }
        postHtml += `<p>${postContent}</p>`;

        $("#postContent").html(postHtml);
    }

    return {
        init : function() {
            pageInit()
        },
    }
}();

$(function() {
    postPage.init();
})