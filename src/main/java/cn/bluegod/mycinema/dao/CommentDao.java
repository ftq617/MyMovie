package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Comment;

import java.util.List;

/**
 * @program: MyMovie
 * @description: 评论dao层接口
 * @author: Mr.Fu
 * @create: 2018-07-31 11:28
 * @Version V1.0
 */
public interface CommentDao {
    int addComment(Comment comment);
    int deleteComment(long id);
    Comment selectCommentById(long id);
    List<Comment> selectCommentByValue(String[] names,String[] values);
    int getCommentNumber(String[] names,String[] values);
}
