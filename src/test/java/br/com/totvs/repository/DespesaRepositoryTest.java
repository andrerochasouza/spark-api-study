package br.com.totvs.repository;

import br.com.totvs.Mock.SQLiteConnectionTest;
import br.com.totvs.domain.Categoria;
import br.com.totvs.domain.Despesa;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class DespesaRepositoryTest {

    private DespesaRepository despesaRepository;
    private FamiliaRepository familiaRepository;
    private SQLiteConnectionTest sqLiteConnectionTest;
    private File dbFileTarget = new File(System.getProperty("user.dir") + "/target/test-classes/dbtest.sqlite");

    private static Despesa getNewDespesa(){
        Despesa despesa = new Despesa();
        despesa.setId(1);
        despesa.setNome("Despesa 1");
        despesa.setValor(100.00);
        despesa.setTipoDespesa(Categoria.ALIMENTACAO);

        return despesa;
    }

    @BeforeEach
    void setup() throws SQLException {

        sqLiteConnectionTest = new SQLiteConnectionTest();
        Connection conn = sqLiteConnectionTest.getConnection();

        familiaRepository = new FamiliaRepository(conn);
        despesaRepository = new DespesaRepository(conn);

        familiaRepository.addFamilia("Familia 1", 1000.0, 1000.0);

    }

    @AfterEach
    void tearDown() throws SQLException {
        sqLiteConnectionTest.closeConnectionAndDeleteFile(dbFileTarget);
    }


    @Test
    @DisplayName("Adicionar uma nova Despesa")
    void testAdicionarDespesa() {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        Assertions.assertDoesNotThrow(() -> despesaRepository.addDespesa(despesa, idFamilia));

    }

    @Test
    @DisplayName("Erro ao adicionar uma nova Despesa")
    void testAdicionarDespesaErro() {

        Despesa despesa = getNewDespesa();
        despesa.setDataInicio(null);
        int idFamilia = 1;

        Assertions.assertThrows(SQLException.class, () -> despesaRepository.addDespesa(despesa, idFamilia));
    }

    @Test
    @DisplayName("Deletar uma despesa pelo ID")
    void testDeletarDespesa() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        Assertions.assertDoesNotThrow(() -> despesaRepository.deleteDespesaById(1));
    }

    @Test
    @DisplayName("Erro ao tentar deletar uma despesa inexistente")
    void testDeletarDespesaInexistente() {
        Assertions.assertThrows(SQLException.class, () -> despesaRepository.deleteDespesaById(2));
    }

    @Test
    @DisplayName("Deletar todas as despesas")
    void testDeletarTodasDespesas() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        Assertions.assertDoesNotThrow(() -> despesaRepository.deleteAllDespesas());
    }

    @Test
    @DisplayName("Erro ao tentar deletar todas as despesas")
    void testDeletarTodasDespesasErro() {
        Assertions.assertThrows(SQLException.class, () -> despesaRepository.deleteAllDespesas());
    }

    @Test
    @DisplayName("Deletar todas as despesas pelo ID da Familia")
    void testDeletarTodasDespesasPorIdFamilia() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        Assertions.assertDoesNotThrow(() -> despesaRepository.deleteDespesasByFamiliaId(idFamilia));
    }

    @Test
    @DisplayName("Erro ao tentar deletar todas as despesas pelo ID da Familia")
    void testDeletarTodasDespesasPorIdFamiliaErro() {
        Assertions.assertThrows(SQLException.class, () -> despesaRepository.deleteDespesasByFamiliaId(1));
    }

    @Test
    @DisplayName("Atualizar uma despesa")
    void testAtualizarDespesa() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        despesa.setNome("Luz");
        despesa.setValor(200.00);
        despesa.setTipoDespesa(Categoria.LUZ);

        Assertions.assertDoesNotThrow(() -> despesaRepository.updateDespesa(despesa, idFamilia));
    }

    @Test
    @DisplayName("Erro ao tentar atualizar uma despesa")
    void testAtualizarDespesaErro() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        despesa.setNome("Luz");
        despesa.setValor(200.00);
        despesa.setTipoDespesa(Categoria.LUZ);
        despesa.setDataInicio(null);

        Assertions.assertThrows(SQLException.class, () -> despesaRepository.updateDespesa(despesa, idFamilia));
    }

    @Test
    @DisplayName("Lista de despesas por ID da Familia")
    void testListarDespesasPorIdFamilia() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        Assertions.assertDoesNotThrow(() -> despesaRepository.getDespesasByFamilia(idFamilia));
    }

    @Test
    @DisplayName("Listar vazio se não tiver despesas pelo ID da Familia")
    void testListarDespesasPorIdFamiliaErro() throws SQLException {
        Assertions.assertEquals(new ArrayList<Despesa>(), despesaRepository.getDespesasByFamilia(1));
    }

    @Test
    @DisplayName("Pegar uma despesa pelo ID")
    void testPegarDespesaPorId() throws SQLException {

        Despesa despesa = getNewDespesa();
        int idFamilia = 1;

        despesaRepository.addDespesa(despesa, idFamilia);

        Assertions.assertEquals(despesa ,despesaRepository.getDespesa(1));
    }

    @Test
    @DisplayName("Erro ao tentar pegar uma despesa pelo ID")
    void testPegarDespesaPorIdErro() throws SQLException {
        Assertions.assertThrows(SQLException.class, () -> despesaRepository.getDespesa(1));
    }
}
