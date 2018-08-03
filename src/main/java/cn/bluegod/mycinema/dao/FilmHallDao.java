package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Filmhall;

import java.util.List;

/**
 * @program: MyMovie
 * @description: 影厅dao层操作接口
 * @author: Mr.Fu
 * @create: 2018-07-27 13:40
 * @Version V1.0
 */
public interface FilmHallDao{

    int createHall(Filmhall filmhall);
    int updateHall(Filmhall filmhall);
    int deleteHallById(long id);
    int deleteHallByC_id(String c_id);
    List<Filmhall> selectHallList();
    List<Filmhall> selectHallByValue(String[] names, String[] values);
    Filmhall selectHallById(long id);
}
