package br.com.totvs.Routes;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Controllers.DespesaController;
import com.google.gson.Gson;

import static spark.Spark.*;
import static spark.Spark.get;

public class RouteConfig {

    private static final Gson gson = new Gson();

    public static void initHMG() {

        port(4567);
        SQLiteConnection.getInstance().testConnection();

        // Mapeamento de Paths do API

        path("/api", () -> {
           before("/*", (q, a) -> {
               // Podemos inserir um fluxo de autenticação aqui.
           });

           path("/despesa", () -> {
               get("/all", (req, res) -> DespesaController.getAllDespesas(req, res), gson::toJson);
           });

        });

        System.out.println("Servidor rodando na porta 4567");
    }

    public static void initPROD() {

        port(8080);
        SQLiteConnection.getInstance().testConnection();

        path("/api", () -> {
            before("/*", (q, a) -> {
                // Podemos inserir um fluxo de autenticação aqui.
            });

            path("/despesa", () -> {
                get("/all", (req, res) -> DespesaController.getAllDespesas(req, res), gson::toJson);
            });

        });

        System.out.println("Servidor rodando na porta 8080");

    }

}
