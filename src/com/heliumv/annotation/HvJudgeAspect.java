package com.heliumv.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heliumv.factory.IJudgeCall;
import com.lp.util.EJBExceptionLP;

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
//		Class clazz = pjp.getSignature().getDeclaringType() ;
//		List<HvJudge> attributesList = new ArrayList<HvJudge>() ;
//	
//		System.out.println("In declaring clazz " + clazz.getName() + " inspecting annotations...") ;
		
		MethodSignature methodSig = (MethodSignature) pjp.getSignature() ;
//		methodSig.getMethod().getAnnotations() ;
//		System.out.println("In method signature: " + methodSig.getMethod().getAnnotations().length + "<") ;

		HvJudge theModul = (HvJudge) methodSig.getMethod().getAnnotation(HvJudge.class);
		if(theModul == null) return ;
		
		System.out.println("Having the HvJudge Annotation with name '" + theModul.recht() + "<") ;
		if(!judgeCall.hatRecht(theModul.recht())) {
			throw new EJBExceptionLP(EJBExceptionLP.FEHLER_UNZUREICHENDE_RECHTE,
					methodSig.getMethod().getName() + ":" + theModul.recht() ) ;
		}
		System.out.println("judge allowed: '" + theModul.recht() + "'") ;
	}
}