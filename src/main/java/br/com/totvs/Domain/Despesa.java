package br.com.totvs.Domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Despesa {

    private int id = 0;
    private String nome = "";
    private double valor = 0;
    private String dataInicio = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private String dataFinal = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private String dataPagamento = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private boolean isParcelado = false;
    private int qtdParcelas = 0;
    private Categoria tipoDespesa = Categoria.OUTROS;

    public Despesa(int id, String nome, double valor, String dataInicio, String dataFinal, String dataPagamento, boolean isParcelado, int qtdParcelas, Categoria tipoDespesa) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.dataPagamento = dataPagamento;
        this.isParcelado = isParcelado;
        this.qtdParcelas = qtdParcelas;
        this.tipoDespesa = tipoDespesa;
    }

    public Despesa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public boolean isParcelado() {
        return isParcelado;
    }

    public void setParcelado(boolean parcelado) {
        isParcelado = parcelado;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Categoria getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(Categoria tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Despesa despesa = (Despesa) o;

        if (id != despesa.id) return false;
        if (Double.compare(despesa.valor, valor) != 0) return false;
        if (isParcelado != despesa.isParcelado) return false;
        if (qtdParcelas != despesa.qtdParcelas) return false;
        if (!Objects.equals(nome, despesa.nome)) return false;
        if (!Objects.equals(dataInicio, despesa.dataInicio)) return false;
        if (!Objects.equals(dataFinal, despesa.dataFinal)) return false;
        if (!Objects.equals(dataPagamento, despesa.dataPagamento))
            return false;
        return tipoDespesa == despesa.tipoDespesa;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        temp = Double.doubleToLongBits(valor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dataInicio != null ? dataInicio.hashCode() : 0);
        result = 31 * result + (dataFinal != null ? dataFinal.hashCode() : 0);
        result = 31 * result + (dataPagamento != null ? dataPagamento.hashCode() : 0);
        result = 31 * result + (isParcelado ? 1 : 0);
        result = 31 * result + qtdParcelas;
        result = 31 * result + (tipoDespesa != null ? tipoDespesa.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", dataInicio='" + dataInicio + '\'' +
                ", dataFinal='" + dataFinal + '\'' +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", isParcelado=" + isParcelado +
                ", qtdParcelas=" + qtdParcelas +
                ", tipoDespesa=" + tipoDespesa +
                '}';
    }
}
