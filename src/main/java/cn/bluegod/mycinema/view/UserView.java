package cn.bluegod.mycinema.view;

import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.view.JPanel.Comment.CommentJpanel;
import cn.bluegod.mycinema.view.JPanel.MoveList.MovieListJPanel;
import cn.bluegod.mycinema.view.JPanel.Ticket.TicketJPanel;
import cn.bluegod.mycinema.view.JPanel.UserInfo.UserInfo;
import cn.bluegod.mycinema.view.JPanel.UserInfo.updatePasswordJPanel;
import cn.bluegod.mycinema.view.JPanel.chooseSession.ChooseSessionJPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 20:07
 * @Version V1.0
 */
public class UserView extends JFrame implements ActionListener{
    private JMenuBar menubar;   //菜单条
    private JMenu movie; //菜单
    private JMenuItem  selectMovie,selectSession,selectTicket;   //菜单项
    private JMenu use; //菜单
    private JMenuItem  userContent,updatePassword;   //菜单项
    private JMenu comment; //菜单
    private JMenuItem  selectComment;   //菜单项

    private User user;

    public UserView(User user){

        setTitle("欢迎"+user.getName()+"登陆");
        setBounds(600,200,1200,600);
        this.user=user;
        menubar=new JMenuBar();

        movie=new JMenu("电影");
        selectMovie=new JMenuItem("查询电影");
        selectMovie.addActionListener(this);
        selectSession=new JMenuItem("查询电影场次");
        selectSession.addActionListener(this);
        selectTicket=new JMenuItem("查询购票记录");
        selectTicket.addActionListener(this);
        movie.add(selectMovie);
        movie.add(selectSession);
        movie.add(selectTicket);
        menubar.add(movie);

        use=new JMenu("用户");
        userContent=new JMenuItem("个人信息");
        userContent.addActionListener(this);
        updatePassword=new JMenuItem("修改密码");
        updatePassword.addActionListener(this);
        use.add(userContent);
        use.add(updatePassword);
        menubar.add(use);

        comment=new JMenu("评论");
        selectComment=new JMenuItem("个人评论");
        selectComment.addActionListener(this);
        comment.add(selectComment);
        menubar.add(comment);
        this.setJMenuBar(menubar);


        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        this.getContentPane().removeAll();
        if (e.getSource()==selectMovie){
            MovieListJPanel movieListJPanel=new MovieListJPanel(this,user.getId());
            this.setContentPane(movieListJPanel.getMovieListJpanel());
            movieListJPanel.getMovieListJpanel().setVisible(true);
        }
        else if (e.getSource()==selectSession){
            ChooseSessionJPanel chooseSessionJPanel=new ChooseSessionJPanel(this,user.getId().intValue(),-1);
            this.setContentPane(chooseSessionJPanel.getCSession());
            chooseSessionJPanel.getCSession().setVisible(true);
            this.pack();
        }else if(e.getSource()==selectTicket){
            TicketJPanel ticketJPanel=new TicketJPanel(this,user.getId().intValue()+"",null);
            this.setContentPane(ticketJPanel.getTicketJP());
            ticketJPanel.getTicketJP().setVisible(true);
            this.pack();
        }
        else if (e.getSource()==userContent){
            UserInfo userInfo=new UserInfo(this,user);
            this.setContentPane(userInfo.getShowUserJpanel());
            userInfo.getShowUserJpanel().setVisible(true);
            this.pack();
        }else if (e.getSource()==updatePassword){
            updatePasswordJPanel updatePassword=new updatePasswordJPanel(this,user);
            this.setContentPane(updatePassword.getUpdatePasswordJPanel());
            updatePassword.getUpdatePasswordJPanel().setVisible(true);
            this.pack();
        }
        else if (e.getSource()==selectComment){
            CommentJpanel commentJpanel=new CommentJpanel(this,user.getId()+"");
            this.setContentPane(commentJpanel.getCommentJPanel());
            commentJpanel.getCommentJPanel().setVisible(true);
            this.pack();
        }
        this.revalidate();
    }
}
