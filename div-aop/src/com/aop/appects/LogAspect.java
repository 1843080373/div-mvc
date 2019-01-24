package com.aop.appects;

import com.aop.anno.After;
import com.aop.anno.AfterThrowing;
import com.aop.anno.Around;
import com.aop.anno.Aspect;
import com.aop.anno.Before;
import com.aop.anno.Component;
import com.aop.anno.Pointcut;

@Component
@Aspect
public class LogAspect {
    @Pointcut("com.aop.service.*")
    public void pointcutName(){}

    @Before("pointcutName()")
    public void before(){
        System.out.println("before");
    }
    
    @After("pointcutName()")
    public void after(){
        System.out.println("after");
    }
    
    @Around("pointcutName()")
    public void around(){
        System.out.println("around");
    }
    
    @AfterThrowing("pointcutName()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }
}