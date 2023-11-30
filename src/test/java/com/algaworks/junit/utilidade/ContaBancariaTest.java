package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {


    @Test
    void saque() {
        ContaBancaria conta = new ContaBancaria(new BigDecimal("70.00"));
        conta.saque(new BigDecimal("50.01"));
        assertEquals(new BigDecimal("19.99"), conta.saldo());
    }

    @Test
    void saqueComValorZeradoFalha(){
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, ()-> conta.saque(new BigDecimal("-10")));
    }

    @Test
    void saqueComValorNegativoFalha(){
        ContaBancaria conta = new ContaBancaria(BigDecimal.TEN);
        assertThrows(IllegalArgumentException.class, ()-> conta.saque(new BigDecimal("-10.0")));
    }



}