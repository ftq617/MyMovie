package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.CinemaDao;
import cn.bluegod.mycinema.dao.TicketDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Ticket;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.SessionService;
import cn.bluegod.mycinema.service.TicketService;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 14:30
 * @Version V1.0
 */
public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;

    public TicketServiceImpl(){
        ticketDao=(TicketDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TicketDao"));
    }

    @Override
    public BGResult createTicket(Ticket... tickets) {
        for (Ticket ticket:tickets) {
            List<Ticket> list = ticketDao.selectTicketByValue(new String[]{"filed_id", "seat"}, new String[]{ticket.getFiled_id(), ticket.getSeat()});
            if (list.size() > 0) {
                return BGResult.build(400, "该票已经被卖了已添加过了");
            }
        }
        for (Ticket ticket:tickets) {
            ticket.setState(1);
            ticket.setType(0);
            ticketDao.createTicket(ticket);
        }
        return BGResult.ok();
    }

    @Override
    public BGResult updateTicket(Ticket ticket) {
        int a=ticketDao.updateTicket(ticket);
        return BGResult.ok(a);
    }

    @Override
    public BGResult deleteTicket(long... ids) {
        int a=0;
        for (long id:ids){
            a+=ticketDao.deleteTicket(id);
        }
        return BGResult.ok(a);
    }

    @Override
    public BGResult selectTicketById(long id) {
        Ticket result=ticketDao.selectTicketById(id);
        if (result==null){
            return BGResult.build(400,"没有找到此电影票");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectTicketByU_id(String u_id) {
        List<Ticket> result=ticketDao.selectTicketByValue(new String[]{"u_id"},new String[]{u_id});
        if (result.size()<=0){
            return BGResult.build(400,"没有找到此电影票");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectTicketByC_id(String c_id) {
        List<Ticket> result=ticketDao.selectTicketByValue(new String[]{"c_id"},new String[]{c_id});
        if (result.size()<=0){
            return BGResult.build(400,"没有找到此电影票");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult selectTicketByM_id(String m_id) {
        List<Ticket> result=ticketDao.selectTicketByValue(new String[]{"m_id"},new String[]{m_id});
        if (result.size()<=0){
            return BGResult.build(400,"没有此电影票");
        }
        return BGResult.ok(result);
    }

    @Override
    public BGResult getTicketList(int page, int rows) {
        return null;
    }
}
