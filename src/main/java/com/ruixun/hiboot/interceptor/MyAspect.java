package com.ruixun.hiboot.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Component
public class MyAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Pointcut("execution(* com.ruixun.hiboot.controller.Login.*(..))")
    public void myPointCut(){}

    @Before("myPointCut()")
    public String before(){
        String username = (String) httpServletRequest.getSession().getAttribute("username");
        System.out.println(username);
        if(username==null){
            return "login";
        }
        return null;
    }
}
