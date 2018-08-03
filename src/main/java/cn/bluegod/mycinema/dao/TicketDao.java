package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Ticket;

import java.util.List;

/**
 * @description: 电影票的dao层接口
 * @author: Mr.Fu
 * @create: 2018-07-30 14:16
 * @Version V1.0
 */
public interface TicketDao {
    int createTicket(Ticket ticket);
    int updateTicket(Ticket ticket);
    int deleteTicket(long id);
    List<Ticket> selectTicketList(int start, int end);
    List<Ticket> selectTicketByValue(String[] names, String[] values);
    Ticket selectTicketById(long id);
    int getTicketNumber();
}
