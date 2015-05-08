package com.heliumv.factory.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.naming.NamingException;

public class LagerCallProxy implements InvocationHandler {

	private Object obj ;

	public static Object newInstance(Object obj) {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), 
				obj.getClass().getInterfaces(), 
				new LagerCallProxy(obj)) ;		
	}
	
	private LagerCallProxy(Object obj) {
		this.obj = obj ;
	}

	@Override
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
        Object result;
        try {
            System.out.println("before method " + m.getName());
            result = m.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
        	if(e instanceof NamingException) {
        		System.out.println("NamingException " + e.getMessage()) ;
        	}
            throw new RuntimeException("unexpected invocation exception: " +
                                       e.getMessage());
        } finally {
            System.out.println("after method " + m.getName());
        }
        return result;
	}
}
