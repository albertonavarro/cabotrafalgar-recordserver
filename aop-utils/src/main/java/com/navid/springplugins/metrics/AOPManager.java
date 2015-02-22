package com.navid.springplugins.metrics;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.net.JMSPerformanceAppender;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;

@Aspect
public class AOPManager {

    private static final String metricsLogId = "com.navid.springplugins.metrics.aspect";

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AOPManager.class);
    private static final org.slf4j.Logger LOG_METRICS = LoggerFactory.getLogger(metricsLogId);

    @Value("${navid.plugins.metrics.enabled:false}")
    boolean enabled;

    @Value("${navid.plugins.metrics.mqurl:TO BE SET}")
    String messageQueueURL;

    @PostConstruct
    public void setUp() {
        LOG.info("Starting AOPManager");
        if(enabled) {
            LOG.info("JMSPerformanceAppender created: {}", createLoggerFor());
        }
    }

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
        LOG_METRICS.info("{} {}", "STARTING", joinPoint.getSignature());
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
            LOG_METRICS.info("{} {} {}", error ? "ERROR" : "SUCCESS", monitor.getTotalTimeSeconds(), joinPoint.getSignature());
        }
    }

    private Logger createLoggerFor() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%msg%n");
        ple.setContext(lc);
        ple.start();

        JMSPerformanceAppender jmsPerformanceAppender = new JMSPerformanceAppender();
        jmsPerformanceAppender.setContext(lc);
        jmsPerformanceAppender.setQueueBindingName("MetricsQueue");
        jmsPerformanceAppender.setQueueConnectionFactoryBindingName("ConnectionFactory");
        jmsPerformanceAppender.setProviderURL(messageQueueURL);
        jmsPerformanceAppender.setInitialContextFactoryName("org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        jmsPerformanceAppender.setName("autologger");
        jmsPerformanceAppender.start();

        Logger logger = lc.getLogger(metricsLogId);
        logger.addAppender(jmsPerformanceAppender);
        logger.setLevel(Level.INFO);
        logger.setAdditive(false);

        return logger;
    }

}
