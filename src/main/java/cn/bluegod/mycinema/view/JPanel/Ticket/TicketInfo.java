package cn.bluegod.mycinema.view.JPanel.Ticket;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.*;
import cn.bluegod.mycinema.service.*;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class TicketInfo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField t_comment;
    private JLabel l_mName;
    private JLabel l_cName;
    private JLabel l_address;
    private JLabel l_hName;
    private JLabel l_price;
    private JLabel l_startTime;
    private JLabel l_timeLen;
    private JLabel l_seat;

    private MovieService movieService;
    private CinemaService cinemaService;
    private SessionService sessionService;
    private FilmHallService filmHallService;
    private CommentService commentService;
    private TicketService ticketService;

    private Ticket ticket;
    private JFrame frame;

    public TicketInfo(JFrame frame,Ticket ticket) {
        this.frame=frame;
        this.ticket=ticket;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("影票详情");
        setBounds(800,200,500,500);

        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        cinemaService=(CinemaService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));
        sessionService=(SessionService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));
        filmHallService=(FilmHallService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));
        commentService=(CommentService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CommentService"));
        ticketService=(TicketService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TicketService"));

        setValue();


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setValue(){
        Movie movie = (Movie) movieService.selectMovieById(Long.parseLong(ticket.getM_id())).getData();
        Cinema cinema = (Cinema) cinemaService.selectCinemaById(Long.parseLong(ticket.getC_id())).getData();
        Filmhall filmhall=(Filmhall)filmHallService.selectFilmHallById(Long.parseLong(ticket.getFh_id())).getData();
        Session session = (Session) sessionService.selectSessionById(Long.parseLong(ticket.getFiled_id())).getData();
        l_mName.setText(movie.getName());
        l_address.setText(cinema.getAddress());
        l_cName.setText(cinema.getName());
        l_hName.setText(filmhall.getSeatinfo().split(",")[0]+"号厅");
        l_price.setText(((double)session.getPrice())/100+"");
        l_seat.setText(ticket.getSeat());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        l_startTime.setText(sdf.format(session.getStarttime()));
        l_timeLen.setText(movie.getTimelength()+"分钟");

        if (ticket.getType()!=0){
            t_comment.setEnabled(false);
            t_comment.setVisible(false);
        }
    }

    private void onOK() {
        // add your code here
        String co=t_comment.getText();
        if (CommonsUtils.isNull(co)) {
            Comment comment=new Comment();
            comment.setuId(Long.parseLong(ticket.getU_id()));
            comment.setContent(co);
            comment.setmId(Long.parseLong(ticket.getM_id()));
            commentService.createComment(comment);
            ticket.setType(1);
            ticketService.updateTicket(ticket);
            dispose();
            JOptionPane.showMessageDialog(frame, "评论成功！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            return;
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
