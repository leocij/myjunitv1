package com.lemelo.servicos;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import com.lemelo.utils.MyDates;

import java.util.Date;
import java.util.List;

public class NegociacaoService {

    public Negociacao venderProduto(Usuario usuario, List<Produto> produtos) throws ProdutoSemEstoqueException, DepositoException {

        if (usuario == null) {
            throw new DepositoException("Usuário vazio");
        }

        if (produtos == null || produtos.isEmpty()) {
            throw new DepositoException("Produto vazio");
        }

        for (Produto produto : produtos) {
            if(produto.getEstoque() == 0) {
                throw new ProdutoSemEstoqueException();
            }
        }

        Negociacao negociacao = new Negociacao();
        negociacao.setProdutos(produtos);
        negociacao.setUsuario(usuario);
        negociacao.setDataNegociacao(new Date());
        Double valorTotal = 0d;
        for (int i=0; i<produtos.size(); i++) {
            Produto produto = produtos.get(i);
            Double valorProduto = produto.getPrecoNegociacao();
            switch (i) {
                case 2: valorProduto = valorProduto * 0.75;
                    break;
                case 3: valorProduto = valorProduto * 0.50;
                    break;
                case 4: valorProduto = valorProduto * 0.25;
                    break;
                case 5: valorProduto = 0d;
                    break;
            }

            valorTotal += valorProduto;
        }
        negociacao.setValor(valorTotal);

        //Devolucao no dia seguinte
        Date dataDevolucao = new Date();
        dataDevolucao = MyDates.aumentaDias(dataDevolucao, 1);
        negociacao.setDataDevolucao(dataDevolucao);

        return negociacao;
    }
}
