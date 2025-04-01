package com.arman.proxy;

import com.arman.proxy.interceptor.InvocationInterceptor;
import com.arman.proxy.order.OrderedComparator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterceptorSupportHandler implements InvocationHandler {
    private final Object target;
    private final boolean exposeProxy;
    private final List<InvocationInterceptor> interceptors;

    public InterceptorSupportHandler(Object target, boolean exposeProxy, List<InvocationInterceptor> interceptors) {
        this.target = target;
        this.interceptors = orderedSort(interceptors);
        this.exposeProxy = exposeProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.isDefault()) {
            return InvocationHandler.invokeDefault(proxy, method, args);
        }

        var methodInvocation = createInvocation(method, args);
        exposeProxy(proxy);
        return methodInvocation.proceed();
    }

    private void exposeProxy(Object proxy) {
        if (exposeProxy) {
            ProxyUtils.setCurrentProxy(proxy);
        }
    }

    private MethodInvocation createInvocation(Method method, Object[] args) {
        return new MethodInvocation(
                target, args, method, interceptors
        );
    }

    private List<InvocationInterceptor> orderedSort(List<InvocationInterceptor> interceptors) {
        var list = new ArrayList<>(interceptors);
        list.sort(OrderedComparator.INSTANCE);
        return Collections.unmodifiableList(list);
    }
}
