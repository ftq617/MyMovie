package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.FilmHallDao;
import cn.bluegod.mycinema.pojo.Filmhall;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:影厅的dao层操作实现类
 * @author: Mr.Fu
 * @create: 2018-07-27 13:42
 * @Version V1.0
 */
public class FilmHallDaoImpl implements FilmHallDao{

    private DB db;

    public FilmHallDaoImpl(){
        db=new DB();
    }


    @Override
    public int createHall(Filmhall filmhall) {
        String sql="insert into filmhall(c_id,seatnumber,seatinfo) values(?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,filmhall.getcId(),filmhall.getSeatnumber(),filmhall.getSeatinfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateHall(Filmhall filmhall) {
        String sql="update filmhall set c_id=?,seatnumber=?,seatinfo=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,filmhall.getcId(),filmhall.getSeatnumber(),filmhall.getSeatinfo(),filmhall.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteHallById(long id) {
        String sql="delete from filmhall where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteHallByC_id(String c_id) {
        String sql="delete from filmhall where c_id="+c_id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Filmhall> selectHallList() {
        String sql = "select * from filmhall";
        List<Filmhall> result=null;
        try {
            result= db.executeQuery(Filmhall.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Filmhall> selectHallByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from filmhall where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Filmhall> result=null;
        try {
            result= db.executeQuery(Filmhall.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Filmhall selectHallById(long id) {
        String sql="select * from filmhall where id="+id;
        List<Filmhall> result=null;
        try {
            result=db.executeQuery(Filmhall.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

}
