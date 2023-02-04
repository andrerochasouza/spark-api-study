package br.com.totvs.controllers;

import br.com.totvs.domain.Categoria;
import br.com.totvs.domain.Despesa;
import br.com.totvs.repository.DespesaRepository;
import br.com.totvs.repository.FamiliaRepository;
import br.com.totvs.response.ResponseResource;
import br.com.totvs.response.TypeStatus;
import br.com.totvs.services.DespesaService;
import spark.Request;
import spark.Response;

import java.util.List;

public class DespesaController {

    private static final DespesaService despesaService = new DespesaService(new DespesaRepository(), new FamiliaRepository());

    private DespesaController() {
    }

    public static ResponseResource getDespesa(Request req, Response res){

        Despesa despesa = null;

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.ofError(res, "Id inválido", TypeStatus.BAD_REQUEST);
        }

        try {
            despesa = despesaService.getDespesa(Integer.parseInt(req.params("id")));
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesa, TypeStatus.OK);

    }

    public static ResponseResource getValorDespesa(Request req, Response res){

        Despesa despesa = null;

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.ofError(res, "Id inválido", TypeStatus.BAD_REQUEST);
        }

        try {
            despesa = despesaService.getDespesa(Integer.parseInt(req.params("id")));
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesa.getValor(), TypeStatus.OK);

    }

    public static ResponseResource deleteDespesa(Request req, Response res){

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.ofError(res, "Id inválido", TypeStatus.BAD_REQUEST);
        }

        try {
            despesaService.deleteDespesa(Integer.parseInt(req.params("id")));
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, "Despesa deletada com sucesso", TypeStatus.OK);

    }

    public static ResponseResource deleteAllDespesas(Request req, Response res){

        String str = null;

        try {
            str = despesaService.deleteAllDespesas();
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, str, TypeStatus.OK);

    }

    public static ResponseResource updateDespesa(Request req, Response res) {

        if(isValido(req.queryParams("dataFinal"))){
            try {
                despesaService.updateDespesa(
                        Integer.parseInt(req.params("id")),
                        Integer.parseInt(req.queryParams("idFamilia")),
                        req.queryParams("nomeDespesa"),
                        Double.parseDouble(req.queryParams("valor")),
                        req.queryParams("dataFinal"),
                        Boolean.parseBoolean(req.queryParams("isPago")),
                        Boolean.parseBoolean(req.queryParams("isParcelado")),
                        Integer.parseInt(req.queryParams("qtdParcelas")),
                        Categoria.valueOf(req.queryParams("tipoDespesa")));
            } catch (Exception e) {
                return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
            }

            return ResponseResource.of(res, "Despesa atualizada com sucesso", TypeStatus.OK);
        } else {
            return ResponseResource.of(res, "Data inválida", TypeStatus.BAD_REQUEST);
        }

    }

    public static ResponseResource addDespesa(Request req, Response res){

        if(isValido(req.queryParams("dataFinal"))) {

            try{
                despesaService.addDespesaAFamilia(
                        Integer.parseInt(req.queryParams("idFamilia")),
                        req.queryParams("nomeDespesa"),
                        Double.parseDouble(req.queryParams("valor")),
                        req.queryParams("dataFinal"),
                        Boolean.parseBoolean(req.queryParams("isPago")),
                        Boolean.parseBoolean(req.queryParams("isParcelado")),
                        Integer.parseInt(req.queryParams("qtdParcelas")),
                        Categoria.valueOf(req.queryParams("tipoDespesa")));

                return ResponseResource.of(res, "Despesa adicionada com sucesso", TypeStatus.OK);
            } catch (Exception e) {
                return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
            }

        } else {
            return ResponseResource.ofError(res, "Data inválida", TypeStatus.BAD_REQUEST);
        }



    }

    public static ResponseResource getAllDespesasByPage(Request req, Response res){

        List<Despesa> despesas = null;

        if(Integer.parseInt(req.queryParams("page")) <= 0 || Integer.parseInt(req.queryParams("size")) <= 0){
            return ResponseResource.ofError(res, "Page ou size inválido", TypeStatus.BAD_REQUEST);
        }

        try {
            despesas = despesaService.getAllDespesasByPage(Integer.parseInt(req.queryParams("page")), Integer.parseInt(req.queryParams("size")));
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesas, TypeStatus.OK);
    }

    public static ResponseResource getAllDespesas(Request req, Response res){

        List<Despesa> despesas = null;

        try {
            despesas = despesaService.getAllDespesas();
        } catch (Exception e) {
            return ResponseResource.ofError(res, e.getMessage(), TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, despesas, TypeStatus.OK);

    }

    private static boolean isValido(String dataFinal) {

        if(dataFinal == null){
            return false;
        } else if(dataFinal.length() != 10){
            return false;
        } else if(dataFinal.charAt(2) != '/' || dataFinal.charAt(5) != '/'){
            return false;
        } else if(Integer.parseInt(dataFinal.substring(0, 2)) > 31 || Integer.parseInt(dataFinal.substring(0, 2)) < 1){
            return false;
        } else if(Integer.parseInt(dataFinal.substring(3, 5)) > 12 || Integer.parseInt(dataFinal.substring(3, 5)) < 1){
            return false;
        } else if(Integer.parseInt(dataFinal.substring(6, 10)) < 2020){
            return false;
        }

        return true;

    }

}
