package cn.bluegod.mycinema.view.JPanel.MoveList;

import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.view.JPanel.chooseSession.ChooseSessionJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 16:42
 * @Version V1.0
 */
public class MovieJPanel implements ActionListener{
    private JLabel l_image;
    private JButton b_buy;
    private JLabel l_name;
    private JLabel l_destor;
    private JPanel MovieJpanel;
    private JLabel l_play;
    private JButton b_look;

    private JFrame frame;
    private Movie movie;
    private long u_id;

    public MovieJPanel(JFrame frame,Movie movie,long u_id){
        this.frame=frame;
        this.movie=movie;
        this.u_id=u_id;
        ImageIcon pic1 = new ImageIcon(movie.getImage());
        pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
        l_image.setIcon(pic1);
        l_image.setSize(150, 200);

        l_destor.setText(movie.getDirector());
        l_name.setText(movie.getName());
        l_play.setText(movie.getPlay());

        b_buy.addActionListener(this);
        b_look.addActionListener(this);


        MovieJpanel.setBorder(BorderFactory.createLineBorder(Color.blue));//设置面板边框颜色

    }


    public JPanel getMovieJpanel() {
        return MovieJpanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_buy) {
            ChooseSessionJPanel chooseSessionJPanel = new ChooseSessionJPanel(frame, u_id, movie.getId().intValue());
            JPanel jPanel = chooseSessionJPanel.getCSession();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }else if (e.getSource()==b_look){
            MovieInfoJPanel movieInfoJPanel=new MovieInfoJPanel(frame,movie,u_id);
            JPanel jPanel = movieInfoJPanel.getPanel1();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }
    }
}
