package utils;

import java.lang.reflect.Field;
import java.util.List;

public class ObjectMapper {
    public static <T> T fromList(List<?> list, Class<T> clazz) throws Exception {
        if (list == null || list.isEmpty() || clazz == null) {
            throw new IllegalArgumentException("List and class must not be null or empty");
        }

        T obj = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        if (fields.length != list.size()) {
            throw new IllegalArgumentException("Number of fields does not match list size");
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = list.get(i);
            if (value != null && fields[i].getType().isAssignableFrom(value.getClass())) {
                fields[i].set(obj, value);
            } else {
                throw new IllegalArgumentException("Type mismatch for field " + fields[i].getName());
            }
        }

        return obj;
    }

}
