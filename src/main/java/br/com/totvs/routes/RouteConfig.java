package br.com.totvs.routes;

import br.com.totvs.controllers.DespesaController;
import br.com.totvs.controllers.FamiliaController;
import br.com.totvs.db.SQLiteConnection;
import br.com.totvs.utils.HealthCheck;
import com.google.gson.Gson;

import static spark.Spark.*;

public class RouteConfig {

    private static final Gson gson = new Gson();

    public static void initHMG(boolean enableHealthCheck, boolean enableCORS, boolean enableAuth) {

        // Conexão com o banco de dados

        if(!SQLiteConnection.getInstance().testConnection()){
            System.out.println("Erro ao conectar com o banco de dados - SQLiteConnection");
            System.exit(0);
        }

        // Configurações do Spark

        port(4567);
        threadPool(8, 2, 30000);

        // CORS e Headers de Segurança && Autenticação

        before("/*", (req, res) -> {

            // CORS e Headers de Segurança
            if(enableCORS){
                res.header("Access-Control-Allow-Origin", "*");
                res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin,");
                res.header("X-Frame-Options", "SAMEORIGIN");
                res.header("X-XSS-Protection", "1; mode=block");
                res.header("X-Content-Type-Options", "nosniff");
                res.header("Content-Security-Policy", "default-src 'self'");
                res.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
                res.header("Referrer-Policy", "no-referrer-when-downgrade");
            }

            // TODO: Implementar autenticação
            if(enableAuth){

            }

        });

        // HealthCheck

        if(enableHealthCheck){
            get("/healthcheck", (req, res) -> "OK");

            if (!HealthCheck.healthCheckByCurl(4567) && enableHealthCheck) {
                System.out.println("Erro ao iniciar o servidor - HealthCheck");
                System.exit(0);
            }
        }

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

        });

        System.out.println("Servidor rodando na porta 4567");
    }

    public static void initPROD(int port, boolean enableHealthCheck, boolean enableCORS, boolean enableAuth) {

        // Conexão com o banco de dados

        if(!SQLiteConnection.getInstance().testConnection()){
            System.out.println("Erro ao conectar com o banco de dados - SQLiteConnection");
            System.exit(0);
        }

        // Configurações do Spark

        port(port);
        threadPool(8, 2, 30000);

        // CORS e Headers de Segurança && Autenticação

        before("/*", (req, res) -> {

            // CORS e Headers de Segurança
            if(enableCORS){
                res.header("Access-Control-Allow-Origin", "*");
                res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin,");
                res.header("X-Frame-Options", "SAMEORIGIN");
                res.header("X-XSS-Protection", "1; mode=block");
                res.header("X-Content-Type-Options", "nosniff");
                res.header("Content-Security-Policy", "default-src 'self'");
                res.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
                res.header("Referrer-Policy", "no-referrer-when-downgrade");
            }

            // TODO: Implementar autenticação
            if(enableAuth){

            }

        });

        // HealthCheck

        if(enableHealthCheck){
            get("/healthcheck", (req, res) -> "OK");

            if (!HealthCheck.healthCheckByCurl(port) && enableHealthCheck) {
                System.out.println("Erro ao iniciar o servidor - HealthCheck");
                System.exit(0);
            }
        }

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

        });

        System.out.println("Servidor rodando na porta " + port);
    }

}
