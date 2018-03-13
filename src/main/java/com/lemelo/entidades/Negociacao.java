package com.lemelo.entidades;

import java.util.Date;

public class Negociacao {
    private Usuario usuario;
    private Produto produto;
    private Date dataNegociacao;
    private Date dataDevolucao;
    private Double valor;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getDataNegociacao() {
        return dataNegociacao;
    }

    public void setDataNegociacao(Date dataNegociacao) {
        this.dataNegociacao = dataNegociacao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
