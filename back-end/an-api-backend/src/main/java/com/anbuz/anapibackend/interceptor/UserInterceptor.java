package com.anbuz.anapibackend.interceptor;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.constant.UserConstant;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import com.anbuz.anapicommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class UserInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //1、从Session中获取用户信息
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);

        //2、校验用户是否登录，如果登录则记录id，否则拒绝请求
        if (user == null) {
            log.info("当前请求未登录，已拒绝, 请求路径：{}", request.getRequestURI());
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }else{
            BaseContext.setCurrentUser(user);
            return true;
        }
    }
}
