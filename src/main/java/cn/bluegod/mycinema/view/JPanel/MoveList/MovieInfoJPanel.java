package cn.bluegod.mycinema.view.JPanel.MoveList;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Comment;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.service.CommentService;
import cn.bluegod.mycinema.view.JPanel.chooseSession.ChooseSessionJPanel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-08-01 09:16
 * @Version V1.0
 */
public class MovieInfoJPanel implements ActionListener{
    private JPanel panel1;
    private JLabel l_image;
    private JTextArea t_desc;
    private JButton b_buy;
    private JLabel l_name;
    private JLabel l_destor;
    private JLabel l_play;
    private JLabel l_timelen;
    private JList li_list;

    private JFrame frame;
    private long u_id;
    private Movie movie;
    private DefaultListModel model;

    private CommentService commentService;

    public MovieInfoJPanel(JFrame frame,Movie movie,long u_id){

        this.u_id=u_id;
        this.movie=movie;
        this.frame=frame;
        model= new DefaultListModel();
        li_list.setModel(model);
        li_list.setFixedCellHeight(30);

        commentService=(CommentService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CommentService"));

        b_buy.addActionListener(this);

        init();

    }

    public void init(){
        l_name.setText(movie.getName());
        l_destor.setText(movie.getDirector());
        l_timelen.setText(movie.getTimelength()+"");

        ImageIcon pic1 = new ImageIcon(movie.getImage());
        pic1.setImage(pic1.getImage().getScaledInstance(200, 280, Image.SCALE_DEFAULT ));
        l_image.setIcon(pic1);
        l_image.setSize(200,280);
        l_play.setText(movie.getPlay());
        t_desc.setText(movie.getDescribe());

        List<Comment> commentList=(List<Comment>) commentService.selectCommentBym_id(movie.getId()+"").getData();

        model.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Comment comment:commentList) {
            model.addElement("网友"+(int)(Math.random()*10000+11111)+"    "+sdf.format(comment.getTime())+"    : "+comment.getContent());
        }
    }

    public JPanel getPanel1() {
        return panel1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChooseSessionJPanel chooseSessionJPanel = new ChooseSessionJPanel(frame, u_id, movie.getId().intValue());
        JPanel jPanel = chooseSessionJPanel.getCSession();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }
}
