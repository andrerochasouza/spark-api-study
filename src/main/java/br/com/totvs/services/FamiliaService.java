package br.com.totvs.services;

import br.com.totvs.domain.Despesa;
import br.com.totvs.domain.Familia;
import br.com.totvs.repository.DespesaRepository;
import br.com.totvs.repository.FamiliaRepository;

import java.util.List;

public class FamiliaService {

    private final DespesaRepository despesaRepository;
    private final FamiliaRepository familiaRepository;

    public FamiliaService(DespesaRepository despesaRepository, FamiliaRepository familiaRepository) {
        this.despesaRepository = despesaRepository;
        this.familiaRepository = familiaRepository;
    }

    public void addFamilia(String nome, double salario, double carteira){
        familiaRepository.addFamilia(nome, salario, carteira);
    }

    public double getSalarioFamilia(int familiaId){
        return familiaRepository.getFamilia(familiaId).getSalario();
    }

    public String deleteFamilia(int familiaId){
        return familiaRepository.deleteFamiliaById(familiaId);
    }

    public String deleteAllFamilias(){
        return familiaRepository.deleteAllFamilias();
    }

    public void updateFamilia(int familiaId, String nome, double salario, double carteira){
        familiaRepository.updateFamilia(familiaId, nome, salario, carteira);
    }

    public void updateFamiliaNome(int familiaId, String nome){
        familiaRepository.updateFamiliaNome(familiaId, nome);
    }

    public void updateFamiliaSalario(int familiaId, double salario){
        familiaRepository.updateFamiliaSalario(familiaId, salario);
    }

    public void updateFamiliaCarteira(int familiaId, double carteira){
        familiaRepository.updateFamiliaCarteira(familiaId, carteira);
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

    public List<Familia> getAllFamilias(){
        return familiaRepository.getAllFamilias();
    }

    public List<Familia> getAllFamiliasByPage(int page, int size){
        return familiaRepository.getAllFamiliasToPage(page, size);
    }

}
