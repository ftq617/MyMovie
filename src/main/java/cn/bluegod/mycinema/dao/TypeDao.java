package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.Type;

import java.util.List;

/**
 * @description: 类型字典的dao类
 * @author: Mr.Fu
 * @create: 2018-07-26 10:23
 * @Version V1.0
 */
public interface TypeDao {
    List<Type> selectTypeList();
    String selectTypeById(int id);
    int selectTypeByName(String name);
    int addType(Type type);
    int updateType(Type type);
    int deleteeType(int  id);
}
