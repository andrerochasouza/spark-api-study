package br.com.totvs.Controllers;

import br.com.totvs.Domain.Despesa;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;
import br.com.totvs.Services.FamiliaService;
import spark.Request;
import spark.Response;

import java.util.List;

public class FamiliaController {

    private static final FamiliaService familiaService = new FamiliaService( new DespesaRepository(), new FamiliaRepository());

    private FamiliaController() {
    }

    public static String addFamilia(Request req, Response res){
        familiaService.addFamilia(req.queryParams("nome"), Double.parseDouble(req.queryParams("salario")));
        return "Familia adicionada com sucesso!";
    }

    public static double getSalarioFamilia(Request req, Response res){
        return familiaService.getSalarioFamilia(Integer.parseInt(req.params("id")));
    }

    public static String deleteFamilia(Request req, Response res){
        return familiaService.deleteFamilia(Integer.parseInt(req.params("id")));
    }

    public static String deleteAllFamilias(Request req, Response res){
        return familiaService.deleteAllFamilias();
    }

    public static Familia updateFamilia(Request req, Response res){
        familiaService.updateFamilia(
                Integer.parseInt(req.params("id")),
                req.queryParams("nome"),
                Double.parseDouble(req.queryParams("salario")));

        return familiaService.getFamilia(Integer.parseInt(req.params("id")));
    }

    public static Familia getFamilia(Request req, Response res){
        return familiaService.getFamilia(Integer.parseInt(req.params("id")));
    }

    public static List<Despesa> getAllDespesasFamilia(Request req, Response res){
        return familiaService.getAllDespesasFamilia(Integer.parseInt(req.params("id")));
    }

    public static List<Despesa> getAllDespesasFamiliaByPage(Request req, Response res){
        return familiaService.getAllDespesasFamiliaByPage(
                Integer.parseInt(req.params("id")),
                Integer.parseInt(req.queryParams("page")),
                Integer.parseInt(req.queryParams("size")));
    }
}
