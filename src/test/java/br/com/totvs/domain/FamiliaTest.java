package br.com.totvs.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(org.junit.platform.runner.JUnitPlatform.class)
public class FamiliaTest {

    private Familia familia;
    private Familia familiaComParametros;

    @BeforeEach
    void setup() {
        familia = new Familia();
        familiaComParametros = new Familia(1, "Familia 1", 1000.0, 1000.0, new ArrayList<>());
    }

    @Test
    @DisplayName("Criando objeto Familia pelo Construtor completo")
    void testConstrutorCompleto() {
        Assertions.assertEquals(familiaComParametros, new Familia(1, "Familia 1", 1000.0, 1000.0, new ArrayList<>()));
    }

    @Test
    @DisplayName("Criando objeto Familia pelo Construtor vazio")
    void testConstrutorVazio() {
        Assertions.assertEquals(familia, new Familia());
    }

    @Test
    @DisplayName("Criando objeto Familia pelo Construtor vazio e setando os atributos")
    void testConstrutorVazioSetandoAtributos() {
        familia.setId(1);
        familia.setNome("Familia 1");
        familia.setSalario(1000.0);
        familia.setCarteira(1000.0);
        familia.setDespesas(new ArrayList<>());

        Assertions.assertEquals(familiaComParametros, familia);
    }
}
