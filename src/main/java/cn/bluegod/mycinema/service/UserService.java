package cn.bluegod.mycinema.service;

import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;

/**
 * @program: MyMovie
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 16:15
 * @Version V1.0
 */
public interface UserService {
        BGResult login(User user);
        BGResult register(User user);
        BGResult updateUser(User user);
        BGResult updateUserPassword(User user);
        BGResult deleteUser(long id);
        BGResult selectUserById(long id);
        BGResult selectUserByC_id(String cid);
        BGResult selectUserByName(String name);
}
