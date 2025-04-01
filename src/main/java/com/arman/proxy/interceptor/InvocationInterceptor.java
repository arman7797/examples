package com.arman.proxy.interceptor;

import com.arman.proxy.MethodInvocation;

public interface InvocationInterceptor {

    default Object intercept(MethodInvocation methodInvocation) throws Throwable {
        before(methodInvocation);
        Object res = methodInvocation.proceed();
        after(methodInvocation, res);
        return res;
    }

    default void after(MethodInvocation methodInvocation, Object result) throws Throwable {

    }

    default void before(MethodInvocation methodInvocation) throws Throwable {
    }

}
