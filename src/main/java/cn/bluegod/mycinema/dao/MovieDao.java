package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Movie;

import java.util.List;

/**
 * @program: MyMovie
 * @description: 电影的Dao接口
 * @author: Mr.Fu
 * @create: 2018-07-25 16:00
 * @Version V1.0
 */
public interface MovieDao {
    int addMovie(Movie movie);
    int deleteMovie(long id);
    int updateMovie(Movie movie);
    List<Movie> queryMovie(int start,int end);
    Movie selectMovieById(long id);
    List<Movie> selectMoveByValue(String[] names, String[] values);
    List<Movie> selectMoveByName(String values);
    int getMoveNumber();
}
