package com.example.sys.controller;

import com.example.sys.pojo.Result;
import com.example.sys.pojo.User;
import com.example.sys.service.UserService;
import com.example.sys.utils.JwtUtil;
import com.example.sys.utils.Md5Util;
import com.example.sys.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated  // 激活请求参数校验,不然@Pattern失效
public class UserController {

    @Autowired
    private UserService userService;
    private User user;

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
    @GetMapping("/userInfo")
    public Result<User> userinfo(/*@RequestHeader(name = "Authorization")String tocken*/){
  //     Map<String,Object> map = JwtUtil.parseToken(tocken);
  //    String username= (String) map.get("username");
  //调用ThreadLocalUtil.get()获取拦截器传递过来的信息，提高代码复用性
        Map<String,Object> map = ThreadLocalUtil.get();
        String username= (String) map.get("username");
        User user = userService.findByUserName(username);

            // 已经被注册
            return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        this.user = user;
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("参数不能为空");
        }

        // 获取当前登录用户
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);

        if (!Md5Util.getMD5String(oldPwd).equals(loginUser.getPassword())) {
            return Result.error("原密码错误");
        }

        if (oldPwd.equals(newPwd)) {
            return Result.error("新密码不能与原密码一致");
        }

        if (!newPwd.equals(rePwd)) {
            return Result.error("新密码需与确认密码一致");
        }

        // 更新密码
        userService.updatePwd(newPwd);
        return Result.success();
    }
}
