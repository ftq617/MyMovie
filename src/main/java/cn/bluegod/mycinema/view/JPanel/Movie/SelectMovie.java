package cn.bluegod.mycinema.view.JPanel.Movie;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Type;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.TypeService;
import cn.bluegod.mycinema.view.JPanel.Filmhall.AddHallJpanel;
import cn.bluegod.mycinema.view.MyCinema;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 16:16
 * @Version V1.0
 */
public class SelectMovie implements ActionListener{

    private JTable movie_table;
    private JButton b_lookMovie;
    private JTextField tm_name;
    private JButton bn_name;
    private JComboBox t_type;
    private JButton b_type;
    private JButton b_update;
    private JButton b_delete;
    private JButton b_on;
    private JButton b_down;
    private JPanel selectPanel;
    private JButton front;
    private JButton next;
    private JLabel page;
    private JButton resch;
    private JButton b_add;

    private int pageNumber;
    private int all;
    private int rows;

    private DefaultTableModel model;

    private JFrame frame;

    private MovieService movieService;
    private TypeService typeService;
    private List<Type> types;
    private List<Movie> movies;
    private long c_id;


    public SelectMovie(){}

    public SelectMovie(JFrame frame,long c_id){
        super();
        this.frame=frame;
        this.c_id=c_id;

        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        typeService=(TypeService)GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TypeService"));

        pageNumber=1;
        rows=10;

        model = new DefaultTableModel();
        movie_table.setModel(model);
        movie_table.setRowHeight(25);
        movie_table.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        movie_table.getTableHeader().setResizingAllowed(false);//不可拉动表格

        model.addColumn("序号");
        model.addColumn("电影名");
        model.addColumn("导演");
        model.addColumn("演员");
        model.addColumn("类型");
        model.addColumn("时长");
        model.addColumn("状态");

        Refresh();
        b_delete.addActionListener(this);
        bn_name.addActionListener(this);
        b_type.addActionListener(this);
        resch.addActionListener(this);
        b_update.addActionListener(this);
        b_down.addActionListener(this);
        b_on.addActionListener(this);
        front.addActionListener(this);
        next.addActionListener(this);
        b_lookMovie.addActionListener(this);
        b_add.addActionListener(this);


        if (c_id!=-1){
            isAdmin();
        }
    }

    public void isAdmin(){
        b_update.setEnabled(false);
        b_add.setEnabled(false);
        b_delete.setEnabled(false);
        b_down.setEnabled(false);
        b_on.setEnabled(false);
    }

    private void setTableValue(List<Movie> list){
        model.getDataVector().removeAllElements();
        int i=(pageNumber-1)*rows+1;
        for (Movie movie:list) {
            String[] str={i+"",movie.getName(),movie.getDirector(),movie.getPlay(),CommonsUtils.getTypeNameByID(movie.getType(),types),movie.getTimelength()+"",movie.getStatus()==1?"热映":"下线"};
            model.addRow(str);
            i++;
        }
    }


    private void setTypeValue(){
        t_type.removeAllItems();
        BGResult result=typeService.selectType();
        if (result.getStatus()==200) {
            List<Type> list = (List<Type>) result.getData();
            types = list;
            for (Type type : list) {
                t_type.addItem(type.getT_name());
            }
        }else {
            JOptionPane.showMessageDialog(frame, "服务器出了点小问题！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        movie_table.clearSelection();
    }


    public void Refresh(){
        setTypeValue();
        BGResult result=movieService.getMovieList(pageNumber,rows);
        int s=Integer.parseInt(result.getMsg());
        if (s%rows==0){
            all=s/rows;
        }else {
            all = s / rows + 1;
        }
        page.setText(pageNumber + "/" + all);
        if (result.getStatus()==200) {
            movies = (List<Movie>) result.getData();
            setTableValue(movies);
        }else {
            JOptionPane.showMessageDialog(frame, "服务器出了点小问题！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        movie_table.clearSelection();
    }

    public JPanel getJPanel(){
        return this.selectPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_delete){
            long[] ids=new long[movie_table.getSelectedRows().length];
            for (int i=0;i<movie_table.getSelectedRows().length;i++){
                ids[i]=movies.get(movie_table.getSelectedRows()[i]).getId();
            }
            BGResult result=movieService.deleteMovie(ids);
            if (result.getStatus()==200){
                JOptionPane.showMessageDialog(frame, result.getMsg()+"成功删除"+result.getData()+"条数据！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            }else {
                JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
            }
            Refresh();
        }
        if (e.getSource()==bn_name){
            String tname=tm_name.getText();
            if (tname==null||tname.equals("")){
                JOptionPane.showMessageDialog(frame, "名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            BGResult result=movieService.selectMovieByName(tname);
            if (result.getStatus()==200){
                movies=(List<Movie>) result.getData();
                setTableValue(movies);
                return;
            }
            JOptionPane.showMessageDialog(frame, "没有找到相关信息！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        if (e.getSource()==b_type){
            String id=CommonsUtils.getTypeId((String)t_type.getSelectedItem(),types);
            BGResult result=movieService.selectMovieByType(id);
            if (result.getStatus()==200){
                movies=(List<Movie>) result.getData();
                setTableValue(movies);
                return;
            }
            JOptionPane.showMessageDialog(frame, "没有找到相关信息！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
        if (e.getSource()== resch){
            Refresh();
        }
        if (e.getSource() == b_lookMovie||e.getSource()==b_update) {
            if (movie_table.getSelectedRow()!=-1&&movie_table.getSelectedRow()<movies.size()) {
                AddMovie addMovie=new AddMovie(frame,movies.get(movie_table.getSelectedRow()),c_id);
                JPanel jPanel=addMovie.getJPanel();
                frame.getContentPane().removeAll();
                frame.setContentPane(jPanel);
                jPanel.setVisible(true);
                frame.pack();
                frame.invalidate();
                frame.repaint();
            }
        }
        if (e.getSource()==b_on){
            int[] ids = movie_table.getSelectedRows();
            if (ids.length>0){
                for (int i=0;i<ids.length;i++) {
                    Movie movie=movies.get(ids[i]);
                    movie.setStatus(1);
                    movieService.updateMovie(movie);
                }
                Refresh();
            }
        }
        if (e.getSource()==b_down){
            int[] ids = movie_table.getSelectedRows();
            if (ids.length>0){
                for (int i=0;i<ids.length;i++) {
                    Movie movie=movies.get(ids[i]);
                    movie.setStatus(2);
                    movieService.updateMovie(movie);
                }
                Refresh();
            }
        }
        if (e.getSource()==next){
            if (pageNumber<all) {
                pageNumber++;
                page.setText(pageNumber + "/" + all);
                Refresh();
            }
        }
        if (e.getSource()==front){
            if (pageNumber>1) {
                pageNumber--;
                page.setText(pageNumber + "/" + all);
                Refresh();
            }
        }
        if (e.getSource()==b_add){
            AddMovie addMovie=new AddMovie(frame,null,c_id);
            JPanel jPanel=addMovie.getJPanel();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }
    }
}
