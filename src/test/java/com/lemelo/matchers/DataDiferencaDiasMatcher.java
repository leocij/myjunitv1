package com.lemelo.matchers;

import com.lemelo.utils.MyDates;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date> {

    private Integer qtdDias;

    public DataDiferencaDiasMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    public void describeTo(Description description) {

    }

    protected boolean matchesSafely(Date data) {
        return MyDates.isDataIgual(data, MyDates.getDataDiferente(qtdDias));
    }
}
