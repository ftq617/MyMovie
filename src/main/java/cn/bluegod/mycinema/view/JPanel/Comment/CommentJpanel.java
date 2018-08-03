package cn.bluegod.mycinema.view.JPanel.Comment;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.pojo.Comment;
import cn.bluegod.mycinema.pojo.Movie;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.CommentService;
import cn.bluegod.mycinema.service.MovieService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-31 15:34
 * @Version V1.0
 */
public class CommentJpanel implements ActionListener{
    private JTable t_table;
    private JButton b_delete;
    private JPanel CommentJPanel;

    private JFrame frame;
    private List<Comment> comments;
    private DefaultTableModel model;
    private String u_id;

    private MovieService movieService;
    private CommentService commentService;

    public CommentJpanel(JFrame frame,String u_id){
        this.frame=frame;
        this.u_id=u_id;

        movieService=(MovieService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("MovieService"));
        commentService=(CommentService) GetObjectUtils.getObject(GetPropertiesUtils.getServiceObject("CommentService"));

        b_delete.addActionListener(this);

        model=new DefaultTableModel();
        t_table.setModel(model);
        model.addColumn("序号");
        model.addColumn("电影名");
        model.addColumn("评论");
        model.addColumn("时间");
        setTableValue();
    }

    public JPanel getCommentJPanel() {
        return CommentJPanel;
    }

    private void setTableValue(){
        model.getDataVector().removeAllElements();
        comments = (List<Comment>) commentService.selectCommentByu_id(u_id).getData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i=1;
        for (Comment comment:comments){
            Movie movie = (Movie) movieService.selectMovieById(comment.getmId()).getData();
            String[] str={i+"",movie.getName(),comment.getContent(),sdf.format(comment.getTime())};
            i++;
            model.addRow(str);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long[] ids=new long[t_table.getSelectedRows().length];
        for (int i=0;i<t_table.getSelectedRows().length;i++){
            ids[i]=comments.get(i).getId();
        }
        BGResult result=commentService.deleteComment(ids);
        if (result.getStatus()==200){
            JOptionPane.showMessageDialog(frame, "成功删除"+result.getData()+"条数据！！！", "提示", JOptionPane.OK_CANCEL_OPTION);
            setTableValue();
        }else {
            JOptionPane.showMessageDialog(frame, result.getMsg(), "提示", JOptionPane.OK_CANCEL_OPTION);
        }
    }
}
