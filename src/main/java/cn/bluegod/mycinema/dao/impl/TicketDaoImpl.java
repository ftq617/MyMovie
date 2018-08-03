package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.TicketDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Ticket;

import java.sql.SQLException;
import java.util.List;

/**
 * @description: 电影票的dao层实现类
 * @author: Mr.Fu
 * @create: 2018-07-30 14:19
 * @Version V1.0
 */
public class TicketDaoImpl implements TicketDao {

    private DB db;

    public TicketDaoImpl(){
        db=new DB();
    }

    @Override
    public int createTicket(Ticket ticket) {
        String sql="insert into ticket(u_id,m_id,fh_id,c_id,filed_id,state,seat,type) values(?,?,?,?,?,?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,ticket.getU_id(),ticket.getM_id(),ticket.getFh_id(),ticket.getC_id(),ticket.getFiled_id(),ticket.getState(),ticket.getSeat(),ticket.getType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateTicket(Ticket ticket) {
        String sql="update ticket set u_id=?,m_id=?,fh_id=?,c_id=?,filed_id=?,state=?,seat=?,type=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,ticket.getU_id(),ticket.getM_id(),ticket.getFh_id(),ticket.getC_id(),ticket.getFiled_id(),ticket.getState(),ticket.getSeat(),ticket.getType(),ticket.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteTicket(long id) {
        String sql="delete from ticket where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<Ticket> selectTicketList(int start, int end) {
        String sql = "select * from ticket limit "+start+","+end ;
        List<Ticket> result=null;
        try {
            result= db.executeQuery(Ticket.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Ticket> selectTicketByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from ticket where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<Ticket> result=null;
        try {
            result= db.executeQuery(Ticket.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Ticket selectTicketById(long id) {
        String sql="select * from ticket where id="+id;
        List<Ticket> result=null;
        try {
            result=db.executeQuery(Ticket.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }

    @Override
    public int getTicketNumber() {
        String sql="select count(*) id from ticket";
        List<Ticket> result=null;
        try {
            result=db.executeQuery(Ticket.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.get(0).getId().intValue();
    }
}
