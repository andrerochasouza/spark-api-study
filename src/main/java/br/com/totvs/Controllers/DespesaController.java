package br.com.totvs.Controllers;

import br.com.totvs.Domain.Despesa;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;
import br.com.totvs.Services.DespesaService;
import spark.Request;
import spark.Response;

import java.util.List;

public class DespesaController {

    private static final DespesaService despesaService = new DespesaService(new DespesaRepository(), new FamiliaRepository());

    public static Despesa getDespesa(Request req, Response res){
        return despesaService.getDespesa(Integer.parseInt(req.params("id")));
    }

    public static List<Despesa> getAllDespesas(Request req, Response res){
        return despesaService.getAllDespesas();
    }
}
