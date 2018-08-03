package cn.bluegod.mycinema.view.JPanel.User;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.UserService;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 13:29
 * @Version V1.0
 */
public class RegisterJPanel implements ActionListener{
    private JTextField t_number;
    private JTextField t_name;
    private JPasswordField t_password;
    private JButton b_chooseImage;
    private JLabel l_showImage;
    private JPanel RegisterJpanel;
    private String headImage;
    private JButton b_refister;
    private JButton b_return;

    private JFrame frame;
    private UserService userService;

    public RegisterJPanel(JFrame frame){

        userService=(UserService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("UserService"));
        this.frame=frame;

        b_chooseImage.addActionListener(this);
        b_refister.addActionListener(this);
        b_return.addActionListener(this);

    }


    public JPanel getRegisterJpanel() {
        return this.RegisterJpanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_refister){
            String number=t_number.getText();
            String n=t_name.getText();
            String p=t_password.getText();
            if (!CommonsUtils.isNull(number, n, p)) {
                JOptionPane.showMessageDialog(frame, "账号或则密码等不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            User user=new User();
            user.setPhone(number);
            user.setPassword(p);
            user.setName(n);
            user.setHeadImage(headImage);
            user.setcId("0");
            BGResult result=userService.register(user);
            if (result.getStatus()==200){
                jumePage();
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }

        }
        else if (e.getSource()==b_return){
            jumePage();
        }
        else if (e.getSource()==b_chooseImage){
            FileDialog fileDialog = new FileDialog(frame, headImage, FileDialog.LOAD);
            fileDialog.setVisible(true);
            if (fileDialog.getDirectory()!=null) {
                headImage = fileDialog.getDirectory() + fileDialog.getFile();//获取文件的路径
                b_chooseImage.setText(headImage);
                if (headImage.length()>20) {
                    b_chooseImage.setText(headImage.substring(0, 20) + "...");
                }else {
                    b_chooseImage.setText(headImage);
                }
                ImageIcon pic1 = new ImageIcon(headImage);
                pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                l_showImage.setIcon(pic1);
                l_showImage.setSize(150, 200);
            }
        }
    }

    public void jumePage(){
        LoginJpanel loginJpanel=new LoginJpanel(frame);
        JPanel jPanel=loginJpanel.getLogin_JPanel();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }
}
