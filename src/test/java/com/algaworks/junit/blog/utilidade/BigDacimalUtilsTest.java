package com.algaworks.junit.blog.utilidade;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BigDacimalUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "10.00,10",
            "9.00,9.00"
    })
    void iguais(BigDecimal x, BigDecimal y){
        assertTrue(BigDacimalUtils.iguais(x, y));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/numeros.csv")
    void diferentes(BigDecimal x, BigDecimal y){
        assertFalse(BigDacimalUtils.iguais(x, y));
    }
}