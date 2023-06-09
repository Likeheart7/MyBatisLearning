/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.reflection.factory;

import java.util.List;
import java.util.Properties;

/**
 * MyBatis uses an ObjectFactory to create all needed new Objects.
 *
 * @author Clinton Begin
 * 对象工厂
 */
//ObjectFactory 是用来创建Mybatis使用过程中需要的新对象的
public interface ObjectFactory {

  /**
   * 设置工厂的属性，是一个默认方法，实现该接口的类可以不重写这个方法
   * Sets configuration properties.
   * @param properties configuration properties
   */
//  设置工厂的属性
  default void setProperties(Properties properties) {
    // NOP
  }

  /**
   * 接受一个类型参数，使用默认的无参构造器创建一个该参数类型的实例
   * Creates a new object with default constructor.
   * @param type Object type
   * @return
   */
//  传入一个类型， 采用无参构造的方式创建该类型的对象实例
  <T> T create(Class<T> type);

  /**
   * 根据接收的参数类型列表和参数值列表调用对应的有参构造方法生成该参数对象类型的实例
   * Creates a new object with the specified constructor and params.
   * @param type Object type
   * @param constructorArgTypes Constructor argument types
   * @param constructorArgs Constructor argument values
   * @return
   */
//  传入一个类型，一个参数列表，一个参数值列表，匹配对应的有参构造函数生成该类型的对象实例
  <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

  /**
   * 判断传入的类型是否是一个集合，主要用于非Java的java.util.Collection包下的集合类
   * Returns true if this object can have a set of other objects.
   * It's main purpose is to support non-java.util.Collection objects like Scala collections.
   *
   * @param type Object type
   * @return whether it is a collection or not
   * @since 3.1.0
   */
//  判断传入的类型是否为集合类
  <T> boolean isCollection(Class<T> type);

}
