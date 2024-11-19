package com.example.sys.controller;

import com.example.sys.pojo.Result;
import com.example.sys.pojo.User;
import com.example.sys.service.UserService;
import com.example.sys.utils.JwtUtil;
import com.example.sys.utils.Md5Util;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated  // 激活请求参数校验,不然@Pattern失效
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result reqister(String username,@Valid @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 查询用户确认未被注册
        User reqisteruser = userService.findByUserName(username);
        if (reqisteruser == null) {
            // 未注册
            // 执行注册
            userService.reqister(username, password);
            return Result.success();
        } else {
            // 已经被注册
            return Result.error("用户名已被注册!");
        }
    }

    @PostMapping("/login")
    public Result <String> login(String username,@Valid @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户查询用户
        User loginuser = userService.findByUserName(username);
        if (loginuser == null) {
            return  Result.error("用户名错误");

        }else {
            //判断密码是否正确
            //注意密码加密
           if(Md5Util.getMD5String(password).equals(loginuser.getPassword())) {
               //生成并返回Tocken
               Map<String,Object> claims  = new HashMap<>();
               claims.put("id",loginuser.getId());
               claims.put("username",loginuser.getUsername());
               String tocken = JwtUtil.genToken(claims);
               return Result.success(tocken);
           }else {
               return Result.error("密码错误");
           }
        }
    }
}
