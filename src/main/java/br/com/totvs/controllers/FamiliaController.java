package br.com.totvs.controllers;

import br.com.totvs.domain.Despesa;
import br.com.totvs.domain.Familia;
import br.com.totvs.repository.DespesaRepository;
import br.com.totvs.repository.FamiliaRepository;
import br.com.totvs.response.ResponseResource;
import br.com.totvs.response.TypeStatus;
import br.com.totvs.services.FamiliaService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class FamiliaController {

    private static final FamiliaService familiaService = new FamiliaService( new DespesaRepository(), new FamiliaRepository());

    private FamiliaController() {
    }

    public static ResponseResource addFamilia(Request req, Response res){

        Familia familia = null;

        try{
            familiaService.addFamilia(req.queryParams("nome"), Double.parseDouble(req.queryParams("salario")), Double.parseDouble(req.queryParams("carteira")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        try{
           familia = familiaService.getFamilia(familiaService.getLastIDFamilia());
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, familia, TypeStatus.OK);
    }

    public static ResponseResource getSalarioFamilia(Request req, Response res){

        Double salario = null;

        try{
            salario = familiaService.getSalarioFamilia(Integer.parseInt(req.params("id")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, salario, TypeStatus.OK);
    }

    public static ResponseResource deleteFamilia(Request req, Response res){

        try{
            familiaService.deleteFamilia(Integer.parseInt(req.params("id")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, "Familia deletada com sucesso", TypeStatus.OK);
    }

    public static ResponseResource deleteAllFamilias(Request req, Response res){

        try{
            familiaService.deleteAllFamilias();
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, "Todas as familias foram deletadas com sucesso", TypeStatus.OK);
    }

    public static ResponseResource updateFamilia(Request req, Response res){

        Set keys = req.queryParams();
        Familia familia = null;

        validarUpdateFamilia(keys, req);

        try {
            if (keys.contains("nome") && keys.size() == 2) {
                familiaService.updateFamiliaNome(Integer.parseInt(req.params("id")), req.queryParams("nome"));
            }

            if (keys.contains("salario") && keys.size() == 2) {
                familiaService.updateFamiliaSalario(Integer.parseInt(req.params("id")), Double.parseDouble(req.queryParams("salario")));
            }

            if (keys.contains("carteira") && keys.size() == 2) {
                familiaService.updateFamiliaCarteira(Integer.parseInt(req.params("id")), Double.parseDouble(req.queryParams("carteira")));
            }

            if (keys.size() == 4) {
                familiaService.updateFamilia(
                        Integer.parseInt(req.params("id")),
                        req.queryParams("nome"),
                        Double.parseDouble(req.queryParams("salario")),
                        Double.parseDouble(req.queryParams("carteira")));
            }
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        try {
            familia = familiaService.getFamilia(Integer.parseInt(req.params("id")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, familia, TypeStatus.OK);
    }

    public static ResponseResource getFamilia(Request req, Response res){

        Familia familia = null;

        try {
            familia = familiaService.getFamilia(Integer.parseInt(req.params("id")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, familia, TypeStatus.OK);
    }

    public static ResponseResource getAllDespesasFamilia(Request req, Response res){

        List<Despesa> despesas = null;

        try{
            despesas = familiaService.getAllDespesasFamilia(Integer.parseInt(req.params("id")));
        }catch (SQLException e){
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesas, TypeStatus.OK);
    }

    public static ResponseResource getAllDespesasFamiliaByPage(Request req, Response res){

        List<Despesa> despesas = null;

        try{
            despesas = familiaService.getAllDespesasFamiliaByPage(
                    Integer.parseInt(req.params("id")),
                    Integer.parseInt(req.queryParams("page")),
                    Integer.parseInt(req.queryParams("size")));
        }catch (SQLException e){
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesas, TypeStatus.OK);

    }

    public static ResponseResource getAllFamilias(Request req, Response res){

        List<Familia> familias = null;

        try{
            familias = familiaService.getAllFamilias();
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, familias, TypeStatus.OK);
    }

    public static ResponseResource getAllFamiliasByPage(Request req, Response res){

        List<Familia> familias = null;

        try{
            familias = familiaService.getAllFamiliasByPage(
                Integer.parseInt(req.queryParams("page")),
                Integer.parseInt(req.queryParams("size")));
        } catch (SQLException e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, familias, TypeStatus.OK);
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
