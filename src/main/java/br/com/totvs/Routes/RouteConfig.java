package br.com.totvs.Routes;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Controllers.DespesaController;
import br.com.totvs.Controllers.FamiliaController;
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
               get("/:id", (req, res) -> DespesaController.getDespesa(req, res), gson::toJson);
               get("/:id/valor", (req, res) -> DespesaController.getValorDespesas(req, res), gson::toJson);
               post("/:idFamilia/add", (req, res) -> DespesaController.addDespesa(req, res), gson::toJson);
               put("/:idFamilia/:idDespesa/update", (req, res) -> DespesaController.updateDespesa(req, res), gson::toJson);
               delete("/:id/delete", (req, res) -> DespesaController.deleteDespesa(req, res), gson::toJson);
               delete("/deleteall", (req, res) -> DespesaController.deleteAllDespesas(req, res), gson::toJson);
           });

           path("/familia", () -> {
               get("/:id", (req, res) -> FamiliaController.getFamilia(req, res), gson::toJson);
               get("/:id/despesas", (req, res) -> FamiliaController.getAllDespesasFamilia(req, res), gson::toJson);
               get("/:id/despesas/page", (req, res) -> FamiliaController.getAllDespesasFamiliaByPage(req, res), gson::toJson);
               get("/:id/salario", (req, res) -> FamiliaController.getSalarioFamilia(req, res), gson::toJson);
               post("/add", (req, res) -> FamiliaController.addFamilia(req, res), gson::toJson);
               put("/:id/update", (req, res) -> FamiliaController.updateFamilia(req, res), gson::toJson);
               delete("/:id/delete", (req, res) -> FamiliaController.deleteFamilia(req, res), gson::toJson);
               delete("/deleteall", (req, res) -> FamiliaController.deleteAllFamilias(req, res), gson::toJson);
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
