package org.apache.chenx.dynamic_example;

/**
 * @author chenx
 * @create 2023-07-05 17:10
 */
public class ExampleImplement implements ExampleInterface{
    @Override
    public void say(String name) {
        System.out.println("你好" + name);
    }
}
