package com.roc.service;

import com.roc.pojo.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentsByBlogId(Long blogId);

    Comment save(Comment comment);

    void delete(Long id);

    Long findBlogIdByCommentId(Long id);

    Long countComments();
}
