package cn.bluegod.mycinema.view.JPanel.Ticket;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.*;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CinemaService;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.SessionService;
import cn.bluegod.mycinema.service.TicketService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 13:55
 * @Version V1.0
 */
public class TicketJPanel implements ActionListener{
    private JPanel TicketJP;
    private JTable t_table;
    private JButton b_look;

    private JFrame frame;
    private String u_id;
    private String c_id;
    private List<Ticket> tickets;
    private DefaultTableModel model;

    private TicketService ticketService;
    private MovieService movieService;
    private CinemaService cinemaService;
    private SessionService sessionService;

    public TicketJPanel(JFrame frame,String u_id,String c_id){
        this.u_id=u_id;
        this.c_id=c_id;
        this.frame=frame;

        model=new DefaultTableModel();
        t_table.setModel(model);
        t_table.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        t_table.getTableHeader().setResizingAllowed(false);//不可拉动表格
        model.addColumn("序号");
        model.addColumn("电影名");
        model.addColumn("电影院名");
        model.addColumn("电影地址");
        model.addColumn("电影价格");
        model.addColumn("电影开始时间");
        t_table.setRowHeight(40);
        t_table.getColumnModel().getColumn(5).setPreferredWidth(150);



        b_look.addActionListener(this);
        ticketService=(TicketService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TicketService"));
        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        cinemaService=(CinemaService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));
        sessionService=(SessionService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));

        setTableValue();
    }

    private void setTableValue(){
        model.getDataVector().removeAllElements();
        if (u_id!=null) {
            tickets = (List<Ticket>) ticketService.selectTicketByU_id(u_id).getData();
        }else {
            tickets = (List<Ticket>) ticketService.selectTicketByC_id(c_id).getData();
        }
        int i=1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (tickets!=null) {
            for (Ticket ticket : tickets) {
                Movie movie = (Movie) movieService.selectMovieById(Long.parseLong(ticket.getM_id())).getData();
                Cinema cinema = (Cinema) cinemaService.selectCinemaById(Long.parseLong(ticket.getC_id())).getData();
                Session session = (Session) sessionService.selectSessionById(Long.parseLong(ticket.getFiled_id())).getData();
                String[] str = {i + "", movie.getName(), cinema.getName(), cinema.getAddress(), ((double) session.getPrice()) / 100 + "", sdf.format(session.getStarttime())};
                model.addRow(str);
                i++;
            }
        }
    }

    public JPanel getTicketJP() {
        return TicketJP;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (t_table.getSelectedRow()!=-1&&t_table.getSelectedRow()<tickets.size()) {
            Ticket ticket=tickets.get(t_table.getSelectedRow());
            if (c_id!=null){
                ticket.setType(1);
            }
            TicketInfo dialog = new TicketInfo(frame,ticket);
            dialog.pack();
            dialog.setVisible(true);
        }
    }
}
