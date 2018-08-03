package cn.bluegod.mycinema.view;

import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.view.JPanel.Filmhall.FilmHallJPanel;
import cn.bluegod.mycinema.view.JPanel.Movie.AddMovie;
import cn.bluegod.mycinema.view.JPanel.Movie.SelectMovie;
import cn.bluegod.mycinema.view.JPanel.Session.SelectSession;
import cn.bluegod.mycinema.view.JPanel.Ticket.TicketJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

/**
 * @description: 显示所用的JFrame
 * @author: Mr.Fu
 * @create: 2018-07-26 10:48
 * @Version V1.0
 */
public class MyCinema extends JFrame implements ActionListener{

    private JMenuBar menubar;   //菜单条
    private JMenu movie; //菜单
    private JMenuItem  selectMovie;   //菜单项
    private JMenu session;
    private JMenuItem selectSession;
    private JMenu hall;
    private JMenuItem selecthall;
    private JMenu user;
    private JMenuItem updateUser;
    private JMenu ticket;
    private JMenuItem lookticket,lookMy;

    private User us;



    public MyCinema(User us){
        this.us=us;
        setTitle("电影管理端");
        setBounds(600,200,1200,600);
        menuInitial();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void menuInitial(){
        menubar=new JMenuBar();

        movie=new JMenu("电影");
        selectMovie=new JMenuItem("查询电影");
        selectMovie.addActionListener(this);
        movie.add(selectMovie);
        menubar.add(movie);

        session=new JMenu("场次");
        selectSession=new JMenuItem("查询场次");
        selectSession.addActionListener(this);
        session.add(selectSession);
        menubar.add(session);

        hall=new JMenu("电影厅");
        selecthall=new JMenuItem("电影厅");
        hall.add(selecthall);
        selecthall.addActionListener(this);
        menubar.add(hall);

//        user=new JMenu("管理员");
//        updateUser=new JMenuItem("修改密码");
//        user.add(updateUser);
//        selecthall.addActionListener(this);
//        menubar.add(user);

        ticket=new JMenu("影票");
        lookticket=new JMenuItem("查看影票");
        ticket.add(lookticket);
        lookticket.addActionListener(this);
        lookMy=new JMenuItem("查看自己的影票");
        lookMy.addActionListener(this);
        ticket.add(lookMy);
        menubar.add(ticket);

        this.setJMenuBar(menubar);


        JPanel jPanel=new JPanel();
        jPanel.setSize(1200,600);
        JLabel logo=new JLabel();
        ImageIcon pic1 = new ImageIcon("C:\\Users\\hasee\\Desktop\\-826553c5d55dea50\\新建文件夹 (2)\\addbullet.gif");
        pic1.setImage(pic1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        logo.setIcon(pic1);
        logo.setSize(200, 200);
        jPanel.add(logo);
        this.getContentPane().add(jPanel);
        logo.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        closeJPanel();
        if (e.getSource()==selectMovie){
            SelectMovie selectMovie=new SelectMovie(this,Long.parseLong(us.getcId()));
            JPanel jPanel=selectMovie.getJPanel();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
        }
        else if (e.getSource()==selecthall){
            FilmHallJPanel filmHallJPanel=new FilmHallJPanel(this,us.getcId());
            JPanel jPanel=filmHallJPanel.getFilmHallJpanel();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
        }
        else if (e.getSource()==selectSession){
            SelectSession selectSession=new SelectSession(this,us);
            JPanel jPanel=selectSession.getSelectSessionJPanel();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();

        }else if (e.getSource()==updateUser){

        }else if (e.getSource()==lookticket){
            TicketJPanel ticketJPanel=new TicketJPanel(this,null,us.getcId());
            JPanel jPanel=ticketJPanel.getTicketJP();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
        }else if (e.getSource()==lookMy){
            TicketJPanel ticketJPanel=new TicketJPanel(this,us.getId()+"",us.getcId());
            JPanel jPanel=ticketJPanel.getTicketJP();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
        }
        this.invalidate();
        this.repaint();
    }


    private void closeJPanel(){
        this.getContentPane().removeAll();
    }
}
