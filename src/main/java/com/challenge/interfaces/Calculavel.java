package com.challenge.interfaces;

import java.math.BigDecimal;

public interface Calculavel<T> {
    BigDecimal somar(Object object);
    BigDecimal subtrair(Object object);
    BigDecimal totalizar(Object object);
}
