package com.github.bohnman.squiggly.jackson.function.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.github.bohnman.core.range.CoreIntRange;
import com.github.bohnman.squiggly.core.function.security.SquigglyFunctionSecurity;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Function security that uses Jackson to determine if properties are accessible.
 */
public class JacksonFunctionSecurity implements SquigglyFunctionSecurity {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isPropertyViewable(Object key, Class type) {
        if (key == null) {
            return true;
        }

        if (type.isPrimitive()) {
            return true;
        }

        if (type.isArray()) {
            return true;
        }

        if (Iterable.class.isAssignableFrom(type)) {
            return true;
        }

        if (Boolean.class.isAssignableFrom(type)) {
            return true;
        }

        if (Character.class.isAssignableFrom(type)) {
            return true;
        }

        if (Function.class.isAssignableFrom(type)) {
            return true;
        }

        if (Predicate.class.isAssignableFrom(type)) {
            return true;
        }

        if (CoreIntRange.class.isAssignableFrom(type)) {
            return true;
        }

        if (Map.class.isAssignableFrom(type)) {
            return true;
        }

        if (Number.class.isAssignableFrom(type)) {
            return true;
        }

        if (String.class.isAssignableFrom(type)) {
            return true;
        }

        return objectMapper.getSerializationConfig().introspect(SimpleType.construct(type))
                .findProperties()
                .stream()
                .map(BeanPropertyDefinition::getName)
                .anyMatch(name -> name.equals(key));
    }
}
