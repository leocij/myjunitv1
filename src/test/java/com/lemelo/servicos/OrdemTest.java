package com.lemelo.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdemTest {

    public static int contadorUm = 0;

    @Test
    public void t1_inicio() {
        contadorUm = 1;
    }

    @Test
    public void t2_verificar() {
        assertEquals(1, contadorUm);
    }
}
