package cn.bluegod.mycinema.dao.impl.test;

import cn.bluegod.mycinema.dao.MovieDao;
import cn.bluegod.mycinema.dao.impl.MovieDaoImpl;
import cn.bluegod.mycinema.pojo.Movie;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @description: 电影dao层实现类的测试类
 * @author: Mr.Fu
 * @create: 2018-07-25 19:29
 * @Version V1.0
 */
public class MovieTest {

    public Movie getMovie(){
        Movie movie=new Movie();
        movie.setName("你说呢");
        movie.setDescribe("asdfa");
        movie.setDirector("asdfwaetgr");
        movie.setEndtime(new Date());
        movie.setImage("fahfgsdrfg");
        movie.setPlay("awefgtaW4RTEGY");
        movie.setTimelength(150);
        movie.setType("1");
        movie.setStatus(1);
        movie.setStarttime(new Date());
        return movie;
    }

    @Test
    public void testMovieDaoAdd(){

        MovieDao movieDao=new MovieDaoImpl();
        int a=movieDao.addMovie(getMovie());
        System.out.println(a);
    }

    @Test
    public void testMovieDaoupdate(){
        MovieDao movieDao=new MovieDaoImpl();
        Movie movie=getMovie();
        movie.setId(3L);
        movie.setName("王定坤");
        int a=movieDao.updateMovie(movie);
        System.out.println(a);
    }

    @Test
    public void testMovieDaoDelete(){
        MovieDao movieDao=new MovieDaoImpl();
        int a=movieDao.deleteMovie(4);
        System.out.println(a);
    }

    @Test
    public void testMovieDaoquery(){
        MovieDao movieDao=new MovieDaoImpl();
        List<Movie> a=movieDao.queryMovie(0,7);
        for (Movie movie:a)
        System.out.println(movie.toString());
    }

    @Test
    public void testMovieDaoQueryByID(){
        MovieDao movieDao=new MovieDaoImpl();
        Movie movie=movieDao.selectMovieById(3);
        System.out.println(movie);
    }

    @Test
    public void testMovieDaoQueryByValue(){
        MovieDao movieDao=new MovieDaoImpl();
        String[] names={"id","m_name"};
        String[] values={"5","最强王者"};
        List<Movie> movies=movieDao.selectMoveByValue(names,values);
        for (Movie movie:movies)
            System.out.println(movie.toString());
    }

    @Test
    public void testMovieDaoQueryByName(){
        MovieDao movieDao=new MovieDaoImpl();
        List<Movie> movies=movieDao.selectMoveByName("请");
        for (Movie movie:movies)
            System.out.println(movie.toString());
    }

    @Test
    public void testMovieAll(){
        MovieDao movieDao=new MovieDaoImpl();
        int i=movieDao.getMoveNumber();
        System.out.println(i);
    }
}
