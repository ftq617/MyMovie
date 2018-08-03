package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.MovieDao;
import cn.bluegod.mycinema.dao.SessionDao;
import cn.bluegod.mycinema.dao.TicketDao;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Session;
import cn.bluegod.mycinema.pojo.Ticket;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.MovieService;

import java.util.Date;
import java.util.List;

/**
 * @description: 电影逻辑处理实现类
 * @author: Mr.Fu
 * @create: 2018-07-25 16:03
 * @Version V1.0
 */
public class MovieServiceImpl implements MovieService{

    private MovieDao movieDao;
    private SessionDao sessionDao;
    private TicketDao ticketDao;

    public MovieServiceImpl(){
        movieDao= (MovieDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("MovieDao"));
        sessionDao= (SessionDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("SessionDao"));
        ticketDao= (TicketDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TicketDao"));
    }

    @Override
    public BGResult createMovie(Movie movie) {
        List<Movie> list=movieDao.selectMoveByValue(new String[]{"m_name","director","play","type"},new String[]{movie.getName(),movie.getDirector(),movie.getPlay(),movie.getType()});
        if (list.size()>0){
            return BGResult.build(400,"该电影已添加过了");
        }
        Date date=new Date();
        movie.setStatus(1);
        movie.setStarttime(date);
        movieDao.addMovie(movie);
        return BGResult.ok();
    }

    @Override
    public BGResult updateMovie(Movie movie) {
        int a=movieDao.updateMovie(movie);
        return BGResult.ok(a);
    }

    @Override
    public BGResult deleteMovie(long... ids) {
        int a=0;
        for (long id:ids){
            List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"m_id"},new String[]{id+""});
            if (list==null||list.size()==0) {
                a += movieDao.deleteMovie(id);
                sessionDao.deleteSessionByM_id(id + "");
            }
        }
        if (a==ids.length) {
            return BGResult.ok(a);
        }else {
            return BGResult.build(200,"有些电影已经卖出票去了，不能删。所以只",a);
        }
    }

    @Override
    public BGResult selectMovieById(long id) {
        Movie result=movieDao.selectMovieById(id);
        if (result==null){
            return BGResult.build(400,"没有找到此电影");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectMovieByName(String name) {
        List<Movie> result=movieDao.selectMoveByName(name);
        if (result.size()==0){
            return BGResult.build(400,"没有找到名字为："+name+" 的电影");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectMovieByType(String type) {
        List<Movie> result=movieDao.selectMoveByValue(new String[]{"type"},new String[]{type,});
        if (result.size()==0){
            return BGResult.build(400,"没有找到此类型的电影");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult getMovieList(int page, int rows) {
        System.out.println(page+":"+rows);
        int start=(page-1)*rows;
        List<Movie> result=movieDao.queryMovie(start,rows);
        int all=movieDao.getMoveNumber();
        return BGResult.build(200,all+"",result);
    }
}
