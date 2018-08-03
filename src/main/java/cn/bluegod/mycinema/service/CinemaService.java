package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @program: MyMovie
 * @description: 电影院的逻辑处理接口
 * @author: Mr.Fu
 * @create: 2018-07-27 10:52
 * @Version V1.0
 */
public interface CinemaService {
    BGResult createCinema(Cinema cinema);
    BGResult updateCinema(Cinema cinema);
    BGResult deleteCinema(long... ids);
    BGResult selectCinemaById(long id);
    BGResult selectCinemaByName(String name);
    BGResult selectCinemaByAddress(String address);
    BGResult getCinemaList(int page,int rows);
}
