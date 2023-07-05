package org.apache.chenx.example02;

import org.apache.ibatis.reflection.property.PropertyCopier;

import java.util.Arrays;

/**
 * @author chenx
 * @create 2023-07-05 14:51
 */
public class PropertiesCopierExample {
    public static void main(String[] args) {
        ExClass exClass = new ExClass("陈生", 23, "合肥市肥西县双岗城", "133828293939");
        exClass.setRace("黄种人");
        ExClass tarClass = new ExClass();
        System.out.println(tarClass);
//        PropetyCopier的copyBeanProperties方法可以对同类对象进行属性拷贝,可以将从父类继承来的属性一样拷贝
        PropertyCopier.copyBeanProperties(exClass.getClass(), exClass, tarClass);

        System.out.println(tarClass);

//      getDeclaredFields获取所有属性，getFields获取所有public属性
        System.out.println(Arrays.toString(ExClass.class.getDeclaredFields()));
        System.out.println(Arrays.toString(ExClass.class.getFields()));
    }
}
