package com.mercury.authservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("execution (* com.mercury.authservice.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint point) {
        Object res = null;
        System.out.println(" ************** Before controller "+point.getSignature().getName());
        long start = System.currentTimeMillis();

        try {
            res = point.proceed(); // get the return of function
            // we can do something here to modify the res
        } catch (Throwable e) {
            e.printStackTrace();
        }
//        long end = System.currentTimeMillis();
//        System.out.println(" ************** After controller "+point.getSignature().getName()
//                +" Used time: " + (end - start));
        return res;
    }

    @Around("execution (* com.mercury.authservice.controller.*.*(..))")
    public Object logService(ProceedingJoinPoint point) {
        Object res = null;
        System.out.println(" ************** Before service "+point.getSignature().getName());
        long start = System.currentTimeMillis();

        try {
            res = point.proceed(); // get the return of function
            // we can do something here to modify the res
        } catch (Throwable e) {
            e.printStackTrace();
        }
//        long end = System.currentTimeMillis();
//        System.out.println(" ************** After service "+point.getSignature().getName()
//                +" Used time: " + (end - start));
        return res;
    }
}
