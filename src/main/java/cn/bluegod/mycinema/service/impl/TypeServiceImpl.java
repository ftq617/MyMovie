package cn.bluegod.mycinema.service.impl;

import cn.bluegod.mycinema.DB.GetObjectUtils;
import cn.bluegod.mycinema.DB.GetPropertiesUtils;
import cn.bluegod.mycinema.dao.TypeDao;
import cn.bluegod.mycinema.pojo.Type;
import cn.bluegod.mycinema.result.BGResult;
import cn.bluegod.mycinema.service.TypeService;

import java.util.List;

/**
 * @description:
 * @author: Mr.Fu
 * @create: 2018-07-26 11:01
 * @Version V1.0
 */
public class TypeServiceImpl implements TypeService {

    private TypeDao typeDao;

    public TypeServiceImpl(){
        typeDao=(TypeDao) GetObjectUtils.getObject(GetPropertiesUtils.getDaoObject("TypeDao"));
    }

    @Override
    public BGResult selectType() {
        List<Type> result=typeDao.selectTypeList();
        return BGResult.ok(result);
    }
}
