package cn.bluegod.mycinema.pay.impl;

import cn.bluegod.mycinema.pay.PayMoney;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-30 19:32
 * @Version V1.0
 */
public class MyPay implements PayMoney{
    @Override
    public double pay(double money) {

        PayDialog dialog = new PayDialog(money);
        dialog.setBounds(300,300,500,500);
        dialog.pack();
        dialog.setVisible(true);
        return dialog.payMoney();
    }
}
