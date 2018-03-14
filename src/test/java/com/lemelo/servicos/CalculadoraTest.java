package com.lemelo.servicos;

import com.lemelo.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

    private Calculadora calc;

    @Before
    public void inicio() {
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        //cenário
        int a = 5;
        int b = 3;

        //ação
        int resultado = calc.somar(a, b);

        //verificação
        Assert.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        //cenário
        int a = 8;
        int b = 5;

        //ação
        int resultado = calc.subtrair(a, b);

        //verificação
        Assert.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        //cenário
        int a = 6;
        int b = 3;

        //ação
        int resultado = calc.divide(a, b);

        //verificação
        Assert.assertEquals(2, resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        //cenário
        int a = 10;
        int b = 0;

        //ação
        calc.divide(a, b);

        //verificação
    }
}
