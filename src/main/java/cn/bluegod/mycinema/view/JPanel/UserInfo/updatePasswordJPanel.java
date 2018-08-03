package cn.bluegod.mycinema.view.JPanel.UserInfo;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 10:45
 * @Version V1.0
 */
public class updatePasswordJPanel implements ActionListener{
    private JTextField t_old;
    private JTextField t_new;
    private JTextField t_newtwo;
    private JButton b_yes;
    private JPanel updatePasswordJPanel;

    private JFrame frame;
    private UserService userService;
    private User user;

    public updatePasswordJPanel(JFrame frame, User user){
        this.frame=frame;
        this.user=user;
        userService=(UserService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("UserService"));

        b_yes.addActionListener(this);


    }

    public JPanel getUpdatePasswordJPanel() {
        return updatePasswordJPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String old=t_old.getText();
        String ne=t_new.getText();
        String neT=t_newtwo.getText();
        if (!CommonsUtils.isNull(old,ne,neT)){
            JOptionPane.showMessageDialog(frame, "密码不能为空", "提示", JOptionPane.OK_CANCEL_OPTION);
            return;
        }
        if (!ne.equals(neT)){
            JOptionPane.showMessageDialog(frame, "两次密码不一致", "提示", JOptionPane.OK_CANCEL_OPTION);
            return;
        }
        user.setPassword(old);
        String cid=user.getcId();
        user.setcId(ne);
        BGResult result=userService.updateUserPassword(user);
        user.setcId(cid);
        JOptionPane.showMessageDialog(frame, "修改密码"+result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
        t_old.setText("");
        t_new.setText("");
        t_newtwo.setText("");
    }
}
