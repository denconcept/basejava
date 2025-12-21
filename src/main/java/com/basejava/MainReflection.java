package com.basejava;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.basejava.model.Resume;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("Name1");
        Class<? extends Resume> resumeClass = r.getClass();
        Field field = resumeClass.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "newUuid");

        Method method = resumeClass.getMethod("toString");
        System.out.println(method.invoke(r));
    }
}
