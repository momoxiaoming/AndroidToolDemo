package com.andr.common.tool.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <pre>
 *     author:
 *     time  : 2019-11-20
 *     desc  : new class
 * </pre>
 */
public class RefUtil
{
    private Class<?> curCls;  //当前操作的class

    private Object curObj;  //当前操作的对象


    private RefUtil(Class<?> curCls)
    {

        this.curCls = curCls;
    }

    private RefUtil(Object curObj)
    {
        this.curObj = curObj;
    }

    private RefUtil(Class<?> curCls, Object curObj)
    {
        this.curCls = curCls;
        this.curObj = curObj;
    }


    public static RefUtil on(Class<?> cls, Object object)
    {

        return new RefUtil(cls, object);
    }
    public static RefUtil on(String className,ClassLoader classLoader, Object object) throws ClassNotFoundException
    {
        return new RefUtil(Class.forName(className,true,classLoader), object);
    }

    public Object get()
    {
        return this.curObj;
    }

    /**
     * 调用含参数的方法
     *
     * @param methodName 方法名
     * @param params     参数
     * @return
     * @throws Exception
     */
    public RefUtil call(String methodName, Object[] params) throws Exception
    {
        Class[] cls = getClsType(params);
        Method method = findMethod(methodName, cls);
        if (method.getReturnType() == Void.class)
        {
            return new RefUtil(curObj);
        }
        Object obj = method.invoke(curObj, params);
        return new RefUtil(obj.getClass(), obj);
    }

    /**
     * 调用不含参数的方法
     *
     * @param methodName
     * @return
     * @throws Exception
     */
    public RefUtil call(String methodName) throws Exception
    {
        return call(methodName, new Object[0]);
    }

    /**
     * 模糊获取变量值
     *
     * @param fieldName 变量名
     * @return
     * @throws Exception
     */
    public RefUtil getField(String fieldName) throws Exception
    {
        return getField(fieldName, null);
    }

    /**
     * 精确获取成员变量值,
     *
     * @param fieldName  变量名
     * @param filedClsNm 变量的类名
     * @return
     * @throws Exception
     */
    public RefUtil getField(String fieldName, String filedClsNm) throws Exception
    {
        Field field = findField(fieldName, filedClsNm);

        return new RefUtil(field.getType(), field.get(curObj));
    }

    /**
     * 模糊设置变量值
     * @param fiedlValue 变量值
     * @param fieldName 变量名
     * @return
     * @throws Exception
     */
    public RefUtil setField(Object fiedlValue,String fieldName) throws Exception
    {
        return setField(fiedlValue,fieldName, null);
    }

    /**
     *  精确设置变量值
     * @param fiedlValue 变量值
     * @param fieldName 变量名
     * @param filedClsNm 变量类名
     * @return
     * @throws Exception
     */
    public RefUtil setField(Object fiedlValue,String fieldName, String filedClsNm) throws Exception
    {
        Field field = findField( fieldName, filedClsNm);
        field.set(curObj,fiedlValue);
        return this;
    }


    private Method findMethod(String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException
    {
        return getMethods(methodName, parameterTypes);
    }


    /**
     * 查找目标类的变量Field
     *
     * @param filedName  变量名
     * @param filedClsNm 变量的类名
     * @return
     * @throws Exception
     */
    private Field findField(String filedName, String filedClsNm) throws Exception
    {
        Field field = getFields(curCls, filedName, filedClsNm);

        if (!field.isAccessible())
        {
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * 获取符合条件的method,子类不存在会从父类中查找
     *
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethods(String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException
    {

        while (curCls != null && !curCls.getName().toLowerCase().equals("java.lang.object"))
        {
            try
            {
                Method method = curCls.getDeclaredMethod(methodName, parameterTypes);
                if (!method.isAccessible())
                    method.setAccessible(true);
                return method;

            } catch (NoSuchMethodException e)
            {
                curCls = curCls.getSuperclass();
            }
        }
        throw new NoSuchMethodException("未找到方法:" + methodName);

    }

    /**
     * 获取参数类型
     *
     * @param params
     * @return
     */
    private Class[] getClsType(Object[] params)
    {
        if (params == null)
        {
            return new Class[0];
        }
        Class[] cls = new Class[params.length];
        for (int i = 0; i < params.length; i++)
        {
            Object item = params[0];

            cls[i] = item == null ? Object.class : item.getClass();
        }

        return cls;
    }

    /**
     * 获取目标类的成员,若当类查不到,会逐级从父类查找
     *
     * @param cls       类
     * @param fieldName 成员名
     * @param fieldCls  成员类名,用于当成员名一致时,精确查找
     * @return
     * @throws Exception
     */
    private Field getFields(Class<?> cls, String fieldName, String fieldCls) throws Exception
    {

        while (cls != null && !cls.getName().toLowerCase().equals("java.lang.object"))
        {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields)
            {
                if (field != null && fieldName.equals(field.getName()))
                {

                    if (!StringUtil.isStringEmpty(fieldCls))
                    {
                        if (fieldCls.equals(field.getType().getName()))
                        {
                            return field;
                        }

                        throw new Exception("未找到类名:" + fieldCls + " 变量名:" + fieldName + " 的成员");
                    } else
                    {
                        return field;
                    }

                }
            }

            cls = cls.getSuperclass();
        }

        throw new Exception("未找到变量名:" + fieldName + " 的成员");

    }
}
