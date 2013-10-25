package com.heliumv.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heliumv.calltrace.CallTracer;

/**
 * Created with IntelliJ IDEA.
 * User: gp
 * Date: 24.10.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class HvCallrateAspect extends BaseAspect {
    @Autowired
    private CallTracer callTracer ;

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}

    @Pointcut("within(com.heliumv..*)")
    private void inHeliumv() {}

    @Pointcut("@annotation(com.heliumv.annotation.HvCallrate)")
    public void processHvCallTracerPointcut() {
    }

//    @Before("anyPublicOperation() && inHeliumv() && @annotation(com.heliumv.annotation.HvCallTracer)")
    @Before("anyPublicOperation() && inHeliumv() && processHvCallTracerPointcut()")
    public void processAttribute(JoinPoint pjp) throws Throwable {
        MethodSignature methodSig = getMethodSignatureFrom(pjp) ;
        Method method = getMethodFrom(pjp) ;
        HvCallrate theModul = (HvCallrate) method.getAnnotation(HvCallrate.class);
        if(theModul == null) return ;

        System.out.println("Having the HvCallTrace Annotation with '" + theModul.maxCalls()
                + "' maxCalls and '" + theModul.durationMs() + "' duration<") ;
        callTracer.trace(theModul, methodSig.getMethod()) ;
    }
}
