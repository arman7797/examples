package com.arman.proxy;

import com.arman.impl.LoggerInterceptor;
import com.arman.proxy.async.AsyncInterceptor;
import com.arman.proxy.interceptor.InvocationInterceptor;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxyFactory {
    private final ProxyProperties props;

    public ProxyFactory() {
        this(ProxyProperties.defaultProperties());
    }

    public ProxyFactory(Class<?> configClass) {
        this(ProxyProperties.create(configClass));
    }

    public ProxyFactory(ProxyProperties props) {
        this.props = props;
    }

    @SuppressWarnings("unchecked")
    public <T> T interceptorProxy(Object target, InvocationInterceptor... invocationInterceptors) {
        var cl = Thread.currentThread().getContextClassLoader();
        return (T) Proxy.newProxyInstance(cl,
                target.getClass().getInterfaces(),
                new InterceptorSupportHandler(target, props.exposeProxy(), createInterceptorList(invocationInterceptors)));
    }

    private List<InvocationInterceptor> createInterceptorList(InvocationInterceptor[] invocationInterceptors) {
        List<InvocationInterceptor> interceptors = new ArrayList<>(invocationInterceptors.length + 1);
        interceptors.addAll(Arrays.asList(invocationInterceptors));
        if (props.asyncProcessingEnabled()) {
            interceptors.add(new AsyncInterceptor());
        }
        return interceptors;
    }

    public <T> T loggingProxy(Object target) {
        return interceptorProxy(target, new LoggerInterceptor());
    }
}
