package com.lemelo.servicos;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
    @Before
    public void inicio() {
        service = new NegociacaoService();
    }

    private static Produto produto1 = new Produto("Produto 1", 2, 4.0);
    private static Produto produto2 = new Produto("Produto 2", 2, 4.0);
    private static Produto produto3 = new Produto("Produto 3", 2, 4.0);
    private static Produto produto4 = new Produto("Produto 4", 2, 4.0);
    private static Produto produto5 = new Produto("Produto 5", 2, 4.0);
    private static Produto produto6 = new Produto("Produto 6", 2, 4.0);

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList( new Object[][] {
                {Arrays.asList(produto1,produto2,produto3), 11.0, "3 Produtos: 25%"},
                {Arrays.asList(produto1,produto2,produto3, produto4), 13.0, "4 Produtos: 50%"},
                {Arrays.asList(produto1,produto2,produto3, produto4, produto5), 14.0, "5 Produtos: 75%"},
                {Arrays.asList(produto1,produto2,produto3, produto4, produto5, produto6), 14.0, "6 Produtos: 100%"}
        });
    }

    @Test
    public void deveCalcularValorDevolucaoConsiderandoDescontos() throws ProdutoSemEstoqueException, DepositoException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");

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
