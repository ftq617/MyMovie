package cn.bluegod.mycinema.dao.impl.test;

import cn.bluegod.mycinema.dao.FilmHallDao;
import cn.bluegod.mycinema.dao.impl.FilmHallDaoImpl;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.pojo.Movie;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 13:55
 * @Version V1.0
 */
public class FilmHallTest {

    public FilmHallDao filmHallDao=new FilmHallDaoImpl();

    public Filmhall getFilmhall(){
        Filmhall filmhall=new Filmhall();
        filmhall.setcId("1");
        filmhall.setSeatinfo("1,2,3,4,5,6,7,9,10,11");
        filmhall.setSeatnumber(11);
        return filmhall;
    }

    @Test
    public void createHall(){
        filmHallDao.createHall(getFilmhall());
    }

    @Test
    public void updateHall(){
        Filmhall filmhall=getFilmhall();
        filmhall.setSeatnumber(11);
        filmhall.setId(1L);
        filmHallDao.updateHall(filmhall);
    }
    @Test
    public void deleteHall(){
        filmHallDao.deleteHallById(2L);
    }

    @Test
    public void selectHallList(){
        List<Filmhall> a=filmHallDao.selectHallList();
        for (Filmhall filmhall:a)
            System.out.println(filmhall.toString());
    }

    @Test
    public void selectHallByValue(){
        List<Filmhall> a=filmHallDao.selectHallByValue(new String[]{"c_id"},new String[]{"1"});
        for (Filmhall filmhall:a)
            System.out.println(filmhall.toString());
    }

    @Test
    public void selectHallById(){
        Filmhall a=filmHallDao.selectHallById(1L);
        System.out.println(a.toString());
    }


}
