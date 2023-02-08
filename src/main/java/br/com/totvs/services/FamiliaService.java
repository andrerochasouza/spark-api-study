package br.com.totvs.services;

import br.com.totvs.domain.Despesa;
import br.com.totvs.domain.Familia;
import br.com.totvs.repository.DespesaRepository;
import br.com.totvs.repository.FamiliaRepository;

import java.sql.SQLException;
import java.util.List;

public class FamiliaService {

    private final DespesaRepository despesaRepository;
    private final FamiliaRepository familiaRepository;

    public FamiliaService(DespesaRepository despesaRepository, FamiliaRepository familiaRepository) {
        this.despesaRepository = despesaRepository;
        this.familiaRepository = familiaRepository;
    }

    public void addFamilia(String nome, double salario, double carteira) throws SQLException {
        familiaRepository.addFamilia(nome, salario, carteira);
    }

    public Double getSalarioFamilia(int familiaId) throws SQLException {

        Familia familia = familiaRepository.getFamilia(familiaId);

        return familia.getSalario();
    }

    public String deleteFamilia(int familiaId) throws SQLException {
        return familiaRepository.deleteFamiliaById(familiaId);
    }

    public String deleteAllFamilias() throws SQLException {
        return familiaRepository.deleteAllFamilias();
    }

    public void updateFamilia(int familiaId, String nome, double salario, double carteira) throws SQLException {
        familiaRepository.updateFamilia(familiaId, nome, salario, carteira);
    }

    public void updateFamiliaNome(int familiaId, String nome) throws SQLException {
        familiaRepository.updateFamiliaNome(familiaId, nome);
    }

    public void updateFamiliaSalario(int familiaId, double salario) throws SQLException {
        familiaRepository.updateFamiliaSalario(familiaId, salario);
    }

    public void updateFamiliaCarteira(int familiaId, double carteira) throws SQLException {
        familiaRepository.updateFamiliaCarteira(familiaId, carteira);
    }

    public Familia getFamilia(int familiaId) throws SQLException {
        return familiaRepository.getFamilia(familiaId);
    }

    public List<Despesa> getAllDespesasFamilia(int familiaId) throws SQLException {

        familiaRepository.getFamilia(familiaId);

        return despesaRepository.getDespesasByFamilia(familiaId);
    }

    public List<Despesa> getAllDespesasFamiliaByPage(int familiaId, int page, int size) throws SQLException {
        return despesaRepository.getDepesasByFamiliaToPage(familiaId, page, size);
    }

    public List<Familia> getAllFamilias() throws SQLException {
        return familiaRepository.getAllFamilias();
    }

    public List<Familia> getAllFamiliasByPage(int page, int size) throws SQLException {
        return familiaRepository.getAllFamiliasToPage(page, size);
    }

    public List<Integer> getAllIDsFamilias() throws SQLException {
        return familiaRepository.getAllIDsFamilias();
    }

    public Integer getLastIDFamilia() throws SQLException {
        return familiaRepository.getLastIDFamilia();
    }

}
