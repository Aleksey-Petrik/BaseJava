package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        Method method = r.getClass().getDeclaredMethod("toString");
        System.out.println(method.invoke(r));
        field.setAccessible(true);
        field.set(r, "new_uuid");
        System.out.println(method.invoke(r));
    }
}
