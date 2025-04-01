package com.arman.proxy.async;

import com.arman.proxy.MethodInvocation;
import com.arman.proxy.interceptor.InvocationInterceptor;
import com.arman.proxy.order.Order;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Order(Integer.MAX_VALUE)
public final class AsyncInterceptor implements InvocationInterceptor {

    private final ExecutorService executor = initializeExecutor();

    @Override
    public Object intercept(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.method();
        if (!isAsyncPresent(method)) {
            return methodInvocation.proceed();
        }

        Class<?> returnType = extractReturnType(method);

        Callable<?> callable = () -> {
            try {
                Object proceed = methodInvocation.proceed();
                if (proceed instanceof Future<?> f) {
                    return f.get();
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            return null;
        };

        return submit(callable, returnType);
    }

    private Object submit(Callable<?> callable, Class<?> returnType) {
        if (Future.class.isAssignableFrom(returnType)) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return callable.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executor);
        } else {
            executor.submit(callable);
            return null;
        }
    }

    private Class<?> extractReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType == Void.TYPE ||
                Future.class.isAssignableFrom(returnType)) {
            return returnType;
        }
        throw new IllegalArgumentException("@Async method must return Future or void");
    }

    private boolean isAsyncPresent(Method method) {
        return method.isAnnotationPresent(Async.class);
    }

    private ExecutorService initializeExecutor() {
        final ExecutorService executor;
        executor = Executors.newFixedThreadPool(3);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down executor");
            executor.shutdownNow();
        }));
        return executor;
    }
}
