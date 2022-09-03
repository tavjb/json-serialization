package com.tav.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtil {
    private static final String GETTER_METHOD_PREFIX = "get";

    public static boolean isPrimitiveOrWrapper(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class;
    }

    public static Object runFieldGetter(final Field field, final Object o) {
        final Class<?> c = o.getClass();

        for (Method method : c.getMethods()) {
            if ((method.getName().startsWith(GETTER_METHOD_PREFIX))
                    && (method.getName().length() == (field.getName().length() + GETTER_METHOD_PREFIX.length()))
            ) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(o);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println(("Could not determine method: " + method.getName()));
                    }
                }
            }
        }

        return null;
    }
}
