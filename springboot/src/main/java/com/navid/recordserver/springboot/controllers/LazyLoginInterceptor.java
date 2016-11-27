package com.navid.recordserver.springboot.controllers;

import com.lazylogin.client.system.v0.SystemCommands;
import com.lazylogin.client.system.v0.UserInfo;
import com.navid.lazylogin.context.RequestContext;
import com.navid.lazylogin.context.RequestContextContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alberto on 25/11/16.
 */
@Component
public class LazyLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LazyLoginInterceptor.class);

    @Resource(name = "client")
    private SystemCommands systemCommands;

    @Autowired
    private RequestContextContainer contextContainer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lazyloginToken = request.getHeader("sessionId");
        String correlationId = request.getHeader("correlationId");

        RequestContext context = contextContainer.get();
        context.setSessionId(lazyloginToken);
        context.setCorrelationId(correlationId);

        if (lazyloginToken != null) {
            try {
                UserInfo userInfo = systemCommands.getUserInfo(lazyloginToken);
                context.setUserId(userInfo.getUserid());
                context.setUserName(userInfo.getUsername());
            } catch (Exception e) {
                logger.error("Error invoking lazylogin with sessionId {}: {}", lazyloginToken, e.getMessage());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        contextContainer.delete();
    }
}
