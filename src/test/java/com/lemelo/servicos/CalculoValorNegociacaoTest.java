package com.lemelo.servicos;

import com.lemelo.dados.NegociacaoDAO;
import com.lemelo.dados.NegociacaoDAOFake;
import com.lemelo.entidades.Negociacao;
import com.lemelo.entidades.Produto;
import com.lemelo.entidades.Usuario;
import com.lemelo.exceptions.DepositoException;
import com.lemelo.exceptions.ProdutoSemEstoqueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.lemelo.builders.ProdutoBuilder.umProduto;
import static com.lemelo.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CalculoValorNegociacaoTest {
    @Parameterized.Parameter
    public List<Produto> produtos;
    @Parameterized.Parameter(value = 1)
    public Double valorNegociacao;
    @Parameterized.Parameter(value = 2)
    public String cenario;

    private NegociacaoService service;

    private SPCService spc;

    @Before
    public void inicio() {
        service = new NegociacaoService();
        NegociacaoDAO dao = Mockito.mock(NegociacaoDAO.class);
        service.setNegociacaoDAO(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSPCService(spc);
    }

    private static Produto produto1 = umProduto().agora();
    private static Produto produto2 = umProduto().agora();
    private static Produto produto3 = umProduto().agora();
    private static Produto produto4 = umProduto().agora();
    private static Produto produto5 = umProduto().agora();
    private static Produto produto6 = umProduto().agora();
    private static Produto produto7 = umProduto().agora();

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList( new Object[][] {
                {Arrays.asList(produto1,produto2), 8.0, "2 Produtos: Sem Desconto"},
                {Arrays.asList(produto1,produto2,produto3), 11.0, "3 Produtos: 25%"},
                {Arrays.asList(produto1,produto2,produto3, produto4), 13.0, "4 Produtos: 50%"},
                {Arrays.asList(produto1,produto2,produto3, produto4, produto5), 14.0, "5 Produtos: 75%"},
                {Arrays.asList(produto1,produto2,produto3, produto4, produto5, produto6), 14.0, "6 Produtos: 100%"},
                {Arrays.asList(produto1,produto2,produto3, produto4, produto5, produto6, produto7), 18.0, "7 Produtos: Sem Desconto"}
        });
    }

    @Test
    public void deveCalcularValorDevolucaoConsiderandoDescontos() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = umUsuario().agora();

        //acao
        Negociacao resultado = service.venderProduto(usuario, produtos);

        //verificacao
        Assert.assertThat(resultado.getValor(), is(valorNegociacao));
    }

    @Test
    public void print() {
        System.out.println(valorNegociacao);
    }
}
