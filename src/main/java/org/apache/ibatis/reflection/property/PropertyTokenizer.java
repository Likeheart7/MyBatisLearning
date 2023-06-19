/**
 *    Copyright 2009-2017 the original author or authors.
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

import java.util.Iterator;

/**
 * @author Clinton Begin
 * 属性标记器
 */

/**
 * 假设传入的为student[sId].name
 * 则各个属性得到以下结果
 *
 * 该属性标记器只能处理一级，即点后面的都作为children
 */
// 属于是一个属性分词器，将变量名、索引名、成员变量名都拆分出来
public class PropertyTokenizer implements Iterator<PropertyTokenizer> {

//  student[sId].name在经过本类处理后各个属性值如下：

  // student
  private String name;
  // student[sId]
  private final String indexedName;
  // sId
  private String index;
  // name
  private final String children;

  public PropertyTokenizer(String fullname) {
    int delim = fullname.indexOf('.');
//    有. 说明存在name和children
    if (delim > -1) {
      name = fullname.substring(0, delim);
      children = fullname.substring(delim + 1);
    } else {
//      没有. 说明没有children
      name = fullname;
      children = null;
    }
    indexedName = name;
    delim = name.indexOf('[');
    if (delim > -1) {
//      有[ 说明还要有index
      index = name.substring(delim + 1, name.length() - 1);
      name = name.substring(0, delim);
    }
  }

  public String getName() {
    return name;
  }

  public String getIndex() {
    return index;
  }

  public String getIndexedName() {
    return indexedName;
  }

  public String getChildren() {
    return children;
  }

  @Override
  public boolean hasNext() {
    return children != null;
  }

  @Override
  public PropertyTokenizer next() {
    return new PropertyTokenizer(children);
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
  }
}
