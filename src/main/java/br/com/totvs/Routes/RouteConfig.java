package br.com.totvs.Routes;

import br.com.totvs.BD.SQLiteConnection;
import br.com.totvs.Controllers.DespesaController;
import br.com.totvs.Controllers.FamiliaController;
import br.com.totvs.Utils.ExceptionHandle;
import com.google.gson.Gson;

import java.sql.SQLException;

import static spark.Spark.*;

public class RouteConfig {

    private static final Gson gson = new Gson();

    public static void initHMG() {

        port(4567);
        SQLiteConnection.getInstance().testConnection();

        // Autenticação

        before("/*", (req, res) -> {
            // TODO: Implementar autenticação
        });

        // Mapeamento de Paths do API

        path("/api", () -> {

            path("/despesa", () -> {
                path("/all", () -> {
                    get("", (req, res) -> DespesaController.getAllDespesas(req, res), gson::toJson);
                    get("/page", (req, res) -> DespesaController.getAllDespesasByPage(req, res), gson::toJson);
                    delete("/delete", (req, res) -> DespesaController.deleteAllDespesas(req, res), gson::toJson);
                });
                path("/:id", () -> {
                    get("", (req, res) -> DespesaController.getDespesa(req, res), gson::toJson);
                    get("/valor", (req, res) -> DespesaController.getValorDespesa(req, res), gson::toJson);
                    put("/update", (req, res) -> DespesaController.updateDespesa(req, res), gson::toJson);
                    delete("/delete", (req, res) -> DespesaController.deleteDespesa(req, res), gson::toJson);
                });
                post("/add", (req, res) -> DespesaController.addDespesa(req, res), gson::toJson);
            });

            path("/familia", () -> {
                path("/all", () -> {
                    get("", (req, res) -> FamiliaController.getAllFamilias(req, res), gson::toJson);
                    get("/page", (req, res) -> FamiliaController.getAllFamiliasByPage(req, res), gson::toJson);
                    delete("/delete", (req, res) -> FamiliaController.deleteAllFamilias(req, res), gson::toJson);
                });
                path("/:id", () -> {
                    get("", (req, res) -> FamiliaController.getFamilia(req, res), gson::toJson);
                    get("/despesas", (req, res) -> FamiliaController.getAllDespesasFamilia(req, res), gson::toJson);
                    get("/despesas/page", (req, res) -> FamiliaController.getAllDespesasFamiliaByPage(req, res), gson::toJson);
                    get("/salario", (req, res) -> FamiliaController.getSalarioFamilia(req, res), gson::toJson);
                    put("/update", (req, res) -> FamiliaController.updateFamilia(req, res), gson::toJson);
                    delete("/delete", (req, res) -> FamiliaController.deleteFamilia(req, res), gson::toJson);
                });
                post("/add", (req, res) -> FamiliaController.addFamilia(req, res), gson::toJson);
            });

            exception(IllegalArgumentException.class, (exception, req, res) -> ExceptionHandle.handleException400(exception, req, res));
            exception(NullPointerException.class, (exception, req, res) -> ExceptionHandle.handleException400(exception, req, res));
            exception(SQLException.class, (exception, req, res) -> ExceptionHandle.handleException500(exception, req, res));
            exception(Exception.class, (exception, req, res) -> ExceptionHandle.handleException500(exception, req, res));

        });

        System.out.println("Servidor rodando na porta 4567");
    }

    public static void initPROD() {

        port(8080);
        SQLiteConnection.getInstance().testConnection();

        // Headers de CORS - PROD

        before("/*", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin,");
            res.header("X-Frame-Options", "SAMEORIGIN");
            res.header("X-XSS-Protection", "1; mode=block");
            res.header("X-Content-Type-Options", "nosniff");
            res.header("Content-Security-Policy", "default-src 'self'");
            res.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
            res.header("Referrer-Policy", "no-referrer-when-downgrade");
        });

        // Autenticação

        before("/*", (req, res) -> {
            // TODO: Implementar autenticação
        });

        // Mapeamento de Paths do API

        path("/api", () -> {

            path("/despesa", () -> {
                path("/all", () -> {
                    get("", (req, res) -> DespesaController.getAllDespesas(req, res), gson::toJson);
                    get("/page", (req, res) -> DespesaController.getAllDespesasByPage(req, res), gson::toJson);
                    delete("/delete", (req, res) -> DespesaController.deleteAllDespesas(req, res), gson::toJson);
                });
                path("/:id", () -> {
                    get("", (req, res) -> DespesaController.getDespesa(req, res), gson::toJson);
                    get("/valor", (req, res) -> DespesaController.getValorDespesa(req, res), gson::toJson);
                    put("/update", (req, res) -> DespesaController.updateDespesa(req, res), gson::toJson);
                    delete("/delete", (req, res) -> DespesaController.deleteDespesa(req, res), gson::toJson);
                });
                post("/add", (req, res) -> DespesaController.addDespesa(req, res), gson::toJson);
            });

            path("/familia", () -> {
                path("/all", () -> {
                    get("", (req, res) -> FamiliaController.getAllFamilias(req, res), gson::toJson);
                    get("/page", (req, res) -> FamiliaController.getAllFamiliasByPage(req, res), gson::toJson);
                    delete("/delete", (req, res) -> FamiliaController.deleteAllFamilias(req, res), gson::toJson);
                });
                path("/:id", () -> {
                    get("", (req, res) -> FamiliaController.getFamilia(req, res), gson::toJson);
                    get("/despesas", (req, res) -> FamiliaController.getAllDespesasFamilia(req, res), gson::toJson);
                    get("/despesas/page", (req, res) -> FamiliaController.getAllDespesasFamiliaByPage(req, res), gson::toJson);
                    get("/salario", (req, res) -> FamiliaController.getSalarioFamilia(req, res), gson::toJson);
                    put("/update", (req, res) -> FamiliaController.updateFamilia(req, res), gson::toJson);
                    delete("/delete", (req, res) -> FamiliaController.deleteFamilia(req, res), gson::toJson);
                });
                post("/add", (req, res) -> FamiliaController.addFamilia(req, res), gson::toJson);
            });

            exception(IllegalArgumentException.class, (exception, req, res) -> ExceptionHandle.handleException400(exception, req, res));
            exception(NullPointerException.class, (exception, req, res) -> ExceptionHandle.handleException400(exception, req, res));
            exception(SQLException.class, (exception, req, res) -> ExceptionHandle.handleException500(exception, req, res));
            exception(Exception.class, (exception, req, res) -> ExceptionHandle.handleException500(exception, req, res));

        });

        System.out.println("Servidor rodando na porta 8080");

    }

}
