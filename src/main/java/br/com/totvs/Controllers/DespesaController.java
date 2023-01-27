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

public class DespesaController {

    private static final DespesaService despesaService = new DespesaService(new DespesaRepository(), new FamiliaRepository());
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private DespesaController() {
    }

    public static Despesa getDespesa(Request req, Response res){
        return despesaService.getDespesa(Integer.parseInt(req.params("id")));
    }

    public static List<Despesa> getAllDespesas(Request req, Response res){
        return despesaService.getAllDespesas();
    }

    public static double getValorDespesas(Request req, Response res){
        return despesaService.getValorDespesa(Integer.parseInt(req.params("id")));
    }

    public static String deleteDespesa(Request req, Response res){
        despesaService.deleteDespesa(Integer.parseInt(req.params("id")));
        return "Despesa deletada com sucesso";
    }

    public static String deleteAllDespesas(Request req, Response res){
        despesaService.deleteAllDespesas();
        return "Todas as despesas foram deletadas";
    }

    public static Despesa updateDespesa(Request req, Response res){
        try {
            despesaService.updateDespesa(
                    Integer.parseInt(req.params("idFamilia")),
                    Integer.parseInt(req.params("idDespesa")),
                    req.queryParams("nomeDespesa"),
                    Double.parseDouble(req.queryParams("valor")),
                    sdf.parse(req.queryParams("dataFinal")),
                    sdf.parse(req.queryParams("dataPagamento")),
                    Boolean.parseBoolean(req.queryParams("isParcelado")),
                    Integer.parseInt(req.queryParams("qtdParcelas")),
                    Categoria.valueOf(req.queryParams("tipoDespesa")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return despesaService.getDespesa(Integer.parseInt(req.params("idDespesa")));
    }

    public static Despesa addDespesa(Request req, Response res){

        try{
            despesaService.addDespesaAFamilia(
                    Integer.parseInt(req.params("idFamilia")),
                    req.queryParams("nomeDespesa"),
                    Double.parseDouble(req.queryParams("valor")),
                    sdf.parse(req.queryParams("dataFinal")),
                    sdf.parse(req.queryParams("dataPagamento")),
                    Boolean.parseBoolean(req.queryParams("isParcelado")),
                    Integer.parseInt(req.queryParams("qtdParcelas")),
                    Categoria.valueOf(req.queryParams("tipoDespesa")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return despesaService.getDespesa(Integer.parseInt(req.params("idFamilia")));
    }

}
