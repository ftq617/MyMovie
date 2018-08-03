package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Ticket;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @program: MyMovie
 * @description: 买票的业务逻辑层接口
 * @author: Mr.Fu
 * @create: 2018-07-30 14:27
 * @Version V1.0
 */
public interface TicketService {
    BGResult createTicket(Ticket... tickets);
    BGResult updateTicket(Ticket ticket);
    BGResult deleteTicket(long... ids);
    BGResult selectTicketById(long id);
    BGResult selectTicketByU_id(String u_id);
    BGResult selectTicketByC_id(String c_id);
    BGResult selectTicketByM_id(String m_id);
    BGResult getTicketList(int page,int rows);
}
