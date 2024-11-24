package com.example.sys.exception;

import com.example.sys.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 全局异常处理器
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();// 打印堆栈信息
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");//优雅
    }
}
