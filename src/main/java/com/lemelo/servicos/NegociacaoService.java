package com.lemelo.servicos;

import com.lemelo.dados.NegociacaoDAO;
import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import com.lemelo.utils.MyDates;
import com.sun.org.apache.xpath.internal.operations.Neg;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NegociacaoService {

    private NegociacaoDAO dao;
    private SPCService spcService;
    private EmailService emailService;

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

        if(spcService.possuiNegativacao(usuario)) {
            throw new DepositoException("Usuário Negativado");
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

        if(MyDates.validarDiaSemana(dataDevolucao, Calendar.SUNDAY)) {
            dataDevolucao = MyDates.aumentaDias(dataDevolucao, 1);
        }

        dao.salvar(negociacao);

        return negociacao;
    }

    public void notificarAtrasos() {
        List<Negociacao>  negociacaos = dao.obterNegociacoesPendentes();
        for(Negociacao negociacao : negociacaos) {
            emailService.notificarAtraso(negociacao.getUsuario());
        }
    }

    public void setNegociacaoDAO(NegociacaoDAO dao) {
        this.dao = dao;
    }

    public void setSPCService(SPCService spc) {
        spcService = spc;
    }

    public void setEmailService(EmailService email) {
        emailService = email;
    }
}
