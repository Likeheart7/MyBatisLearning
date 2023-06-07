package org.apache.chenx.example01;

import java.util.List;

public interface UserMapper {
    List<User> queryUserBySchoolName(User user);
}
