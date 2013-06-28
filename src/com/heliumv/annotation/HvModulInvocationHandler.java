package com.heliumv.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HvModulInvocationHandler implements InvocationHandler {

	private Object proxied = null ;
	
	public HvModulInvocationHandler(Object proxied) {
		this.proxied = proxied ;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Method m = proxied.getClass().getMethod(method.getName(), method.getParameterTypes());
		if (m.isAnnotationPresent(HvModul.class)) {
			System.out.println("\tIn the annotation processor of HvModul");
		}

		return method.invoke(proxied, args);
	}
}
