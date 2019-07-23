package var3d.net.center.tool;

import com.badlogic.gdx.utils.GdxRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Reflex {

    private static boolean doMatch(Class<?> type, Class<?> parameterType) {
        if (!type.isPrimitive())
            return type.isAssignableFrom(parameterType);
        else if (type == int.class)
            return Integer.class.isAssignableFrom(parameterType);
        else if (type == byte.class)
            return Byte.class.isAssignableFrom(parameterType);
        else if (type == short.class)
            return Short.class.isAssignableFrom(parameterType);
        else if (type == long.class)
            return Long.class.isAssignableFrom(parameterType);
        else if (type == float.class)
            return Float.class.isAssignableFrom(parameterType);
        else if (type == double.class)
            return Double.class.isAssignableFrom(parameterType);
        else if (type == char.class)
            return Character.class.isAssignableFrom(parameterType);
        else if (type == boolean.class)
            return Boolean.class.isAssignableFrom(parameterType);
        return false;
    }

    private static NoSuchMethodException wrapNoSuchMethodException(String name, Object[] args) {
        final String[] typeNames = new String[args.length];
        for (int i = 0, j = typeNames.length; i < j; i++) {
            final Object index;
            if ((index = args[i]) != null) {
                typeNames[i] = index.getClass().getName();
            }
        }
        return new NoSuchMethodException(name + Arrays.toString(typeNames).replace('[', '(').replace(']', ')'));
    }

    public static Method getMethod(String name, Class<?> cls, Object... args) {
        do {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals(name)) continue;
                Class<?>[] types = method.getParameterTypes();
                if (types.length != args.length) continue;

                boolean isMatch = true;
                for (int i = 0, j = types.length; i < j; i++) {
                    final Object parameter;
                    if ((parameter = args[i]) != null && !doMatch(types[i], parameter.getClass())) {
                        isMatch = false;
                        break;
                    }
                }

                if (isMatch) {
                    method.setAccessible(true);
                    return method;
                }
            }
        } while ((cls = cls.getSuperclass()) != null);
        throw new GdxRuntimeException(wrapNoSuchMethodException(name, args));
    }

    public static Object invokeMethod(String name, Object obj, Object... args) {
        try {
            Method method = getMethod(name, obj.getClass(), args);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static Object invokeStaticMethod(String name, String cls, Object... args) {
        return invokeStaticMethod(name, forName(cls), args);
    }

    public static Object invokeStaticMethod(String name, Class<?> cls, Object... args) {
        try {
            Method method = getMethod(name, cls, args);
            return method.invoke(null, args);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static Field getField(String name, Class<?> cls) {
        Field field = null;

        do {
            try {
                field = cls.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {
            }
        } while ((cls = cls.getSuperclass()) != null);

        if (field == null) {
            throw new GdxRuntimeException(new NoSuchFieldException(name));
        }

        field.setAccessible(true);
        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(String name, Object obj) {
        try {
            Field field = getField(name, obj.getClass());
            return (T) field.get(obj);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static void setFieldValue(String name, Object obj, Object value) {
        try {
            Field field = getField(name, obj.getClass());
            field.set(obj, value);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getStaticFieldValue(String name, String cls) {
        try {
            Field field = getField(name, forName(cls));
            return (T) field.get(null);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getStaticFieldValue(String name, Class<?> cls) {
        try {
            Field field = getField(name, cls);
            return (T) field.get(null);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static void setStaticFieldValue(String name, Class<?> cls, Object value) {
        try {
            Field field = getField(name, cls);
            field.set(null, value);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static Constructor getConstructor(Class<?> cls, Object... args) {
        Constructor[] constructors = cls.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length != args.length) continue;
            boolean isMatch = true;
            for (int i = 0, j = types.length; i < j; i++) {
                final Object parameter;
                if ((parameter = args[i]) != null && !doMatch(types[i], parameter.getClass())) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                return constructor;
            }
        }
        throw new GdxRuntimeException(wrapNoSuchMethodException(cls.getName(), args));
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> cls, Object... args) {
        Constructor constructor = getConstructor(cls, args);
        try {
            return (T) constructor.newInstance(args);
        } catch (Exception e) {
            throw new GdxRuntimeException(e);
        }
    }

    public static <T> T newInstance(String cls, Object... args) {
        return newInstance(forName(cls), args);
    }

    public static Class<?> forName(String cls) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return loader.loadClass(cls);
        } catch (ClassNotFoundException e) {
            throw new GdxRuntimeException(e);
        }
    }
}
