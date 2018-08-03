package cn.bluegod.mycinema.view.JPanel.Session;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Session;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.FilmHallService;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.SessionService;
import cn.bluegod.mycinema.view.JPanel.BuyTicket.BuyTicketJPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 09:30
 * @Version V1.0
 */
public class SelectSession implements ActionListener{
    private JTable t_table;
    private JButton b_add;
    private JButton b_query;
    private JButton b_reset;
    private JButton b_look;
    private JButton b_update;
    private JButton b_delete;
    private JTextField t_query;
    private JPanel selectSessionJPanel;
    private JButton b_buy;


    private JFrame frame;
    private List<Session> sessions;
    private DefaultTableModel model;
    private User user;

    private SessionService sessionService;
    private MovieService movieService;
    private FilmHallService filmHallService;

    public SelectSession(JFrame frame,User user){
        this.frame=frame;
        this.user=user;
        sessionService=(SessionService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));
        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        filmHallService=(FilmHallService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));

        model=new DefaultTableModel();
        t_table.setModel(model);
        t_table.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        t_table.getTableHeader().setResizingAllowed(false);//不可拉动表格
        model.addColumn("序号");
        model.addColumn("电影名");
        model.addColumn("场厅号");
        model.addColumn("开始时间");
        model.addColumn("时间长度");
        model.addColumn("单价");
        model.addColumn("还剩座位数");

        b_add.addActionListener(this);
        b_delete.addActionListener(this);
        b_look.addActionListener(this);
        b_update.addActionListener(this);
        b_reset.addActionListener(this);
        b_query.addActionListener(this);
        b_buy.addActionListener(this);

        refresh();

    }

    public void refresh(){
        BGResult result=sessionService.selectSessionByC_id(user.getcId());
        if (result.getStatus()==200) {
            sessions = (List<Session>) result.getData();
            setTableValue(sessions);
        }else {
            JOptionPane.showMessageDialog(frame, "服务器出了点小问题！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        t_table.clearSelection();
    }

    private void setTableValue(List<Session> list){
        model.getDataVector().removeAllElements();
        int i=1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Session session:list) {
            BGResult result=movieService.selectMovieById(Long.parseLong(session.getmId()));
            Filmhall filmhall=(Filmhall)filmHallService.selectFilmHallById(Long.parseLong(session.getFhId())).getData();
            String[] str={i+"",((Movie)result.getData()).getName(),filmhall.getSeatinfo().split(",")[0]+"号厅",sdf.format(session.getStarttime()),session.getTimelength()+"分钟",((double)session.getPrice())/100+"元",session.getRemain()+"个"};
            model.addRow(str);
            i++;
        }
    }

    public JPanel getSelectSessionJPanel() {
        return this.selectSessionJPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_add) {
            jumpPage(null);
        } else if (e.getSource() == b_query) {
            String n=t_query.getText();
            if (!CommonsUtils.isNull(n)){
                JOptionPane.showMessageDialog(frame, "请输入搜索值", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            BGResult result=sessionService.selectSessionByM_id(n);
            if (result.getStatus()==200){
                setTableValue((List<Session>) result.getData());
                return;
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
        } else if (e.getSource() == b_reset) {
            refresh();
        } else if (e.getSource() == b_look || e.getSource() == b_update) {
           if (t_table.getSelectedRow()!=-1&&t_table.getSelectedRow()<sessions.size()) {
                jumpPage(sessions.get(t_table.getSelectedRow()));
            }
        }else if(e.getSource()==b_buy){
            if (t_table.getSelectedRow()!=-1&&t_table.getSelectedRow()<sessions.size()) {
                BuyTicketJPanel buyTicketJPanel = new BuyTicketJPanel(frame, sessions.get(t_table.getSelectedRow()),user.getId(),1);
                JPanel jPanel = buyTicketJPanel.getBuyJpanel();
                frame.getContentPane().removeAll();
                frame.setContentPane(jPanel);
                jPanel.setVisible(true);
                frame.pack();
                frame.invalidate();
                frame.repaint();
            }
        } else if (e.getSource() == b_delete) {
            long[] ids=new long[t_table.getSelectedRows().length];
            for (int i=0;i<t_table.getSelectedRows().length;i++){
                ids[i]=sessions.get(t_table.getSelectedRows()[i]).getId();
            }
            BGResult result=sessionService.deleteSession(ids);
            if (result.getStatus()==200){
                JOptionPane.showMessageDialog(frame, result.getMsg()+"成功删除"+result.getData()+"条数据！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }
            refresh();
        }
    }

    public void jumpPage(Session session){
        AddSession addSession=new AddSession(frame,session,user);
        JPanel jPanel=addSession.getAddSessionJPanel();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }
}
