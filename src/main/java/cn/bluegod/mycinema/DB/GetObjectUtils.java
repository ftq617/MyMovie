package cn.bluegod.mycinema.DB;

/**
 * @description: 反射得到实现类的工具类
 * @author: Mr.Fu
 * @create: 2018-07-25 18:52
 * @Version V1.0
 */
public class GetObjectUtils {

    public static Object getObject(String name){
        Object obj=null;
        try {
            obj=Class.forName(name).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
