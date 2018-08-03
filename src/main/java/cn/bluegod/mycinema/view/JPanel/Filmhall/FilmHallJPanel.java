package cn.bluegod.mycinema.view.JPanel.Filmhall;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.Filmhall;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.FilmHallService;
import cn.bluegod.mycinema.view.JPanel.Cinema.AddCinema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 19:18
 * @Version V1.0
 */
public class FilmHallJPanel implements ActionListener {
    private JButton b_add;
    private JButton b_update;
    private JButton b_delete;
    private JButton b_look;
    private JButton reset;
    private JPanel FilmHallJpanel;
    private JTable t_table;

    private JFrame frame;
    private List<Filmhall> filmhallList;
    private DefaultTableModel model;
    private String c_id;

    private FilmHallService filmHallService;

    public FilmHallJPanel(JFrame frame,String c_id){
        this.frame=frame;
        this.c_id=c_id;
        filmHallService=(FilmHallService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("FilmHallService"));

        model=new DefaultTableModel();
        t_table.setModel(model);
        t_table.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        t_table.getTableHeader().setResizingAllowed(false);//不可拉动表格

        model.addColumn("序号");
        model.addColumn("厅号");
        model.addColumn("座位数");

        b_add.addActionListener(this);
        b_delete.addActionListener(this);
        b_look.addActionListener(this);
        b_update.addActionListener(this);
        reset.addActionListener(this);
        refresh();

    }

    private void setTableValue(List<Filmhall> list){
        model.getDataVector().removeAllElements();
        int i=1;
        if (list!=null) {
            for (Filmhall filmhall : list) {
                String[] str = {i + "", filmhall.getSeatinfo().split(",")[0] + "号厅", filmhall.getSeatnumber() + ""};
                model.addRow(str);
                i++;
            }
        }
    }

    public JPanel getFilmHallJpanel() {
        return this.FilmHallJpanel;
    }

    public void refresh(){
        BGResult result=filmHallService.selectFilmHallByC_id(c_id);
        if (result.getStatus()==200) {
            filmhallList = (List<Filmhall>) result.getData();
            setTableValue(filmhallList);
        }else {
            JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        t_table.clearSelection();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==reset){
            refresh();
        }
        if (e.getSource()==b_add){
            jumpPage(null);
        }
        if (e.getSource()==b_look||e.getSource()==b_update){
            if (t_table.getSelectedRow()!=-1&&t_table.getSelectedRow()<filmhallList.size()) {
                jumpPage(filmhallList.get(t_table.getSelectedRow()));
            }
        }
        if (e.getSource()==b_delete){
            long[] ids=new long[t_table.getSelectedRows().length];
            for (int i=0;i<t_table.getSelectedRows().length;i++){
                ids[i]=filmhallList.get(t_table.getSelectedRows()[i]).getId();
            }
            BGResult result=filmHallService.deleteFilmHall(ids);
            if (result.getStatus()==200){
                JOptionPane.showMessageDialog(frame, result.getMsg()+"成功删除"+result.getData()+"条数据！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }
            refresh();
        }
    }
    public void jumpPage(Filmhall filmhall){
        AddHallJpanel addCinema=new AddHallJpanel(frame,filmhall,c_id);
        JPanel jPanel=addCinema.getAddHallJpanel();
        frame.getContentPane().removeAll();
        frame.setContentPane(jPanel);
        jPanel.setVisible(true);
        frame.pack();
        frame.invalidate();
        frame.repaint();
    }
}
