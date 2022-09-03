package com.tav.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    public static String serialize(final Object o) {
        return serialize(o, 1);
    }

    private static String serialize(final Object o, final int indentations) {
        final Class<?> c = o.getClass();
        final Field[] fields = c.getDeclaredFields();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");

        boolean isFirstObjectField = true;
        for (final Field field : fields) {
            if (isFirstObjectField) { isFirstObjectField = false; } else { jsonBuilder.append(",\n"); }
            tabs(jsonBuilder, indentations);
            jsonBuilder.append(String.format("\"%s\": ", field.getName()));
            final Class<?> fieldType = field.getType();
            final Object fieldValue = ReflectionUtil.runFieldGetter(field, o);
            final String serializedField = serializeComponent(indentations, fieldType, fieldValue);
            jsonBuilder.append(serializedField);
        }

        jsonBuilder.append("\n");
        tabs(jsonBuilder, indentations - 1);
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    private static String serializeComponent(
            final int indentations, final Class<?> componentType, final Object componentValue
    ) {
        StringBuilder jsonBuilder = new StringBuilder();

        if (componentValue == null) {
            jsonBuilder.append("null");
        } else {
            if (Character.class.equals(componentType) || String.class.equals(componentType)) {
                jsonBuilder.append(String.format("\"%s\"", componentValue));
            } else if (componentType.isArray() || List.class.isAssignableFrom(componentType)) {
                jsonBuilder.append(serializeArray(indentations, (Iterable<?>) componentValue));
            } else if (ReflectionUtil.isPrimitiveOrWrapper(componentType)) {
                jsonBuilder.append(String.format("%s", componentValue));
            } else {
                final String serializedObject = serialize(componentValue, indentations + 1);
                jsonBuilder.append(serializedObject);
            }
        }

        return jsonBuilder.toString();
    }

    private static String serializeArray(int indentations, Iterable<?> componentValue) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("[\n");
        boolean isFirstArrayElement = true;
        for (final Object element : componentValue) {
            if (isFirstArrayElement) { isFirstArrayElement = false; } else { jsonBuilder.append(",\n"); }
            tabs(jsonBuilder, indentations + 1);
            final Class<?> elementType = element.getClass();
            final String serializedElement = serializeComponent(indentations + 1, elementType, element);
            jsonBuilder.append(serializedElement);
        }
        jsonBuilder.append("\n");
        tabs(jsonBuilder, indentations);
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }

    private static void tabs(final StringBuilder jsonBuilder, final int indentations) {
        jsonBuilder.append("\t".repeat(Math.max(0, indentations)));
    }
}
