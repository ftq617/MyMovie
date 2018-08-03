package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Cinema;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 10:04
 * @Version V1.0
 */
public interface CinemaDao{

    int createCinema(Cinema cinema);
    int updateCinema(Cinema cinema);
    int deleteCinema(long id);
    List<Cinema> selectCinemaList(int start,int end);
    List<Cinema> selectCinemaByValue(String[] names, String[] values);
    List<Cinema> selectCinemaByName(String name);
    Cinema selectCinemaById(long id);
    int getCinemaNumber();
}
