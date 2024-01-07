package com.msb.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Aspect
@Component
@Order(1)
public class DaoAspect2 {

    // 定义一个公共切点  切点表达式直接指向接口
    @Pointcut("execution(* com.msb.dao.*.add*(..))")
    public void addPointCut(){};


    @Before("addPointCut()")
    public void methodBefore(JoinPoint joinPoint){
        System.out.println("methodBefore2 invoked ... ... ");
    }

    /*
    * 切点方法无论是否出现异常,都会执行的方法  最终通知
    *
    *
    * */
    @After("addPointCut()")
    public void methodAfter(JoinPoint joinPoint){
        System.out.println("methodAfter2 invoked ... ... ");
    }

    /*
    * 返回通知:在切点方法返回结构之后增强的功能
    *  切点方法如果出现异常则不执行
    * */
    @AfterReturning( value = "addPointCut()",returning = "res")
    public void methodAfterReturning(JoinPoint joinPoint,Object res){
        System.out.println("methodAfterReturning2 invoked... ... ");
    }

    /*
    * 异常通知: 切点方法出现异常就执行,不出现异常就不执行
    * 可以接收切点方法抛出的异常对象
    * */
    @AfterThrowing(value = "addPointCut()",throwing = "ex")
    public void methodAfterThrowing(Exception ex){
        System.out.println("methodAfterThrowing2 invoked ... ... ");
    }


    /*环绕通知  切点方法之前和之后都进行功能的增强
    * 参数列表需要带上一个特殊的形参
    * ProceedingJoinPoint 代表我们的切点
    * 通过ProceedingJoinPoint手动控制切点方法执行的位置
    * 环绕通知的返回值必须是Object 在环绕通知中必须要将切点方法继续向上返回
    *
    * */
    @Around("addPointCut()")
    public Object methodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("methodAroundA2 invoked... ... ");
        Object res = proceedingJoinPoint.proceed();// 控制切点方法在这里执行
        System.out.println("methodAroundB2 invoked... ... ");
        return res;

    }

}
