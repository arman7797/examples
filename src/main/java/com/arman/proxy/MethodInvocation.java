package com.arman.proxy;

import com.arman.proxy.interceptor.InvocationInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public final class MethodInvocation {
    private final Object target;
    private final Object[] args;
    private final Method method;
    private final List<InvocationInterceptor> interceptorList;
    private int interceptorIndex = 0;

    public MethodInvocation(Object target, Object[] args, Method method, List<InvocationInterceptor> interceptorList) {
        validate(target, method);
        this.interceptorList = interceptorList;
        this.target = target;
        this.args = args;
        this.method = method;
    }

    public Object proceed() throws Throwable {
        if (interceptorIndex >= interceptorList.size()) {
            return invokeTargetMethod();
        }

        return interceptorList.get(interceptorIndex++).intercept(this);
    }

    private Object invokeTargetMethod() throws Throwable {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private void validate(Object target, Method method) {
        Objects.requireNonNull(target, "target must not be null");
        if (!method.canAccess(target)) {
            throw new IllegalArgumentException("Method " + method.getName() + " is not accessible for target " + target);
        }
    }

    public Object[] args() {
        return args;
    }

    public Method method() {
        return method;
    }

}
