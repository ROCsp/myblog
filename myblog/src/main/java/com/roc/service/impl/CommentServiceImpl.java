package com.roc.service.impl;

import com.roc.dao.CommentDao;
import com.roc.pojo.Comment;
import com.roc.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> listCommentsByBlogId(Long blogId) {

        Sort sort = Sort.by(Sort.Direction.ASC, "createDate");
        List<Comment> comments = commentDao.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        Long parentId = comment.getParentComment().getId();
        if ( parentId != -1){
            comment.setParentComment(commentDao.getOne(parentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateDate(new Date());
        return commentDao.save(comment);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        commentDao.deleteById(id);
    }

    @Override
    public Long findBlogIdByCommentId(Long id) {
        return commentDao.getOne(id).getBlog().getId();
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> newComments = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            newComments.add(c);
        }
        combineChildren(newComments);
        return newComments;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments){
            List<Comment> replayComments = comment.getReplayComments();
            for (Comment reply : replayComments){
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplayComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplayComments().size() > 0){
            List<Comment> replayComments = comment.getReplayComments();
            for (Comment reply : replayComments){
                    recursively(reply);
            }
        }
    }

    @Override
    public Long countComments() {
        return commentDao.count();
    }
}
