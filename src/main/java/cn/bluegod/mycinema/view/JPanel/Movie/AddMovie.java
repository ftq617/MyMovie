package cn.bluegod.mycinema.view.JPanel.Movie;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Type;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.TypeService;
import cn.bluegod.mycinema.view.JPanel.Filmhall.FilmHallJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 14:36
 * @Version V1.0
 */
public class AddMovie implements ActionListener{
    private JTextField m_name;
    private JTextField director;
    private JTextField play;
    private JComboBox type;
    private JButton checkImage;
    private JTextArea describes;
    private JButton b_yes;
    private JButton b_reset;
    private JPanel MovieJpanel;
    private JTextField timelength;
    private JLabel imgs;
    private JLabel l_function;
    private JButton b_return;

    private String image;

    private JFrame frame;

    private MovieService movieService;
    private TypeService typeService;

    private List<Type> types;
    private Movie movie;
    private long c_id;

    public AddMovie(){}


    public AddMovie(JFrame frame,Movie movie,long c_id){
        super();
        movieService= (MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        typeService=(TypeService)GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TypeService"));
        this.frame=frame;
        this.c_id=c_id;
        checkImage.addActionListener(this);
        b_yes.addActionListener(this);
        b_reset.addActionListener(this);
        b_return.addActionListener(this);
        setJComboBoxValue();
        l_function.setText("增加");
        if (movie!=null){
            setUpdateValue(movie);
        }
        if (c_id!=-1){
            b_yes.setEnabled(false);
            b_reset.setEnabled(false);
        }
    }

    public JPanel getJPanel(){
        return this.MovieJpanel;
    }

    public void setAddValue(){
        setJComboBoxValue();
        l_function.setText("增加");
        reset();
        b_yes.setText("增加");
        movie=null;
    }

    public void setUpdateValue(Movie movie){
        setJComboBoxValue();
        m_name.setText(movie.getName());
        director.setText(movie.getDirector());
        play.setText(movie.getPlay());
        type.setSelectedItem(CommonsUtils.getTypeNameByID(movie.getType(),types));
        checkImage.setText(movie.getImage());
        timelength.setText(movie.getTimelength()+"");
        describes.setText(movie.getDescribe());
        ImageIcon pic1 = new ImageIcon(movie.getImage());
        pic1.setImage(pic1.getImage().getScaledInstance(150, 200,Image.SCALE_DEFAULT ));
        image=movie.getImage();
        imgs.setIcon(pic1);
        imgs.setSize(150,200);
        b_yes.setText("修改");
        l_function.setText("查看/修改");
        this.movie=movie;
    }

    public void setJComboBoxValue(){
        type.removeAllItems();
        BGResult result=typeService.selectType();
        if (result.getStatus()==200) {
            types = (List<Type>) result.getData();
            for (Type t : (List<Type>) result.getData()) {
                type.addItem(t.getT_name());
            }
        }else {
            JOptionPane.showMessageDialog(frame, "服务器出了点小问题！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==checkImage){

            FileDialog fileDialog = new FileDialog(frame, image, FileDialog.LOAD);
            fileDialog.setVisible(true);
            if (fileDialog.getDirectory()!=null) {
                image = fileDialog.getDirectory() + fileDialog.getFile();//获取文件的路径
                if (image.length()>20) {
                    checkImage.setText(image.substring(0, 20) + "...");
                }else {
                    checkImage.setText(image);
                }
                ImageIcon pic1 = new ImageIcon(image);
                pic1.setImage(pic1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                imgs.setIcon(pic1);
                imgs.setSize(200, 200);
            }
        }
        if (e.getSource()==b_reset){
            reset();
        }
        if (e.getSource()==b_yes){
            if (movie==null) {
                movie = new Movie();
            }
            if (type.getSelectedIndex()==-1){
                JOptionPane.showMessageDialog(frame, "请选择电影类型！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            if (!CommonsUtils.isNull(m_name.getText())){
                JOptionPane.showMessageDialog(frame, "电影名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if (!CommonsUtils.isNull(director.getText())) {
                JOptionPane.showMessageDialog(frame, "导演名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if (!CommonsUtils.isNull(play.getText())){
                JOptionPane.showMessageDialog(frame, "演员名字不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if(!CommonsUtils.isNull(describes.getText())){
                JOptionPane.showMessageDialog(frame, "电影描述不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }if (!CommonsUtils.isNull(image)){
                JOptionPane.showMessageDialog(frame, "电影海报不能为空！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            movie.setName(m_name.getText());
            movie.setDirector(director.getText());
            movie.setPlay(play.getText());
            try {
                movie.setTimelength(Integer.parseInt(timelength.getText()));
            }catch (Exception e1){
                JOptionPane.showMessageDialog(frame, "时长是数字！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            movie.setDescribe(describes.getText());
            movie.setImage(image);
            movie.setType(CommonsUtils.getTypeId((String)type.getSelectedItem(),types));
            if (l_function.getText().equals("增加")) {
                BGResult result=movieService.createMovie(movie);
                if (result.getStatus()==200){
                    JOptionPane.showMessageDialog(frame, "增加电影成功！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else{
                    JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }else if(l_function.getText().equals("查看/修改")){
                BGResult result=movieService.updateMovie(movie);
                if (result.getStatus()==200){
                    JOptionPane.showMessageDialog(frame, "修改电影成功！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
                }else{
                    JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        }if (e.getSource()==b_return){
            SelectMovie selectMovie=new SelectMovie(frame,c_id);
            JPanel jPanel=selectMovie.getJPanel();
            frame.getContentPane().removeAll();
            frame.setContentPane(jPanel);
            jPanel.setVisible(true);
            frame.pack();
            frame.invalidate();
            frame.repaint();
        }

    }


    public void reset(){
        m_name.setText("");
        director.setText("");
        play.setText("");
        timelength.setText("");
        describes.setText("");
        checkImage.setText("请选择图片");
        image="";
        imgs.setIcon(null);
        setJComboBoxValue();
    }
}
