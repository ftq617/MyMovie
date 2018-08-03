package cn.bluegod.mycinema.service.impl.test;

import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.impl.MovieServiceImpl;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 09:51
 * @Version V1.0
 */
public class MovieServiceTest {

    MovieService service=new MovieServiceImpl();

    public Movie getMovie(){
        Movie movie=new Movie();
        movie.setName("生气世界");
        movie.setDescribe("是法国");
        movie.setDirector("士大夫");
        movie.setEndtime(new Date());
        movie.setImage("楼梯u过热时");
        movie.setPlay("热舞计划");
        movie.setTimelength(150);
        movie.setType("1");
        movie.setStatus(4);
        movie.setStarttime(new Date());
        return movie;
    }

    @Test
    public void createMovie(){
        service.createMovie(getMovie());
    }

    @Test
    public void updateMovie(){
        Movie movie=getMovie();
        movie.setId(20L);
        movie.setImage("rajhewgtHSDBJKjsdfbkhalbkbkbkbkbkbkbkbkbk");
        service.updateMovie(movie);
    }

    @Test
    public void deleteMovie(){
        service.deleteMovie(20);
    }

    @Test
    public void selectMovieById(){
        BGResult result=service.selectMovieById(19);
        System.out.println(result.getData().toString());
    }

    @Test
    public void selectMovieByName(){
        BGResult result=service.selectMovieByName("未");
        for (Movie movie:(List<Movie>)result.getData())
        System.out.println(movie.toString());
    }

    @Test
    public void selectMovieByType(){
        BGResult result=service.selectMovieByType("1");
        for (Movie movie:(List<Movie>)result.getData())
            System.out.println(movie.toString());
    }

    @Test
    public void getMovieList(){
        BGResult result=service.getMovieList(1,10);
        System.out.println(result.getMsg());
        for (Movie movie:(List<Movie>)result.getData())
            System.out.println(movie.toString());
    }

}
