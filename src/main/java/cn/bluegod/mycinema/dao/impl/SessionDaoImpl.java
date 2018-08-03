package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.SessionDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 14:25
 * @Version V1.0
 */
public class SessionDaoImpl implements SessionDao {

    private DB db;

    public SessionDaoImpl(){
        db=new DB();
    }

    @Override
    public int createSession(Session session) {
        String sql="insert into session(c_id,m_id,fh_id,starttime,timelength,price,selectseat,remain) values(?,?,?,?,?,?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,session.getcId(),session.getmId(),session.getFhId(),session.getStarttime(),session.getTimelength(),session.getPrice(),session.getSelectseat(),session.getRemain());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateSession(Session session) {
        String sql="update session set c_id=?,m_id=?,fh_id=?,starttime=?,timelength=?,price=?,selectseat=?,remain=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,session.getcId(),session.getmId(),session.getFhId(),session.getStarttime(),session.getTimelength(),session.getPrice(),session.getSelectseat(),session.getRemain(),session.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteSessionById(long id) {
        String sql="delete from session where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteSessionByC_id(String c_id) {
        String sql="delete from session where c_id="+c_id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteSessionByFh_id(String fh_id) {
        String sql="delete from session where fh_id="+fh_id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteSessionByM_id(String m_id) {
        String sql="delete from session where m_id="+m_id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Session> selectSessionList(int start, int end) {
        String sql = "select * from session limit "+start+","+end ;
        List<Session> result=null;
        try {
            result= db.executeQuery(Session.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Session> selectSessionByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from session where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Session> result=null;
        try {
            result= db.executeQuery(Session.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Session selectSessionById(long id) {
        String sql="select * from session where id="+id;
        List<Session> result=null;
        try {
            result=db.executeQuery(Session.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

    @Override
    public int getSessionNumber() {
        String sql="select count(*) id from session";
        List<Session> result=null;
        try {
            result=db.executeQuery(Session.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0).getId().intValue();
    }
}
