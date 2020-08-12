package fun.zrbac.service.impl;

import fun.zrbac.dao.UserDao;
import fun.zrbac.entity.User;
import fun.zrbac.service.UserService;
import fun.zrbac.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
