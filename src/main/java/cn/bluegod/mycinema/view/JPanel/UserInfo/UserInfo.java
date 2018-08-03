package cn.bluegod.mycinema.view.JPanel.UserInfo;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 09:23
 * @Version V1.0
 */
public class UserInfo implements ActionListener{
    private JTextField t_name;
    private JButton b_head;
    private JLabel l_head;
    private JLabel l_phone;
    private JLabel l_type;
    private JLabel l_money;
    private JLabel l_time;
    private JButton b_update;
    private JPanel showUserJpanel;

    private JFrame frame;
    private User user;
    private UserService userService;

    public UserInfo(JFrame frame,User user){
        this.frame=frame;
        this.user=user;

        userService=(UserService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("UserService"));

        b_head.addActionListener(this);
        b_update.addActionListener(this);

        setValue();
    }

    public void setValue(){
        t_name.setText(user.getName());
        l_phone.setText(user.getPhone());
        ImageIcon pic1 = new ImageIcon(user.getHeadImage());
        pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT ));
        l_head.setIcon(pic1);
        l_head.setSize(150,200);
        b_head.setText(user.getHeadImage());
        l_type.setText(user.getcId().equals("0")?"普通用户":"电影院管理员");
        l_money.setText(user.getMoney()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        l_time.setText(sdf.format(user.getStartTime()));
    }

    public JPanel getShowUserJpanel() {
        return showUserJpanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==b_head){
            FileDialog fileDialog = new FileDialog(frame, "请选择头像", FileDialog.LOAD);
            fileDialog.setVisible(true);
            if (fileDialog.getDirectory()!=null) {
                user.setHeadImage( fileDialog.getDirectory() + fileDialog.getFile());//获取文件的路径
                b_head.setText(user.getHeadImage());
                ImageIcon pic1 = new ImageIcon(user.getHeadImage());
                pic1.setImage(pic1.getImage().getScaledInstance(150, 200, Image.SCALE_DEFAULT));
                l_head.setIcon(pic1);
                l_head.setSize(150, 200);
            }
        }else if (e.getSource()==b_update){
            String name=t_name.getText();
            if (!CommonsUtils.isNull(name,user.getHeadImage())){
                JOptionPane.showMessageDialog(frame, "名字和图片不能为空", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            user.setName(name);
            userService.updateUser(user);
            JOptionPane.showMessageDialog(frame, "修改成功", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
    }
}
