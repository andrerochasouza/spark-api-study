package br.com.totvs.Services;

import br.com.totvs.Domain.Categoria;
import br.com.totvs.Domain.Despesa;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final FamiliaRepository familiaRepository;

    public DespesaService(DespesaRepository despesaRepository, FamiliaRepository familiaRepository) {
        this.despesaRepository = despesaRepository;
        this.familiaRepository = familiaRepository;
    }

    public void addDespesaAFamilia(int idFamilia, String nomeDespesa, double valor, String dataFinal, String dataPagamento, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa){

        Despesa despesa = new Despesa();
        Familia familia = familiaRepository.getFamilia(idFamilia);

        despesa.setNome(nomeDespesa);
        despesa.setValor(valor);
        despesa.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        despesa.setDataFinal(dataFinal);
        despesa.setDataPagamento(dataPagamento);
        despesa.setParcelado(isParcelado);
        despesa.setQtdParcelas(qtdParcelas);
        despesa.setTipoDespesa(tipoDespesa);

        despesaRepository.addDespesa(despesa, familia.getId());

    }

    public Despesa getDespesa(int despesaId){
        return despesaRepository.getDespesa(despesaId);
    }

    public double getValorDespesa(int despesaId){
        return despesaRepository.getDespesa(despesaId).getValor();
    }

    public List<Despesa> getAllDespesas(){
        return despesaRepository.getAllDespesas();
    }

    public String deleteDespesa(int despesaId){
        return despesaRepository.deleteDespesaById(despesaId);
    }

    public String deleteAllDespesas(){
        return despesaRepository.deleteAllDespesas();
    }

    public void updateDespesa(int id, int idFamilia, String nomeDespesa, double valor, String dataFinal, String dataPagamento, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa){
        Despesa despesa = despesaRepository.getDespesa(id);
        Familia familia = familiaRepository.getFamilia(idFamilia);

        despesa.setNome(nomeDespesa);
        despesa.setValor(valor);
        despesa.setDataFinal(dataFinal);
        despesa.setDataPagamento(dataPagamento);
        despesa.setParcelado(isParcelado);
        despesa.setQtdParcelas(qtdParcelas);
        despesa.setTipoDespesa(tipoDespesa);

        despesaRepository.updateDespesa(despesa, familia.getId());
    }

    public List<Despesa> getAllDespesasByPage(int page, int size){
        return despesaRepository.getAllDespesasByPage(page, size);
    }


}
