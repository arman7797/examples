package com.arman.proxy;

public class ProxyUtils {
    private static final ThreadLocal<Object> currentProxy = new ThreadLocal<>();

    public static Object currentProxy() {
        Object proxy = currentProxy.get();
        if (proxy == null) {
            throw new IllegalStateException("Proxy is not exposed");
        }
        return proxy;
    }

    public static void setCurrentProxy(Object proxy) {
        currentProxy.set(proxy);
    }
}
