package com.heliumv.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heliumv.factory.IMandantCall;
import com.lp.util.EJBExceptionLP;

@Component
@Aspect
public class HvModulAspect {

	@Autowired
	private IMandantCall mandantCall ;

	@Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}
    
    @Pointcut("within(com.heliumv..*)")  
    private void inHeliumv() {}
    
	@Pointcut("@annotation(com.heliumv.annotation.HvModul)")
	public void processHvModulPointcut() {
	}
	
	@Before("anyPublicOperation() && inHeliumv() && @annotation(com.heliumv.annotation.HvModul)")
	public void processAttribute(JoinPoint pjp) throws Throwable {
//		Class clazz = pjp.getSignature().getDeclaringType() ;
//		List<HvJudge> attributesList = new ArrayList<HvJudge>() ;
//	
//		System.out.println("In declaring clazz " + clazz.getName() + " inspecting annotations...") ;
		
		MethodSignature methodSig = (MethodSignature) pjp.getSignature() ;
//		methodSig.getMethod().getAnnotations() ;
//		System.out.println("In method signature: " + methodSig.getMethod().getAnnotations().length + "<") ;

		HvModul theModul = (HvModul) methodSig.getMethod().getAnnotation(HvModul.class);
		if(theModul == null) return ;
		
		System.out.println("Having the HvModul Annotation with name '" + theModul.modul() +
				"<" + methodSig.getMethod().getName() + ":" + theModul.modul() + ">") ;
		if(!mandantCall.hasNamedModul(theModul.modul())) {
			throw new EJBExceptionLP(EJBExceptionLP.FEHLER_UNZUREICHENDE_RECHTE,
					methodSig.getMethod().getName() + ":" + theModul.modul() ) ;
		}
		System.out.println("modul allowed: '" + theModul.modul() + "'") ;
	}

// 	@Before("processHvModulPointcut()")
//	public void processAttribute(JoinPoint pjp) throws Throwable {
//		Class clazz = pjp.getSignature().getDeclaringType() ;
//		List<HvModul> attributesList = new ArrayList<HvModul>() ;
//		
////		System.out.println("In declaring clazz " + clazz.getName() + " inspecting annotations...") ;
//		for(Method method : clazz.getMethods()) {
//			HvModul theModul = (HvModul)method.getAnnotation(HvModul.class);
//			if(theModul != null){
//				attributesList.add(theModul);
//				System.out.println("Aspect method name:" + method.getName() + ".") ;
//			}			
//		}
//		
//		if(attributesList.size() > 0) {
//			System.out.println("Having '" + attributesList.size() + "' moduldefinitions (name='" + attributesList.get(0).modul() + "')") ;
//		}		
//	}
//
	
//	@Before("anyPublicMethod() && @annotation(com.heliumv.annotation.HvModul)")
//	public void checkIfModulAvailable(HvModul theModule) {
//		System.out.println("I have to authorize a method") ;
//	}
	
//	@Around("@annotation(aParameterName)")
//	public Object checkServiceStatus(ProceedingJoinPoint pjp, HvModul aParameterName) throws Throwable {
//		System.out.println("Around " + aParameterName.name()) ;
//		return pjp.proceed() ;
//	}
}
