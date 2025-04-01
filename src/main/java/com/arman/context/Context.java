package com.arman.context;

import com.arman.context.definition.ComponentDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {
    private final Map<String, ComponentDefinition> managedComponents = new ConcurrentHashMap<>();
}
