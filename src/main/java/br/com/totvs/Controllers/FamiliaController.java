package br.com.totvs.Controllers;

import br.com.totvs.Domain.Despesa;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;
import br.com.totvs.Services.FamiliaService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Set;

public class FamiliaController {

    private static final FamiliaService familiaService = new FamiliaService( new DespesaRepository(), new FamiliaRepository());

    private FamiliaController() {
    }

    public static String addFamilia(Request req, Response res){
        familiaService.addFamilia(req.queryParams("nome"), Double.parseDouble(req.queryParams("salario")), Double.parseDouble(req.queryParams("carteira")));
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

        Set keys = req.queryParams();

        validarUpdateFamilia(keys, req);

        if(keys.contains("nome") && keys.size() == 2){
            familiaService.updateFamiliaNome(Integer.parseInt(req.params("id")), req.queryParams("nome"));
        }

        if(keys.contains("salario") && keys.size() == 2){
            familiaService.updateFamiliaSalario(Integer.parseInt(req.params("id")), Double.parseDouble(req.queryParams("salario")));
        }

        if(keys.contains("carteira") && keys.size() == 2){
            familiaService.updateFamiliaCarteira(Integer.parseInt(req.params("id")), Double.parseDouble(req.queryParams("carteira")));
        }

        if(keys.size() == 4){
            familiaService.updateFamilia(
                    Integer.parseInt(req.params("id")),
                    req.queryParams("nome"),
                    Double.parseDouble(req.queryParams("salario")),
                    Double.parseDouble(req.queryParams("carteira")));
        }

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

    public static List<Familia> getAllFamilias(Request req, Response res){
        System.out.println("getAllFamilias");
        return familiaService.getAllFamilias();
    }

    public static List<Familia> getAllFamiliasByPage(Request req, Response res){
        return familiaService.getAllFamiliasByPage(
                Integer.parseInt(req.queryParams("page")),
                Integer.parseInt(req.queryParams("size")));
    }

    private static void validarUpdateFamilia(Set keys, Request req){

        if(keys.isEmpty()){
            throw new IllegalArgumentException("Nenhum parametro foi passado para atualizar a familia!");
        }

        if(req.queryParams("id") == null){
            throw new IllegalArgumentException("O id da familia não pode ser nulo!");
        }

        if(keys.size() == 1){
            throw new IllegalArgumentException("Nenhum parametro foi passado para atualizar a familia!");
        }

        if(keys.size() == 3){
            throw new IllegalArgumentException("Podem passar apenas 2 parametros OU 4 parametros para atualizar a familia!");
        }

        if(!keys.contains("id")){
            throw new IllegalArgumentException("O id da familia não foi passado!");
        }

        if(keys.size() >= 2 && keys.contains("id") && keys.contains("nome") && req.queryParams("nome").isEmpty()){
            throw new IllegalArgumentException("O nome da familia não pode ser vazio!");
        }

        if(keys.size() >= 2 && keys.contains("id") && keys.contains("salario") && Double.parseDouble(req.queryParams("salario")) < 0){
            throw new IllegalArgumentException("O salario da familia não pode ser negativo!");
        }

        if(keys.size() >= 2 && keys.contains("id") && keys.contains("carteira") && Double.parseDouble(req.queryParams("carteira")) < 0){
            throw new IllegalArgumentException("A carteira da familia não pode ser negativa!");
        }

    }
}
