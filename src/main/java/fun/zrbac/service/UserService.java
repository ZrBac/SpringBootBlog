package fun.zrbac.service;

import fun.zrbac.entity.User;

public interface UserService {
    //核对用户名和密码
     User checkUser(String username, String password);
}
