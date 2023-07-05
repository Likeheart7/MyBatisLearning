package org.apache.chenx.dynamic_example;

import java.lang.reflect.Proxy;

/**
 * @author chenx
 * @create 2023-07-05 17:09
 */
public class DynamicProxyExample {
    public static void main(String[] args) {
        ExampleImplement ex = new ExampleImplement();
        ExampleProxy<ExampleInterface> proxy = new ExampleProxy<>(ex);
        ExampleInterface proxyInstance = (ExampleInterface) Proxy.newProxyInstance(ExampleImplement.class.getClassLoader(), new Class[]{ExampleInterface.class}, proxy);
        proxyInstance.say("123");
    }
}
