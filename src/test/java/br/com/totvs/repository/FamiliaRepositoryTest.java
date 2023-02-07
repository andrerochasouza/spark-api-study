package br.com.totvs.repository;

import br.com.totvs.db.SQLiteConnectionTest;
import br.com.totvs.domain.Familia;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import spark.utils.Assert;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class FamiliaRepositoryTest {

    private FamiliaRepository familiaRepository;
    private DespesaRepository despesaRepository;
    private SQLiteConnectionTest sqLiteConnectionTest;
    private File dbFileTarget = new File(System.getProperty("user.dir") + "/target/classes/dbtest.sqlite");

    @BeforeEach
    void setup() {

        sqLiteConnectionTest = new SQLiteConnectionTest();
        Connection conn = sqLiteConnectionTest.getConnection();

        familiaRepository = new FamiliaRepository(conn);
        despesaRepository = new DespesaRepository(conn);
    }

    @AfterEach
    void tearDown() throws SQLException {
        sqLiteConnectionTest.closeConnectionAndDeleteFile();
    }


    @Test
    @DisplayName("Adiciona uma familia")
    void adicionaUmaFamilia() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        Assertions.assertEquals(familia, familiaRepository.getFamilia(1));

    }

    @Test
    @DisplayName("Retorna um Erro ao deletar uma familia")
    void erroAoDeletarUmaFamilia() {

        int id = 0;

        Assertions.assertThrows(SQLException.class, () -> {
            familiaRepository.deleteFamiliaById(id);
        });
    }

    @Test
    @DisplayName("Verifica se a Familia existe")
    void verificaSeFamiliaExiste() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        int id = 1;
        familia = null;

        familia = familiaRepository.getFamilia(id);

        Assertions.assertNotNull(familia);
    }

    @Test
    @DisplayName("Verifica se a familia é deletado")
    void validaSeFamiliaEDeletado() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        int id = 1;

        String result = familiaRepository.deleteFamiliaById(id);

        Assertions.assertEquals("Familia deletada com sucesso.", result);
    }

    @Test
    @DisplayName("Adiciona uma familia e retorna um erro")
    void adicionaUmaFamiliaERetornaUmErro() {

        String nome = null;
        double salario = 1000;
        double carteira = 1000.00;

        Assertions.assertThrows(SQLException.class, () -> {
            familiaRepository.addFamilia(nome, salario, carteira);
        });

    }

    @Test
    @DisplayName("Erro ao tentar deletar uma familia, mas não existe nenhuma familia na tabela")
    void erroAoTentarDeletarUmaFamillia(){
        int id = 1;
        Assertions.assertThrows(SQLException.class, () -> {
            familiaRepository.deleteFamiliaById(id);
        });
    }

    @Test
    @DisplayName("Retorna uma lista de familias")
    void retornaUmaListaDeFamilias() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);
        List<Familia> familias = familiaRepository.getAllFamilias();
        List<Familia> familiasValida = new ArrayList<>();
        familiasValida.add(familia);

        Assertions.assertEquals(familiasValida, familias);
    }

    @Test
    @DisplayName("Deleta todas as familias")
    void deletaTodasAsFamilias() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        Assertions.assertEquals("Todas as familias deletadas com sucesso.", familiaRepository.deleteAllFamilias());
    }

    @Test
    @DisplayName("Atualiza uma familia")
    void atualizaUmaFamilia() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        int id = 1;
        String nome2 = "Familia Teste 2";
        double salario2 = 2000.00;
        double carteira2 = 2000.00;

        familiaRepository.addFamilia(nome, salario, carteira);

        // assert para confirmar que não deu erro/Exception

        Assertions.assertDoesNotThrow(() -> {
            familiaRepository.updateFamilia(id, nome2, salario2, carteira2);
        });

    }

    @Test
    @DisplayName("Pega uma familia pelo id")
    void pegaUmaFamiliaPeloId() throws SQLException{

        int id = 1;
        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(id);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        Assertions.assertEquals(familia, familiaRepository.getFamilia(id));

    }

    @Test
    @DisplayName("Pega lista de Ids de familias")
    void pegaListaDeIdsDeFamilias() throws SQLException{

        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(1);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        List<Integer> ids = familiaRepository.getAllIDsFamilias();
        List<Integer> idsValida = new ArrayList<>();
        idsValida.add(1);

        Assertions.assertEquals(idsValida, ids);
    }

    @Test
    @DisplayName("Pega o ultimo Id Inserido na tabela Familia")
    void pegaUltimoIdInseridoNaTabelaFamilia() throws SQLException{

        Integer id = 1;
        String nome = "Familia Teste";
        double salario = 1000.00;
        double carteira = 1000.00;
        Familia familia = new Familia();
        familia.setId(id);
        familia.setNome(nome);
        familia.setSalario(salario);
        familia.setCarteira(carteira);

        familiaRepository.addFamilia(nome, salario, carteira);

        Integer idTest = familiaRepository.getLastIDFamilia();

        Assertions.assertEquals(id, idTest);
    }


}
