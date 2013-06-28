package com.heliumv.annotation;

import java.lang.reflect.Proxy;

public class HvModulProxy {
	public static Object getNewProxy(Object proxied, Class<?> theInterface) {
		Object proxy = Proxy.newProxyInstance(HvModulInvocationHandler.class.getClassLoader(), 
				new Class[] { theInterface}, new HvModulInvocationHandler(proxied)) ;
		return proxy ;
	}
}
