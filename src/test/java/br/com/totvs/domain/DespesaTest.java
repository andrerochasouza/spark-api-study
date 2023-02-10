package br.com.totvs.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(org.junit.platform.runner.JUnitPlatform.class)
public class DespesaTest {

    private Despesa despesa;
    private Despesa despesaComParametros;

    @BeforeEach
    void setup() {
        despesa = new Despesa();
        despesaComParametros = new Despesa(1, "teste", 100.0, "01/01/2020", "01/01/2020", true, true, 1, Categoria.OUTROS);
    }

    @Test
    @DisplayName("Teste de criação de Despesa")
    void testDespesa() {
        Assertions.assertNotNull(despesa);
    }

    @Test
    @DisplayName("Teste de criação de Despesa com parâmetros")
    void testDespesaComParametros() {
        Assertions.assertEquals(despesaComParametros, new Despesa(1, "teste", 100.0, "01/01/2020", "01/01/2020", true, true, 1, Categoria.OUTROS));
    }

    @Test
    @DisplayName("Teste de pegar todos os parametros da Despesa")
    void testGetAll() {
        ArrayList<Object> parametros = new ArrayList<>();
        parametros.add(despesaComParametros.getId());
        parametros.add(despesaComParametros.getNome());
        parametros.add(despesaComParametros.getValor());
        parametros.add(despesaComParametros.getDataInicio());
        parametros.add(despesaComParametros.getDataFinal());
        parametros.add(despesaComParametros.isPago());
        parametros.add(despesaComParametros.isParcelado());
        parametros.add(despesaComParametros.getQtdParcelas());
        parametros.add(despesaComParametros.getTipoDespesa());
        Assertions.assertEquals(parametros.get(0), despesaComParametros.getId());
        Assertions.assertEquals(parametros.get(1), despesaComParametros.getNome());
        Assertions.assertEquals(parametros.get(2), despesaComParametros.getValor());
        Assertions.assertEquals(parametros.get(3), despesaComParametros.getDataInicio());
        Assertions.assertEquals(parametros.get(4), despesaComParametros.getDataFinal());
        Assertions.assertEquals(parametros.get(5), despesaComParametros.isPago());
        Assertions.assertEquals(parametros.get(6), despesaComParametros.isParcelado());
        Assertions.assertEquals(parametros.get(7), despesaComParametros.getQtdParcelas());
        Assertions.assertEquals(parametros.get(8), despesaComParametros.getTipoDespesa());
    }


}
