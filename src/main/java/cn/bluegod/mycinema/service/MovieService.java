package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @program: MyMovie
 * @description: 电影逻辑处理接口
 * @author: Mr.Fu
 * @create: 2018-07-25 16:03
 * @Version V1.0
 */
public interface MovieService {
    BGResult createMovie(Movie movie);
    BGResult updateMovie(Movie movie);
    BGResult deleteMovie(long... ids);
    BGResult selectMovieById(long id);
    BGResult selectMovieByName(String name);
    BGResult selectMovieByType(String type);
    BGResult getMovieList(int page,int rows);
}
