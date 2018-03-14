package com.lemelo.builders;

import com.lemelo.entidades.Produto;

public class ProdutoBuilder {
    private Produto produto;
    private ProdutoBuilder(){}

    public static ProdutoBuilder umProduto() {
        ProdutoBuilder builder = new ProdutoBuilder();
        builder.produto = new Produto();
        builder.produto.setEstoque(2);
        builder.produto.setDescricao("Produto 1");
        builder.produto.setPrecoNegociacao(4.0);
        return builder;
    }

    public static ProdutoBuilder umProdutoSemEstoque() {
        ProdutoBuilder builder = new ProdutoBuilder();
        builder.produto = new Produto();
        builder.produto.setEstoque(0);
        builder.produto.setDescricao("Produto 1");
        builder.produto.setPrecoNegociacao(4.0);
        return builder;
    }

    public ProdutoBuilder semEstoque() {
        produto.setEstoque(0);
        return this;
    }

    public ProdutoBuilder comValor(Double valor) {
        produto.setPrecoNegociacao(valor);
        return this;
    }

    public Produto agora() {
        return produto;
    }
}
