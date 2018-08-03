package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.*;
import cn.bluegod.mycinema.pojo.*;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.SessionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 场次逻辑处理实现类
 * @author: Mr.Fu
 * @create: 2018-07-27 14:53
 * @Version V1.0
 */
public class SessionServiceImpl implements SessionService {

    private SessionDao sessionDao;
    private MovieDao movieDao;
    private CinemaDao cinemaDao;
    private TicketDao ticketDao;

    public SessionServiceImpl(){
        sessionDao=(SessionDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("SessionDao"));
        movieDao=(MovieDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("MovieDao"));
        cinemaDao=(CinemaDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("CinemaDao"));
        ticketDao= (TicketDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TicketDao"));
    }

    @Override
    public BGResult createSession(Session session) {
//        Movie movie=movieDao.selectMovieById(Long.parseLong(session.getmId()));
//        Filmhall filmhall=filmHallDao.selectHallById(Long.parseLong(session.getFhId()));
//        Cinema cinema=cinemaDao.selectCinemaById(Long.parseLong(session.getcId()));
//
//        if (movie==null||filmhall==null||cinema==null){
//            return BGResult.build(404,"出现网络错误！！！");
//        }
        if (!isHaveHall(session)){
            return BGResult.build(400,"这个电影厅在时间段已经有一场电影，换一个时间段吧");
        }

        session.setSelectseat("");
        int a=sessionDao.createSession(session);
        return BGResult.ok(a);
    }

    public boolean isHaveHall(Session session){
        List<Session> list=sessionDao.selectSessionByValue(new String[]{"c_id","fh_id"},new String[]{session.getcId(),session.getFhId()});

        for (Session session1:list){
            if (session.getId()==null||session1.getId()!=session.getId()) {
                long start1 = session1.getStarttime().getTime() / 1000;
                long end1 = start1 + session1.getTimelength() * 60;
                long start = session.getStarttime().getTime() / 1000;
                long end = start + session.getTimelength() * 60;
                System.out.println(session1.getStarttime());
                System.out.println(session.getStarttime());
                System.out.println(start1+":"+end1+","+start+":"+end);
                if ((start < end1 && start > start1) || (end < end1 && end > start1) || (start > start1 && end < end1) || (start < start1 && end > end1)) {
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public BGResult updateSessionByBuyTicket(Session session){
        int a = sessionDao.updateSession(session);
        return BGResult.ok(a);
    }

    @Override
    public BGResult updateSession(Session session) {
        if (!isHaveHall(session)){
            return BGResult.build(400,"这个电影厅在时间段已经有一场电影，换一个时间段吧");
        }
        List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"filed_id"},new String[]{session.getId()+""});
        if (list==null||list.size()==0) {
            int a = sessionDao.updateSession(session);
            return BGResult.ok(a);
        }
        return BGResult.build(400,"票都卖出去了，不能改");
    }

    @Override
    public BGResult deleteSession(long... ids) {
        int a=0;
        for (long id:ids) {
            List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"filed_id"},new String[]{id+""});
            if(list==null||list.size()==0) {
                a += sessionDao.deleteSessionById(id);
            }
        }
        if (a==ids.length) {
            return BGResult.ok(a);
        }else {
            return BGResult.build(200,"有些场次已经卖出票去了，不能删。所以只",a);
        }
    }

    @Override
    public BGResult selectSessionById(long id) {
        Session session=sessionDao.selectSessionById(id);
        return BGResult.ok(session);
    }

    @Override
    public BGResult selectSessionByM_id(String m_id) {
        List<Session> list=sessionDao.selectSessionByValue(new String[]{"m_id"},new String[]{m_id});
        return BGResult.ok(list);
    }

    @Override
    public BGResult selectSessionByC_id(String c_id) {
        List<Session> list=sessionDao.selectSessionByValue(new String[]{"c_id"},new String[]{c_id});
        return BGResult.ok(list);
    }

    @Override
    public BGResult selectSessionByM_name(String name) {
        List<Movie> movies=movieDao.selectMoveByName(name);
        List<Session> sessions=new ArrayList<>();
        for (Movie movie:movies){
            sessions.addAll((List<Session>)(selectSessionByM_id(movie.getId()+"").getData()));
        }
        return BGResult.ok(sessions);
    }

    @Override
    public BGResult selectSessionByC_name(String name) {
        List<Cinema> cinemas=cinemaDao.selectCinemaByName(name);
        System.out.println(cinemas.size());
        List<Session> sessions=new ArrayList<>();
        for (Cinema cinema:cinemas){
            sessions.addAll((List<Session>)(selectSessionByC_id(cinema.getId()+"").getData()));
        }
        return BGResult.ok(sessions);
    }

    @Override
    public BGResult selectSessionByTime(Date date) {
//        List<Session> list=sessionDao.selectSessionByValue(new String[]{"starttime"},new String[]{date});
//        return BGResult.ok(list);
        return null;
    }

    @Override
    public BGResult getSessionList(int page, int rows) {
        int start=(page-1)*rows;
        List<Session> list=sessionDao.selectSessionList(start,rows);
        return BGResult.ok(list);
    }
}
