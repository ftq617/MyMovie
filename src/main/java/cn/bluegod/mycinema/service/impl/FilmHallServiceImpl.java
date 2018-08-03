package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.FilmHallDao;
import cn.bluegod.mycinema.dao.MovieDao;
import cn.bluegod.mycinema.dao.SessionDao;
import cn.bluegod.mycinema.dao.TicketDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.pojo.Ticket;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.FilmHallService;

import java.util.List;

/**
 * @description: 电影厅的逻辑操作实现类
 * @author: Mr.Fu
 * @create: 2018-07-27 14:13
 * @Version V1.0
 */
public class FilmHallServiceImpl implements FilmHallService {

    private FilmHallDao filmHallDao;
    private SessionDao sessionDao;
    private TicketDao ticketDao;

    public FilmHallServiceImpl(){
        filmHallDao=(FilmHallDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("FilmHallDao"));
        sessionDao=(SessionDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("SessionDao"));
        ticketDao= (TicketDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TicketDao"));
    }

    @Override
    public BGResult createFilmHall(Filmhall filmhall) {
        int a=filmHallDao.createHall(filmhall);
        return BGResult.ok(a);
    }

    @Override
    public BGResult updateFilmHall(Filmhall filmhall) {
        List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"fh_id"},new String[]{filmhall.getId()+""});
        if (list==null||list.size()==0) {
            int a = filmHallDao.updateHall(filmhall);
            return BGResult.ok(a);
        }
        return BGResult.build(400,"票都卖出去了，不能改");
    }

    @Override
    public BGResult deleteFilmHall(long... ids) {
        int a=0;
        for (long id:ids){
            List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"fh_id"},new String[]{id+""});
            if (list==null||list.size()==0) {
                a += filmHallDao.deleteHallById(id);
                sessionDao.deleteSessionByFh_id(id + "");
            }
        }
        if (a==ids.length) {
            return BGResult.ok(a);
        }else {
            return BGResult.build(200,"有些场厅已经卖出票去了，不能删。所以只",a);
        }
    }

    @Override
    public BGResult selectFilmHallById(long id) {
        Filmhall result=filmHallDao.selectHallById(id);
        if (result==null){
            return BGResult.build(400,"没有找到此电影厅");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectFilmHallByC_id(String c_id) {
        List<Filmhall> result=filmHallDao.selectHallByValue(new String[]{"c_id"},new String[]{c_id});
        if (result.size()==0){
            return BGResult.build(200,"还没有电影厅");
        }
        return BGResult.ok(result);
    }
}
