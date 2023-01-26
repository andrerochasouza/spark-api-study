package br.com.totvs;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ajax.JSON;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567);
        System.out.println("Servidor rodando na porta 4567");

        initExceptionHandler((e) -> System.out.println("Erro ao iniciar o servidor."));

        SQLiteConnection.getInstance().testConnection();
        DespesaRepository despesaRepository = new DespesaRepository();
        FamiliaRepository familiaRepository = new FamiliaRepository();

        Gson gson = new Gson();


        post("/familia/add", (req, res) -> {
            familiaRepository.addFamilia(req.queryParams("nome"), Double.parseDouble(req.queryParams("salario")));
            return "Despesa inserida com sucesso.";
        });

        get("/familia/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            return familiaRepository.getFamilia(id);
        }, gson::toJson);
    }
}