package cn.bluegod.mycinema.view.JPanel.User;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.UserService;
import cn.bluegod.mycinema.view.Admin;
import cn.bluegod.mycinema.view.MyCinema;
import cn.bluegod.mycinema.view.UserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 13:03
 * @Version V1.0
 */
public class LoginJpanel implements ActionListener{
    private JPanel Login_JPanel;
    private JTextField t_phone;
    private JTextField t_password;
    private JLabel l_logo;
    private JButton b_login;
    private JButton b_reset;
    private JFrame frame;

    private UserService userService;

    public LoginJpanel(JFrame frame){
        this.frame=frame;

        userService=(UserService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("UserService"));


        ImageIcon pic1 = new ImageIcon("C:\\Users\\hasee\\Desktop\\-826553c5d55dea50\\新建文件夹 (2)\\boss_1.gif");
        pic1.setImage(pic1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
        l_logo.setIcon(pic1);
        l_logo.setSize(100, 100);
        b_login.addActionListener(this);
        b_reset.addActionListener(this);
    }


    public JPanel getLogin_JPanel() {
        return this.Login_JPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_login){
            String n=t_phone.getText();
            String p=t_password.getText();
            if (!CommonsUtils.isNull(n,p)){
                JOptionPane.showMessageDialog(frame, "账号或则密码不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            User user=new User();
            user.setPhone(n);
            user.setPassword(p);
            BGResult result=userService.login(user);
            if (result.getStatus()==200){
                user=(User)result.getData();
                if (user.getcId().equals("0")){
                    UserView userView=new UserView(user);
                    frame.dispose();
                }
                else if (user.getcId().equals("-1")){
                    Admin admin=new Admin(user.getcId());
                    frame.dispose();
                }
                else{
                    MyCinema myCinema=new MyCinema(user);
                    frame.dispose();
                }
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }
        }else if(e.getSource()==b_reset){
            RegisterJPanel registerJPanel=new RegisterJPanel(frame);
            JPanel jPanel=registerJPanel.getRegisterJpanel();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }
    }
}
