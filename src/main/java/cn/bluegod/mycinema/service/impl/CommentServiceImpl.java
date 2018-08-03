package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.CommentDao;
import cn.bluegod.mycinema.pojo.Comment;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CommentService;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 13:32
 * @Version V1.0
 */
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;

    public CommentServiceImpl(){
        commentDao=(CommentDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("CommentDao"));
    }


    @Override
    public BGResult createComment(Comment comment) {
        comment.setTime(new Date());
        int a=commentDao.addComment(comment);
        return BGResult.ok(a);
    }

    @Override
    public BGResult deleteComment(long... ids) {
        int a=0;
        for (long id:ids) {
             a += commentDao.deleteComment(id);
        }
        return BGResult.ok(a);
    }

    @Override
    public BGResult selectCommentById(long id) {
        Comment comment=commentDao.selectCommentById(id);
        return BGResult.ok(comment);
    }

    @Override
    public BGResult selectCommentByu_id(String u_id) {
        List<Comment> list=commentDao.selectCommentByValue(new String[]{"u_id"},new String[]{u_id});
        return BGResult.ok(list);
    }

    @Override
    public BGResult selectCommentBym_id(String m_id) {
        List<Comment> list=commentDao.selectCommentByValue(new String[]{"m_id"},new String[]{m_id});
        return BGResult.ok(list);
    }
}
