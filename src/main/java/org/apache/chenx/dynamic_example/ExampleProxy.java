package org.apache.chenx.dynamic_example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chenx
 * @create 2023-07-05 17:12
 */
public class ExampleProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy pre");
        Object result = method.invoke(proxy, args);
        System.out.println("proxy post");
        return result;
    }
}
