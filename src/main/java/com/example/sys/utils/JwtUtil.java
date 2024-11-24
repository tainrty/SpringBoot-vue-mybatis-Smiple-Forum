package com.example.sys.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "202210801082";  // 定义私钥，用于加密 JWT，必须保密

    // 接收业务数据，生成 token 并返回
    public static String genToken(Map<String, Object> claims) {

        return JWT.create()
                .withClaim("claims", claims)  // 将业务数据作为 claims 存储在 token 中
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))  // 设置 token 的过期时间为当前时间 + 12 小时
                .sign(Algorithm.HMAC256(KEY));  // 使用 HMAC256 算法和私钥进行签名
    }

    // 接收 token，验证 token 并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        // 使用 HMAC256 算法和私钥验证 token 的签名，验证成功后获取 claims
        return JWT.require(Algorithm.HMAC256(KEY))  // 创建一个验证 JWT 的构造器，指定签名算法
                .build()  // 创建验证器
                .verify(token)  // 验证 token 的有效性
                .getClaim("claims")  // 获取 "claims" 负载数据
                .asMap();  // 将获取到的 claims 转换为 Map 格式，返回业务数据
    }

}
