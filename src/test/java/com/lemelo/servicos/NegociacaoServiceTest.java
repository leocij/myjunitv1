package com.lemelo.servicos;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.lemelo.utils.MyDates.getDataDiferente;
import static com.lemelo.utils.MyDates.isDataIgual;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class NegociacaoServiceTest {

    private NegociacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void inicio() {
        service = new NegociacaoService();
    }

    @Test
    public void testeNegociacao() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario Um");
        List<Produto> produtos = Arrays.asList(new Produto("Produto Um", 1, 5.0));

        //acao
        Negociacao negociacao = service.venderProduto(usuario, produtos);

        //verificacao
        error.checkThat(negociacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isDataIgual(negociacao.getDataNegociacao(), new Date()), is(true));
        error.checkThat(isDataIgual(negociacao.getDataDevolucao(), getDataDiferente(1)), is(true));
    }

    //Elegante
    @Test(expected = ProdutoSemEstoqueException.class)
    public void testNegociacao_produtoSemEstoque() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario Um");
        List<Produto> produtos = Arrays.asList(new Produto("Produto Um", 0, 4.0));

        //acao
        service.venderProduto(usuario, produtos);
    }

    //Robusta
    @Test
    public void testNegociacao_usuarioVazio() throws ProdutoSemEstoqueException {
        //cenario
        List<Produto> produtos = Arrays.asList(new Produto("Produto Um", 1, 5.0));

        //acao
        try {
            service.venderProduto(null, produtos);
            fail();
        } catch (DepositoException e) {
            assertThat(e.getMessage(), is("Usuário vazio"));
        }
    }

    //Forma Nova
    @Test
    public void testNegociacao_produtoVazio() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario Um");

        exception.expect(DepositoException.class);
        exception.expectMessage("Produto vazio");

        //acao
        service.venderProduto(usuario, null);
    }
}