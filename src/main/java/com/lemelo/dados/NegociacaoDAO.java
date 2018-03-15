package com.lemelo.dados;

import com.lemelo.entidades.Negociacao;

import java.util.List;

public interface NegociacaoDAO {
    public void salvar(Negociacao negociacao);

    List<Negociacao> obterNegociacoesPendentes();
}
