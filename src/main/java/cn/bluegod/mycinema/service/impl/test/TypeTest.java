package cn.bluegod.mycinema.service.impl.test;

import cn.bluegod.mycinema.pojo.Type;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.TypeService;
import cn.bluegod.mycinema.service.impl.TypeServiceImpl;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-27 10:01
 * @Version V1.0
 */
public class TypeTest {
    TypeService typeService=new TypeServiceImpl();

    @Test
    public void selectType(){
        BGResult result=typeService.selectType();
        for (Type type:(List<Type>)result.getData())
        System.out.println(type.toString());
    }

    @Test
    public void serdsagf(){
        long start1 = new Date().getTime() / 1000;
        long end1 = start1 + 60 * 1000;

        long start = new Date().getTime() / 1000;
        long end = start + 50 * 1000;
        if ((start < end1 && start > start1) || (end < end1 && end > start1) || (start > start1 && end < end1) || (start < start1 && end > end1)) {
            System.out.println("213");
        }
    }
}
