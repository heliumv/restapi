/*******************************************************************************
 * HELIUM V, Open Source ERP software for sustained success
 * at small and medium-sized enterprises.
 * Copyright (C) 2004 - 2014 HELIUM V IT-Solutions GmbH
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published 
 * by the Free Software Foundation, either version 3 of theLicense, or 
 * (at your option) any later version.
 * 
 * According to sec. 7 of the GNU Affero General Public License, version 3, 
 * the terms of the AGPL are supplemented with the following terms:
 * 
 * "HELIUM V" and "HELIUM 5" are registered trademarks of 
 * HELIUM V IT-Solutions GmbH. The licensing of the program under the 
 * AGPL does not imply a trademark license. Therefore any rights, title and
 * interest in our trademarks remain entirely with us. If you want to propagate
 * modified versions of the Program under the name "HELIUM V" or "HELIUM 5",
 * you may only do so if you have a written permission by HELIUM V IT-Solutions 
 * GmbH (to acquire a permission please contact HELIUM V IT-Solutions
 * at trademark@heliumv.com).
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contact: developers@heliumv.com
 ******************************************************************************/
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
