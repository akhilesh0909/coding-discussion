package com.ems.codingdiscussion.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
	
	@Pointcut("execution(* com.ems.codingdiscussion.controllers.*.*(..))")
	public void loggingPointCut() {}
	
	@Before("loggingPointCut()")
	public void before(JoinPoint joinPoint) {
		log.info("Before method invoked : "+ joinPoint.getSignature());
	}

}
