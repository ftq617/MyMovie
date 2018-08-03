package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.*;
import cn.bluegod.mycinema.pojo.*;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CinemaService;

import java.util.Date;
import java.util.List;

/**
 * @description: 电影院逻辑处理层实现类
 * @author: Mr.Fu
 * @create: 2018-07-27 10:55
 * @Version V1.0
 */
public class CinemaServiceImpl implements CinemaService {

    private CinemaDao cinemaDao;
    private FilmHallDao filmHallDao;
    private SessionDao sessionDao;
    private TicketDao ticketDao;
    private UserDao userDao;

    public CinemaServiceImpl(){
        cinemaDao= (CinemaDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("CinemaDao"));
        filmHallDao= (FilmHallDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("FilmHallDao"));
        sessionDao= (SessionDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("SessionDao"));
        ticketDao= (TicketDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TicketDao"));
        userDao=(UserDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("UserDao"));
    }

    @Override
    public BGResult createCinema(Cinema cinema) {
        List<Cinema> list=cinemaDao.selectCinemaByValue(new String[]{"c_name","address"},new String[]{cinema.getName(),cinema.getAddress()});
        if (list.size()>0){
            return BGResult.build(400,"该电影院已添加过了");
        }
        cinemaDao.createCinema(cinema);
        User user=new User();
        user.setName(cinema.getName());
        String phone=((char)('a'+((int)(Math.random()*26)))+""+(char)('a'+((int)(Math.random()*26)))+"")+(int)(Math.random()*10000+654321);
        List<User> users=userDao.selectUserByValue(new String[]{"phone"},new String[]{phone});
        while (users!=null&&users.size()>0){
            phone=((char)('a'+((int)(Math.random()*26)))+""+(char)('a'+((int)(Math.random()*26)))+"")+(int)(Math.random()*10000+654321);
            users=userDao.selectUserByValue(new String[]{"phone"},new String[]{phone});
        }
        user.setPhone(phone);
        String pass=(int)(Math.random()*1000000+123456)+"";
        user.setPassword(CommonsUtils.MD5Encrypt(pass));
        user.setcId(cinema.getId()+"");
        user.setHeadImage(cinema.getImage());
        user.setStartTime(new Date());
        userDao.addUser(user);
        user.setPassword(pass);
        return BGResult.ok(user);
    }

    @Override
    public BGResult updateCinema(Cinema cinema) {
        int a=cinemaDao.updateCinema(cinema);
        return BGResult.ok(a);
    }

    @Override
    public BGResult deleteCinema(long... ids) {
        int a=0;
        for (long id:ids){
            List<Ticket> list=ticketDao.selectTicketByValue(new String[]{"c_id"},new String[]{id+""});
            if (list==null||list.size()==0) {
                a += cinemaDao.deleteCinema(id);
                filmHallDao.deleteHallByC_id(id + "");
                sessionDao.deleteSessionByC_id(id + "");
                userDao.deleteUserByC_id(id);
            }
        }
        if (a==ids.length) {
            return BGResult.ok(a);
        }else {
            return BGResult.build(200,"有些电影院已经卖出票去了，不能删。所以只",a);
        }
    }

    @Override
    public BGResult selectCinemaById(long id) {
        Cinema result=cinemaDao.selectCinemaById(id);
        if (result==null){
            return BGResult.build(400,"没有找到此电影院");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectCinemaByName(String name) {
        List<Cinema> result=cinemaDao.selectCinemaByName(name);
        if (result.size()==0){
            return BGResult.build(400,"没有找到名字为："+name+" 的电影");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectCinemaByAddress(String address) {
        List<Cinema> result=cinemaDao.selectCinemaByValue(new String[]{"address"},new String[]{address,});
        if (result.size()==0){
            return BGResult.build(400,"没有找到此类型的电影");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult getCinemaList(int page, int rows) {
        int start=(page-1)*rows;
        List<Cinema> result=cinemaDao.selectCinemaList(start,rows);
        int all=cinemaDao.getCinemaNumber();
        return BGResult.build(200,all+"",result);
    }
}
