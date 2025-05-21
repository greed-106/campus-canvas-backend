package com.ymj.campuscanvas.interceptor;

import com.ymj.campuscanvas.exception.NotLoggedInException;
import com.ymj.campuscanvas.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component  //成为一个bean,交给IOC容器管理
@Slf4j      //输出日志
public class LoginCheckInterceptor implements HandlerInterceptor {

    // 在目标方法运行前运行
    // 返回true：放行
    // 返回false：不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")){
//            log.info("请求方法为OPTIONS，放行并返回200");
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        // 输出请求信息（方法 + 路径）
        log.info("请求方法：{}，请求路径：{}", request.getMethod(), request.getRequestURI());

        String token = request.getHeader("Authorization");

        // 判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if(!StringUtils.hasLength(token)){
            log.info("请求头Authorization为空，返回未登录信息");
            //使用全局异常处理器机制处理抛出的异常
            throw new NotLoggedInException("User not logged in");
        }
//        log.info("token: {}", token);

        // 开发使用
        if(token.equals("pku")){
//            log.info("看到pku✌直接跪了");
            return true;
        }

        // 解析JWT令牌，判断是否合法
        // 如果解析失败，返回错误结果（未登录）
        try{
            Long userId = JwtUtil.getUserIdFromToken(token);
//            log.info("解析令牌成功，用户ID为: {}", userId);
        }catch(Exception e){
            log.info("令牌解析失败：{}", e.getMessage());
            throw new NotLoggedInException("User not logged in");
        }
//        log.info("令牌解析成功，放行");
        //令牌验证通过，放行
        return true;
    }

}
