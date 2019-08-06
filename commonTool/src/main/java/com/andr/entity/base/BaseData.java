package com.andr.entity.base;

import com.andr.common.tool.util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/4/15
 *     desc  : 所有实体类的超类
 * </pre>
 */
public class BaseData
{

    /**
     * 检查参数是否不为空且服务要求
     *
     * @param filedNm 可变长度,变量名
     * @return
     */
    public boolean isReady(String... filedNm)
    {
        if (filedNm != null)
        {
            for (String item : filedNm)
            {
                Object obj = ReflectUtils.getFieldValue(this, item);

                if (obj == null || "".equals(obj))
                {
                    return false;
                }
            }


        }
        return true;
    }

    /**
     * 判断所有参数是否为空
     * @return
     */
    public boolean isAllParamReady()
    {

        List<Field> fields = ReflectUtils.getFields(this.getClass());
        if (fields == null)
        {
            return true;
        }
        for (Field item : fields)
        {
            Object obj = ReflectUtils.getFieldValue(this, item.getName());

            if (obj == null || "".equals(obj))
            {
                return false;
            }
        }


        return true;
    }

}
