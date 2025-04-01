package com.arman.context.definition;

import lombok.Getter;

import java.lang.reflect.Constructor;

@Getter
public class ComponentDefinition {
    private final String name;
    private final Class<?> type;
    private final Class<?>[] interfaces;

    public ComponentDefinition(String name, Class<?> target) {
        this.name = name;
        this.type = target;
        this.interfaces = target.getInterfaces();
    }


}
