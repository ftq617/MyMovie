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
import cn.bluegod.mycinema.view.JPanel.Filmhall.FilmHallJPanel;
import com.eltima.components.ui.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 10:40
 * @Version V1.0
 */
public class AddSession implements ActionListener{
    private JLabel l_status;
    private JComboBox c_hall;
    private JTextField t_price;
    private JButton b_yes;
    private JButton b_reset;
    private JButton b_return;
    private JComboBox c_movie;
    private JLabel l_seat;
    private JPanel AddSessionJPanel;
    private JPanel jp_seat;
    private JPanel j_time;
    private JTextField t_movie;

    private JFrame jframe;

    private DatePicker datepick;
    private Session session;

    private FilmHallService filmHallService;
    private MovieService movieService;
    private SessionService sessionService;

    private User user;
    private List<Filmhall> filmhalls;
    private List<Movie> movies;

    public AddSession(JFrame frame, Session session,User user){
        this.jframe=frame;
        this.user=user;
        filmHallService=(FilmHallService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));
        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        sessionService=(SessionService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("SessionService"));

        b_reset.addActionListener(this);
        b_yes.addActionListener(this);
        b_return.addActionListener(this);

        j_time.setLayout(new GridLayout());
        datepick=getDatePicker();
        j_time.add(datepick);
        setHall();
        setMovie();
        l_seat.setVisible(false);
        if (session!=null){
            l_seat.setVisible(true);
            l_status.setText("修改场次");
            t_price.setText(((double)session.getPrice())/100+"");
            for (Movie movie:movies)
            {
                if (session.getmId().equals(movie.getId()+"")){
                    c_movie.setSelectedItem(movie.getName());
                }
            }
            Filmhall f=null;
            for (Filmhall filmhall:filmhalls)
            {
                if (session.getFhId().equals(filmhall.getId()+"")){
                    c_hall.setSelectedItem(filmhall.getSeatinfo().split(",")[0]+"号厅");
                    f=filmhall;
                }
            }
            showSeat(f.getSeatinfo(),session.getSelectseat());
            this.session=session;
        }

    }

    public void showSeat(String f,String y){
        String[] ss=f.split(",");
        String[] yy=y.split(",");
        int row=Integer.parseInt(ss[1]);
        int col=Integer.parseInt(ss[2]);
        jp_seat.removeAll();
        jp_seat.setLayout(new GridLayout(row,col,5,5));
        for (int i=0;i<row;i++) {
            for (int j = 0; j < col; j++) {
                JButton button = new JButton(ss[3 + row * i + j]);
                jp_seat.add(button);
                button.setVisible(true);
                if (CommonsUtils.isHave(yy,ss[3 + row * i + j])){
                    button.setBackground(Color.red);
                }
            }
        }
    }


    public void setMovie(){
        BGResult result=movieService.getMovieList(1,50);
        List<Movie> list=(List<Movie>)result.getData();
        this.movies=list;
        if (movies!=null) {
            for (int i = 0; i < list.size(); i++) {
                c_movie.addItem(list.get(i).getName());
            }
        }
    }

    public void setHall(){
        BGResult result=filmHallService.selectFilmHallByC_id(user.getcId());
        List<Filmhall> list=(List<Filmhall>)result.getData();
        this.filmhalls=list;
        if (list!=null) {
            for (int i = 0; i < list.size(); i++) {
                c_hall.addItem(list.get(i).getSeatinfo().split(",")[0] + "号厅");
            }
        }
    }

    public JPanel getAddSessionJPanel() {
        return this.AddSessionJPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==b_yes){
            if (c_movie.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(jframe, "请选择电影", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            if (c_hall.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(jframe, "请选择场厅", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            double p;
            try {
                p = Double.parseDouble(t_price.getText());
            }catch (Exception eq) {
                JOptionPane.showMessageDialog(jframe, "价格是数字", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            if (session!=null) {
                session.setmId(movies.get(c_movie.getSelectedIndex()).getId()+"");
                session.setFhId(filmhalls.get(c_hall.getSelectedIndex()).getId()+"");
                System.out.println("sssss          ssss"+datepick.getValue());
                session.setStarttime((Date) datepick.getValue());
                session.setRemain(filmhalls.get(c_hall.getSelectedIndex()).getSeatnumber());
                session.setPrice((int)p*100);
                session.setTimelength(movies.get(c_movie.getSelectedIndex()).getTimelength());
                BGResult re=sessionService.updateSession(session);
                if (re.getStatus()==200) {
                    JOptionPane.showMessageDialog(jframe, "修改场次成功", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else {
                    JOptionPane.showMessageDialog(jframe, re.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }else {
                Session s1=new Session();
                s1.setmId(movies.get(c_movie.getSelectedIndex()).getId()+"");
                s1.setFhId(filmhalls.get(c_hall.getSelectedIndex()).getId()+"");
                System.out.println("sssss          ssss"+datepick.getValue());
                s1.setStarttime((Date) datepick.getValue());
                s1.setRemain(filmhalls.get(c_hall.getSelectedIndex()).getSeatnumber());
                s1.setPrice((int)p*100);
                s1.setcId(user.getcId());
                s1.setTimelength(movies.get(c_movie.getSelectedIndex()).getTimelength());
                BGResult re=sessionService.createSession(s1);
                if (re.getStatus()==200) {
                    JOptionPane.showMessageDialog(jframe, "增加场次成功", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else {
                    JOptionPane.showMessageDialog(jframe, re.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        }else if (e.getSource()==b_reset){

        }else if (e.getSource()==b_return){
            returnMain();
        }
    }

    public void returnMain(){
        SelectSession selectSession=new SelectSession(jframe,user);
        JPanel jPanel=selectSession.getSelectSessionJPanel();
        jframe.getContentPane().removeAll();
        jframe.setContentPane(jPanel);
        jPanel.setVisible(true);
        jframe.pack();
        jframe.invalidate();
        jframe.repaint();
    }

    private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
        // 当前时间
        Date date = new Date();
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);

        Dimension dimension = new Dimension(177, 24);

        int[] hilightDays = { 1, 3, 5, 7 };

        int[] disabledDays = { 4, 6, 5, 9 };
        //构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);

        datepick.setLocation(137, 83);//设置起始位置
        /*
        //也可用setBounds()直接设置大小与位置
        datepick.setBounds(137, 83, 177, 24);
        */
        // 设置一个月份中需要高亮显示的日子
//        datepick.setHightlightdays(hilightDays, Color.red);
        // 设置一个月份中不需要的日子，呈灰色显示
//        datepick.setDisableddays(disabledDays);
        // 设置国家
        datepick.setLocale(Locale.CANADA);
        // 设置时钟面板可见
        datepick.setTimePanleVisible(true);
        return datepick;
    }
}
