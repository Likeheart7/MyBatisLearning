/**
 *    Copyright 2009-2016 the original author or authors.
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
package org.apache.ibatis.reflection.wrapper;

import org.apache.ibatis.reflection.MetaObject;

/**
 * @author Clinton Begin
 * 对象包装器工厂的接口，默认实现为DefaultObjectWrapperFactory，但是该实现没有实现任何功能，调用该默认实现的getWrapperFor方法时会抛出异常
 * 用户可以通过配置文件的objectWrapperFactory节点注入新的ObjectWrapperFactory
 */
public interface ObjectWrapperFactory {

  boolean hasWrapperFor(Object object);

  ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}
