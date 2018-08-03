package cn.bluegod.mycinema.view.JPanel.Filmhall;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.FilmHallService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 19:50
 * @Version V1.0
 */
public class AddHallJpanel implements ActionListener{
    private JTextField t_name;
    private JTextField t_row;
    private JTextField t_col;
    private JPanel seat_jpanel;
    private JPanel AddHallJpanel;
    private JButton b_yes;
    private JButton b_reset;
    private JLabel l_seat;
    private JButton b_return;
    private JLabel l_sho;

    private JFrame frame;
    private Filmhall filmhall;
    private String c_id;
    private FilmHallService filmHallService;

    public AddHallJpanel(JFrame frame, Filmhall filmhall,String c_id){
        this.frame=frame;
        this.filmhall=filmhall;
        this.c_id=c_id;

        filmHallService=(FilmHallService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));

        b_yes.addActionListener(this);
        b_reset.addActionListener(this);
        b_return.addActionListener(this);
        l_seat.setVisible(false);
        l_sho.setText("增加厅：");
        if (filmhall!=null){
            l_sho.setText("修改厅：");
            l_seat.setVisible(true);
            String[] seat=filmhall.getSeatinfo().split(",");
            String name=seat[0];
            int row=Integer.parseInt(seat[1]);
            int col=Integer.parseInt(seat[2]);
            seat_jpanel.setLayout(new GridLayout(row,col,5,5));
            for (int i=0;i<row;i++){
                for (int j=0;j<col;j++){
                    JButton button=new JButton(seat[3+col*i+j]);
                    seat_jpanel.add(button);
                    button.setVisible(true);
                }
            }
            t_name.setText(name);
            t_row.setText(row+"");
            t_col.setText(col+"");
        }

    }

    public JPanel getAddHallJpanel() {
        return this.AddHallJpanel;
    }

    private void reset(){
        t_name.setText("");
        t_row.setText("");
        t_col.setText("");
    }

    private void addAndUpdateHall(){
        String n=t_name.getText();
        String r=t_row.getText();
        String c=t_col.getText();
        if (!CommonsUtils.isNull(n,r,c)){
            JOptionPane.showMessageDialog(frame, "输入框不能为空", "提示", JOptionPane.OK_CANCEL_OPTION);
            return;
        }
        StringBuffer seat=new StringBuffer();
        seat.append(n+","+r+","+c+",");
        int row;
        int col;
        try {
             row = Integer.parseInt(r);
             col = Integer.parseInt(c);
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= col; j++) {
                    seat.append(i + "-" + j + ",");
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "行列是数字", "提示", JOptionPane.OK_CANCEL_OPTION);
            return;
        }
        System.out.println(seat.toString());
        if (filmhall==null) {
            Filmhall film = new Filmhall();
            if (row * col>110){
                JOptionPane.showMessageDialog(frame, "现实点吧，你家影院没那么大", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            film.setSeatnumber(row * col);
            film.setSeatinfo(seat.toString());
            film.setcId(c_id);
            BGResult result = filmHallService.createFilmHall(film);
            if (result.getStatus() == 200) {
                JOptionPane.showMessageDialog(frame, "增加成功", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            } else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
        }else {
            filmhall.setSeatnumber(row*col);
            filmhall.setSeatinfo(seat.toString());
            filmhall.setcId(c_id);
            BGResult result = filmHallService.updateFilmHall(filmhall);
            if (result.getStatus() == 200) {
                seat_jpanel.removeAll();
                String[] ss=filmhall.getSeatinfo().split(",");
                for (int i=0;i<row;i++) {
                    for (int j = 0; j < col; j++) {
                        JButton button = new JButton(ss[3 + row * i + j]);
                        seat_jpanel.add(button);
                        button.setVisible(true);
                    }
                }
                returnMain();
                JOptionPane.showMessageDialog(frame, "修改成功", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            } else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
        }
    }

    public void returnMain(){
        FilmHallJPanel filmHallJPanel=new FilmHallJPanel(frame,c_id);
        JPanel jPanel=filmHallJPanel.getFilmHallJpanel();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_return){
            returnMain();
        }
        if (e.getSource()==b_yes){
            addAndUpdateHall();
        }
        if (e.getSource()==b_reset){
            reset();
        }

    }


}
