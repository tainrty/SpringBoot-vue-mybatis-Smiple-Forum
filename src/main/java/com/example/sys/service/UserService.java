package com.example.sys.service;

import com.example.sys.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
    //注册方法
    void reqister(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
