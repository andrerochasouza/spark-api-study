package br.com.totvs.Controllers;

import br.com.totvs.Domain.Categoria;
import br.com.totvs.Domain.Despesa;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;
import br.com.totvs.Services.DespesaService;
import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

public class DespesaController {

    private static final DespesaService despesaService = new DespesaService(new DespesaRepository(), new FamiliaRepository());

    private DespesaController() {
    }

    public static Despesa getDespesa(Request req, Response res){

        if(Integer.parseInt(req.params("id")) <= 0){
            throw new IllegalArgumentException("Id da despesa deve ser maior que 0");
        }

        return despesaService.getDespesa(Integer.parseInt(req.params("id")));
    }

    public static List<Despesa> getAllDespesas(Request req, Response res){
        return despesaService.getAllDespesas();
    }

    public static double getValorDespesa(Request req, Response res){

        if(Integer.parseInt(req.params("id")) <= 0){
            throw new IllegalArgumentException("Id da despesa deve ser maior que 0");
        }

        return despesaService.getValorDespesa(Integer.parseInt(req.params("idDespesa")));

    }

    public static String deleteDespesa(Request req, Response res){
        despesaService.deleteDespesa(Integer.parseInt(req.params("id")));
        return "Despesa deletada com sucesso";
    }

    public static String deleteAllDespesas(Request req, Response res){
        return despesaService.deleteAllDespesas();
    }

    public static Despesa updateDespesa(Request req, Response res) {

        validarData(req.queryParams("dataFinal"), req.queryParams("dataPagamento"));

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

        return despesaService.getDespesa(Integer.parseInt(req.params("idDespesa")));
    }

    public static Despesa addDespesa(Request req, Response res){

        validarData(req.queryParams("dataFinal"), req.queryParams("dataPagamento"));

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

        return despesaService.getDespesa(Integer.parseInt(req.params("idFamilia")));
    }

    public static List<Despesa> getAllDespesasByPage(Request req, Response res){
        return despesaService.getAllDespesasByPage(Integer.parseInt(req.params("page")), Integer.parseInt(req.params("size")));
    }

    private static void validarData(String dataFinal, String dataPagamento) {

        if(dataFinal.length() != 10 || dataPagamento.length() != 10){
            throw new IllegalArgumentException("Data final deve estar no formato dd/MM/yyyy");
        } else if(dataFinal.charAt(2) != '/' || dataFinal.charAt(5) != '/' ||
                dataPagamento.charAt(2) != '/' || dataPagamento.charAt(5) != '/'){
            throw new IllegalArgumentException("Data final deve estar no formato dd/MM/yyyy");
        } else if (Integer.parseInt(dataFinal.substring(0, 2)) > 31 ||
                Integer.parseInt(dataFinal.substring(3, 5)) > 12 ||
                Integer.parseInt(dataPagamento.substring(0, 2)) > 31 ||
                Integer.parseInt(dataPagamento.substring(3, 5)) > 12){
            throw new IllegalArgumentException("Data final deve estar no formato dd/MM/yyyy");
        } else if (Integer.parseInt(dataFinal.substring(0, 2)) <= 0 ||
                Integer.parseInt(dataFinal.substring(3, 5)) <= 0 ||
                Integer.parseInt(dataPagamento.substring(0, 2)) <= 0 ||
                Integer.parseInt(dataPagamento.substring(3, 5)) <= 0){
            throw new IllegalArgumentException("Data final deve estar no formato dd/MM/yyyy");
        }

    }

}
