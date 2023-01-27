package br.com.totvs.Services;

import br.com.totvs.Domain.Despesa;
import br.com.totvs.Domain.Familia;
import br.com.totvs.Repository.DespesaRepository;
import br.com.totvs.Repository.FamiliaRepository;

import java.util.List;

public class FamiliaService {

    private final DespesaRepository despesaRepository;
    private final FamiliaRepository familiaRepository;

    public FamiliaService(DespesaRepository despesaRepository, FamiliaRepository familiaRepository) {
        this.despesaRepository = despesaRepository;
        this.familiaRepository = familiaRepository;
    }

    public void addFamilia(String nome, double salario){
        familiaRepository.addFamilia(nome, salario);
    }

    public double getSalarioFamilia(int familiaId){
        return familiaRepository.getFamilia(familiaId).getSalario();
    }

    public void deleteFamilia(int familiaId){
        familiaRepository.deleteFamiliaById(familiaId);
    }

    public void updateFamilia(int familiaId, String nome, double salario){
        familiaRepository.updateFamilia(familiaId, nome, salario);
    }

    public Familia getFamilia(int familiaId){
        return familiaRepository.getFamilia(familiaId);
    }

    public List<Despesa> getAllDespesasFamilia(int familiaId){
        return despesaRepository.getDespesasByFamilia(familiaId);
    }

    public List<Despesa> getAllDespesasFamiliaByPage(int familiaId, int page, int size){
        return despesaRepository.getDepesasByFamiliaToPage(familiaId, page, size);
    }

}
