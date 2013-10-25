package com.heliumv.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class BaseAspect {
	public MethodSignature getMethodSignatureFrom(JoinPoint pjp) throws Throwable {
		return (MethodSignature) pjp.getSignature() ;
	}
	
	public Method getMethodFrom(JoinPoint pjp) throws Throwable {
		MethodSignature methodSig = getMethodSignatureFrom(pjp) ;
		Method method = methodSig.getMethod() ;
        if(method.getDeclaringClass().isInterface()) {
        	String methodName = pjp.getSignature().getName();
        	method = pjp.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes()) ;
        }
	    
        return method ;
	}
}
