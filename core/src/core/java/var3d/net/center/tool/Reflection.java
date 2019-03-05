package var3d.net.center.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by feng on 2019/3/5.
 * 反射获取父类私有方法或私有属性
 */

public class Reflection {
    public static Method getMethod(Class clazz, String methodName, final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    public static Object invoke(Class clazz, final Object obj, final String methodName
            , final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(clazz, methodName, classes);
            method.setAccessible(true);// 调用private方法的关键一句话
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Field getDeclaredField(Class clazz, String fieldName) {
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Object getFieldValue(Class clazz, Object object, String fieldName) {
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(clazz, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获的属性值
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Class clazz, Object object, String fieldName, Object value) {
        //根据 对象和属性名通过取 Field对象
        Field field = getDeclaredField(clazz, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
