package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.CinemaDao;
import cn.bluegod.mycinema.pojo.Cinema;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 电影院的dao层实现类
 * @author: Mr.Fu
 * @create: 2018-07-27 10:10
 * @Version V1.0
 */
public class CinemaDaoImpl implements CinemaDao {

    private DB db;

    public CinemaDaoImpl(){
        db=new DB();
    }
    @Override
    public int createCinema(Cinema cinema) {
        String sql= "select max(id) id  from cinema ";
        List<Cinema> result=null;
        try {
            result=db.executeQuery(Cinema.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result.size()==0||result.get(0).getId()==null){
            cinema.setId(1L);
        }else {
            cinema.setId(result.get(0).getId() + 1);
        }
        sql="insert into cinema(id,c_name,address,image,describes) values(?,?,?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,cinema.getId(),cinema.getName(),cinema.getAddress(),cinema.getImage(),cinema.getDescribe());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateCinema(Cinema cinema) {
        String sql="update cinema set c_name=?,address=?,image=?,describes=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,cinema.getName(),cinema.getAddress(),cinema.getImage(),cinema.getDescribe(),cinema.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteCinema(long id) {
        String sql="delete from cinema where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Cinema> selectCinemaList(int start, int end) {
        String sql = "select * from cinema limit "+start+","+end ;
        List<Cinema> result=null;
        try {
            result= db.executeQuery(Cinema.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Cinema> selectCinemaByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from cinema where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Cinema> result=null;
        try {
            result= db.executeQuery(Cinema.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Cinema> selectCinemaByName(String name) {
        String sql="select * from cinema where  c_name like '%"+name+"%'";
        List<Cinema> result=null;
        try {
            result=db.executeQuery(Cinema.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Cinema selectCinemaById(long id) {
        String sql="select * from cinema where id="+id;
        List<Cinema> result=null;
        try {
            result=db.executeQuery(Cinema.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

    @Override
    public int getCinemaNumber() {
        String sql="select count(*) id from cinema";
        List<Cinema> result=null;
        try {
            result=db.executeQuery(Cinema.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0).getId().intValue();
    }
}
