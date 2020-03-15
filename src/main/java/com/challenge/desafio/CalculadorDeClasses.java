package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.stream.Stream;

public class CalculadorDeClasses implements Calculavel {

    @Override
    public BigDecimal somar(Object object) {
        return calcula(object, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object object) {
        return calcula(object, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object object) {
        return calcula(object, Somar.class).subtract(calcula(object, Subtrair.class));
    }

    private BigDecimal calcula(Object object, Class anotacao)  {
        Field[] fields = object.getClass().getDeclaredFields();

        return Stream.of(fields)
                .filter(field -> field.isAnnotationPresent(anotacao) && field.getType().isAssignableFrom(BigDecimal.class))
                .map(field -> getBigDecimal(object, field))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private BigDecimal getBigDecimal(Object object, Field field) {
        try {
            field.setAccessible(true);
            return (BigDecimal) field.get(object);
        } catch (IllegalAccessException e) {
            return BigDecimal.ZERO;
        }
    }
}
