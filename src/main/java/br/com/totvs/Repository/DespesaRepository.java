package br.com.totvs.Repository;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Domain.Categoria;
import br.com.totvs.Domain.Despesa;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DespesaRepository {

    private Connection connection;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DespesaRepository() {
        this.connection = SQLiteConnection.getInstance().getConnection();
        this.createTable();
    }


    private void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS despesas (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	nome text NOT NULL,\n"
                + "	valor real NOT NULL,\n"
                + "	dataInicio text NOT NULL,\n"
                + "	dataFinal text NOT NULL,\n"
                + "	dataPagamento text,\n"
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

    public void addDespesa(Despesa despesa, int idFamilia) {
        String sql = "INSERT INTO despesas(nome, valor, dataInicio, dataFinal, dataPagamento, isParcelado, qtdParcelas, tipoDespesa, idFamilia) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, despesa.getNome());
            stmt.setObject(2, despesa.getValor());
            stmt.setObject(3, despesa.getDataInicio());
            stmt.setObject(4, despesa.getDataFinal());
            stmt.setObject(5, despesa.getDataPagamento());
            stmt.setObject(6, despesa.isParcelado());
            stmt.setObject(7, despesa.getQtdParcelas());
            stmt.setObject(8, despesa.getTipoDespesa().toString());
            stmt.setObject(9, idFamilia);
            stmt.execute();
            System.out.println("Despesa inserida com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível inserir a despesa.");
        }
    }

    public void deleteDespesaById(int idDespesa){
        String sql = "DELETE FROM despesas WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getDespesa(idDespesa) == null){
                System.out.println("Não há despesa para ser deletada.");
                return;
            }

            stmt.setObject(1, idDespesa);
            stmt.execute();
            System.out.println("Despesa deletada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível deletar a despesa.");
        }
    }

    public void deleteAllDespesas(){
        String sql = "DELETE FROM despesas";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            if(this.getAllDespesas().size() == 0){
                System.out.println("Não há despesas para serem deletadas.");
                return;
            }


            stmt.execute();
            System.out.println("Todas as despesas foram deletadas com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível deletar as despesas.");
        }
    }

    public void deleteDespesasByFamiliaId(int idFamilia){
        String sql = "DELETE FROM despesas WHERE idFamilia = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, idFamilia);
            stmt.execute();
            System.out.println("Despesas da família deletadas com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível deletar as despesas da família.");
        }
    }

    public void updateDespesa(Despesa despesa, int idFamilia){
        String sql = "UPDATE despesas SET nome = ?, valor = ?, dataInicio = ?, dataFinal = ?, dataPagamento = ?, isParcelado = ?, qtdParcelas = ?, tipoDespesa = ?, idFamilia = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {

            stmt.setObject(1, despesa.getNome());
            stmt.setObject(2, despesa.getValor());
            stmt.setObject(3, despesa.getDataInicio());
            stmt.setObject(4, despesa.getDataFinal());
            stmt.setObject(5, despesa.getDataPagamento());
            stmt.setObject(6, despesa.isParcelado());
            stmt.setObject(7, despesa.getQtdParcelas());
            stmt.setObject(8, despesa.getTipoDespesa().toString());
            stmt.setObject(9, idFamilia);
            stmt.setObject(10, despesa.getId());
            stmt.execute();
            System.out.println("Despesa atualizada com sucesso.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Não foi possível atualizar a despesa.");
        }
    }

    public List<Despesa> getAllDespesas(){
        String sql = "SELECT * FROM despesas";
        List<Despesa> despesas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Despesa despesa = new Despesa();
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(sdf.parse(rs.getString("dataInicio")));
                despesa.setDataFinal(sdf.parse(rs.getString("dataFinal")));
                despesa.setDataPagamento(sdf.parse(rs.getString("dataPagamento")));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return despesas;
    }

    public List<Despesa> getDespesasByFamilia(int idFamilia){
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
                despesa.setDataInicio(sdf.parse(rs.getString("dataInicio")));
                despesa.setDataFinal(sdf.parse(rs.getString("dataFinal")));
                despesa.setDataPagamento(sdf.parse(rs.getString("dataPagamento")));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return despesas;
    }

    public Despesa getDespesa(int id){
        String sql = "SELECT * FROM despesas WHERE id = ?";
        Despesa despesa = new Despesa();
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                despesa.setId(rs.getInt("id"));
                despesa.setNome(rs.getString("nome"));
                despesa.setValor(rs.getDouble("valor"));
                despesa.setDataInicio(sdf.parse(rs.getString("dataInicio")));
                despesa.setDataFinal(sdf.parse(rs.getString("dataFinal")));
                despesa.setDataPagamento(sdf.parse(rs.getString("dataPagamento")));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return despesa;
    }

    public List<Despesa> getDepesasByFamiliaToPage(int idFamilia, int page, int size){
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
                despesa.setDataInicio(sdf.parse(rs.getString("dataInicio")));
                despesa.setDataFinal(sdf.parse(rs.getString("dataFinal")));
                despesa.setDataPagamento(sdf.parse(rs.getString("dataPagamento")));
                despesa.setParcelado(rs.getBoolean("isParcelado"));
                despesa.setQtdParcelas(rs.getInt("qtdParcelas"));
                despesa.setTipoDespesa(Categoria.valueOf(rs.getString("tipoDespesa")));
                despesas.add(despesa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return despesas;
    }

}
