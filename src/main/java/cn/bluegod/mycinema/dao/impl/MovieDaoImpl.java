package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.MovieDao;
import cn.bluegod.mycinema.pojo.Movie;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 电影Dao的实现类
 * @author: Mr.Fu
 * @create: 2018-07-25 16:01
 * @Version V1.0
 */
public class MovieDaoImpl implements MovieDao{

    private DB db;

    public MovieDaoImpl(){
        db=new DB();
    }

    @Override
    public int addMovie(Movie movie) {
        String sql="insert into movie(m_name,director,play,type,image,timelength,describes,state,starttime,endtime) values(?,?,?,?,?,?,?,?,?,?)";
        int a=-1;
        try {
            a=db.executeUpdate(sql,movie.getName(),movie.getDirector(),movie.getPlay(),movie.getType(),movie.getImage(),movie.getTimelength(),movie.getDescribe(),movie.getStatus(),movie.getStarttime(),movie.getEndtime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteMovie(long id) {
        String sql="delete from movie where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateMovie(Movie movie) {
        String sql="update movie set m_name=?,director=?,play=?,type=?,image=?,timelength=?,describes=?,state=?,starttime=?,endtime=? where id=?";
        int a=0;
        try {
            a=db.executeUpdate(sql,movie.getName(),movie.getDirector(),movie.getPlay(),movie.getType(),movie.getImage(),movie.getTimelength(),movie.getDescribe(),movie.getStatus(),movie.getStarttime(),movie.getEndtime(),movie.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Movie> queryMovie(int start,int end) {
        String sql = "select * from movie limit "+start+","+end ;
        System.out.println(sql);
        List<Movie> result=null;
        try {
             result= db.executeQuery(Movie.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Movie selectMovieById(long id) {
        String sql="select * from movie where id="+id;
        List<Movie> result=null;
        try {
            result=db.executeQuery(Movie.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<Movie> selectMoveByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from movie where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Movie> result=null;
        try {
            result= db.executeQuery(Movie.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Movie> selectMoveByName(String values) {
        String sql="select * from movie where  m_name like '%"+values+"%'";
        List<Movie> result=null;
        try {
            result=db.executeQuery(Movie.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getMoveNumber() {
        String sql="select count(*) id from movie";
        List<Movie> result=null;
        try {
            result=db.executeQuery(Movie.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0).getId().intValue();
    }
}
