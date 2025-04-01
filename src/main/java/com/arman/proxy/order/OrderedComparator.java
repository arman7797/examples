package com.arman.proxy.order;

import java.util.Comparator;

public class OrderedComparator implements Comparator<Object> {

    public static final Comparator<Object> INSTANCE = new OrderedComparator();

    @Override
    public int compare(Object o1, Object o2) {
        return Comparator.<Integer>naturalOrder().compare(getOrder(o1), getOrder(o2));
    }

    private Integer getOrder(Object o1) {
        if (o1 instanceof OrderAware oa) {
            return oa.getOrder();
        }

        if (o1.getClass().isAnnotationPresent(Order.class)) {
            Order order = o1.getClass().getAnnotation(Order.class);
            return order.value();
        }

        return Integer.MIN_VALUE;
    }
}
