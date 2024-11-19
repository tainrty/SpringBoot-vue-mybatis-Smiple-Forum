package com.example.sys.controller;


import com.example.sys.pojo.Result;
import com.example.sys.pojo.User;
import com.example.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
        public Result reqister (String username, String password) {
        //查询用户确认未被注册
      User user =  userService.findByUserName(username);
      if(user == null) {
          //未注册
          //执行注册
          userService.reqister(username,password);
          return Result.success();
      }else {
          //已经被注册
          return Result.error("用户名已被注册!");
      }
    }

}
