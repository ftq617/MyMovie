package cn.bluegod.mycinema.view;

import cn.bluegod.mycinema.view.JPanel.Cinema.SelectCiemaJpanel;
import cn.bluegod.mycinema.view.JPanel.Movie.SelectMovie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-08-02 10:01
 * @Version V1.0
 */
public class Admin extends JFrame implements ActionListener{

    private JMenuBar menubar;   //菜单条
    private JMenu movie; //菜单
    private JMenuItem  selectMovie;   //菜单项
    private JMenu cinema;
    private JMenuItem selectcinema;
    private String c_id;

    public Admin(String c_id){
        this.c_id=c_id;

        menubar=new JMenuBar();

        movie=new JMenu("电影");
        selectMovie=new JMenuItem("查询电影");
        selectMovie.addActionListener(this);
        movie.add(selectMovie);
        menubar.add(movie);

        cinema=new JMenu("电影院");
        selectcinema=new JMenuItem("查询电影院");
        selectcinema.addActionListener(this);
        cinema.add(selectcinema);
        menubar.add(cinema);

        this.setJMenuBar(menubar);

        setTitle("客服端");
        setBounds(600,200,1200,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==selectcinema){
            SelectCiemaJpanel selectCiemaJpanel=new SelectCiemaJpanel(this);
            JPanel jPanel=selectCiemaJpanel.getSelectCiemaJPanel();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
            this.invalidate();
            this.repaint();
        }else if (e.getSource()==selectMovie){
            SelectMovie selectMovie=new SelectMovie(this,Long.parseLong(c_id));
            JPanel jPanel=selectMovie.getJPanel();
            this.getContentPane().removeAll();
            this.setContentPane(jPanel);
            jPanel.setVisible(true);
            this.pack();
            this.invalidate();
            this.repaint();
        }
    }
}
