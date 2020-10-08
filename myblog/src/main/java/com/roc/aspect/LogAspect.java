package com.roc.aspect;

import com.roc.pojo.RequestLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志处理
 * 打印请求信息及返回值信息
 */
//Aspect用于切面操作，声明当前类为切面类,Component用于注解扫描
@Aspect
@Component
public class LogAspect {
    //日志类
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //切入点表达式，对哪些方法进行增强
    @Pointcut("execution(* com.roc.controller.*.*(..))")
    public void log(){
    }

    //前置通知
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取请求参数
        Object[] args = joinPoint.getArgs();
        //获取访问方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {}",requestLog);
    }

    //后置通知
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRun(Object result){
        logger.info("Result : {}" , result);
    }

}
