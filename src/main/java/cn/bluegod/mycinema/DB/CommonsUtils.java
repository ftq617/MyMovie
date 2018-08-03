package cn.bluegod.mycinema.DB;

import cn.bluegod.mycinema.pojo.Type;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 21:10
 * @Version V1.0
 */
public class CommonsUtils {

    public static String getTypeId(String name, List<Type> types){
        for (Type type:types){
            if (type.getT_name().equals(name)){
                return type.getId()+"";
            }
        }
        return null;
    }

    public static String  getTypeNameByID(String id,List<Type> types){
        for (Type type:types){
            if (type.getId()==Integer.parseInt(id))
            {
                return type.getT_name();
            }
        }
        return "错误类型";
    }

    public static boolean isNull(String... str){
        for (int i=0;i<str.length;i++) {
            if (str[i] == null || str[i].equals("")) {
                return false;
            }
        }
        return true;
    }


    public static String MD5Encrypt(String password){
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isHave(String[] yy,String s){
        for (int i=0;i<yy.length;i++){
            if (s.equals(yy[i])){
                return true;
            }
        }
        return false;
    }

}
