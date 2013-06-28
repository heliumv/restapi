package com.heliumv.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HvModulAspect {

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {}
    
    @Pointcut("within(com.heliumv..*)")  
    private void inHeliumv() {}
    
	@Pointcut("@annotation(com.heliumv.annotation.HvModul)")
	public void processHvModulPointcut() {
	}
	
	@Before("processHvModulPointcut()")
	public void processAttribute(JoinPoint pjp) throws Throwable {
		Class clazz = pjp.getSignature().getDeclaringType() ;
		List<HvModul> attributesList = new ArrayList<HvModul>() ;
		
//		System.out.println("In declaring clazz " + clazz.getName() + " inspecting annotations...") ;
		for(Method method : clazz.getMethods()) {
			HvModul theModul = (HvModul)method.getAnnotation(HvModul.class);
			if(theModul != null){
				attributesList.add(theModul);
				System.out.println("Aspect method name:" + method.getName() + ".") ;
			}			
		}
		
		if(attributesList.size() > 0) {
			System.out.println("Having '" + attributesList.size() + "' moduldefinitions (name='" + attributesList.get(0).name() + "')") ;
		}		
	}

	
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
