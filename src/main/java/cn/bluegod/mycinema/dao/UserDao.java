package cn.bluegod.mycinema.dao;

import cn.bluegod.mycinema.pojo.User;

import java.util.List;

/**
 * @program: MyMovie
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 15:48
 * @Version V1.0
 */
public interface UserDao {
    int addUser(User user);
    int deleteUser(long id);
    int deleteUserByC_id(long id);
    int updateUser(User user);
    int updateUserPassword(String password, long id);
    List<User> selectUserByValue(String[] names,String[] values);
    User selectUserById(long id);
}
