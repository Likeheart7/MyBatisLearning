package org.apache.chenx.example01;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> queryUserBySchoolName(String name, int age, @Param("abc")User user);
}
