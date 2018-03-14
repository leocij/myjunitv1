package com.lemelo.suites;

import com.lemelo.servicos.CalculadoraTest;
import com.lemelo.servicos.CalculoValorNegociacaoTest;
import com.lemelo.servicos.NegociacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoValorNegociacaoTest.class,
        NegociacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder
}
