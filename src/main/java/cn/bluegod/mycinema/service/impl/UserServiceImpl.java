package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.CommonsUtils;
import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.UserDao;
import cn.bluegod.mycinema.pojo.User;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.UserService;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 16:39
 * @Version V1.0
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(){
        userDao=(UserDao)GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("UserDao"));
    }

    @Override
    public BGResult login(User user) {
        List<User> list=userDao.selectUserByValue(new String[]{"phone"},new String[]{user.getPhone()});
        if (list.size()<=0){
            return BGResult.build(250,"用户名错误");
        }
        User u=list.get(0);
        if(u.getPassword().equals(CommonsUtils.MD5Encrypt(user.getPassword()))){
            u.setPassword("");
            return BGResult.ok(u);
        }
        return BGResult.build(300,"密码错误");
    }


    @Override
    public BGResult register(User user) {
        List<User> list=userDao.selectUserByValue(new String[]{"phone"},new String[]{user.getPhone()});
        if (list.size()>0){
            return BGResult.build(250,"此账号已注册");
        }
        user.setPassword(CommonsUtils.MD5Encrypt(user.getPassword()));
        user.setMoney(0L);
        user.setStartTime(new Date());
        user.setStatus(1);
        int a=userDao.addUser(user);
        return BGResult.ok(a);
    }

    @Override
    public BGResult updateUser(User user) {
        int a=userDao.updateUser(user);
        return BGResult.ok(a);
    }

    @Override
    public BGResult updateUserPassword(User user) {
        User user1=userDao.selectUserById(user.getId());
        if (user1.getPassword().equals(CommonsUtils.MD5Encrypt(user.getPassword()))){
            int a=userDao.updateUserPassword(CommonsUtils.MD5Encrypt(user.getcId()),user.getId());
            return BGResult.ok(a);
        }
        return BGResult.build(400,"密码错误");
    }

    @Override
    public BGResult deleteUser(long id) {
        int a=userDao.deleteUser(id);
        return BGResult.ok(a);
    }

    @Override
    public BGResult selectUserById(long id) {
        User user=userDao.selectUserById(id);
        return BGResult.ok(user);
    }

    @Override
    public BGResult selectUserByC_id(String cid) {
        List<User> list=userDao.selectUserByValue(new String[]{"c_id"},new String[]{cid});
        for (User user:list){
            user.setPassword("");
            user.setMoney(0L);
            user.setStatus(0);
        }
        return BGResult.ok(list);
    }

    @Override
    public BGResult selectUserByName(String name) {
        List<User> list=userDao.selectUserByValue(new String[]{"name"},new String[]{name});
        for (User user:list){
            user.setPassword("");
            user.setMoney(0L);
            user.setStatus(0);
        }
        return BGResult.ok(list);
    }
}
