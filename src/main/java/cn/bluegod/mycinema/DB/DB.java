package cn.bluegod.mycinema.DB;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 连接数据库读写工具类
 * @author: Mr.Fu
 * @create: 2018-07-25 17:40
 * @Version V1.0
 */
public class DB {

    private static JdbcConnectionsPool pool = new JdbcConnectionsPool();

    private Connection con;
    private  PreparedStatement statement;

    public DB(){ }

    private  void getConnect()  {
        try {
            con=pool.getConnection();
            System.out.println("获得数据库连接成功");
        } catch (SQLException e) {
            System.out.println("获得数据库连接失败");
            e.printStackTrace();
        }
    }

    private  void getStatement(String sql){
        getConnect();
        try {
            statement=con.prepareStatement(sql);
            System.out.println("获得statement成功");
        } catch (SQLException e) {
            System.out.println("获得statement失败");
            e.printStackTrace();
        }
    }

    public  int executeUpdate(String sql,Object... objs) throws SQLException {
        getStatement(sql);
        if (objs!=null){
            for (int i = 1; i <= objs.length; i++) {
                statement.setObject(i, objs[i-1]);
            }
        }
        int a=statement.executeUpdate();
        closedStatement();
        closedCon();
        return a;
    }

    public  <T> List<T> executeQuery(Class tClass, String sql, Object... value) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        getStatement(sql);
        if (value!=null){
            for (int i = 1; i <= value.length; i++) {
                statement.setObject(i, value[i-1]);
            }
        }
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData rsmd=rs.getMetaData();
        List<T> list=new ArrayList<T>();
        if (rs!=null) {
            while (rs.next()) {
                T entity =  (T)tClass.newInstance();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    Field field=tClass.getDeclaredField(rsmd.getColumnName(i+1));
                    field.setAccessible(true);
                    try {
                        field.set(entity, rs.getObject(i + 1));
                    }catch (Exception e){
                        field.set(entity, rs.getObject(i + 1)+"");
                    }
                }
                list.add(entity);
            }
        }
        rs.close();
        closedStatement();
        closedCon();
        return list;
    }


    public void closedStatement() throws SQLException {
        if (statement!=null){
            statement.close();
        }
//
    }

    public void closedCon() throws SQLException {
        if (con!=null){
            con.close();
            con=null;
        }
    }
}
