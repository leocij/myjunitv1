package com.lemelo.servicos;

import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import com.lemelo.utils.MyDates;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.lemelo.utils.MyDates.getDataDiferente;
import static com.lemelo.utils.MyDates.isDataIgual;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
    public void deveVenderProduto() throws Exception {
        Assume.assumeFalse(MyDates.validarDiaSemana(new Date(), Calendar.SATURDAY));

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
    public void naoDeveVenderProdutoSemEstoque() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Usuario Um");
        List<Produto> produtos = Arrays.asList(new Produto("Produto Um", 0, 4.0));

        //acao
        service.venderProduto(usuario, produtos);
    }

    //Robusta
    @Test
    public void naoDeveVenderProdutoSemUsuario() throws ProdutoSemEstoqueException {
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
    public void naoDeveVenderProdutoSemProduto() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario Um");

        exception.expect(DepositoException.class);
        exception.expectMessage("Produto vazio");

        //acao
        service.venderProduto(usuario, null);
    }

    @Test
    public void devePagar75PctNoProduto3() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Produto> produtos = Arrays.asList(
                new Produto("Produto 1", 2, 4.0),
                new Produto("Produto 2", 2, 4.0),
                new Produto("Produto 3", 2, 4.0)
        );

        //acao
        Negociacao resultado = service.venderProduto(usuario, produtos);

        //verificacao
        //4.0+4.0+3.0=11.0
        assertThat(resultado.getValor(), is(11.0));
    }

    @Test
    public void devePagar50PctNoProduto4() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Produto> produtos = Arrays.asList(
                new Produto("Produto 1", 2, 4.0),
                new Produto("Produto 2", 2, 4.0),
                new Produto("Produto 3", 2, 4.0),
                new Produto("Produto 4", 2, 4.0)
        );

        //acao
        Negociacao resultado = service.venderProduto(usuario, produtos);

        //verificacao
        //4.0+4.0+3.0+2.0=13.0
        assertThat(resultado.getValor(), is(13.0));
    }

    @Test
    public void devePagar25PctNoProduto4() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Produto> produtos = Arrays.asList(
                new Produto("Produto 1", 2, 4.0),
                new Produto("Produto 2", 2, 4.0),
                new Produto("Produto 3", 2, 4.0),
                new Produto("Produto 4", 2, 4.0),
                new Produto("Produto 5", 2, 4.0)
        );

        //acao
        Negociacao resultado = service.venderProduto(usuario, produtos);

        //verificacao
        //4.0+4.0+3.0+2.0+1.0=14.0
        assertThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void devePagar0PctNoProduto4() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Produto> produtos = Arrays.asList(
                new Produto("Produto 1", 2, 4.0),
                new Produto("Produto 2", 2, 4.0),
                new Produto("Produto 3", 2, 4.0),
                new Produto("Produto 4", 2, 4.0),
                new Produto("Produto 5", 2, 4.0),
                new Produto("Produto 5", 2, 4.0)
        );

        //acao
        Negociacao resultado = service.venderProduto(usuario, produtos);

        //verificacao
        //4.0+4.0+3.0+2.0+1.0+0.0=14.0
        assertThat(resultado.getValor(), is(14.0));
    }

    @Test
    public void deveDevolverNaSegundaAoComprarNoSabado() throws ProdutoSemEstoqueException, DepositoException {
        Assume.assumeTrue(MyDates.validarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Produto> produtos = Arrays.asList(
                new Produto("Produto 1", 2, 5.0)
        );

        //acao
        Negociacao devolucao = service.venderProduto(usuario, produtos);

        //verificacao
        boolean ehSegunda = MyDates.validarDiaSemana(devolucao.getDataDevolucao(), Calendar.MONDAY);
        assertTrue(ehSegunda);
    }
}