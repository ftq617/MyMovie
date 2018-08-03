package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Comment;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @program: MyMovie
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 13:25
 * @Version V1.0
 */
public interface CommentService {
    BGResult createComment(Comment comment);
    BGResult deleteComment(long... ids);
    BGResult selectCommentById(long id);
    BGResult selectCommentByu_id(String u_id);
    BGResult selectCommentBym_id(String m_id);
}
