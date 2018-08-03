package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.CommentDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Comment;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 11:38
 * @Version V1.0
 */
public class CommentDaoImpl implements CommentDao {

    private DB db;

    public CommentDaoImpl(){
        db=new DB();
    }

    @Override
    public int addComment(Comment comment) {
        String sql="insert into comment(u_id,m_id,content,time) values(?,?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,comment.getuId(),comment.getmId(),comment.getContent(),comment.getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteComment(long id) {
        String sql="delete from comment where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public Comment selectCommentById(long id) {
        String sql="select * from comment where id="+id;
        List<Comment> result=null;
        try {
            result=db.executeQuery(Comment.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<Comment> selectCommentByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from comment where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Comment> result=null;
        try {
            result= db.executeQuery(Comment.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getCommentNumber(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select count(*) id from comment where");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");
        List<Comment> result=null;
        try {
            result=db.executeQuery(Comment.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0).getId().intValue();
    }
}
