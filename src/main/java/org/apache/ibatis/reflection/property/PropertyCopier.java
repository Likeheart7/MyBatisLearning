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
package org.apache.ibatis.reflection.property;

import java.lang.reflect.Field;

import org.apache.ibatis.reflection.Reflector;

/**
 * @author Clinton Begin
 * 属性拷贝器
 */
public final class PropertyCopier {

  private PropertyCopier() {
    // Prevent Instantiation of Static Class
  }

  /**
   * 完成对象的输出拷贝
   * @param type 对象的类型
   * @param sourceBean 提供属性值的对象
   * @param destinationBean 要被写入新属性值的对象
   */
  public static void copyBeanProperties(Class<?> type, Object sourceBean, Object destinationBean) {
    // 这两个对象同属的类
    Class<?> parent = type;
    while (parent != null) {
      // 获取该类的所有属性
      final Field[] fields = parent.getDeclaredFields();
      // 循环遍历属性进行拷贝
      for (Field field : fields) {
        try {
          try {
            field.set(destinationBean, field.get(sourceBean));
          } catch (IllegalAccessException e) {
            if (Reflector.canControlMemberAccessible()) {
              field.setAccessible(true);
              field.set(destinationBean, field.get(sourceBean));
            } else {
              throw e;
            }
          }
        } catch (Exception e) {
          // Nothing useful to do, will only fail on final fields, which will be ignored.
//          只有在给final修饰的成员变量拷贝值时才会来到这里，该类属性的值在拷贝时被忽略
//          其余各种异常进入这里都被忽略
        }
      }
//      当前类的属性拷贝完之后，将parent转为其父类，对其从父类继承的属性进行拷贝，直到父类为null(Object父类为null)
      parent = parent.getSuperclass();
    }
  }

  public static void main(String[] args) {
    System.out.println(String.class.getSuperclass());   //java.lang.Object
    System.out.println(Object.class.getSuperclass());   // null
  }
}
