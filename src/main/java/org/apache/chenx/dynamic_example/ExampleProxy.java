package org.apache.chenx.dynamic_example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chenx
 * @create 2023-07-05 17:12
 */
public class ExampleProxy<T> implements InvocationHandler {
    T target;

    public ExampleProxy(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("proxy pre");
        Object result = method.invoke(target, args);
        System.out.println("proxy post");
        return result;
    }
}
