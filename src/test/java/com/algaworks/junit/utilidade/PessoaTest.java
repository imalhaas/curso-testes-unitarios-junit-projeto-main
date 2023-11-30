package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    void assercaoAgrupada(){
        Pessoa pessoa = new Pessoa("Lucas", "Magalhaes");

       assertAll("Asserções de pessoa",
               ()-> assertEquals("Lucas", pessoa.getNome()),
               ()-> assertEquals("Magalhaes", pessoa.getSobrenome()));

    }

}