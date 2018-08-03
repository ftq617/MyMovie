package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Session;
import cn.bluegod.mycinema.result.BGResult;

import java.util.Date;

/**
 * @description: 场次逻辑接口
 * @author: Mr.Fu
 * @create: 2018-07-27 14:47
 * @Version V1.0
 */
public interface SessionService {
    BGResult createSession(Session session);
    BGResult updateSession(Session session);
    BGResult updateSessionByBuyTicket(Session session);
    BGResult deleteSession(long... ids);
    BGResult selectSessionById(long id);
    BGResult selectSessionByM_id(String m_id);
    BGResult selectSessionByC_id(String c_id);
    BGResult selectSessionByM_name(String name);
    BGResult selectSessionByC_name(String name);
    BGResult selectSessionByTime(Date date);
    BGResult getSessionList(int page,int rows);
}
