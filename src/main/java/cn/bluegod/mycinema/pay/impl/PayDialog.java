package cn.bluegod.mycinema.pay.impl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PayDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel image;
    private JLabel show_money;

    private double money;

    public PayDialog(double money) {
        this.money=money;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("支付");
        setBounds(800,200,500,500);

        show_money.setText("请支付"+money/100+"元");

        ImageIcon pic1 = new ImageIcon(PayDialog.class.getClassLoader().getResource("image/pay.png").getPath());
        pic1.setImage(pic1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT ));
        image.setIcon(pic1);
        image.setSize(200,200);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        money=-1;
        dispose();
    }

    public double payMoney(){
        return money;
    }
}
