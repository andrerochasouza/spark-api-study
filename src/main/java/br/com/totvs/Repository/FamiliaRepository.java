package br.com.totvs.Repository;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Domain.Familia;

import java.sql.*;

public class FamiliaRepository {

    private final Connection connection;
    private final DespesaRepository despesaRepository;

    public FamiliaRepository() {
        this.connection = SQLiteConnection.getInstance().getConnection();
        this.despesaRepository = new DespesaRepository();
        this.createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS familias (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	nome text NOT NULL,\n"
                + "	salario real NOT NULL\n"
                + ");";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFamilia(String nome, double salario) {
        String sql = "INSERT INTO familias(nome, salario) VALUES(?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.execute();
            System.out.println("Familia inserida com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFamiliaById(int id) {
        String sql = "DELETE FROM familias WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, id);
            stmt.execute();
            despesaRepository.deleteDespesasByFamiliaId(id);
            System.out.println("Familia deletada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível deletar a familia.");
        }
    }

    public void deleteAllFamilias() {
        String sql = "DELETE FROM familias";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.execute();
            despesaRepository.deleteAllDespesas();
            System.out.println("Todas as familias deletadas com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível deletar as familias.");
        }
    }

    public void updateFamilia(int id, String nome, double salario) {
        String sql = "UPDATE familias SET nome = ?, salario = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.setObject(3, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamilia(int id, String nome) {
        String sql = "UPDATE familias SET nome = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamilia(int id, double salario) {
        String sql = "UPDATE familias SET salario = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, salario);
            stmt.setObject(2, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível atualizar a familia.");
        }
    }


    public Familia getFamilia(int id) {
        String sql = "SELECT * FROM familias WHERE id = ?";
        Familia familia = new Familia();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            familia.setId(rs.getInt("id"));
            familia.setNome(rs.getString("nome"));
            familia.setSalario(rs.getDouble("salario"));
            familia.setDespesas(despesaRepository.getDespesasByFamilia(id));
            System.out.println("Familia selecionada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível selecionar a familia.");
        }

        return familia;

    }
}
