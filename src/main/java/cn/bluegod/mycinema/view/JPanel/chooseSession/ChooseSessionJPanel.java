package cn.bluegod.mycinema.view.JPanel.chooseSession;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Session;
import cn.bluegod.mycinema.service.CinemaService;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.SessionService;
import cn.bluegod.mycinema.view.JPanel.BuyTicket.BuyTicketJPanel;
import cn.bluegod.mycinema.view.JPanel.Session.SelectSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 15:18
 * @Version V1.0
 */
public class ChooseSessionJPanel implements ActionListener {
    private JTextField t_cinema;
    private JButton b_cinema;
    private JTextField t_movie;
    private JButton b_movie;
    private JPanel CSession;
    private JButton b_yes;
    private JTable t_table1;
    private JButton b_reset;
    private JPanel show_content;
    private DefaultTableModel model;

    private JFrame frame;

    private SessionService sessionService;
    private MovieService movieService;
    private CinemaService cinemaService;
    private long u_id;
    private long m_id;

    public List<Session> sessions;

    public ChooseSessionJPanel(JFrame frame,long u_id,long m_id){
        this.frame=frame;
        this.u_id=u_id;
        this.m_id=m_id;
        sessionService=(SessionService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));
        movieService=(MovieService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        cinemaService=(CinemaService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));

        model=new DefaultTableModel();
        t_table1.setModel(model);
        t_table1.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        t_table1.getTableHeader().setResizingAllowed(false);//不可拉动表格

        model.addColumn("电影名");
        model.addColumn("电影院");
        model.addColumn("时间");
        model.addColumn("价格");

        b_cinema.addActionListener(this);
        b_movie.addActionListener(this);
        b_reset.addActionListener(this);
        b_yes.addActionListener(this);

        reset();

    }

    public void reset(){
        if (m_id==-1){
            sessions=(List<Session>) sessionService.getSessionList(1,10).getData();
        }
        else {
            sessions = (List<Session>) sessionService.selectSessionByM_id(m_id + "").getData();
        }
        setTable();
    }

    public void setTable(){
        model.getDataVector().removeAllElements();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Session session:sessions) {
            Movie movie=(Movie)(movieService.selectMovieById(Long.parseLong(session.getmId())).getData());
            Cinema cinema=(Cinema)(cinemaService.selectCinemaById(Long.parseLong(session.getcId())).getData());
          String[] str={movie.getName(),cinema.getName(),sdf.format(session.getStarttime()),((double)session.getPrice())/100+""};
            model.addRow(str);
        }
        if (sessions.size()==0){
            model.addRow(new String[]{});
        }
    }

    public JPanel getCSession() {
        return CSession;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_reset){
            reset();
        }else if (e.getSource()==b_yes){
            if (t_table1.getSelectedRow()!=-1&&t_table1.getSelectedRow()<sessions.size()) {
                BuyTicketJPanel buyTicketJPanel = new BuyTicketJPanel(frame, sessions.get(t_table1.getSelectedRow()),u_id,0);
                JPanel jPanel = buyTicketJPanel.getBuyJpanel();
                frame.getContentPane().removeAll();
                frame.setContentPane(jPanel);
                jPanel.setVisible(true);
                frame.pack();
                frame.invalidate();
                frame.repaint();
            }
        }else if (e.getSource()==b_movie){
            String name=t_movie.getText();
            if (!CommonsUtils.isNull(name)){
                JOptionPane.showMessageDialog(frame, "电影名不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            sessions=(List<Session>) sessionService.selectSessionByM_name(name).getData();
            setTable();
        }
        else if (e.getSource()==b_cinema){
            String name=t_cinema.getText();
            if (!CommonsUtils.isNull(name)){
                JOptionPane.showMessageDialog(frame, "电影院名不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            sessions=(List<Session>) sessionService.selectSessionByC_name(name).getData();
            setTable();
        }

    }
}
