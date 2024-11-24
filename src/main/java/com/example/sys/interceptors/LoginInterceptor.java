package com.example.sys.interceptors;

import com.example.sys.pojo.Result;
import com.example.sys.utils.JwtUtil;
import com.example.sys.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
           String token= request.getHeader("Authorization");
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储进入ThreadLocal,不用再每个接口重复解析tocken,优雅!
            ThreadLocalUtil.set(claims);
            return true;//放行
        }catch (Exception e){
            e.printStackTrace();
            response.setStatus(401);
            return false;//拦截
        }
    }

//请求结束，清空hreadLocal线程,避免内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       ThreadLocalUtil.remove();
    }
}

