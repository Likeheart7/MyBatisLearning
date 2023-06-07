package org.apache.chenx.example01;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DemoApplication {
    public static void main(String[] args) {
        // 第一阶段：MyBatis的初始化阶段
        String resource = "org/apache/chenx/example01/mybatis-config.xml";
        // 得到配置文件的输入流
        InputStream inputStream = null;
        try {
//            通过getResourceAsStream()将配置文件读取为流
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 得到SqlSessionFactory，用于获取SqlSession
//        内部就是通过解析mybatis-config.xml文件，返回一个Configuration类的实例，构建一个DefaultSqlSessionFacotry
        SqlSessionFactory sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);

        // 第二阶段：数据读写阶段
//        从SqlSessionFactory中获取SqlSession，DefaultSqlSessionFactory返回的是DefaultSqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 找到接口对应的实现，通过前面读取配置文件存入configuration中的对象信息通过代理创建对象，实现指定的接口。
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // 构造查询参数
            User userParam = new User();
            userParam.setSchoolName("Sunny School");
            // 调用接口展开数据库操作，已经是代理类来操作了，会进入代理类的invoke方法，在此处就是MapperProxy.invoke
            List<User> userList = userMapper.queryUserBySchoolName(userParam);
            // 打印查询结果
            for (User user : userList) {
                System.out.println("name : " + user.getName() + " ;  email : " + user.getEmail());
            }
        }
    }
}
