package br.com.totvs.repository;

import br.com.totvs.db.SQLiteConnection;
import br.com.totvs.domain.Familia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FamiliaRepository {

    private final Connection connection;
    private final DespesaRepository despesaRepository;

    public FamiliaRepository() {
        this.connection = SQLiteConnection.getInstance().getConnection();
        this.despesaRepository = new DespesaRepository();
        this.createTable();
    }

    public FamiliaRepository(Connection connection) {
        this.connection = connection;
        this.despesaRepository = new DespesaRepository();
        this.createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS familias (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	nome text NOT NULL,\n"
                + "	salario real NOT NULL,\n"
                + " carteira real NOT NULL\n"
                + ");";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFamilia(String nome, double salario, double carteira) throws SQLException {
        String sql = "INSERT INTO familias(nome, salario, carteira) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.setObject(3, carteira);
            stmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Não foi possível inserir a familia.");
        }
    }

    public String deleteFamiliaById(int id) throws SQLException {
        String sql = "DELETE FROM familias WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getFamilia(id) == null){
                throw new SQLException("Não existe nenhuma familia com esse id.");
            }

            stmt.setObject(1, id);
            stmt.execute();

            if(despesaRepository.getDespesasByFamilia(id).size() > 0){
                despesaRepository.deleteDespesasByFamiliaId(id);
            }

            return "Familia deletada com sucesso.";

        } catch (SQLException e) {
            throw new SQLException("Não foi possível deletar a familia.");
        }
    }

    public String deleteAllFamilias() throws SQLException {
        String sql = "DELETE FROM familias";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getAllFamilias().size() == 0){
                throw new SQLException("Não existe nenhuma familia cadastrada.");
            }

            stmt.execute();

            if(despesaRepository.getAllDespesas().size() > 0){
                despesaRepository.deleteAllDespesas();
            }

        } catch (SQLException e) {
            throw new SQLException("Não foi possível deletar as familias.");
        }

        return "Todas as familias deletadas com sucesso.";
    }

    public void updateFamilia(int id, String nome, double salario, double carteira) throws SQLException {
        String sql = "UPDATE familias SET nome = ?, salario = ?, carteira = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.setObject(3, carteira);
            stmt.setObject(4, id);
            stmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamiliaNome(int id, String nome) throws SQLException {
        String sql = "UPDATE familias SET nome = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamiliaSalario(int id, double salario) throws SQLException {
        String sql = "UPDATE familias SET salario = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, salario);
            stmt.setObject(2, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamiliaCarteira(int id, double carteira) throws SQLException {
        String sql = "UPDATE familias SET carteira = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, carteira);
            stmt.setObject(2, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível atualizar a familia.");
        }
    }


    public Familia getFamilia(int id) throws SQLException {

        String sql = "SELECT * FROM familias WHERE id = ?";
        Familia familia = new Familia();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()){
                throw new SQLException("Não existe nenhuma familia com esse id.");
            }

            familia.setId(rs.getInt("id"));
            familia.setNome(rs.getString("nome"));
            familia.setSalario(rs.getDouble("salario"));
            familia.setCarteira(rs.getDouble("carteira"));
            familia.setDespesas(despesaRepository.getDespesasByFamilia(id));

        } catch (SQLException e) {
            throw new SQLException("Não foi possível selecionar a familia.");
        }

        return familia;

    }

    public List<Familia> getAllFamilias() throws SQLException {
        String sql = "SELECT * FROM familias";
        List<Familia> familias = new ArrayList<>();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Familia familia = new Familia();
                familia.setId(rs.getInt("id"));
                familia.setNome(rs.getString("nome"));
                familia.setSalario(rs.getDouble("salario"));
                familia.setCarteira(rs.getDouble("carteira"));
                familia.setDespesas(despesaRepository.getDespesasByFamilia(rs.getInt("id")));
                familias.add(familia);
            }

        } catch (SQLException e) {
            throw new SQLException("Não foi possível selecionar as familias.");
        }

        return familias;
    }

    public List<Familia> getAllFamiliasToPage(int page, int size) throws SQLException {
        String sql = "SELECT * FROM familias LIMIT ? OFFSET ?";
        List<Familia> familias = new ArrayList<>();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, size);
            stmt.setObject(2, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Familia familia = new Familia();
                familia.setId(rs.getInt("id"));
                familia.setNome(rs.getString("nome"));
                familia.setSalario(rs.getDouble("salario"));
                familia.setCarteira(rs.getDouble("carteira"));
                familia.setDespesas(despesaRepository.getDespesasByFamilia(rs.getInt("id")));
                familias.add(familia);
            }
            System.out.println("Familias selecionadas com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível selecionar as familias.");
        }

        return familias;
    }

    public List<Integer> getAllIDsFamilias() throws SQLException {
        String sql = "SELECT id FROM familias";
        List<Integer> ids = new ArrayList<>();

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                ids.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new SQLException("Não foi possível selecionar os IDs.");
        }

        return ids;
    }

    public Integer getLastIDFamilia() throws SQLException {
        String sql = "SELECT id FROM familias ORDER BY id DESC LIMIT 1";
        Integer id = null;

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                id = rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new SQLException("Não foi possível selecionar o ID.");
        }

        return id;
    }
}
