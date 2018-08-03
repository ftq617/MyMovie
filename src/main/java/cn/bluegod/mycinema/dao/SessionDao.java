package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Session;

import java.util.List;

/**
 * @program: MyMovie
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 14:21
 * @Version V1.0
 */
public interface SessionDao {
    int createSession(Session session);
    int updateSession(Session session);
    int deleteSessionById(long id);
    int deleteSessionByC_id(String c_id);
    int deleteSessionByFh_id(String fh_id);
    int deleteSessionByM_id(String m_id);
    List<Session> selectSessionList(int start, int end);
    List<Session> selectSessionByValue(String[] names, String[] values);
    Session selectSessionById(long id);
    int getSessionNumber();
}
