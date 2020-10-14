package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	// Setup Logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// Setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {

	}

	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {

	}

	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {

	}

	@Pointcut("forControllerPackage()||forServicePackage()||forDaoPackage()")
	private void forAppFlow() {

	}

	// Add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		// Display Method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("======>> in @Before: calling method: " + theMethod);
		// Display Arguments

		// Get arguments
		Object[] argsObjects = theJoinPoint.getArgs();

		// Loop and Display Args
		for (Object tempArg : argsObjects) {
			myLogger.info("====>> argument: " + tempArg);
		}
	}

	// Add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

		// Display Method we are returning From
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("======>> in @AfterReturning: calling method: " + theMethod);

		// Display Data Returning
		myLogger.info("====>> result: " + theResult);
	}
}
