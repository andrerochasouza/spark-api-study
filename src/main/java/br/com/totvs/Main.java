package br.com.totvs;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        System.out.println("Servidor rodando na porta 4567");

        SQLiteConnection.getInstance().testConnection();
        DespesaRepository despesaRepository = new DespesaRepository();
        FamiliaRepository familiaRepository = new FamiliaRepository();

        get("/despesas", (req, res) -> despesaRepository.getAllDespesas());
        get("/despesas/:id", (req, res) -> despesaRepository.getDespesa(Integer.parseInt(req.params(":id"))));
        post("/familia/add", (req, res) -> {
            familiaRepository.addFamilia(req.queryParams("nome"), Double.parseDouble(req.queryParams("salario")));
            return "Despesa inserida com sucesso.";
        });
    }
}