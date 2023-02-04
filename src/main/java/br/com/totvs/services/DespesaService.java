package br.com.totvs.services;

import br.com.totvs.domain.Categoria;
import br.com.totvs.domain.Despesa;
import br.com.totvs.domain.Familia;
import br.com.totvs.repository.DespesaRepository;
import br.com.totvs.repository.FamiliaRepository;

import java.sql.SQLException;
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

    public void addDespesaAFamilia(int idFamilia, String nomeDespesa, double valor, String dataFinal, boolean isPago, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa) throws SQLException {

        Despesa despesa = new Despesa();
        Familia familia = familiaRepository.getFamilia(idFamilia);

        if(familia == null){
            throw new RuntimeException("Familia não encontrada");
        }

        System.out.println("Familia: " + familia.getNome());

        despesa.setNome(nomeDespesa);
        despesa.setValor(valor);
        despesa.setDataInicio(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        despesa.setDataFinal(dataFinal);
        despesa.setPago(isPago);
        despesa.setParcelado(isParcelado);
        despesa.setQtdParcelas(qtdParcelas);
        despesa.setTipoDespesa(tipoDespesa);

        despesaRepository.addDespesa(despesa, familia.getId());

    }

    public Despesa getDespesa(int despesaId) throws SQLException {
        return despesaRepository.getDespesa(despesaId);
    }

    public double getValorDespesa(int despesaId) throws SQLException {
        return despesaRepository.getDespesa(despesaId).getValor();
    }

    public List<Despesa> getAllDespesas() throws SQLException {
        return despesaRepository.getAllDespesas();
    }

    public String deleteDespesa(int despesaId) throws SQLException {
        return despesaRepository.deleteDespesaById(despesaId);
    }

    public String deleteAllDespesas() throws SQLException {
        return despesaRepository.deleteAllDespesas();
    }

    public void updateDespesa(int id, int idFamilia, String nomeDespesa, double valor, String dataFinal, boolean isPago, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa) throws SQLException {
        Despesa despesa = despesaRepository.getDespesa(id);
        Familia familia = familiaRepository.getFamilia(idFamilia);

        if(familia == null){
            throw new RuntimeException("Familia não encontrada");
        }

        despesa.setNome(nomeDespesa);
        despesa.setValor(valor);
        despesa.setDataFinal(dataFinal);
        despesa.setPago(isPago);
        despesa.setParcelado(isParcelado);
        despesa.setQtdParcelas(qtdParcelas);
        despesa.setTipoDespesa(tipoDespesa);

        despesaRepository.updateDespesa(despesa, familia.getId());
    }

    public List<Despesa> getAllDespesasByPage(int page, int size) throws SQLException {
        return despesaRepository.getAllDespesasByPage(page, size);
    }


}
