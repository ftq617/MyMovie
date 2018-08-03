package cn.bluegod.mycinema.view;

import cn.bluegod.mycinema.view.JPanel.User.LoginJpanel;

import javax.swing.*;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 11:26
 * @Version V1.0
 */
public class Tests {
    public static void main(String[] args) {
            JFrame frame = new JFrame("影院登陆");
            frame.setBounds(600,200,200,600);
             LoginJpanel loginJpanel= new LoginJpanel(frame);
            frame.setContentPane(loginJpanel.getLogin_JPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
    }
}
