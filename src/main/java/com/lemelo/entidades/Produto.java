package com.lemelo.entidades;

public class Produto {
    private String descricao;
    private Integer estoque;
    private Double precoNegociacao;

    public Produto() {
    }

    public Produto(String descricao, Integer estoque, Double precoNegociacao) {
        this.descricao = descricao;
        this.estoque = estoque;
        this.precoNegociacao = precoNegociacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Double getPrecoNegociacao() {
        return precoNegociacao;
    }

    public void setPrecoNegociacao(Double precoNegociacao) {
        this.precoNegociacao = precoNegociacao;
    }
}
