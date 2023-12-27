package com.algaworks.junit.utilidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Executable;

import static com.algaworks.junit.utilidade.SaudacaoUtil.*;
import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("teste no utilitário de saudação")
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com Bom dia")
    public void saudarComBomDia() {
        // Arrange
        int horaValida = 9;

        //Act
        String saudacao = saudar(horaValida);

        //Assert
        assertEquals("Bom dia", saudacao);
    }

    @Test
    @DisplayName("Deve saudar com bom dia as 5h")
    public void saudarComBomDiaApartir5h(){
        int horaValida = 5;
        String saudacao = saudar(horaValida);
        assertEquals("Bom dia", saudacao);
    }

    @Test
    public void saudarComBoaTarde(){
        int horaValida = 14;
        String saudacao = saudar(horaValida);
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    public void saudarComBoaNoite(){
        int horaValida = 20;
        String saudacao = saudar(horaValida);
        assertEquals("Boa noite", saudacao);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    public void Dado_horario_matinal_Entao_deve_retornar_bom_dia(int hora){
        String saudacao = SaudacaoUtil.saudar(hora);
        assertEquals("Bom dia", saudacao);
    }


   /* @Test
    public void deveLancarException(){

        int horaInvalida = -10;

        Executable chamadaMetodoInvalida = () -> saudar(horaInvalida);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, chamadaMetodoInvalida);
        assertEquals("Hora inválida", e.getMessage());
    }
*/


}