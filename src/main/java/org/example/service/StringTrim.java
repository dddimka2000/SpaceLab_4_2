package org.example.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class StringTrim {
    public void trimStringFields(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                String value = (String) field.get(object);
                if (value != null) {
                    field.set(object, value.trim());
                }
            }
        }
    }
}
