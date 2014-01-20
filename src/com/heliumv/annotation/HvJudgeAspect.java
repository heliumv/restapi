package com.heliumv.annotation;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
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
public class HvJudgeAspect extends BaseAspect {

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
		

	    MethodSignature methodSig = getMethodSignatureFrom(pjp) ;
	    Method method = getMethodFrom(pjp) ;
		HvJudge theModul = (HvJudge) method.getAnnotation(HvJudge.class);
		if(theModul == null) return ;
		
		System.out.println("Having the HvJudge Annotation with name |'" + theModul.recht() + "'|" +
				StringUtils.join(theModul.rechtOder(), "|") + "<") ;
	
		if(theModul.recht().length() > 0) {
			if(!judgeCall.hatRecht(theModul.recht())) {
				throw new EJBExceptionLP(EJBExceptionLP.FEHLER_UNZUREICHENDE_RECHTE,
						methodSig.getMethod().getName() + ":" + theModul.recht() ) ;
			}

			System.out.println("judge allowed: '" + theModul.recht() + "'") ;
			return ;
		}
		
		if(theModul.rechtOder().length > 0) {
			for (String recht : theModul.rechtOder()) {
				if(judgeCall.hatRecht(recht)) {
					System.out.println("judge allowed: '" + recht + "'") ;
					return ;					
				}
			}

			throw new EJBExceptionLP(EJBExceptionLP.FEHLER_UNZUREICHENDE_RECHTE,
					methodSig.getMethod().getName() + ":" + theModul.rechtOder().toString() ) ;
		}
	}
}
