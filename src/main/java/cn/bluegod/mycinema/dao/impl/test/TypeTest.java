package cn.bluegod.mycinema.dao.impl.test;

import cn.bluegod.mycinema.dao.TypeDao;
import cn.bluegod.mycinema.dao.impl.TypeDaoImpl;
import cn.bluegod.mycinema.pojo.Type;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 10:37
 * @Version V1.0
 */
public class TypeTest {

    @Test
    public void selectTypeListTest(){
        TypeDao dao=new TypeDaoImpl();
        List<Type> list=dao.selectTypeList();
        for (Type type:list) {
            System.out.println(type.getT_name());
        }
    }

    @Test
    public void addTypeTest(){
        TypeDao dao=new TypeDaoImpl();
        Type type=new Type();
        type.setT_name("喜剧");
        dao.addType(type );
    }

    @Test
    public void updateTypeTest(){
        TypeDao dao=new TypeDaoImpl();
        Type type=new Type();
        type.setId(1);
        type.setT_name("科幻无敌");
        System.out.println(dao.updateType(type));
    }

    @Test
    public void deleteTypeTest(){
        TypeDao dao=new TypeDaoImpl();
        System.out.println(dao.deleteeType(2));
    }

    @Test
    public void selectTypeTest(){
        TypeDao dao=new TypeDaoImpl();
        System.out.println(dao.selectTypeById(1));
        System.out.println(dao.selectTypeByName("科幻"));
    }

}
