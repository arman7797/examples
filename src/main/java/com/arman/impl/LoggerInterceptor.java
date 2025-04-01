package com.arman.impl;

import com.arman.proxy.MethodInvocation;
import com.arman.proxy.interceptor.InvocationInterceptor;
import com.arman.proxy.order.Order;
import com.arman.util.StringUtil;

@Order(1)
public class LoggerInterceptor implements InvocationInterceptor {

    @Override
    public void after(MethodInvocation methodInvocation, Object result) {
        var method = methodInvocation.method();
        if (!method.getReturnType().equals(Void.TYPE)) {
            printResult(method.getName(), result);
        }
    }

    @Override
    public void before(MethodInvocation methodInvocation) {
        var method = methodInvocation.method();
        var args = methodInvocation.args();
        printArgs(method.getName(), args);
    }

    private void printResult(String name, Object result) {
        System.out.printf("Result of %s : %s%n%n", name, StringUtil.toString(result));
    }

    private void printArgs(String name, Object[] args) {
        System.out.printf("Executing %s : with params %s %n", name, StringUtil.toString(args));
    }
}
