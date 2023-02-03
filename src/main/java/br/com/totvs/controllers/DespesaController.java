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

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        Despesa despesa = despesaService.getDespesa(Integer.parseInt(req.params("id")));

        if(despesa.getId() == 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, TypeStatus.OK, despesa);

    }

    public static ResponseResource getValorDespesa(Request req, Response res){

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        Despesa despesa = despesaService.getDespesa(Integer.parseInt(req.params("id")));

        if(despesa.getId() == 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, TypeStatus.OK, despesa.getValor());

    }

    public static ResponseResource deleteDespesa(Request req, Response res){

        if(Integer.parseInt(req.params("id")) <= 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        despesaService.deleteDespesa(Integer.parseInt(req.params("id")));

        return ResponseResource.of(res, TypeStatus.OK);

    }

    public static ResponseResource deleteAllDespesas(Request req, Response res){

        String str = despesaService.deleteAllDespesas();

        return ResponseResource.of(res, TypeStatus.OK, str);

    }

    public static ResponseResource updateDespesa(Request req, Response res) {

        if(validarData(req.queryParams("dataFinal"), req.queryParams("dataPagamento"))){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        try {
            despesaService.updateDespesa(
                    Integer.parseInt(req.params("id")),
                    Integer.parseInt(req.queryParams("idFamilia")),
                    req.queryParams("nomeDespesa"),
                    Double.parseDouble(req.queryParams("valor")),
                    req.queryParams("dataFinal"),
                    req.queryParams("dataPagamento"),
                    Boolean.parseBoolean(req.queryParams("isParcelado")),
                    Integer.parseInt(req.queryParams("qtdParcelas")),
                    Categoria.valueOf(req.queryParams("tipoDespesa")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseResource.of(res, TypeStatus.OK);

    }

    public static ResponseResource addDespesa(Request req, Response res){

        if(validarData(req.queryParams("dataFinal"), req.queryParams("dataPagamento"))) {
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        try{
            despesaService.addDespesaAFamilia(
                    Integer.parseInt(req.params("idFamilia")),
                    req.queryParams("nomeDespesa"),
                    Double.parseDouble(req.queryParams("valor")),
                    req.queryParams("dataFinal"),
                    req.queryParams("dataPagamento"),
                    Boolean.parseBoolean(req.queryParams("isParcelado")),
                    Integer.parseInt(req.queryParams("qtdParcelas")),
                    Categoria.valueOf(req.queryParams("tipoDespesa")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseResource.of(res, TypeStatus.OK);

    }

    public static ResponseResource getAllDespesasByPage(Request req, Response res){

        if(Integer.parseInt(req.queryParams("page")) <= 0 || Integer.parseInt(req.queryParams("size")) <= 0){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        List<Despesa> despesas = despesaService.getAllDespesasByPage(Integer.parseInt(req.queryParams("page")), Integer.parseInt(req.queryParams("size")));

        if(despesas.isEmpty()){
            return ResponseResource.of(res, TypeStatus.BAD_REQUEST);
        }

        return ResponseResource.of(res, TypeStatus.OK, despesas);
    }

    public static ResponseResource getAllDespesas(Request req, Response res){

        List<Despesa> despesas = despesaService.getAllDespesas();

        return ResponseResource.of(res, TypeStatus.OK, despesas);

    }

    private static boolean validarData(String dataFinal, String dataPagamento) {

        if(dataFinal.length() != 10 || dataPagamento.length() != 10){
            return false;
        } else if(dataFinal.charAt(2) != '/' || dataFinal.charAt(5) != '/' ||
                dataPagamento.charAt(2) != '/' || dataPagamento.charAt(5) != '/'){
            return false;
        } else if (Integer.parseInt(dataFinal.substring(0, 2)) > 31 ||
                Integer.parseInt(dataFinal.substring(3, 5)) > 12 ||
                Integer.parseInt(dataPagamento.substring(0, 2)) > 31 ||
                Integer.parseInt(dataPagamento.substring(3, 5)) > 12){
            return false;
        } else if (Integer.parseInt(dataFinal.substring(0, 2)) <= 0 ||
                Integer.parseInt(dataFinal.substring(3, 5)) <= 0 ||
                Integer.parseInt(dataPagamento.substring(0, 2)) <= 0 ||
                Integer.parseInt(dataPagamento.substring(3, 5)) <= 0){
            return false;
        }

        return true;

    }

}
