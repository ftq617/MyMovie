package cn.bluegod.mycinema.dao.impl.test;

import cn.bluegod.mycinema.dao.SessionDao;
import cn.bluegod.mycinema.dao.impl.SessionDaoImpl;
import cn.bluegod.mycinema.pojo.Session;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 14:33
 * @Version V1.0
 */
public class SessionTest {

    SessionDao sessionDao=new SessionDaoImpl();

    public Session getSession(){
        Session session=new Session();
        session.setcId("1");
        session.setFhId("1");
        session.setmId("7");
        session.setPrice(30000);
        session.setRemain(11);
        session.setStarttime(new Date());
        session.setTimelength(150);
        session.setSelectseat("");
        return session;
    }

    @Test
    public void createSession(){
        sessionDao.createSession(getSession());
    }

    @Test
    public void updateSession(){
        Session session=getSession();
        session.setPrice(3000);
        session.setId(1L);
        sessionDao.updateSession(session);
    }

    @Test
    public void deleteSession(){
        sessionDao.deleteSessionById(2L);
    }

    @Test
    public void selectSessionList(){
        List<Session> list=sessionDao.selectSessionList(0,10);
        for (Session session:list){
            System.out.println(session.toString());
        }
    }

    @Test
    public void selectSessionByValue(){
        List<Session> list=sessionDao.selectSessionByValue(new String[]{"c_id"},new String[]{"1"});
        for (Session session:list){
            System.out.println(session.toString());
        }
    }

    @Test
    public void selectSessionById(){
        Session session=sessionDao.selectSessionById(1L);
        System.out.println(session.toString());
    }

    @Test
    public void getSessionNumber(){

    }

}
