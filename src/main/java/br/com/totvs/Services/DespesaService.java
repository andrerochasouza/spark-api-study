package br.com.totvs.Services;

import br.com.totvs.Domain.Categoria;
import br.com.totvs.Domain.Despesa;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final FamiliaRepository familiaRepository;

    public DespesaService(DespesaRepository despesaRepository, FamiliaRepository familiaRepository) {
        this.despesaRepository = despesaRepository;
        this.familiaRepository = familiaRepository;
    }

    public void addDespesaAFamilia(int idFamilia, String nomeDespesa, double valor, Date dataFinal, Date dataPagamento, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa){

        Despesa despesa = new Despesa();
        Familia familia = familiaRepository.getFamilia(idFamilia);

        despesa.setNome(nomeDespesa);
        despesa.setValor(valor);
        despesa.setDataInicio(Date.from(new Date().toInstant()));
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

    public void deleteDespesa(int despesaId){
        despesaRepository.deleteDespesaById(despesaId);
    }

    public void updateDespesa(int idFamilia, int despesaId, String nomeDespesa, double valor, Date dataFinal, Date dataPagamento, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa){
        Despesa despesa = despesaRepository.getDespesa(despesaId);
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


}
