package com.lemelo.builders;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.utils.MyDates;

import java.util.Arrays;
import java.util.Date;

import static com.lemelo.builders.ProdutoBuilder.umProduto;
import static com.lemelo.builders.UsuarioBuilder.umUsuario;
import static com.lemelo.utils.MyDates.getDataDiferente;

public class NegociacaoBuilder {
    private Negociacao elemento;
    private NegociacaoBuilder(){}

    public static NegociacaoBuilder umNegociacao() {
        NegociacaoBuilder builder = new NegociacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    private static void inicializarDadosPadroes(NegociacaoBuilder builder) {
        builder.elemento = new Negociacao();
        Negociacao elemento = builder.elemento;

        elemento.setUsuario(umUsuario().agora());
        elemento.setProdutos(Arrays.asList(umProduto().agora()));
        elemento.setDataNegociacao(new Date());
        elemento.setDataDevolucao(getDataDiferente(1));
        elemento.setValor(4.0);
    }

    public NegociacaoBuilder comUsuario(Usuario param) {
        elemento.setUsuario(param);
        return this;
    }

    public NegociacaoBuilder comListaProdutos(Produto...params) {
        elemento.setProdutos(Arrays.asList(params));
        return this;
    }

    public NegociacaoBuilder comDataNegociacao(Date param) {
        elemento.setDataNegociacao(param);
        return this;
    }

    public NegociacaoBuilder comDataDevolucao(Date param) {
        elemento.setDataDevolucao(param);
        return this;
    }

    public NegociacaoBuilder comValor(Double param) {
        elemento.setValor(param);
        return this;
    }

    public Negociacao agora() {
        return elemento;
    }
}

