package cn.bluegod.mycinema.view.JPanel.Cinema;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CinemaService;
import cn.bluegod.mycinema.view.MyCinema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 11:33
 * @Version V1.0
 */
public class SelectCiemaJpanel implements ActionListener{
    private JButton b_refresh;
    private JButton b_look;
    private JButton b_name;
    private JTextField t_name;
    private JTextField t_address;
    private JButton b_address;
    private JButton b_delete;
    private JButton b_update;
    private JTable c_table;
    private JPanel selectCiemaJPanel;
    private JButton b_front;
    private JButton b_next;
    private JLabel pages;
    private JButton b_add;

    private int pageNumber;
    private int all;
    private int rows;
    private JFrame frame;

    private DefaultTableModel model;

    private CinemaService cinemaService;
    private List<Cinema> cinemas;

    public SelectCiemaJpanel(){}

    public SelectCiemaJpanel(JFrame frame){
        cinemaService=(CinemaService)  GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CinemaService"));
        this.frame=frame;
        pageNumber=1;
        rows=10;
        model=new DefaultTableModel();
        c_table.setModel(model);
        c_table.setRowHeight(25);
        c_table.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        c_table.getTableHeader().setResizingAllowed(false);//不可拉动表格
        model.addColumn("序号");
        model.addColumn("电影院名");
        model.addColumn("地址");
        model.addColumn("图片");

        b_address.addActionListener(this);
        b_delete.addActionListener(this);
        b_look.addActionListener(this);
        b_name.addActionListener(this);
        b_refresh.addActionListener(this);
        b_update.addActionListener(this);
        b_front.addActionListener(this);
        b_next.addActionListener(this);
        b_add.addActionListener(this);

        Refresh();
    }

    private void setTableValue(List<Cinema> list){
        model.getDataVector().removeAllElements();
        int i=(pageNumber-1)*rows+1;
        for (Cinema cinema:list) {
            String[] str={i+"",cinema.getName(),cinema.getAddress(),cinema.getImage()};
            model.addRow(str);
            i++;
        }
    }

    public void Refresh(){
        BGResult result=cinemaService.getCinemaList(pageNumber,rows);
        int s=Integer.parseInt(result.getMsg());
        if (s%rows==0){
            all=s/rows;
        }else {
            all = s / rows + 1;
        }
        pages.setText(pageNumber + "/" + all);
        if (result.getStatus()==200) {
             cinemas= (List<Cinema>) result.getData();
            setTableValue(cinemas);
        }else {
            JOptionPane.showMessageDialog(frame, "服务器出了点小问题！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        c_table.clearSelection();
    }

    public JPanel getSelectCiemaJPanel() {
        return this.selectCiemaJPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SelectCiemaJpanel");
        frame.setContentPane(new SelectCiemaJpanel(frame).selectCiemaJPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_add){
            jumpPage(null);
        }
        if (e.getSource()==b_look||e.getSource()==b_update){
            if (c_table.getSelectedRow()!=-1&&c_table.getSelectedRow()<cinemas.size()) {
                jumpPage(cinemas.get(c_table.getSelectedRow()));
            }
        }
        if(e.getSource()==b_refresh){
            Refresh();
        }
        if (e.getSource()==b_name){
            String cname=t_name.getText();
            if (!CommonsUtils.isNull(cname)){
                JOptionPane.showMessageDialog(frame, "名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            BGResult result=cinemaService.selectCinemaByName(cname);
            if (result.getStatus()==200){
                cinemas=(List<Cinema>) result.getData();
                setTableValue(cinemas);
                return;
            }
            JOptionPane.showMessageDialog(frame, "没有找到相关信息！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        if (e.getSource()==b_address){
            String address=t_address.getText();
            if (!CommonsUtils.isNull(address)){
                JOptionPane.showMessageDialog(frame, "地址不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            BGResult result=cinemaService.selectCinemaByAddress(address);
            if (result.getStatus()==200){
                cinemas=(List<Cinema>) result.getData();
                setTableValue(cinemas);
                return;
            }
            JOptionPane.showMessageDialog(frame, "没有找到相关信息！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        if (e.getSource()==b_delete){
            long[] ids=new long[c_table.getSelectedRows().length];
            for (int i=0;i<c_table.getSelectedRows().length;i++){
                ids[i]=cinemas.get(c_table.getSelectedRows()[i]).getId();
                System.out.println(ids[i]);
            }
            BGResult result=cinemaService.deleteCinema(ids);
            if (result.getStatus()==200){
                JOptionPane.showMessageDialog(frame, result.getMsg()+"成功删除"+result.getData()+"条数据！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }
            Refresh();
        }if (e.getSource()==b_next){
            if (pageNumber<all) {
                pageNumber++;
                pages.setText(pageNumber + "/" + all);
                Refresh();
            }
        }
        if (e.getSource()==b_front){
            if (pageNumber>1) {
                pageNumber--;
                pages.setText(pageNumber + "/" + all);
                Refresh();
            }
        }
    }

    public void jumpPage(Cinema cinema){
        AddCinema addCinema=new AddCinema(frame,cinema);
        JPanel jPanel=addCinema.getCinemaJpanel();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }

}
