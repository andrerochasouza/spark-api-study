package br.com.totvs.repository;

import br.com.totvs.db.SQLiteConnectionTest;
import br.com.totvs.domain.Familia;
import org.junit.jupiter.api.*;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class FamiliaRepositoryTest {

    private static FamiliaRepository familiaRepository;
    private static DespesaRepository despesaRepository;

    @BeforeAll
    static void setup() {

        Connection conn = SQLiteConnectionTest.getInstance().getConnection();

        familiaRepository = new FamiliaRepository(conn);
        despesaRepository = new DespesaRepository(conn);
    }

    @AfterAll
    static void tearDown() {
        URL dbFileUrl = FamiliaRepositoryTest.class.getClassLoader().getResource("dbtest.sqlite");
        File dbFile = new File(dbFileUrl.getFile());
        dbFile.delete();
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

        int id = 2;

        Assertions.assertThrows(SQLException.class, () -> {
            familiaRepository.deleteFamiliaById(id);
        });
    }

    @Test
    @DisplayName("Verifica se a Familia existe")
    void verificaSeFamiliaExiste() throws SQLException{

        int id = 1;
        Familia familia = null;

        familia = familiaRepository.getFamilia(id);

        Assertions.assertFalse(familia == null);
    }

    @Test
    @DisplayName("Verifica se a familia e deletado")
    void validaSeFamiliaEDeletado() throws SQLException{

        int id = 1;

        familiaRepository.deleteFamiliaById(id);

        Assertions.assertThrows(SQLException.class, () -> {
            familiaRepository.getFamilia(id);
        });
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

}
