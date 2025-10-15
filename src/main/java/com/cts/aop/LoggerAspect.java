package com.cts.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
	@Pointcut("execution(* com.cts.controller.*.*(..))")
	public void controllerMethods() {
	}
	
	@Pointcut("execution(* com.cts.service.*.*(..))")
	public void serviceMethods() {
	}
	
	private static final String ALL_LAYERS = "controllerMethods() || serviceMethods()";

	
	@Before(ALL_LAYERS)
	public void logMethodEntry(JoinPoint jp) {
		log.info("Before...");
	    log.info("➡️ ENTRY: {}() with arguments: {}", 
	        jp.getSignature().getName(),
	        jp.getArgs());
	}

	@AfterReturning(pointcut = ALL_LAYERS, returning = "result")
	public void logMethodExit(JoinPoint jp, Object result) {
	    String resultSnippet = (result != null && result.toString().length() > 200) 
	                          ? result.toString().substring(0, 200) + "..." 
	                          : String.valueOf(result);
	    log.info("Successfully Returning...");
	    log.info("⬅️ Existing method: {} with result: {}",
	        jp.getSignature().getName(),
	        resultSnippet);
	}

	@AfterThrowing(pointcut = ALL_LAYERS, throwing = "e")
	public void logMethodException(JoinPoint jp, Throwable exception) {
		log.info("OOPS!!! Throws Exception");
	    log.error("💥 Exception in method: {} with cause: {}", 
	        jp.getSignature().getName(),
	        exception.getMessage());
	}
	
}
