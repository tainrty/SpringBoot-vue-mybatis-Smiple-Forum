package com.example.sys.service.impl;

import com.example.sys.mapper.UserMapper;
import com.example.sys.pojo.User;
import com.example.sys.service.UserService;
import com.example.sys.utils.Md5Util;
import com.example.sys.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
//更新基本信息
    @Override
    public void update(User user) {
      user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
//更新头像
    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");
        String md5String = Md5Util.getMD5String(newPwd);
        userMapper.updatePwd(md5String,id);

    }
}
