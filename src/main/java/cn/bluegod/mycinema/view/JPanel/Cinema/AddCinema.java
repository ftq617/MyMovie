package cn.bluegod.mycinema.view.JPanel.Cinema;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CinemaService;
import cn.bluegod.mycinema.view.JPanel.User.RegisterJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 11:05
 * @Version V1.0
 */
public class AddCinema implements ActionListener{
    private JTextField t_Cname;
    private JTextField t_address;
    private JButton b_checkimage;
    private JLabel l_showImage;
    private JPanel CinemaJpanel;
    private JTextArea t_describes;
    private String imagePath;
    private JButton b_yes;
    private JButton b_reset;
    private JButton b_return;
    private JLabel l_showType;

    private JFrame frame;

    private Cinema cinema;

    private CinemaService cinemaService;

    public AddCinema(){}

    public AddCinema(JFrame frame, Cinema cinema){
        cinemaService=(CinemaService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));
        this.frame=frame;
        this.cinema=cinema;
        b_return.addActionListener(this);
        b_yes.addActionListener(this);
        b_reset.addActionListener(this);
        b_checkimage.addActionListener(this);

        l_showType.setText("新增");
        b_yes.setText("确定");
        if (cinema!=null){
            setValue(cinema);
            l_showType.setText("查看/修改");
            b_yes.setText("修改");
        }
    }

    private void setValue(Cinema cinema){
        t_Cname.setText(cinema.getName());
        t_address.setText(cinema.getAddress());
        t_describes.setText(cinema.getDescribe());
        if (cinema.getImage()!=null) {
            b_checkimage.setText(cinema.getImage());
        }else {
            b_checkimage.setText("null");
        }

        imagePath=cinema.getImage();

        ImageIcon pic1 = new ImageIcon(imagePath);
        pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT ));
        l_showImage.setIcon(pic1);
        l_showImage.setSize(150,200);

    }


    public JPanel getCinemaJpanel(){
        return this.CinemaJpanel;
    }

    public void reset(){
        t_Cname.setText("");
        t_address.setText("");
        t_describes.setText("");
        b_checkimage.setText("请选择图片");
        imagePath="";
        l_showImage.setIcon(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_return){
            SelectCiemaJpanel selectCiemaJpanel=new SelectCiemaJpanel(frame);
            JPanel jPanel=selectCiemaJpanel.getSelectCiemaJPanel();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }
        if (e.getSource()==b_yes){
            if (cinema==null){
                cinema=new Cinema();
            }
            if (!CommonsUtils.isNull(t_Cname.getText())){
                JOptionPane.showMessageDialog(frame, "电影院名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if (!CommonsUtils.isNull(t_address.getText())) {
                JOptionPane.showMessageDialog(frame, "地址不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if (!CommonsUtils.isNull(t_describes.getText())){
                JOptionPane.showMessageDialog(frame, "电影院描述不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            if (!CommonsUtils.isNull(imagePath)){
                JOptionPane.showMessageDialog(frame, "电影院图片不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            cinema.setName(t_Cname.getText());
            cinema.setAddress(t_address.getText());
            cinema.setImage(imagePath);
            cinema.setDescribe(t_describes.getText());
            if (b_yes.getText().equals("确定")){
                BGResult result =cinemaService.createCinema(cinema);
                if (result.getStatus()==200){
                    User user=(User)result.getData();
                    JOptionPane.showMessageDialog(frame, "增加电影院成功！！！\n请记住：\n登陆账号："+user.getPhone()+"\n登陆密码"+user.getPassword()+"\n", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else{
                    JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }else if (b_yes.getText().equals("修改")){
                BGResult result=cinemaService.updateCinema(cinema);
                if (result.getStatus()==200){
                    JOptionPane.showMessageDialog(frame, "修改电影院成功！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else{
                    JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        }
        if (e.getSource()==b_reset){
            reset();
        }
        if (e.getSource()==b_checkimage){
            FileDialog fileDialog = new FileDialog(frame, imagePath, FileDialog.LOAD);
            fileDialog.setVisible(true);
            if (fileDialog.getDirectory()!=null) {
                imagePath = fileDialog.getDirectory() + fileDialog.getFile();//获取文件的路径
                if (imagePath.length()>20) {
                    b_checkimage.setText(imagePath.substring(0, 20) + "...");
                }else {
                    b_checkimage.setText(imagePath);
                }
                ImageIcon pic1 = new ImageIcon(imagePath);
                pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                l_showImage.setIcon(pic1);
                l_showImage.setSize(150, 200);
            }
        }
    }

}
