package com.arman.proxy;

import com.arman.proxy.annotation.ProxyConfig;

public record ProxyProperties(boolean exposeProxy, boolean asyncProcessingEnabled) {

    public static ProxyProperties create(Class<?> annotatedClass) {
        ProxyConfig annotation = annotatedClass.getAnnotation(ProxyConfig.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Class " + annotatedClass.getName() + " is not annotated with @ProxyConfig");
        }
        return new ProxyProperties(annotation.exposeProxy(), annotation.asyncProcessingEnabled());
    }
}
