package com.dhkim.blog.post.dto;

import lombok.Data;

@Data
public class ReplyDto {
    // 게시글_Id
    String post_id;
    // 댓글
    String comment;
    // 작성자 닉네임
    String nickname;
    // 대댓글
    String parentReplyId;
}
