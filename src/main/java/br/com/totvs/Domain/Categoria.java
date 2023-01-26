package br.com.totvs.Domain;

public enum Categoria {

    ALIMENTACAO("Alimentação"),
    ALUGUEL("Aluguel"),
    AGUA("Água"),
    LUZ("Luz"),
    INTERNET("Internet"),
    TELEFONE("Telefone"),
    TRANSPORTE("Transporte"),
    OUTROS("Outros");

    private String descricao;

    Categoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
