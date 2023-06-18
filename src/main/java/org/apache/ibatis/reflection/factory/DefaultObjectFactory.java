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

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.Reflector;

/**
 * @author Clinton Begin
 * 对象工厂ObjectFactory接口的默认实现，实现了ObjectFactory接口中的所有非默认方法
 * create方法用于创建对象实例，isCollection方法用于判断传入的类型是否是一个集合类
 *
 */
public class DefaultObjectFactory implements ObjectFactory, Serializable {

  private static final long serialVersionUID = -8855120656740914948L;

  @Override
  public <T> T create(Class<T> type) {
    return create(type, null, null);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    Class<?> classToCreate = resolveInterface(type);
    // we know types are assignable
    // 创建类型实例
    return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
  }

  /**
   * 创建类的实例的具体方法，私有方法，在调用create方法中使用
   * 具体通过反射找到对应参数的构造器，然后通过反射调用该构造方法生成实例
   * @param type 要创建实例的类
   * @param constructorArgTypes 构造方法入参类型
   * @param constructorArgs 构造方法入参
   * @param <T> 实例类型
   * @return 创建的实例
   */
  private  <T> T instantiateClass(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    try {
      // 构造方法
      Constructor<T> constructor;
//      参数类型列表和参数值列表有一个为null，就直接使用无参构造器
      if (constructorArgTypes == null || constructorArgs == null) { // 参数类型列表为null或者参数列表为null
        // 因此获取无参构造函数，getDeclaredConstructor方法在不传入参数时，返回的是无参构造器
        constructor = type.getDeclaredConstructor();
//        这一步进行异常捕获是防止访问的构造器的访问修饰符可能在本包内无法访问的
        try {
          // 使用无参构造函数创建对象
          return constructor.newInstance();
        } catch (IllegalAccessException e) {
          // 如果发生异常，则修改构造函数的访问属性后再次尝试
          if (Reflector.canControlMemberAccessible()) {
            constructor.setAccessible(true);
            return constructor.newInstance();
          } else {
//            如果还有异常直接抛出(可能是方法内部抛出的NoSuchMethodException？)
            throw e;
          }
        }
      }

      // 根据入参类型查找对应的构造器
//      此处toArray方法有警告因为在toArray方法内部，如果传入的数组的大小小于所需的长度，会自动转为调用该方法的集合的size
      constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
      try {
        // 采用有参构造函数创建实例
//        在找不到对应参数的构造方法时会抛出NoSuchMethodException
        return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
      } catch (IllegalAccessException e) {
        if (Reflector.canControlMemberAccessible()) {
          // 如果发生异常，则修改构造函数的访问属性后再次尝试
          constructor.setAccessible(true);
          return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } else {
          throw e;
        }
      }
    } catch (Exception e) {
      // 收集所有的参数类型
//      ofNullable判断传入的值是否为null，如果为null返回Optional.empty()，否则返回带有该参数的Optional实例
//      orElseGet方法没理解其目的
      String argTypes = Optional.ofNullable(constructorArgTypes).orElseGet(Collections::emptyList)
          .stream().map(Class::getSimpleName).collect(Collectors.joining(","));
      // 收集所有的参数
      String argValues = Optional.ofNullable(constructorArgs).orElseGet(Collections::emptyList)
          .stream().map(String::valueOf).collect(Collectors.joining(","));
//      如果在上述实例化过程中出现错误，就抛出ReflectionException，异常的具体描述信息包括在使用哪些参数列表和参数值实例化哪个类型时出现了异常
      throw new ReflectionException("Error instantiating " + type + " with invalid types (" + argTypes + ") or values (" + argValues + "). Cause: " + e, e);
    }
  }

/**
 * 判断要创建的目标对象的类型，即如果传入的是接口则给出它的一种实现
 * 方法逻辑非常简单，就是在传入List、Collection、Iterable接口时返回ArrayList实例，Map返回HashMap，
 * SortedSet返回TreeSet，Set返回HashSet，如果都不是，就直接返回传进来的参数
 */
  protected Class<?> resolveInterface(Class<?> type) {
    Class<?> classToCreate;
    if (type == List.class || type == Collection.class || type == Iterable.class) {
      classToCreate = ArrayList.class;
    } else if (type == Map.class) {
      classToCreate = HashMap.class;
    } else if (type == SortedSet.class) { // issue #510 Collections Support
      classToCreate = TreeSet.class;
    } else if (type == Set.class) {
      classToCreate = HashSet.class;
    } else {
      classToCreate = type;
    }
    return classToCreate;
  }

  @Override
  public <T> boolean isCollection(Class<T> type) {
    return Collection.class.isAssignableFrom(type);
  }

}
