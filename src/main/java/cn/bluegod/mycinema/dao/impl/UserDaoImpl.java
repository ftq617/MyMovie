package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.UserDao;
import cn.bluegod.mycinema.pojo.Cinema;
import cn.bluegod.mycinema.pojo.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 16:05
 * @Version V1.0
 */
public class UserDaoImpl implements UserDao {

    private DB db;

    public UserDaoImpl(){
        db=new DB();
    }

    @Override
    public int addUser(User user) {
        String sql="insert into user(phone,name,password,head_image,c_id,state,money,starttime) values(?,?,?,?,?,?,?,?)";
        int a=-1;
        try {
            a= db.executeUpdate(sql,user.getPhone(),user.getName(),user.getPassword(),user.getHeadImage(),user.getcId(),user.getStatus(),user.getMoney(),user.getStartTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteUser(long id) {
        String sql="delete from user where id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int deleteUserByC_id(long id) {
        if (id==0){
            return -1;
        }
        String sql="delete from user where c_id="+id;
        int a=0;
        try {
            a=db.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateUser(User user) {
        String sql="update user set phone=?,name=?,head_image=?,c_id=?,state=?,money=?,starttime=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,user.getPhone(),user.getName(),user.getHeadImage(),user.getcId(),user.getStatus(),user.getMoney(),user.getStartTime(),user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateUserPassword(String password, long id) {
        String sql="update user set password=? where id=?";
        int a=0;
        try {
            a= db.executeUpdate(sql,password,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public List<User> selectUserByValue(String[] names, String[] values) {
        StringBuffer sql=new StringBuffer();
        sql.append("select * from user where ");
        for (int i=0;i<names.length-1;i++){
            sql.append(names[i]+"='"+values[i]+"' and ");
        }
        sql.append(names[names.length-1]+"='"+values[names.length-1]+"'");

        List<User> result=null;
        try {
            result= db.executeQuery(User.class,sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User selectUserById(long id) {
        String sql="select * from user where id="+id;
        List<User> result=null;
        try {
            result=db.executeQuery(User.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null&&result.size()>0){
            return result.get(0);
        }
        return null;
    }
}
