package cn.bluegod.mycinema.dao.impl.test;

import cn.bluegod.mycinema.dao.CinemaDao;
import cn.bluegod.mycinema.dao.impl.CinemaDaoImpl;
import cn.bluegod.mycinema.pojo.Cinema;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 10:27
 * @Version V1.0
 */
public class CinemaTest {
    CinemaDao dao=new CinemaDaoImpl();

    public Cinema getCinema(){
        Cinema cinema=new Cinema();
        cinema.setName("万达影城");
        cinema.setAddress("万达广场");
        cinema.setImage("123123123");
        cinema.setDescribe("王健林的饭后影厅，想多了");
        return cinema;
    }

    @Test
    public  void createCinema(){
        dao.createCinema(getCinema());
    }

    @Test
    public  void updateCinema(){
        Cinema cinema=getCinema();
        cinema.setId(2L);
        cinema.setImage("qweqweqweqweqwe");
        dao.updateCinema(cinema);
    }

    @Test
    public  void deleteCinema(){
        System.out.println(dao.deleteCinema(3L));
    }

    @Test
    public  void selectCinemaList(){
        List<Cinema> list=dao.selectCinemaList(0,5);
        for(Cinema cinema:list){
            System.out.println(cinema.toString());
        }
    }

    @Test
    public  void selectCinemaByValue(){
        String[] names={"c_name"};
        String[] values={"万达影城"};
        List<Cinema> list=dao.selectCinemaByValue(names,values);
        for(Cinema cinema:list){
            System.out.println(cinema.toString());
        }
    }

    @Test
    public  void selectCinemaByName(){
        List<Cinema> list=dao.selectCinemaByName("万");
        for(Cinema cinema:list){
            System.out.println(cinema.toString());
        }
    }

    @Test
    public  void selectCinemaById(){
        Cinema cinema=dao.selectCinemaById(1L);
        System.out.println(cinema.toString());
    }

    @Test
    public  void getCinemaNumber(){
        System.out.println(dao.getCinemaNumber());
    }
}
