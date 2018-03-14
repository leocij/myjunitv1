package com.lemelo.servicos;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static com.lemelo.utils.MyDates.getDataDiferente;
import static com.lemelo.utils.MyDates.isDataIgual;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NegociacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testeNegociacao() throws Exception {
        //cenario
        NegociacaoService service = new NegociacaoService();
        Usuario usuario = new Usuario("Usuario Um");
        Produto produto = new Produto("Produto Um", 2, 5.0);

        //acao
        Negociacao negociacao = service.venderProduto(usuario, produto);

        //verificacao
        error.checkThat( negociacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isDataIgual(negociacao.getDataNegociacao(), new Date()), is(true));
        error.checkThat(isDataIgual(negociacao.getDataDevolucao(), getDataDiferente(1)), is(true));

//        //acao
//        Negociacao negociacao = null;
//        try {
//            negociacao = service.venderProduto(usuario, produto);
//
//            //verificacao
//            error.checkThat( negociacao.getValor(), is(equalTo(5.0)));
//            error.checkThat(isDataIgual(negociacao.getDataNegociacao(), new Date()), is(true));
//            error.checkThat(isDataIgual(negociacao.getDataDevolucao(), getDataDiferente(1)), is(true));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail("Não deveria lançar exceção");
//        }
    }

    //Elegante
    @Test(expected = Exception.class)
    public void testNegociacao_produtoSemEstoque() throws Exception {
        //cenario
        NegociacaoService service = new NegociacaoService();
        Usuario usuario = new Usuario("Usuario Um");
        Produto produto = new Produto("Produto Um", 0, 5.0);

        //acao
        service.venderProduto(usuario, produto);
    }

    //Robusto
    @Test
    public void testNegociacao_produtoSemEstoque_2() {
        //cenario
        NegociacaoService service = new NegociacaoService();
        Usuario usuario = new Usuario("Usuario Um");
        Produto produto = new Produto("Produto Um", 0, 5.0);

        //acao
        try {
            service.venderProduto(usuario, produto);
            Assert.fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Produto sem estoque"));
        }
    }

    //Nova forma
    @Test
    public void testNegociacao_produtoSemEstoque_3() throws Exception {
        //cenario
        NegociacaoService service = new NegociacaoService();
        Usuario usuario = new Usuario("Usuario Um");
        Produto produto = new Produto("Produto Um", 0, 5.0);

        exception.expect(Exception.class);
        exception.expectMessage("Produto sem estoque");

        //acao
        service.venderProduto(usuario, produto);
    }
}
