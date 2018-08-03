package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @description: 电影厅的逻辑操作接口
 * @author: Mr.Fu
 * @create: 2018-07-27 14:10
 * @Version V1.0
 */
public interface FilmHallService {
    BGResult createFilmHall(Filmhall filmhall);
    BGResult updateFilmHall(Filmhall filmhall);
    BGResult deleteFilmHall(long... ids);
    BGResult selectFilmHallById(long id);
    BGResult selectFilmHallByC_id(String c_id);
}
