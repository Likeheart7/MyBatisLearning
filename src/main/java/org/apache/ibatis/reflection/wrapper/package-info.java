/**
 *    Copyright 2009-2015 the original author or authors.
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
/**
 * Object wrappers.
 * 使用装饰器模式对各种类型的对象(包括Bean对象、集合对象、Map对象)进行封装，为其增加一些功能，便于使用。
 * 包括抽象类BaseWrapper和其三个子类BeanWrapper, CollectionWrapper，MapWrapper；以及对象包装器工厂。
 *
 */
package org.apache.ibatis.reflection.wrapper;
