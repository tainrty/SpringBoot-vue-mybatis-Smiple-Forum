package com.example.sys.service.impl;

import com.example.sys.mapper.UserMapper;
import com.example.sys.pojo.User;
import com.example.sys.service.UserService;
import com.example.sys.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
      User user =  userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void reqister(String username, String password) {
        //密码加密
      String md5String = Md5Util.getMD5String(password);

      userMapper.add(username,md5String);


    }
}
