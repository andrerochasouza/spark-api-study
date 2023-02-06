package br.com.totvs.repository;

import br.com.totvs.db.SQLiteConnection;
import br.com.totvs.domain.Categoria;
import br.com.totvs.domain.Despesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    private Connection connection;

    public DespesaRepository() {
        this.connection = SQLiteConnection.getInstance().getConnection();
        this.createTable();
    }

    public DespesaRepository(Connection connection) {
        this.connection = connection;
        this.createTable();
    }


    private void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS despesas (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	nome text NOT NULL,\n"
                + "	valor real NOT NULL,\n"
                + "	dataInicio text NOT NULL,\n"
                + "	dataFinal text NOT NULL,\n"
                + "	isPago boolean,\n"
                + "	isParcelado boolean,\n"
                + "	qtdParcelas integer,\n"
                + "	tipoDespesa text NOT NULL,\n"
                + "	idFamilia integer NOT NULL,\n"
                + "	FOREIGN KEY(idFamilia) REFERENCES familias(id)\n"
                + ");";

        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addDespesa(Despesa despesa, int idFamilia) throws SQLException {
        String sql = "INSERT INTO despesas(nome, valor, dataInicio, dataFinal, isPago, isParcelado, qtdParcelas, tipoDespesa, idFamilia) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, despesa.getNome());
            stmt.setObject(2, despesa.getValor());
            stmt.setObject(3, despesa.getDataInicio());
            stmt.setObject(4, despesa.getDataFinal());
            stmt.setObject(5, despesa.isPago());
            stmt.setObject(6, despesa.isParcelado());
            stmt.setObject(7, despesa.getQtdParcelas());
            stmt.setObject(8, despesa.getTipoDespesa().toString());
            stmt.setObject(9, idFamilia);
            stmt.execute();
            System.out.println("Despesa inserida com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível inserir a despesa.");
        }
    }

    public String deleteDespesaById(int idDespesa) throws SQLException {
        String sql = "DELETE FROM despesas WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getDespesa(idDespesa) == null){
                throw new SQLException("Não há despesa para ser deletada.");
            }

            stmt.setObject(1, idDespesa);
            stmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Não foi possível deletar a despesa.");
        }

        return "Despesa deletada com sucesso.";
    }

    public String deleteAllDespesas() throws SQLException {
        String sql = "DELETE FROM despesas";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getAllDespesas().size() == 0){
                throw new SQLException("Não há despesas para serem deletadas.");
            }

            stmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Não foi possível deletar as despesas.");
        }

        return "Todas as despesas foram deletadas com sucesso.";
    }

    public String deleteDespesasByFamiliaId(int idFamilia) throws SQLException {
        String sql = "DELETE FROM despesas WHERE idFamilia = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getDespesasByFamilia(idFamilia).size() == 0){
                throw new SQLException("Não há despesas para serem deletadas.");
            }

            stmt.setObject(1, idFamilia);
            stmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Não foi possível deletar as despesas da família.");
        }

        return "Despesas da família deletadas com sucesso.";
    }

    public void updateDespesa(Despesa despesa, int idFamilia) throws SQLException {
        String sql = "UPDATE despesas SET nome = ?, valor = ?, dataInicio = ?, dataFinal = ?, isPago = ?, isParcelado = ?, qtdParcelas = ?, tipoDespesa = ?, idFamilia = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, despesa.getNome());
            stmt.setObject(2, despesa.getValor());
            stmt.setObject(3, despesa.getDataInicio());
            stmt.setObject(4, despesa.getDataFinal());
            stmt.setObject(5, despesa.isPago());
            stmt.setObject(6, despesa.isParcelado());
            stmt.setObject(7, despesa.getQtdParcelas());
            stmt.setObject(8, despesa.getTipoDespesa().toString());
            stmt.setObject(9, idFamilia);
            stmt.setObject(10, despesa.getId());
            stmt.execute();
            System.out.println("Despesa atualizada com sucesso.");

        } catch (SQLException e) {
            throw new SQLException("Não foi possível atualizar a despesa.");
        }
    }

    public List<Despesa> getDespesasByFamilia(int idFamilia) throws SQLException {
        String sql = "SELECT * FROM despesas WHERE idFamilia = ?";
        List<Despesa> despesas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, idFamilia);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(rs.getString("dataInicio"));
                despesa.setDataFinal(rs.getString("dataFinal"));
                despesa.setPago(rs.getBoolean("isPago"));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            throw new SQLException("Não foi possível buscar as despesas da família.");
        }

        return despesas;
    }

    public Despesa getDespesa(int id) throws SQLException {

        String sql = "SELECT * FROM despesas WHERE id = ?";
        Despesa despesa = new Despesa();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(rs.getString("dataInicio"));
                despesa.setDataFinal(rs.getString("dataFinal"));
                despesa.setPago(rs.getBoolean("isPago"));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
            } else {
                throw new SQLException("Não foi possível buscar a despesa.");
            }

        } catch (SQLException e) {
            throw new SQLException("Não foi possível buscar a despesa.");
        }

        return despesa;
    }

    public List<Despesa> getDepesasByFamiliaToPage(int idFamilia, int page, int size) throws SQLException {
        String sql = "SELECT * FROM despesas WHERE idFamilia = ? LIMIT ? OFFSET ?";
        List<Despesa> despesas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, idFamilia);
            stmt.setObject(2, size);
            stmt.setObject(3, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(rs.getString("dataInicio"));
                despesa.setDataFinal(rs.getString("dataFinal"));
                despesa.setPago(rs.getBoolean("isPago"));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            throw new SQLException("Não foi possível buscar as despesas.");
        }

        return despesas;
    }

    public List<Despesa> getAllDespesas() throws SQLException {
        String sql = "SELECT * FROM despesas";
        List<Despesa> despesas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(rs.getString("dataInicio"));
                despesa.setDataFinal(rs.getString("dataFinal"));
                despesa.setPago(rs.getBoolean("isPago"));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar despesas");
        }

        return despesas;
    }

    public List<Despesa> getAllDespesasByPage(int page, int size) throws SQLException {
        String sql = "SELECT * FROM despesas LIMIT ? OFFSET ?";
        List<Despesa> despesas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, size);
            stmt.setObject(2, (page - 1) * size);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(rs.getString("dataInicio"));
                despesa.setDataFinal(rs.getString("dataFinal"));
                despesa.setPago(rs.getBoolean("isPago"));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar despesas por paginação");
        }

        return despesas;
    }

}
