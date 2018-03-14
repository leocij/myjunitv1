package com.lemelo.servicos;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.utils.MyDates;

import java.util.Date;

public class NegociacaoService {

    public Negociacao venderProduto(Usuario usuario, Produto produto) throws Exception {

        if(produto.getEstoque() == 0) {
            throw new Exception("Produto sem estoque");
        }

        Negociacao negociacao = new Negociacao();
        negociacao.setProduto(produto);
        negociacao.setUsuario(usuario);
        negociacao.setDataNegociacao(new Date());
        negociacao.setValor(produto.getPrecoNegociacao());

        //Devolucao no dia seguinte
        Date dataDevolucao = new Date();
        dataDevolucao = MyDates.aumentaDias(dataDevolucao, 1);
        negociacao.setDataDevolucao(dataDevolucao);

        return negociacao;
    }
}
