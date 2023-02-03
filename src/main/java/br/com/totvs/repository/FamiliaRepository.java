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

    public void addFamilia(String nome, double salario, double carteira) {
        String sql = "INSERT INTO familias(nome, salario, carteira) VALUES(?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.setObject(3, carteira);
            stmt.execute();
            System.out.println("Familia inserida com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String deleteFamiliaById(int id) {
        String sql = "DELETE FROM familias WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getFamilia(id) == null){
                return "Não existe nenhuma familia com esse id.";
            }

            stmt.setObject(1, id);
            stmt.execute();
            despesaRepository.deleteDespesasByFamiliaId(id);
            return "Familia deletada com sucesso.";

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Não foi possível deletar a familia.";
        }
    }

    public String deleteAllFamilias() {
        String sql = "DELETE FROM familias";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getAllFamilias().size() == 0){
                return "Não existe nenhuma familia para ser deletada.";
            }

            stmt.execute();
            despesaRepository.deleteAllDespesas();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Não foi possível deletar as familias.";
        }

        return "Todas as familias deletadas com sucesso.";
    }

    public void updateFamilia(int id, String nome, double salario, double carteira) {
        String sql = "UPDATE familias SET nome = ?, salario = ?, SET carteira = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, nome);
            stmt.setObject(2, salario);
            stmt.setObject(3, carteira);
            stmt.setObject(4, id);
            stmt.execute();
            System.out.println("Familia atualizada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível atualizar a familia.");
        }
    }

    public void updateFamiliaNome(int id, String nome) {
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

    public void updateFamiliaSalario(int id, double salario) {
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

    public void updateFamiliaCarteira(int id, double carteira) {
        String sql = "UPDATE familias SET carteira = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, carteira);
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

            if(!rs.next()){
                return null;
            }

            familia.setId(rs.getInt("id"));
            familia.setNome(rs.getString("nome"));
            familia.setSalario(rs.getDouble("salario"));
            familia.setCarteira(rs.getDouble("carteira"));
            familia.setDespesas(despesaRepository.getDespesasByFamilia(id));
            System.out.println("Familia selecionada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível selecionar a familia.");
        }

        return familia;

    }

    public List<Familia> getAllFamilias(){
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
            System.out.println("Familias selecionadas com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível selecionar as familias.");
        }

        return familias;
    }

    public List<Familia> getAllFamiliasToPage(int page, int size){
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
            System.out.println(e.getMessage());
            System.out.println("Não foi possível selecionar as familias.");
        }

        return familias;
    }
}
