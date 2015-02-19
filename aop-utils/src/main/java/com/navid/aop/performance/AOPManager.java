package com.navid.aop.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class AOPManager {

    private static final Logger LOG = LoggerFactory.getLogger(AOPManager.class);

    @Pointcut("@within(javax.jws.WebService)")
    private void anyWebService() {
    }
    
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    private void anyController() {
    }
    
    @Pointcut("@within(org.springframework.stereotype.Repository)")
    private void anyRepository() {
    }
    
    @Pointcut("@within(org.springframework.stereotype.Service)")
    private void anyService() {
    }

    @Around("anyWebService() || anyController() || anyRepository() || anyService()")
    public Object logServiceAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.debug("{} {}", "STARTING", joinPoint.getSignature());
        StopWatch monitor = new StopWatch();
        monitor.start("monitor");
        boolean error = false;
        Object returned = null;
        try {
            returned = joinPoint.proceed();
            monitor.stop();
            return returned;
        } catch (Throwable e) {
            monitor.stop();
            error = true;
            throw e;
        } finally {
            LOG.info("{} {} {}", error ? "ERROR" : "SUCCESS", monitor.getTotalTimeSeconds(), joinPoint.getSignature());
        }
    }

}
