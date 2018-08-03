package cn.bluegod.mycinema.view.JPanel.MoveList;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.pojo.Type;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.MovieService;
import cn.bluegod.mycinema.service.TypeService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 17:10
 * @Version V1.0
 */
public class MovieListJPanel implements ActionListener{
    private JPanel p_movieList;
    private JTextField t_name;
    private JButton b_movieName;
    private JPanel MovieListJpanel;
    private JScrollPane j_scro;
    private JComboBox c_box;
    private JButton b_type;

    private JFrame frame;
    private long u_id;

    private List<Movie> movies;
    private List<Type> types;

    private MovieService movieService;
    private TypeService typeService;

    public MovieListJPanel(JFrame frame,long u_id){
        this.frame=frame;
        this.u_id=u_id;
        movieService= (MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        typeService=(TypeService)GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("TypeService"));

        b_movieName.addActionListener(this);
        b_type.addActionListener(this);
        movies=(List<Movie>) movieService.getMovieList(1,10).getData();
        setDataValue();
        setTypeValue();
        frame.setSize(700,600);
    }

    private void setDataValue(){
        p_movieList.removeAll();
        if (movies!=null&&movies.size()>0) {
            if (movies.size()>=4) {
                p_movieList.setLayout(new GridLayout(movies.size(), 1, 10, 10));
            }else {
                p_movieList.setLayout(new GridLayout(3, 1, 10, 10));
            }
            for (Movie movie : movies) {
                MovieJPanel jPanel = new MovieJPanel(frame, movie, u_id);
                p_movieList.add(jPanel.getMovieJpanel());
                jPanel.getMovieJpanel().setVisible(true);
            }
            if (movies.size() < 4) {
                for (int i=0;i<3-movies.size();i++) {
                    p_movieList.add(new JPanel());
                }
            }
            p_movieList.setVisible(true);
        }
    }

    private void setTypeValue(){
        c_box.removeAllItems();
        BGResult result=typeService.selectType();
        if (result.getStatus()==200) {
            List<Type> list = (List<Type>) result.getData();
            types = list;
            for (Type type : list) {
                c_box.addItem(type.getT_name());
            }
        }else {
            JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
        }
    }

    public JPanel getMovieListJpanel() {
        return MovieListJpanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b_movieName) {
            String name = t_name.getText();
            if (CommonsUtils.isNull(name)) {
                movies = (List<Movie>) movieService.selectMovieByName(name).getData();
                setDataValue();
                p_movieList.revalidate();
            }
        }else if (e.getSource()==b_type){
            String id=CommonsUtils.getTypeId((String)c_box.getSelectedItem(),types);
            BGResult result=movieService.selectMovieByType(id);
            if (result.getStatus()==200){
                movies=(List<Movie>) result.getData();
                setDataValue();
                p_movieList.revalidate();
                return;
            }
            JOptionPane.showMessageDialog(frame, "没有找到相关信息！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
        }
    }
}
