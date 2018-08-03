package cn.bluegod.mycinema.view.JPanel.BuyTicket;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pay.PayMoney;
import cn.bluegod.mycinema.pay.impl.MyPay;
import cn.bluegod.mycinema.pojo.*;
import cn.bluegod.mycinema.service.*;
import cn.bluegod.mycinema.view.JPanel.chooseSession.ChooseSessionJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 16:47
 * @Version V1.0
 */
public class BuyTicketJPanel implements ActionListener{
    private JPanel BuyJpanel;
    private JButton b_buy;
    private JButton b_return;
    private JTextArea t_des;
    private JLabel l_mname;
    private JLabel l_mdao;
    private JLabel l_mplay;
    private JLabel l_price;
    private JLabel l_cinema;
    private JPanel jp_seat;
    private JLabel l_money;
    private JLabel l_image;
    private JLabel l_hall;

    private JFrame frame;
    private Session session;
    private JButton[][] button;
    private StringBuffer seatChoose;
    private int ourMoney=0;
    private long u_id;

    private SessionService sessionService;
    private MovieService movieService;
    private CinemaService cinemaService;
    private FilmHallService filmHallService;
    private TicketService ticketService;

    private PayMoney payMoney;
    private int type=0;

    private int row;
    private int col;

    public BuyTicketJPanel(JFrame frame, Session session,long u_id,int type){
        this.frame=frame;
        this.session=session;
        this.u_id=u_id;
        this.type=type;

        b_return.addActionListener(this);
        b_buy.addActionListener(this);

        setContent();
    }

    public void setContent(){
        sessionService=(SessionService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));
        movieService=(MovieService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        cinemaService=(CinemaService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));
        filmHallService=(FilmHallService)GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));
        ticketService=(TicketService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TicketService"));
        reset();


    }

    public void reset(){
        Movie movie=(Movie) movieService.selectMovieById(Long.parseLong(session.getmId())).getData();
        ImageIcon pic1 = new ImageIcon(movie.getImage());
        pic1.setImage(pic1.getImage().getScaledInstance(200, 280, Image.SCALE_DEFAULT ));
        l_image.setIcon(pic1);
        l_image.setSize(200,280);;
        l_mname.setText(movie.getName());
        l_mdao.setText(movie.getDirector());
        l_mplay.setText(movie.getPlay());
        l_money.setText("￥0元");
        t_des.setText(movie.getDescribe());

        l_price.setText(((double)session.getPrice())/100+"");

        Cinema cinema=(Cinema)cinemaService.selectCinemaById(Long.parseLong(session.getcId())).getData();
        l_cinema.setText(cinema.getName());

        Filmhall filmhall=(Filmhall)filmHallService.selectFilmHallById(Long.parseLong(session.getFhId())).getData();
        String[] seat=filmhall.getSeatinfo().split(",");
        String[] t=session.getSelectseat().split(",");
        l_hall.setText(seat[0]+"号厅");

        row=Integer.parseInt(seat[1]);
        col=Integer.parseInt(seat[2]);
        button=new JButton[row][col];
        jp_seat.removeAll();
        jp_seat.setLayout(new GridLayout(row,col,1,1));
        for (int i=0;i<row;i++) {
            for (int j = 0; j < col; j++) {
                JButton b = new JButton(seat[3 + row * i + j]);

                b.setBackground(Color.white);
                b.addActionListener(this);
                jp_seat.add(b);
                b.setVisible(true);
                if (CommonsUtils.isHave(t,seat[3 + row * i + j])){
                    b.setBackground(Color.red);
                    b.setEnabled(false);
                }
                button[i][j]=b;
            }
        }
    }

    public JPanel getBuyJpanel() {
        return BuyJpanel;
    }

    public List<Ticket> getSeat(){
        seatChoose=new StringBuffer();
        List<Ticket> list=new ArrayList<>();
        for (int i=0;i<row;i++) {
            for (int j = 0; j < col; j++) {
                if (button[i][j].getBackground()==Color.yellow) {
                    Ticket ticket=new Ticket();
                    ticket.setC_id(session.getcId());
                    ticket.setFh_id(session.getFhId());
                    ticket.setM_id(session.getmId());
                    ticket.setU_id(u_id+"");
                    ticket.setSeat(button[i][j].getText());
                    ticket.setFiled_id(session.getId()+"");
                    list.add(ticket);
                    seatChoose.append(button[i][j].getText()+",");
                }
            }
        }
        return list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==b_buy){
            if(ourMoney>0||type==1) {
                payMoney = new MyPay();
                if (type==1||payMoney.pay(ourMoney) >= ourMoney) {
                    List<Ticket> list = getSeat();
                    for (Ticket ticket : list) {
                        ticketService.createTicket(ticket);
                    }
                    session.setRemain(session.getRemain() - list.size());
                    session.setSelectseat(session.getSelectseat() + seatChoose.toString());
                    sessionService.updateSessionByBuyTicket(session);
                    reset();
                }
            }
        }else if (e.getSource()==b_return){
            ChooseSessionJPanel chooseSessionJPanel = new ChooseSessionJPanel(frame,u_id,-1);
            JPanel jPanel = chooseSessionJPanel.getCSession();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }
        else {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (e.getSource() == button[i][j]) {
                        if (button[i][j].getBackground() == Color.yellow) {
                            button[i][j].setBackground(Color.white);
                            if (type==0) {
                                ourMoney -= session.getPrice();
                            }
                        } else {
                            button[i][j].setBackground(Color.yellow);
                            if (type==0) {
                                ourMoney += session.getPrice();
                            }
                        }
                    }
                }
            }
            l_money.setText("￥" + ((double) ourMoney) / 100 + "元");
        }
    }
}
