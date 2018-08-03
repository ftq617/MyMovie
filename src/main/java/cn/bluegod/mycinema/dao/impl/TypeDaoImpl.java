package cn.bluegod.mycinema.dao.impl;

import cn.bluegod.mycinema.DB.DB;
import cn.bluegod.mycinema.dao.TypeDao;
import cn.bluegod.mycinema.pojo.Type;

import java.sql.SQLException;
import java.util.List;


/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 10:27
 * @Version V1.0
 */
public class TypeDaoImpl implements TypeDao {

    private DB db;

    public TypeDaoImpl(){
        db=new DB();
    }
    @Override
    public List<Type> selectTypeList() {
        String sql="select * from type";
        List<Type> list=null;
        try {
            list= db.executeQuery(Type.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String selectTypeById(int id) {
        String sql="select * from type where id="+id;
        List<Type> list=null;
        try {
            list= db.executeQuery(Type.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size()==0){
            return null;
        }
        return list.get(0).getT_name();
    }

    @Override
    public int selectTypeByName(String name) {
        String sql="select * from type where t_name='"+name+"'";
        List<Type> list=null;
        try {
            list= db.executeQuery(Type.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size()==0){
            return -1;
        }
        return list.get(0).getId();
    }

    @Override
    public int  addType(Type type) {
        String sql="insert into type(t_name) values(?)";
        int a=0;
        try {
            a=db.executeUpdate(sql,type.getT_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int updateType(Type type) {
        String sql="update type set t_name=? where id=?";
        int a=0;
        try {
            a=db.executeUpdate(sql,type.getT_name(),type.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public int  deleteeType(int id) {
        String sql="delete from type where id=?";
        int a=0;
        try {
            a=db.executeUpdate(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }
}
