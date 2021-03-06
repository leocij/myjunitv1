package com.lemelo.servicos;

import com.lemelo.builders.NegociacaoBuilder;
import com.lemelo.builders.ProdutoBuilder;
import com.lemelo.dados.NegociacaoDAO;
import com.lemelo.dados.NegociacaoDAOFake;
import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import com.lemelo.utils.MyDates;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.lemelo.builders.NegociacaoBuilder.umNegociacao;
import static com.lemelo.builders.ProdutoBuilder.umProduto;
import static com.lemelo.builders.ProdutoBuilder.umProdutoSemEstoque;
import static com.lemelo.builders.UsuarioBuilder.umUsuario;
import static com.lemelo.matchers.MatchersProprios.*;
import static com.lemelo.utils.MyDates.getDataDiferente;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class NegociacaoServiceTest {

    private NegociacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SPCService spc;
    private NegociacaoDAO dao;
    private EmailService email;

    @Before
    public void inicio() {
        service = new NegociacaoService();
        dao = Mockito.mock(NegociacaoDAO.class);
        service.setNegociacaoDAO(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSPCService(spc);
        email = Mockito.mock(EmailService.class);
        service.setEmailService(email);
    }

    @Test
    public void deveVenderProduto() throws Exception {
        Assume.assumeFalse(MyDates.validarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Produto> produtos = Arrays.asList(umProduto().comValor(5.0).agora());

        //acao
        Negociacao negociacao = service.venderProduto(usuario, produtos);

        //verificacao
        error.checkThat(negociacao.getValor(), is(equalTo(5.0)));
        error.checkThat(negociacao.getDataNegociacao(), ehHoje());
        error.checkThat(negociacao.getDataDevolucao(), ehHojeComDiferencaDias(1));
    }

    //Elegante
    @Test(expected = ProdutoSemEstoqueException.class)
    public void naoDeveVenderProdutoSemEstoque() throws Exception {
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Produto> produtos = Arrays.asList(umProdutoSemEstoque().agora());

        //acao
        service.venderProduto(usuario, produtos);
    }

    //Robusta
    @Test
    public void naoDeveVenderProdutoSemUsuario() throws ProdutoSemEstoqueException {
        //cenario
        List<Produto> produtos = Arrays.asList(umProduto().agora());

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
        Usuario usuario = umUsuario().agora();

        exception.expect(DepositoException.class);
        exception.expectMessage("Produto vazio");

        //acao
        service.venderProduto(usuario, null);
    }

    @Test
    public void deveDevolverNaSegundaAoComprarNoSabado() throws ProdutoSemEstoqueException, DepositoException {
        Assume.assumeTrue(MyDates.validarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Produto> produtos = Arrays.asList(umProduto().agora());

        //acao
        Negociacao devolucao = service.venderProduto(usuario, produtos);

        //verificacao
        assertThat(devolucao.getDataDevolucao(), caiNumaSegunda());
    }

    @Test
    public void naoDeveVenderProdutoParaNegativadoSPC() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = umUsuario().agora();
        //Usuario usuario2 = umUsuario().comNome("Usario 2").agora();
        List<Produto> produtos = Arrays.asList(umProduto().agora());

        when(spc.possuiNegativacao(usuario)).thenReturn(true);

        //acao
        try {
            service.venderProduto(usuario, produtos);

        //verificacao
            Assert.fail();
        }catch (DepositoException e) {
            Assert.assertThat(e.getMessage(), is("Usuário Negativado"));
        }


        Mockito.verify(spc).possuiNegativacao(usuario);
    }

    @Test
    public void deveEnviarEmailParaDevolucoesAtrasadas() {
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Negociacao> negociacaos = Arrays.asList(
                umNegociacao()
                        .comUsuario(usuario)
                        .comDataDevolucao(getDataDiferente(2))
                        .agora());

        when(dao.obterNegociacoesPendentes()).thenReturn(negociacaos);

        //acao
        service.notificarAtrasos();

        //verificacao
        Mockito.verify(email).notificarAtraso(usuario);
    }

}