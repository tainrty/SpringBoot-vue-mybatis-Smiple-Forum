package com.example.sys.controller;

import com.example.sys.pojo.Result;
import com.example.sys.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping ("list")
  public Result <String> list(){
//        try {
//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            return Result.success("验证通过，后续完善");
//        }catch (Exception e){
//            e.printStackTrace();
//            response.setStatus(401);
//            return Result.error("未登录，后续完善");
//        }
//    }
        return Result.error("登录，后续完善");
    }
}
