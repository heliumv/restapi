package com.heliumv.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heliumv.factory.IJudgeCall;

@Component
@Aspect
public class HvJudgeAspect {

	@Autowired
	private IJudgeCall judgeCall ;
	
    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}
    
    @Pointcut("within(com.heliumv..*)")  
    private void inHeliumv() {}
    
	@Pointcut("@annotation(com.heliumv.annotation.HvJudge)")
	public void processHvJudgePointcut() {
	}
	
//	@Before("processHvJudgePointcut()")
	@Before("anyPublicOperation() && inHeliumv() && @annotation(com.heliumv.annotation.HvJudge)")
	public void processAttribute(JoinPoint pjp) throws Throwable {
		Class clazz = pjp.getSignature().getDeclaringType() ;
		List<HvJudge> attributesList = new ArrayList<HvJudge>() ;
	
		System.out.println("In declaring clazz " + clazz.getName() + " inspecting annotations...") ;
		
		MethodSignature methodSig = (MethodSignature) pjp.getSignature() ;
		methodSig.getMethod().getAnnotations() ;
		System.out.println("In method signature: " + methodSig.getMethod().getAnnotations().length + "<") ;

		HvJudge theOneModul = (HvJudge)methodSig.getMethod().getAnnotation(HvJudge.class);
		System.out.println("Having the HvJudge Annotation with name '" + theOneModul.name() + "<") ;
		
		for(Method method : clazz.getMethods()) {
			HvJudge theModul = (HvJudge)method.getAnnotation(HvJudge.class);
			if(theModul != null){
				attributesList.add(theModul);
				System.out.println("Aspect method name:" + method.getName() + ".") ;
			}			
		}
		
		if(attributesList.size() > 0) {
			System.out.println("Having '" + attributesList.size() + "' judgedefinitions (name='" + attributesList.get(0).name() + "')") ;
			
			boolean allowed = judgeCall.hatRecht(attributesList.get(0).name()) ;
			System.out.println("Judge call for '" +attributesList.get(0).name() + "' returns '" + allowed + "'" ) ;
		}		
	}
}
