package br.com.totvs.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Familia {

    private int id = 0;
    private String nome = "";
    private double salario = 0;

    private double carteira = 0;

    private List<Despesa> despesas = new ArrayList<>();


    public Familia(int id, String nome, double salario, double carteira, List<Despesa> despesas) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.carteira = carteira;
        this.despesas = despesas;
    }

    public Familia() {
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getCarteira() {
        return carteira;
    }

    public void setCarteira(double carteira) {
        this.carteira = carteira;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Familia familia = (Familia) o;

        if (id != familia.id) return false;
        if (Double.compare(familia.salario, salario) != 0) return false;
        if (Double.compare(familia.carteira, carteira) != 0) return false;
        if (!Objects.equals(nome, familia.nome)) return false;
        return Objects.equals(despesas, familia.despesas);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        temp = Double.doubleToLongBits(salario);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(carteira);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (despesas != null ? despesas.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Familia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", salario=" + salario +
                ", carteira=" + carteira +
                ", despesas=" + despesas +
                '}';
    }
}
