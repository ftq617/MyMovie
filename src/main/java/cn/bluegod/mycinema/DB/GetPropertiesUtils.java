package cn.bluegod.mycinema.DB;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description: 获取配置文件信息的工具类
 * @author: Mr.Fu
 * @create: 2018-07-25 18:47
 * @Version V1.0
 */
public class GetPropertiesUtils {

    public static String getData(String name,String url){
        Properties p=new Properties();
        InputStream is=GetPropertiesUtils.class.getClassLoader().getResourceAsStream(url);
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p.getProperty(name);
    }

    public static String getUrlData(String name){
        return getData(name, "resource/db.properties");
    }

    public static String getDaoObject(String name){
        return getData(name,"resource/dao.properties");
    }

    public static String getServiceObject(String name){
        return getData(name,"resource/service.properties");
    }
}
